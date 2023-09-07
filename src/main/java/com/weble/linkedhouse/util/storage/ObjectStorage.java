package com.weble.linkedhouse.util.storage;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ObjectStorage {

    String uploadFile(MultipartFile file);
    List<String> uploadListImage(List<MultipartFile> files);
    boolean deleteFile(String fileUrl);
    boolean deleteListImages(List<String> fileUrls);

}
