package com.webapi.ashop.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;


@Slf4j
public class ImageHandler {
    private static final String IMAGE_DIR = "src/main/resources/static/images/";

    public static String uploadImage (MultipartFile file) {

        try {
            log.info("ImageHandler:uploadImage execution started");
            /*
            * generate a unique file name
            * */
            String fileName =  UUID.randomUUID() + "_" + file.getOriginalFilename();
            String filePath = IMAGE_DIR+fileName;
            file.transferTo(new File(filePath));
            log.debug("ImageHandler:uploadImage image {} saved to file system", fileName);
            return filePath;
        } catch (IOException e) {
            log.error("Exception occurred while uploading image to file system, Exception message {}", e.getMessage());
            throw new RuntimeException("Failed to store image", e);
        }
    }

    public static void deleteImage (String imagePath) {
        try {
            log.info("ImageHandler:deleteImage execution started");
            String fileName = imagePath.substring(imagePath.lastIndexOf("/") + 1);
            Path filePath = Paths.get(IMAGE_DIR, fileName);
            Files.deleteIfExists(filePath);
            log.debug("ImageHandler:deleteImage image {} deleted to file system", fileName);
        }catch (IOException e) {
            log.error("Exception occurred while deleting image to file system, Exception message {}", e.getMessage());
            throw new RuntimeException("Failed to delete image", e);
        }
    }
}
