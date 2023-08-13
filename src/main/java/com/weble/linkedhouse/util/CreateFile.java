package com.weble.linkedhouse.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Slf4j
public class CreateFile {
    public String saveImage(MultipartFile file) {
        //TODO: 저장되는 System Path 나중에 deploy 환경에서 서버에 맞게 루트를 조정해야 합니다
        String sysPath = "D:\\image\\profile\\file\\";
        String storeFileName = createStoreFileName(file.getOriginalFilename());
        String pullPath = sysPath + storeFileName;

        try {
            file.transferTo(new File(pullPath));
        } catch (IOException e) {
            log.error("파일 upload exception", e);
        }
        return pullPath;
    }

    private String createStoreFileName(String originalFilename) {
        String ext = extractExt(originalFilename);
        String uuid = UUID.randomUUID().toString();
        return uuid + "." + ext;
    }

    private String extractExt(String originalFilename) {
        int pos = originalFilename.lastIndexOf(".");
        return originalFilename.substring(pos + 1);
    }
}
