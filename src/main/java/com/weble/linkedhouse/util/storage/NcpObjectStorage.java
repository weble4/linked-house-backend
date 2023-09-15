package com.weble.linkedhouse.util.storage;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@Transactional
public class NcpObjectStorage implements ObjectStorage {


    private final AmazonS3 s3Client;

    @Value("${ncp.bucket}")
    private String bucket;

    public NcpObjectStorage(NaverProperties properties) {
        log.info("NcpObject Storage Service properties 주입");
        this.s3Client = AmazonS3ClientBuilder.standard()
                .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(
                        properties.getEndPoint(),
                        properties.getRegionName()))
                .withCredentials(new AWSStaticCredentialsProvider(new BasicAWSCredentials(
                        properties.getAccessKey(),
                        properties.getSecretKey())))
                .build();
    }

    @Override
    public String uploadFile(MultipartFile file) {
        String fileName = createFileName(file.getOriginalFilename());

        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentLength(file.getSize());

        try {
            s3Client.putObject(new PutObjectRequest(
                    bucket, fileName, file.getInputStream(), objectMetadata)
                    .withCannedAcl(CannedAccessControlList.PublicRead)
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        log.info("url : {} ", s3Client.getUrl(bucket, fileName).toString());
        return s3Client.getUrl(bucket, fileName).toString();
    }



    @Override
    public boolean deleteFile(String fileUrl) {
        try {
            String[] temp = fileUrl.split("/");
            String fileKey = temp[temp.length - 1];
            s3Client.deleteObject(bucket, fileKey);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public List<String> uploadListImage(List<MultipartFile> files) {

        List<String> imagePathList = new ArrayList<>();

        for (MultipartFile file : files) {
            String fileName = createFileName(file.getOriginalFilename());
            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentLength(file.getSize());

            try {
                s3Client.putObject(new PutObjectRequest(
                        bucket, fileName, file.getInputStream(), objectMetadata)
                        .withCannedAcl(CannedAccessControlList.PublicRead)
                );
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            imagePathList.add(s3Client.getUrl(bucket, fileName).toString());
        }
        return imagePathList;
    }

    @Override
    public boolean deleteListImages(List<String> fileUrls) {
        for (String fileUrl : fileUrls) {
            try {
                String[] temp = fileUrl.split("/");
                String fileKey = temp[temp.length - 1];
                s3Client.deleteObject(bucket, fileKey);
            } catch (Exception e) {
                return false;
            }
        }
        return true;
    }

    private String createFileName(String originalFilename) {
        String fileType = extractExt(originalFilename);
        String uuid = UUID.randomUUID().toString();
        return uuid + "." + fileType;
    }

    private String extractExt(String originalFilename) {
        int pos = originalFilename.lastIndexOf(".");
        return originalFilename.substring(pos + 1);
    }
}
