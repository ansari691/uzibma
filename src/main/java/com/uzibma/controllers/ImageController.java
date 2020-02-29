package com.uzibma.controllers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.uzibma.entities.FileInfo;
import com.uzibma.entities.Product;
import com.uzibma.repositories.FileInfoRepository;
import com.uzibma.util.Utility;

@RestController
@RequestMapping("api/images")
@CrossOrigin
public class ImageController {

	@Value("${uzibma.images.path}")
	private String imageFolder;

	@Autowired
	FileInfoRepository fileInfoRepository;

	private static final String DOT = ".";

	@PostMapping("/{type}")
	public void uploadFile(@PathVariable String type, @RequestParam("image") MultipartFile photo,
			HttpServletResponse response) {

		String destination = imageFolder + "/";

		File destinationFolder = new File(destination);
		if (!destinationFolder.exists()) {
			destinationFolder.mkdirs();
		}

		if (type.equals("abaya") || type.equals("newArrival") || type.equals("pkDress") || type.equals("kurtis")
				|| type.equals("tqDesign")) {

			Optional<FileInfo> optional = fileInfoRepository.findById(type);
			FileInfo fileInfo = null;
			if (optional.isPresent()) {
				fileInfo = optional.get();
			} else {
				fileInfo = new FileInfo();
				fileInfo.setTypeName(type);
			}
			String saveFileName = type + DOT + Utility.getFileExtension(photo.getOriginalFilename());
			fileInfo.setFilePath(saveFileName);
			String extention = Utility.getFileExtension(photo.getOriginalFilename());
			fileInfo.setFileExtention(extention);
			fileInfoRepository.save(fileInfo);
			File imgFile = new File(destination + fileInfo.getFilePath());

			if (imgFile.exists()) {
				imgFile.delete();
			}
			try {
				photo.transferTo(imgFile);
			} catch (IllegalStateException | IOException e) {
				e.printStackTrace();
			}
		}
	}

	@GetMapping("/{type}")
	public void getFile(@PathVariable String type, HttpServletResponse response) {
		Optional<FileInfo> optional = fileInfoRepository.findById(type);
		File imgFile = null;
		if (optional.isPresent()) {
			imgFile = new File(imageFolder + "/" + optional.get().getFilePath());

			try (InputStream is = new FileInputStream(imgFile)) {
				if(optional.get().getFilePath().equals("png")) {
					response.setContentType(MediaType.IMAGE_PNG_VALUE);
				}else {
					response.setContentType(MediaType.IMAGE_JPEG_VALUE);
				}
				StreamUtils.copy(is, response.getOutputStream());
			} catch (FileNotFoundException fileNotFoundException) {
				fileNotFoundException.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}
	
}
