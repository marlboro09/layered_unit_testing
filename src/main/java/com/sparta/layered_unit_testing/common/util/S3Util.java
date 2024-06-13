package com.sparta.layered_unit_testing.common.util;

import org.springframework.stereotype.Component;

@Component
public class S3Util {
	private static final long MAX_IMAGE_SIZE = 10 * 1024 * 1024; //10mb
	private static final long MAX_VIDEO_SIZE = 200 * 1024 * 1024; //200mb

	public void validateFileSize(long size, String ext) {
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

	public String getContentType(String ext) {
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
