package com.example.demo.service;

import lombok.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.stream.Stream;

@Service
public class FileSystemStorageService implements StorageService{

    //@Value("${spring.servlet.multipart.location}")
    private String uploadPath;

    @Override
    public void init() {
        try {
            Files.createDirectories(Paths.get(uploadPath));
        } catch (IOException e) {
            throw new RuntimeException("업로드할 파일폴더가 없습니다.");
        }
    }

    @Override
    public void store(List<MultipartFile> files) {
        for(MultipartFile file : files) {
            try {
                if (file.isEmpty()) {
                    throw new Exception("업로드할 파일이 없습니다.");
                }
                Path root = Paths.get(uploadPath);
                if (!Files.exists(root)) {
                    init();
                }

                try (InputStream inputStream = file.getInputStream()) {
                    Files.copy(inputStream, root.resolve(file.getOriginalFilename()),
                            StandardCopyOption.REPLACE_EXISTING);
                }
            } catch (Exception e) {
                throw new RuntimeException("파일 업로드 실패 에러명: " + e.getMessage());
            }
        }
    }

    @Override
    public Stream<Path> loadAll() {
        return null;
    }

    @Override
    public Path load(String filename) {
        return null;
    }

    @Override
    public Resource loadAsResource(String filename) {
        try {
            Path file = load(filename);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            }
            else {
                throw new RuntimeException("파일 다운로드 실패. 파일명: " + filename);
            }
        }
        catch (MalformedURLException e) {
            throw new RuntimeException("파일 다운로드 실패. 파일명: " + filename, e);
        }
    }

    @Override
    public void deleteAll() {

    }
}
