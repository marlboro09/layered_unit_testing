package com.prac.music.domain.user.service;

import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class S3Service {
    @Value("${cloud.aws.s3.bucket}")
    private String bucket;
    private final AmazonS3 s3;

    public String s3Upload(MultipartFile file) throws IOException {
        String fileName = file.getOriginalFilename();
        String ext = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase(); // 확장자를 소문자로 변환
        String contentType = getContentType(ext);

        try {
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentType(contentType);
            s3.putObject(new PutObjectRequest(bucket, fileName, file.getInputStream(), metadata));
        } catch (AmazonS3Exception e) {
            e.printStackTrace();
            throw new RuntimeException("S3 업로드 중 오류가 발생했습니다.");
        } catch (SdkClientException e) {
            e.printStackTrace();
        }
        return s3.getUrl(bucket, fileName).toString();
    }
    private String getContentType(String ext) {
        switch (ext) {
            case "jpeg":
                return "image/jpeg";
            case "jpg":
                return "image/jpg";
            case "png":
                return "image/png";
            case "mp4":
                return "video/mp4";
            case "avi":
                return "video/x-msvideo";
            case "mov":
                return "video/quicktime";
            default:
                throw new IllegalArgumentException("적절한 확장자가 아닙니다.");
        }
    }

}
