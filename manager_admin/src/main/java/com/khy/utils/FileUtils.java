package com.khy.utils;

import java.io.IOException;
import java.io.InputStream;

import org.springframework.web.multipart.MultipartFile;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.OSSException;

public class FileUtils {

    
	public static void uploadImg(MultipartFile file, String fileName) {
		try {
	        OSSClient ossClient = new OSSClient(Constants.endpoint, Constants.accessKeyId, Constants.accessKeySecret);
			InputStream is = file.getInputStream();
			ossClient.putObject(Constants.bucketName,fileName,is);
			ossClient.shutdown();
		} catch (OSSException e) {
			e.printStackTrace();
		} catch (ClientException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
