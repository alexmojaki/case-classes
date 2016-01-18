package com.github.alexmojaki.caseclasses.serialization;

import com.github.alexmojaki.caseclasses.AbstractResultBuilder;
import com.github.alexmojaki.caseclasses.CaseClass;
import com.github.alexmojaki.caseclasses.CaseClasses;
import com.github.alexmojaki.caseclasses.SimpleCaseClass;

import java.io.*;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.List;

/**
 * This class allows writing several {@code CaseClass}es to some form of output
 * very easily - often in a single line. Construct an instance,
 * then optionally chain various methods to configure the result,
 * and finally call {@link CSVWriter#write(Iterable)} (see for more details).
 * For example:
 * <pre>
 * {@code
 *     new CSVWriter("data/employees.csv")
 *          .separator('\t')
 *          .formatter(CSVWriter.Formatter.ALWAYS_QUOTE)
 *          .write(employees);
 * }
 * </pre>
 */
public class CSVWriter {

    private final EasyWriter out;
    private boolean autoClose = false;
    private char separator = ',';
    private char quote = '"';
    private char escape = '\\';
    private String nullRepresentation = "";
    private Formatter formatter = Formatter.QUOTE_IF_NEEDED;
    private boolean includeHeader = true;
    private CSVResultBuilder builder = new CSVResultBuilder();
    private List<String> names;
    private int stringBuilderCapacity = 16;

    /**
     * Set the character encoding for the output. Has no effect if this instance
     * was constructed using {@link CSVWriter#CSVWriter(Writer)}. If this method is
     * not called and a different constructor is used, the system default character
     * set is used.
     *
     * @return this instance for chaining
     */
    public CSVWriter charset(Charset charset) {
        out.charset(charset);
        return this;
    }

    /**
     * Set the character that separates fields in the output. By default this is
     * a comma (',').
     *
     * @return this instance for chaining
     */
    public CSVWriter separator(char separator) {
        this.separator = separator;
        return this;
    }

    /**
     * Set the character that surrounds fields in the output to disambiguate
     * where fields start and end. This is not always used. The default is a double
     * quote ('"').
     *
     * @return this instance for chaining
     */
    public CSVWriter quote(char quote) {
        this.quote = quote;
        return this;
    }

    /**
     * Set the character that is inserted before special characters (the separator,
     * quote, and the escape character itself) to indicate that the special character
     * should be interpreted as a literal character. The default is a backslash ('\').
     *
     * @return this instance for chaining
     */
    public CSVWriter escape(char escape) {
        this.escape = escape;
        return this;
    }

    /**
     * Set the string that is written to output when a null value is encountered.
     * The default is an empty string ("").
     *
     * @return this instance for chaining
     */
    public CSVWriter nullRepresentation(String nullRepresentation) {
        this.nullRepresentation = nullRepresentation;
        return this;
    }

    /**
     * Set the {@link Formatter} used by the writer. This determines the syntax
     * of the resulting CSV and how parsers will disambiguate where the fields
     * start and end and how special characters should be interpreted.
     * The default is {@link Formatter#QUOTE_IF_NEEDED}.
     *
     * @return this instance for chaining
     */
    public CSVWriter formatter(Formatter formatter) {
        this.formatter = formatter;
        return this;
    }

    /**
     * By default the names of the components of the written {@code CaseClass}es are
     * used to write a header as the first line of the CSV. Call this method to
     * prevent that.
     *
     * @return this instance for chaining
     */
    public CSVWriter excludeHeader() {
        includeHeader = false;
        return this;
    }

    /**
     * Ensures that this instance is always closed at
     * the end of {@link CSVWriter#write(Iterable)}. This method is automatically called
     * by {@link CSVWriter#CSVWriter(File)} and {@link CSVWriter#CSVWriter(String)}.
     *
     * @return this instance for chaining
     * @see CSVWriter#close()
     */
    public CSVWriter autoClose() {
        autoClose = true;
        return this;
    }

