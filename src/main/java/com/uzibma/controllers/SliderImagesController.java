package com.uzibma.controllers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.uzibma.entities.SliderImage;
import com.uzibma.repositories.SliderImageRepository;
import com.uzibma.services.SequenceGeneratorService;
import com.uzibma.util.Utility;

@RestController
@RequestMapping("api/sliderImages")
@CrossOrigin
public class SliderImagesController {

	@Value("${uzibma.images.path}")
	private String imageFolder;

	@Autowired
	SequenceGeneratorService sequenceGeneratorService;

	@Autowired
	SliderImageRepository sliderImageRepository;

	public static final String SUCCESS = "success";

	public static final String PACKAGE_NOT_FOUND = "package not found";

	public static final String FILE_NOT_FOUND = "file not found";

	@PutMapping
	public String productImages(@RequestParam("sliderImages") MultipartFile photos[])
			throws IOException {
		String destination = imageFolder + "/sliderImages";
		File file = new File(destination);
		if (!file.exists()) {
			file.mkdirs();
		}
		int maxSeq = sequenceGeneratorService.getMaxSequenceNoForSequence(SliderImage.SEQUENCE_NAME);
		int i = maxSeq == 0 ? 1:maxSeq;
		
		for (MultipartFile photo : photos) {
			String fileExtention = Utility.getFileExtension(photo.getOriginalFilename());
			String entityPhoto = imageFolder + "/sliderImages/" + i + "." + fileExtention;
			File photoFile = new File(entityPhoto);
			if (photoFile.exists()) {
				photoFile.delete();
			}
			photo.transferTo(photoFile);
			SliderImage image = new SliderImage();
			image.setImageId(i++);
			image.setActive(true);
			image.setFileExtention(fileExtention);
			sliderImageRepository.save(image);
			sequenceGeneratorService.generateSequence(SliderImage.SEQUENCE_NAME);
		}
		return ""+i;
	}

	@GetMapping(value = "/{id}")
	public String getProductImage(@PathVariable Integer id,HttpServletResponse response)
			throws IOException {
		String msg = SUCCESS;
		Optional<SliderImage> optional = sliderImageRepository.findById(id);
		if(optional.isPresent()) {
			try (InputStream is = new FileInputStream(imageFolder+ "/sliderImages/" + id + "." + optional.get().getFileExtention())) {
				StreamUtils.copy(is, response.getOutputStream());
				msg = SUCCESS;
			} catch (FileNotFoundException fileNotFoundException) {
				msg = FILE_NOT_FOUND;
			}
		}	
		return msg;
	}
	
	@PutMapping("/{id}/deactivate")
	public String disable(@PathVariable Integer id) {
		String msg  = SUCCESS;
		Optional<SliderImage> optional = sliderImageRepository.findById(id);
		if(optional.isPresent()) {
			SliderImage image = optional.get();
			image.setActive(false);
			sliderImageRepository.save(image);
		}else {
			msg = "not found";
		}
		return msg;
	}
	
	@PutMapping("/{id}/activate")
	public String enable(@PathVariable Integer id) {
		String msg  = SUCCESS;
		Optional<SliderImage> optional = sliderImageRepository.findById(id);
		if(optional.isPresent()) {
			SliderImage image = optional.get();
			image.setActive(true);
			sliderImageRepository.save(image);
		}else {
			msg = "failure";
		}
		return msg;
	}
	
	@GetMapping("/active")
	public List<SliderImage> getAllActiveProductImage()  {
		return sliderImageRepository.findByActive(true);
	}
	
	@GetMapping
	public List<SliderImage> getAll()  {
		return sliderImageRepository.findAll();
	}
}
