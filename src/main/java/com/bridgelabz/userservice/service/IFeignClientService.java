package com.bridgelabz.userservice.service;

import java.io.IOException;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;

@Component
@FeignClient(name = "awsbucketservice", url = "http://localhost:8800") // serviceId of awsservice
public interface IFeignClientService {
	@PostMapping("/bucket/uploadimage")
	public String uploadImage(@RequestParam(value = "folderName") String folderName,
			@RequestParam(value = "filePath") String filePath,
			@RequestParam(value = "innerFolderName") String innerFolderName)
			throws AmazonServiceException, AmazonClientException, IOException;
}
