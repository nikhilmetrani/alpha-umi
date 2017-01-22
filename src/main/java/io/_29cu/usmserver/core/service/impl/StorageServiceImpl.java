package io._29cu.usmserver.core.service.impl;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import io._29cu.usmserver.common.exceptions.StorageException;
import io._29cu.usmserver.common.exceptions.StorageFileNotFoundException;
import io._29cu.usmserver.common.utilities.StorageProperties;
import io._29cu.usmserver.core.service.StorageService;

//@ComponentScan
@Service
public class StorageServiceImpl implements StorageService {

    private final Path rootLocation;
    private final Path profileImageLocation;
    private final Path appLogoLocation;
    private final Path setupsLocation;
    private final Path screenshotsLocation;

    @Autowired
    public StorageServiceImpl(StorageProperties properties) {
        this.rootLocation = Paths.get(properties.getLocation());
        this.profileImageLocation = this.rootLocation.resolve("profiles");
        this.setupsLocation = this.rootLocation.resolve("setups");
        this.screenshotsLocation = this.rootLocation.resolve("screenshots");
        this.appLogoLocation = this.rootLocation.resolve("appLogos");
    }

    @Override
    public void store(MultipartFile file) {
        try {
            if (file.isEmpty()) {
                throw new StorageException("Failed to store empty file " + file.getOriginalFilename());
            }
            Files.copy(file.getInputStream(), this.rootLocation.resolve(file.getOriginalFilename()));
        } catch (IOException e) {
            throw new StorageException("Failed to store file " + file.getOriginalFilename(), e);
        }
    }

    @Override
    public Path storeProfileImage(MultipartFile file, Long userId) {
        if (file.isEmpty()) {
            throw new StorageException("Failed to store empty file " + file.getOriginalFilename());
        }
        String extension = getFileExtension(file.getOriginalFilename());
        Path destinationPath = createProfileDirectory(userId).resolve("profileImage." + extension);
        Path backUpPath = createProfileDirectory(userId).resolve("profileImage." + extension + ".bkp");
        try {
            if (Files.exists(destinationPath)) {
                Files.move(destinationPath, backUpPath, StandardCopyOption.REPLACE_EXISTING);
            }
            Files.copy(file.getInputStream(), destinationPath, StandardCopyOption.REPLACE_EXISTING);
            if (Files.exists(backUpPath))
                Files.delete(backUpPath);
        } catch (IOException e) {
            // Trying to restore the backed up file
            if (Files.exists(backUpPath)) {
                try {
                    Files.move(backUpPath, destinationPath, StandardCopyOption.REPLACE_EXISTING);
                } catch (IOException ex) {
                    // Let the original exception be thrown.
                }
            }
            throw new StorageException("Failed to store file " + file.getOriginalFilename(), e);
        }
        return destinationPath;
    }

    @Override
    public Path storeApplicationLogo(MultipartFile file, Long userId, String appId) {
        if (file.isEmpty()) {
            throw new StorageException("Failed to store empty file " + file.getOriginalFilename());
        }
        String extension = getFileExtension(file.getOriginalFilename());
        Path destinationPath = createAppLogosDirectory(userId).resolve(appId);
        Path backUpPath = createAppLogosDirectory(userId).resolve(appId + ".bkp");
        try {
            if (Files.exists(destinationPath)) {
                Files.move(destinationPath, backUpPath, StandardCopyOption.REPLACE_EXISTING);
            }
            Files.copy(file.getInputStream(), destinationPath, StandardCopyOption.REPLACE_EXISTING);
            if (Files.exists(backUpPath))
                Files.delete(backUpPath);
        } catch (IOException e) {
            // Trying to restore the backed up file
            if (Files.exists(backUpPath)) {
                try {
                    Files.move(backUpPath, destinationPath, StandardCopyOption.REPLACE_EXISTING);
                } catch (IOException ex) {
                    // Let the original exception be thrown.
                }
            }
            throw new StorageException("Failed to store file " + file.getOriginalFilename(), e);
        }
        return destinationPath;
    }

    @Override
    public Stream<Path> loadAll() {
        try {
            return Files.walk(this.rootLocation, 1)
                    .filter(path -> !path.equals(this.rootLocation))
                    .map(path -> this.rootLocation.relativize(path));
        } catch (IOException e) {
            throw new StorageException("Failed to read stored files", e);
        }

    }

    @Override
    public Path load(String filename) {
        return rootLocation.resolve(filename);
    }

    @Override
    public Resource loadApplicationLogoAsResource(Long userId, String appId) {
        Path filePath = Paths.get("appLogos", userId.toString(), appId);
        return loadAsResource(filePath.toString());
    }

    @Override
    public Resource loadAsResource(String filename) {
        try {
            Path file = load(filename);
            Resource resource = new UrlResource(file.toUri());
            if(resource.exists() || resource.isReadable()) {
                return resource;
            }
            else {
                throw new StorageFileNotFoundException("Could not read file: " + filename);

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
            Files.createDirectory(rootLocation);
            Files.createDirectory(profileImageLocation);
            Files.createDirectory(setupsLocation);
            Files.createDirectory(screenshotsLocation);
            Files.createDirectory(appLogoLocation);
        } catch (IOException e) {
            throw new StorageException("Could not initialize storage", e);
        }
    }

    private String getFileExtension(String fileName) {
        String extension = "";

        int i = fileName.lastIndexOf('.');
        if (i >= 0) {
            extension = fileName.substring(i+1);
        }
        return extension;
    }

    private Path createProfileDirectory(Long userId) {
        Path subdir = profileImageLocation.resolve(userId.toString());
        try {
            if (!Files.exists(subdir))
                Files.createDirectory(subdir);
        } catch (IOException e) {
            throw new StorageException("Could not initialize storage", e);
        }
        return subdir;
    }

    private Path createAppLogosDirectory(Long userId) {
        Path subdir = appLogoLocation.resolve(userId.toString());
        try {
            if (!Files.exists(subdir))
                Files.createDirectory(subdir);
        } catch (IOException e) {
            throw new StorageException("Could not initialize storage", e);
        }
        return subdir;
    }
}
