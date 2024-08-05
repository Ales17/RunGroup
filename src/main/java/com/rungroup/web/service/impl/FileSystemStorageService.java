package com.rungroup.web.service.impl;

import com.rungroup.web.service.StorageService;
import com.rungroup.web.storage.StorageException;
import com.rungroup.web.storage.StorageFileNotFoundException;
import com.rungroup.web.storage.StorageProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Map;
import java.util.stream.Stream;

import static com.rungroup.web.storage.FileUtil.newFileName;
import static org.apache.commons.io.FilenameUtils.getExtension;

@Service
public class FileSystemStorageService implements StorageService {

    private final Path rootLocation;
    private final Map<String, String> allowedFileTypes = Map.of("jpg", "image/jpeg", "jpeg", "image/jpeg", "png", "image/png");

    @Autowired
    public FileSystemStorageService(StorageProperties properties) {

        if (properties.getLocation().trim().isEmpty()) {
            throw new StorageException("File upload location can not be Empty.");
        }

        this.rootLocation = Paths.get(properties.getLocation());
    }


    @Override
    public String store(MultipartFile file) {
        try {
            // EMPTY CHECK
            if (file.isEmpty()) {
                throw new StorageException("Failed to store empty file.");
            }

            // NOT STORE OUTSIDE DIR CHECK
            String originalFilename = file.getOriginalFilename();
            String newFileName = newFileName(originalFilename);
            Path destinationFile = this.rootLocation.resolve(
                            Paths.get(newFileName))
                    .normalize().toAbsolutePath();

            if (!destinationFile.getParent().equals(this.rootLocation.toAbsolutePath())) {
                // This is a security check
                throw new StorageException(
                        "Cannot store file outside current directory.");
            }
            // EXTENSION CHECK
            String extension = getExtension(originalFilename);
            if (!allowedFileTypes.containsKey(extension)) {
                String ex = String.format("The %s extension is not allowed.", extension);
                throw new StorageException(ex);
            }
            // Allowed MIME for extension, i.e. image/jpeg for jpg | image/png for png
            String allowedMimeType = allowedFileTypes.get(extension);

            InputStream is = new BufferedInputStream(file.getInputStream());
            String guessedMimeType = URLConnection.guessContentTypeFromStream(is);
            // MIME CHECK
            if (!allowedMimeType.equals(guessedMimeType)) {
                String ex = String.format("The %s does not allow this content type.", extension);
                throw new StorageException(ex);
            }
            // SAVING
            try (InputStream inputStream = file.getInputStream()) {
                Files.copy(inputStream, destinationFile,
                        StandardCopyOption.REPLACE_EXISTING);
                return newFileName;
            }
        } catch (IOException e) {
            throw new StorageException("Failed to store file.", e);
        }
    }

    @Override
    public Stream<Path> loadAll() {
        try {
            return Files.walk(this.rootLocation, 1)
                    .filter(path -> !path.equals(this.rootLocation))
                    .map(this.rootLocation::relativize);
        } catch (IOException e) {
            throw new StorageException("Failed to read stored files", e);
        }

    }

    @Override
    public Path load(String filename) {
        return rootLocation.resolve(filename);
    }

    @Override
    public Resource loadAsResource(String filename) {
        try {
            Path file = load(filename);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new StorageFileNotFoundException(
                        "Could not read file: " + filename);

            }
        } catch (MalformedURLException e) {
            throw new StorageFileNotFoundException("Could not read file: " + filename, e);
        }
    }

    @Override
    public void deleteAll() {
        FileSystemUtils.deleteRecursively(rootLocation.toFile());
    }

    @Override
    public void init() {
        try {
            Files.createDirectories(rootLocation);
        } catch (IOException e) {
            throw new StorageException("Could not initialize storage", e);
        }
    }
}