    /**
     * Close the underlying {@code Writer}.
     *
     * @see CSVWriter#autoClose()
     */
    public void close() {
        out.close();
    }

    /**
     * Construct a {@code CSVWriter} that writes directly
     * to the given {@code Writer}.
     */
    public CSVWriter(Writer writer) {
        out = new EasyWriter(writer);
    }

    /**
     * Construct a {@code CSVWriter} that writes to
     * to the given {@code OutputStream}, using either the system
     * default {@code Charset} or the one specified by the
     * {@link CSVWriter#charset(Charset)} method.
     */
    public CSVWriter(OutputStream stream) {
        out = new EasyWriter(stream);
    }

    /**
     * Construct a {@code CSVWriter} that writes to
     * to the given {@code File}, using either the system
     * default {@code Charset} or the one specified by the
     * {@link CSVWriter#charset(Charset)} method.
     * Automatically closes itself after writing.
     */
    public CSVWriter(File file) {
        out = new EasyWriter(file);
        autoClose();
    }

    /**
     * Construct a {@code CSVWriter} that writes to
     * to a file at the given path, using either the system
     * default {@code Charset} or the one specified by the
     * {@link CSVWriter#charset(Charset)} method.
     * Automatically closes itself after writing.
     */
    public CSVWriter(String filePath) {
        out = new EasyWriter(filePath);
        autoClose();
    }

    /**
     * Write the given {@code CaseClass}es to the output specified in the constructor.
     * The process is as follows:
     * <ol>
     *     <li>If {@link CSVWriter#excludeHeader()} was not called, write a header
     *     based on the names in the first object. The header is formatted in the same way as the other rows.</li>
     *     <li>For each {@code CaseClass} in the argument, write a row consisting of the
     *     values in the {@code CaseClass}, separated by the {@link CSVWriter#separator(char)}
     *     character if given (comma by default), and ending in a newline character.
     *     Individual values are processed as follows:
     *     <ol>
     *         <li>If the value is null, convert it to the {@link CSVWriter#nullRepresentation(String)}
     *         (empty string by default). Otherwise convert it to a string using {@code toString()}.</li>
     *         <li>Escape any instances of the {@link CSVWriter#escape(char)} character (default '\').</li>
     *         <li>Format the value: see {@link CSVWriter#formatter(Formatter)} and {@link CSVWriter#quote(char)}.</li>
     *     </ol></li>
     *     <li>If {@link CSVWriter#autoClose()} was called (some constructors do this, or you can yourself),
     *     call {@link CSVWriter#close()}. This step will happen even if an exception is thrown during writing.</li>
     * </ol>
     *
     * A special case is that nothing will be written (i.e. the first two steps will be skipped) if the argument is
     * empty.
     *
     * @throws IllegalArgumentException if any of the {@code CaseClass} objects have no components,
     * or if any of the objects have a different sequence of names.
     * @throws IllegalStateException if an {@code IOException} is thrown during writing or closing.
     */
    @SuppressWarnings("ThrowFromFinallyBlock")
    public void write(Iterable<? extends CaseClass> objects) {
        RuntimeException writeException = null;
        try {
            out.ensureReady();
            Iterator<? extends CaseClass> objectIterator = objects.iterator();
            if (!objectIterator.hasNext()) {
                return;
            }
            CaseClass object = objectIterator.next();
            names = CaseClasses.getNameList(object);
            if (names.isEmpty()) {
                throw new IllegalArgumentException("First object has no components");
            }
            if (includeHeader) {
                writeHeader();
                out.write('\n');
            }
            while (true) {
                object.buildResult(builder);
                out.write('\n');
                if (!objectIterator.hasNext()) {
                    break;
                }
                object = objectIterator.next();
            }
        } catch (RuntimeException e) {
            writeException = e;
        } finally {
            if (autoClose) {
                try {
                    close();
                } catch (RuntimeException closeException) {
                    if (writeException != null) {
                        closeException.addSuppressed(writeException);
                    }
                    throw closeException;
                }
            }
            if (writeException != null) {
                throw writeException;
            }
        }
    }

