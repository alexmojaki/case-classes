package com.github.alexmojaki.caseclasses.serialization;

import com.github.alexmojaki.caseclasses.AbstractResultBuilder;
import com.github.alexmojaki.caseclasses.CaseClass;
import com.github.alexmojaki.caseclasses.CaseClasses;
import com.github.alexmojaki.caseclasses.SimpleCaseClass;

import java.io.*;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.List;

public class CSVWriter {

    private final EasyWriter out;
    private boolean autoClose = true;
    private char separator = ',';
    private char quote = '"';
    private char escape = '\\';
    private String nullRepresentation = "";
    private Formatter formatter = Formatter.QUOTE_IF_NEEDED;
    private boolean includeHeader = true;
    private CSVResultBuilder builder = new CSVResultBuilder();
    private List<String> names;
    private int stringBuilderCapacity = 16;

    public CSVWriter charset(Charset charset) {
        out.charset(charset);
        return this;
    }

    public CSVWriter separator(char separator) {
        this.separator = separator;
        return this;
    }

    public CSVWriter quote(char quote) {
        this.quote = quote;
        return this;
    }

    public CSVWriter escape(char escape) {
        this.escape = escape;
        return this;
    }

    public CSVWriter nullRepresentation(String nullRepresentation) {
        this.nullRepresentation = nullRepresentation;
        return this;
    }

    public CSVWriter formatter(Formatter formatter) {
        this.formatter = formatter;
        return this;
    }

    public CSVWriter excludeHeader() {
        includeHeader = false;
        return this;
    }

    public CSVWriter leaveOpen() {
        autoClose = false;
        return this;
    }

    public void close() {
        out.close();
    }

    public CSVWriter(Writer writer) {
        out = new EasyWriter(writer);
    }

    public CSVWriter(OutputStream stream) {
        out = new EasyWriter(stream);
    }

    public CSVWriter(File file) {
        out = new EasyWriter(file);
    }

    public CSVWriter(String filename) {
        out = new EasyWriter(filename);
    }

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
                throw new IllegalArgumentException("First object has no getValuesList");
            }
            if (includeHeader) {
                writeHeader(object);
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
                } catch (IllegalStateException closeException) {
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

    private void writeHeader(CaseClass caseClass) {
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

    public interface Formatter {
        void format(StringBuilder value, char separator, char quote, char escape);

        enum Utils {
            ;

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

            public static void quote(StringBuilder value, char quote) {
                value.insert(0, quote);
                value.append(quote);
            }

        }

        Formatter ESCAPE_SEPARATORS = new Formatter() {
            @Override
            public void format(StringBuilder value, char separator, char quote, char escape) {
                Utils.escape(value, separator, escape);
            }
        };

        Formatter ALWAYS_QUOTE = new Formatter() {
            @Override
            public void format(StringBuilder value, char separator, char quote, char escape) {
                Utils.escape(value, quote, escape);
                Utils.quote(value, quote);
            }

        };

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
