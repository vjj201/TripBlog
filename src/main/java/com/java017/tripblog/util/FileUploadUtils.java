package com.java017.tripblog.util;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

/**
 * @author YuCheng
 * @date 2021/10/28 - 下午 04:31
 */
public class FileUploadUtils {

    public static void saveUploadFile(String dir, String fileName,
                                MultipartFile multipartFile){
        Path path = Paths.get(dir);

        if (!Files.exists(path)) {
            try {
                Files.createDirectories(path);
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }

        try (InputStream inputStream = multipartFile.getInputStream()) {
            Path filePath = path.resolve(fileName);
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
