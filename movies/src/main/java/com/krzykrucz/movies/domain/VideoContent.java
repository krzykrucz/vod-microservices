package com.krzykrucz.movies.domain;

import com.google.common.io.ByteSource;
import com.google.common.io.Files;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class VideoContent {

    private final byte[] byteContent;

    private VideoContent(byte[] byteContent) {
        this.byteContent = byteContent;
    }

    public static VideoContent fromStream(InputStream streamContentFromStore) {
        return null;
    }

    public static VideoContent fromFile(File file) throws IOException {
        final byte[] byteArray = Files.toByteArray(file);
        return new VideoContent(byteArray);
    }

    public InputStream toStream() throws IOException {
        return ByteSource.wrap(byteContent)
                .openStream();
    }
}
