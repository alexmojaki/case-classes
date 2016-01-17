package com.github.alexmojaki.caseclasses.serialization;

import java.io.*;
import java.nio.charset.Charset;

class EasyWriter {

    private Writer writer;
    private OutputStream stream;
    private Charset charset;

    void charset(Charset charset) {
        this.charset = charset;
    }

    EasyWriter(Writer writer) {
        this.writer = writer;
    }

    EasyWriter(OutputStream stream) {
        this.stream = stream;
    }

    EasyWriter(File file) {
        try {
            stream = new BufferedOutputStream(new FileOutputStream(file));
        } catch (FileNotFoundException e) {
            throw new IllegalStateException(e);
        }
    }

    EasyWriter(String filename) {
        this(new File(filename));
    }

    void write(String string) {
        try {
            writer.write(string);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    void write(char c) {
        write(String.valueOf(c));
    }

    void close() {
        try {
            writer.close();
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    void ensureReady() {
        if (writer == null) {
            writer = charset == null ?
                    new OutputStreamWriter(stream) :
                    new OutputStreamWriter(stream, charset);
        }
    }
}
