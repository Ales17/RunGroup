package com.rungroup.web.storage;

import java.util.UUID;

import static org.apache.commons.io.FilenameUtils.getExtension;

public class FileUtil {

    public static final String ROOT_LOCATION = "/files/";

    public static String newFileName(String originalName) {
        String newBaseName = UUID.randomUUID().toString();
        String extension = getExtension(originalName);
        return String.format("%s.%s", newBaseName, extension);
    }
}