    private void writeHeader() {
        SimpleCaseClass namesAsValues = new SimpleCaseClass();
        for (String name : names) {
            namesAsValues.add(name, name);
        }
        namesAsValues.buildResult(builder);
    }

    private class CSVResultBuilder extends AbstractResultBuilder {

        int column = 0;

        @Override
        protected void simpleAdd(String name, Object value) {
            ensureNameMatches(name);
            writeSeparatorIfNeeded();
            writeValue(value);
            incColumn();
        }

        private void ensureNameMatches(String name) {
            String expectedName = names.get(column);
            if (!name.equals(expectedName)) {
                throw new IllegalArgumentException("Found the name " + name + ", expected " + expectedName +
                        ". Ensure that every value has the same sequence of names.");
            }
        }

        private void writeSeparatorIfNeeded() {
            if (column > 0) {
                out.write(separator);
            }
        }

        private void writeValue(Object value) {
            StringBuilder stringBuilder = new StringBuilder(stringBuilderCapacity);
            stringBuilder.append(value == null ? nullRepresentation : value.toString());
            Formatter.Utils.escape(stringBuilder, escape, escape);
            formatter.format(stringBuilder, separator, quote, escape);
            out.write(stringBuilder.toString());
            stringBuilderCapacity = Math.max(stringBuilderCapacity, stringBuilder.length());
        }

        private void incColumn() {
            column = (column + 1) % names.size();
        }
    }

    /**
     * This interface determines the syntax of the CSV used and how parsers
     * will disambiguate where the fields start and end and how special characters should be interpreted.
     * Note that escape characters are already escaped before this is applied.
     * Built-in formatters are provided as constants in this interface.
     * Utilities for writing formatters are provided in {@link Utils}.
     */
    public interface Formatter {

        /**
         * Modify the given {@code StringBuilder}, which will be written to the CSV,
         * based on the given special characters.
         */
        void format(StringBuilder value, char separator, char quote, char escape);

        enum Utils {
            ;

            /**
             * Insert {@code escapeChar} before all instances of {@code toEscape}.
             */
            public static void escape(StringBuilder value, char toEscape, char escapeChar) {
                int length = value.length();
                for (int i = 0; i < length; i++) {
                    if (value.charAt(i) == toEscape) {
                        value.insert(i, escapeChar);
                        i++;
                        length++;
                    }
                }
            }

            /**
             * Place the {@code quote} character on both sides of {@code value}.
             */
            public static void quote(StringBuilder value, char quote) {
                value.insert(0, quote);
                value.append(quote);
            }

        }

        /**
         * Escape separators in the value. The quote character plays no role in this format.
         */
        Formatter ESCAPE_SEPARATORS = new Formatter() {
            @Override
            public void format(StringBuilder value, char separator, char quote, char escape) {
                Utils.escape(value, separator, escape);
            }
        };

        /**
         * Always enclose the value in quotes. Escape any quotes within the value.
         */
        Formatter ALWAYS_QUOTE = new Formatter() {
            @Override
            public void format(StringBuilder value, char separator, char quote, char escape) {
                Utils.escape(value, quote, escape);
                Utils.quote(value, quote);
            }

        };

        /**
         * Enclose the value in quotes if it contains any separators.
         * Always escape any quotes within the value.
         */
        Formatter QUOTE_IF_NEEDED = new Formatter() {
            @Override
            public void format(StringBuilder value, char separator, char quote, char escape) {
                Utils.escape(value, quote, escape);
                if (value.indexOf(String.valueOf(separator)) != -1) {
                    Utils.quote(value, quote);
                }
            }
        };

    }

}
