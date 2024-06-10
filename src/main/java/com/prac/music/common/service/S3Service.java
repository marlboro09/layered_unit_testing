package com.prac.music.common.service;

import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.prac.music.aop.MeasureExecutionTime;
import com.prac.music.common.exception.S3ServiceException;
import com.prac.music.common.util.S3Util;
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
    private final S3Util s3Util;

    @MeasureExecutionTime
    public String s3Upload(MultipartFile file) throws IOException {
        if (file != null) {
            try {
                String fileName = file.getOriginalFilename();
                String ext = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase(); // 확장자를 소문자로 변환
                String contentType = s3Util.getContentType(ext);

                s3Util.validateFileSize(file.getSize(), ext);
                ObjectMetadata metadata = new ObjectMetadata();
                metadata.setContentType(contentType);
                s3.putObject(new PutObjectRequest(bucket, fileName, file.getInputStream(), metadata));
                return s3.getUrl(bucket, fileName).toString();
            } catch (AmazonS3Exception e) {
                e.printStackTrace();
                throw new S3ServiceException("S3 업로드 중 오류가 발생했습니다.");
            } catch (SdkClientException e) {
                e.printStackTrace();
            }
        }
        return "null";
    }
}

