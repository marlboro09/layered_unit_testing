package com.prac.music.domain.user.service;

import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.prac.music.aop.MeasureExecutionTime;
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
    private static final long MAX_IMAGE_SIZE = 10 * 1024 * 1024; //10mb
    private static final long MAX_VIDEO_SIZE = 200 * 1024 * 1024; //200mb

    @MeasureExecutionTime
    public String s3Upload(MultipartFile file) throws IOException {
        String fileName = file.getOriginalFilename();
        String ext = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase(); // 확장자를 소문자로 변환
        String contentType = getContentType(ext);

        validateFileSize(file.getSize(), ext);

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

    private void validateFileSize(long size, String ext) {
        if (isImageFile(ext) && size > MAX_IMAGE_SIZE) {
            throw new IllegalArgumentException("이미지 파일 크기는 10MB를 초과할 수 없습니다.");
        } else if (isVideoFile(ext) && size > MAX_VIDEO_SIZE) {
            throw new IllegalArgumentException("비디오 파일 크기는 200MB를 초과할 수 없습니다.");
        }
    }

    private boolean isImageFile(String ext) {
        return ext.equals("jpeg") || ext.equals("jpg") || ext.equals("png");
    }

    private boolean isVideoFile(String ext) {
        return ext.equals("mp4") || ext.equals("avi") || ext.equals("mov");
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
                return "video/x-avi";
            case "mov":
                return "video/quicktime";
            default:
                throw new IllegalArgumentException("적절한 확장자가 아닙니다.");
        }
    }
}
