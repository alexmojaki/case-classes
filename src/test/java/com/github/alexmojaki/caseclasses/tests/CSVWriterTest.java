package com.github.alexmojaki.caseclasses.tests;

import com.github.alexmojaki.caseclasses.CaseClass;
import com.github.alexmojaki.caseclasses.ResultBuilder;
import com.github.alexmojaki.caseclasses.SimpleCaseClass;
import com.github.alexmojaki.caseclasses.serialization.CSVWriter;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.fail;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class CSVWriterTest {

    @Rule
    public ExpectedException exception = ExpectedException.none();

    private char separator = ',';
    private char quote = '"';
    private char escape = '\\';
    private String nullRepresentation = "";
    private CSVWriter.Formatter formatter = CSVWriter.Formatter.QUOTE_IF_NEEDED;

    private CSVWriter configureWriter(CSVWriter csvWriter) {
        return csvWriter.separator(separator).quote(quote).escape(escape).nullRepresentation(nullRepresentation).formatter(formatter).autoClose();
    }

    private static CaseClass row(Object a, Object b, Object c) {
        return new SimpleCaseClass("a", a, "b", b, "c", c);
    }

    private void assertCsvThorough(String name, CaseClass... rows) {
        List<CaseClass> rowList = Arrays.asList(rows);
        String path = "src/test/resources/goldenfiles/csv/" + name + ".csv";
        File goldenFile = new File(path);
        Charset utf8 = StandardCharsets.UTF_8;
        if (!goldenFile.exists()) {
            configureWriter(new CSVWriter(goldenFile)).charset(utf8).write(rowList);
        }
        String testPath = path + ".test";
        File testFile = new File(testPath);
        configureWriter(new CSVWriter(testFile)).charset(utf8).write(rowList);
        assertFilesEqual(goldenFile, testFile);
        //noinspection ResultOfMethodCallIgnored
        testFile.delete();

        configureWriter(new CSVWriter(testPath)).charset(utf8).write(rowList);
        testFile = new File(testPath);
        assertFilesEqual(goldenFile, testFile);
        //noinspection ResultOfMethodCallIgnored
        testFile.delete();

        StringWriter stringWriter = new StringWriter();
        configureWriter(new CSVWriter(stringWriter)).write(rowList);
        String expectedString = fileToString(goldenFile, utf8);
        assertEquals(expectedString, stringWriter.toString());

        for (Charset charset : Arrays.asList(utf8, StandardCharsets.ISO_8859_1)) {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            configureWriter(new CSVWriter(outputStream)).charset(charset).write(rowList);
            assertArrayEquals(expectedString.getBytes(charset), outputStream.toByteArray());
        }

        // No charset specified, i.e. use default
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        configureWriter(new CSVWriter(outputStream)).write(rowList);
        assertArrayEquals(expectedString.getBytes(), outputStream.toByteArray());

    }

    private void assertFilesEqual(File file1, File file2) {
        assertArrayEquals(fileToBytes(file1), fileToBytes(file2));
    }

    private byte[] fileToBytes(File file) {
        try {
            return Files.readAllBytes(file.toPath());
        } catch (IOException e) {
            fail();
            throw new AssertionError();
        }
    }

    private String fileToString(File file, Charset charset) {
        return new String(fileToBytes(file), charset);
    }

    @Test
    public void quoteIfNeeded() {
        assertCsvThorough("quoteIfNeeded/basic",
                row(1, 2, 3),
                row(4, 5, 6));
        assertCsvThorough("quoteIfNeeded/separatorsQuoted",
                row("a", "b,b,", ","),
                row("null", null, "e"),
                row(",", "\"\\,\"", "\\\\,"));
        assertCsvThorough("quoteIfNeeded/quotesEscaped",
                row("\"", "\"\"", "\"a\""),
                row("a\"", "\"a", "a\"a\"a"),
                row("a\"\"a", "\"\"\"", "\\\\\""));
    }

    @Test
    public void alwaysQuote() {
        formatter = CSVWriter.Formatter.ALWAYS_QUOTE;
        assertCsvThorough("alwaysQuote",
                row(1, 2, 3),
                row(4, 5, 6),
                row("a", "b,b,", ","),
                row("null", null, "e"),
                row(",", "\"\\,\"", "\\\\,"),
                row("\"", "\"\"", "\"a\""),
                row("a\"", "\"a", "a\"a\"a"),
                row("a\"\"a", "\"\"\"", "\\\\\""));
    }

    @Test
    public void escapeSeparators() {
        formatter = CSVWriter.Formatter.ESCAPE_SEPARATORS;
        assertCsvThorough("escapeSeparators",
                row(1, 2, 3),
                row(4, 5, 6),
                row("a", "b,b,", ","),
                row("null", null, "e"),
                row(",", "\"\\,\"", "\\\\,"),
                row("\"", "\"\"", "\"a\""),
                row("a\"", "\"a", "a\"a\"a"),
                row(",,,,,", "\\,\\,\\,", "\"987,654\","));
    }

    @Test
    public void configureCharsAndNull() {
        separator = '|';
        quote = '\'';
        escape = '/';
        nullRepresentation = "NULL";
        assertCsvThorough("configureCharsAndNull/basic",
                row(1, 2, 3),
                row(4, 5, 6));
        assertCsvThorough("configureCharsAndNull/separatorsQuoted",
                row("a", "b|b,", "|"),
                row("null", null, "e"),
                row("|", "'/|'", "//|"));
        assertCsvThorough("configureCharsAndNull/quotesEscaped",
                row("'", "''", "'a'"),
                row("a'", "'a", "a'a'a"),
                row("a''a", "'''", "//'"));
    }

    @Test
    public void unicode() {
        assertCsvThorough("unicode",
                row("¡™£¢∞§¶•ªº", "œ∑´†¥¨ˆπ", "åß∂ƒ©˙∆˚¬"));
    }

    @Test
    public void empty() {
        assertCsvThorough("empty");
    }

    private static class TestCloseWriter extends Writer {

        private boolean closed = false;

        @Override
        public void write(char[] cbuf, int off, int len) throws IOException {
        }

        @Override
        public void flush() throws IOException {
        }

        @Override
        public void close() throws IOException {
            closed = true;
        }
    }

    @Test
    public void autoCloseNormally() {
        TestCloseWriter writer = new TestCloseWriter();
        assertFalse(writer.closed);
        new CSVWriter(writer).autoClose().write(Arrays.asList(row(1, 2, 3), row(4, 5, 6)));
        assertTrue(writer.closed);
    }

    @Test
    public void autoCloseWithError() {
        TestCloseWriter writer = new TestCloseWriter();
        assertFalse(writer.closed);
        try {
            new CSVWriter(writer).autoClose().write(Arrays.asList(row(1, 2, 3), new CaseClass() {
                @Override
                public void buildResult(ResultBuilder builder) {
                    throw new Error();
                }
            }));
        } catch (Error e) {
            assertTrue(writer.closed);
            return;
        }
        fail();
    }

    @Test
    public void leaveOpen() {
        TestCloseWriter writer = new TestCloseWriter();
        assertFalse(writer.closed);
        new CSVWriter(writer).write(Arrays.asList(row(1, 2, 3), row(4, 5, 6)));
        assertFalse(writer.closed);
    }

    @Test
    public void excludeHeader() {
        StringWriter writer = new StringWriter();
        new CSVWriter(writer).excludeHeader().write(Arrays.asList(row(1, 2, 3), row(4, 5, 6)));
        assertEquals("1,2,3\n4,5,6\n", writer.toString());
    }

    @Test
    public void emptyRow() {
        exception.expect(IllegalArgumentException.class);
        new CSVWriter(new StringWriter()).write(Collections.singletonList(new SimpleCaseClass()));
    }

    @Test
    public void mismatchedRows() {
        exception.expect(IllegalArgumentException.class);
        new CSVWriter(new StringWriter()).write(Arrays.asList(new SimpleCaseClass("a", 1, "b", 2), new SimpleCaseClass("a", 1, "c", 2)));
    }

    private static class TestExceptionWriter extends Writer {

        @Override
        public void write(char[] cbuf, int off, int len) throws IOException {
            throw new IOException("write");
        }

        @Override
        public void flush() throws IOException {
        }

        @Override
        public void close() throws IOException {
            throw new IOException("close");
        }
    }

    @Test
    public void fileNotFoundReThrown() {
        exception.expect(IllegalStateException.class);
        new CSVWriter("q/w/e/r/t/y/uiop.csv").write(Arrays.asList(row(1, 2, 3), row(4, 5, 6)));
    }

    @Test
    public void writeAndCloseExceptions() {
        try {
            new CSVWriter(new TestExceptionWriter()).autoClose().write(Arrays.asList(row(1, 2, 3), row(4, 5, 6)));
        } catch (IllegalStateException e) {
            assertEquals("close", e.getCause().getMessage());
            assertEquals("java.io.IOException: write", e.getSuppressed()[0].getMessage());
            return;
        }
        fail();
    }

    @Test
    public void closeExceptionReThrown() {
        try {
            new CSVWriter(new TestExceptionWriter()).close();
        } catch (IllegalStateException e) {
            assertEquals("close", e.getCause().getMessage());
            return;
        }
        fail();
    }

    @Test
    public void writeExceptionReThrown() {
        try {
            CSVWriter csvWriter = new CSVWriter(new TestExceptionWriter());
            csvWriter.write(Arrays.asList(row(1, 2, 3), row(4, 5, 6)));
        } catch (IllegalStateException e) {
            assertEquals("write", e.getCause().getMessage());
            return;
        }
        fail();
    }



}