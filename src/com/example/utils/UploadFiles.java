package com.example.utils;


import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * 上传文件
 * @author CXW-HP
 *
 */
public class UploadFiles {

	// 仅上传单张图片
	public boolean uploadSingleImage(String uploadFilePath, String urlPath) {
		File uploadFile = new File(uploadFilePath);	
		try {
			FormFile formFile = new FormFile(uploadFile, "image", "image/pjpeg");
			return SocketHttpRequester.post(urlPath, new HashMap<String, String>(), formFile);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	//上传图片, 如果有文本参数，同时通过post上传文本参数
	public boolean uploadImages(String uploadFilePath, Map<String, String> params, String urlPath) {
		File uploadFile = new File(uploadFilePath);
		try {
			FormFile formFile = new FormFile(uploadFile, "image", "image/pjpeg");
			return SocketHttpRequester.post(urlPath, params, formFile);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
}