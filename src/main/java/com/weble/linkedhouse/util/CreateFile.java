package com.weble.linkedhouse.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
public class CreateFile {

    public List<String> saveHouseImage(List<MultipartFile> images, Long customerId) {

        List<String> imagePathList = new ArrayList<>();
        //TODO: 저장되는 System Path 나중에 deploy 환경에서 서버에 맞게 루트를 조정해야 합니다
        String uploadDir = "D:\\images\\house\\host" + customerId; // 이미지를 저장할 디렉토리 지정
        File dir = new File(uploadDir);

        if (!dir.exists()) {
            if (dir.mkdirs()) {
                log.info("Directory created: " + uploadDir);
            } else {
                log.error("Failed to create directory: " + uploadDir);
            }
        }

        for (MultipartFile image : images) {
            String storeFileName = createStoreFileName(image.getOriginalFilename());
            String pullPath = uploadDir + storeFileName;

            try {
                image.transferTo(new File(pullPath));
            } catch (IOException e) {
                log.error("파일 upload exception", e);
            }
            imagePathList.add(pullPath);
        }
        return imagePathList;
    }

    public String saveImage(MultipartFile file, Long customerId) {
        //TODO: 저장되는 System Path 나중에 deploy 환경에서 서버에 맞게 루트를 조정해야 합니다
        String sysPath = "D:\\image\\profile\\user" + customerId;
        File dir = new File(sysPath);

        if (!dir.exists()) {
            if (dir.mkdirs()) {
                log.info("Directory created: " + sysPath);
            } else {
                log.error("Failed to create directory: " + sysPath);
            }
        }

        String storeFileName = createStoreFileName(file.getOriginalFilename());
        String pullPath = sysPath + storeFileName;

        try {
            file.transferTo(new File(pullPath));
        } catch (IOException e) {
            log.error("파일 upload exception", e);
        }
        return pullPath;
    }

    public void deleteImageFile(List<String> collect) {
        for (String path : collect) {
            try {
                Files.deleteIfExists(Paths.get(path));
            } catch (IOException e) {
                log.error("Failed to delete image file: " + path, e);
            }
        }
    }

    private String createStoreFileName(String originalFilename) {
        String ext = extractExt(originalFilename);
        String uuid = UUID.randomUUID().toString().substring(0,13);
        return uuid + "." + ext;
    }

    private String extractExt(String originalFilename) {
        int pos = originalFilename.lastIndexOf(".");
        return originalFilename.substring(pos + 1);
    }

}
