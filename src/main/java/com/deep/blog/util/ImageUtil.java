package com.deep.blog.util;

import jakarta.servlet.http.Part;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

public class ImageUtil {

    private static final String upload_dir = "D:/blog-uploads/";

    public static String saveUploadedFile(Part filePart) throws IOException {

        if (filePart == null || filePart.getSize() == 0) {
            return null;
        }

        String originalFileName = filePart.getSubmittedFileName();
        String extension = originalFileName.substring(originalFileName.lastIndexOf("."));
        String uniqueName = UUID.randomUUID() + extension;

        Path uploadPath = Paths.get(upload_dir);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        try (InputStream inputStream = filePart.getInputStream()) {
            Files.copy(inputStream, uploadPath.resolve(uniqueName), StandardCopyOption.REPLACE_EXISTING);
        }

        return uniqueName;
    }


    public static void deleteFile(String fileName){

        if(fileName == null || fileName.isBlank()){
            return;
        }

        Path filePath = Paths.get(upload_dir,fileName);

        try{
            Files.deleteIfExists(filePath);
        }catch(IOException e){
            System.err.println("Failed to delete file: " + fileName + " - " + e.getMessage());
        }
    }
}
