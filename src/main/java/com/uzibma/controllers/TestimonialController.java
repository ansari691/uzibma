package com.uzibma.controllers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.uzibma.entities.Testimonial;
import com.uzibma.repositories.TestimonialRepository;
import com.uzibma.services.SequenceGeneratorService;
import com.uzibma.util.Utility;

@RestController
@RequestMapping("/api/testimonials")
@CrossOrigin
public class TestimonialController {
	
	@Autowired
	SequenceGeneratorService sequenceGeneratorService;
	
	@Autowired
	TestimonialRepository testimonialRepository;
	
	@Value("${uzibma.images.path}")
	private String imageFolder;
	
	@PostMapping
	public int save(@RequestBody Testimonial testimonial) {
		testimonial.setId(sequenceGeneratorService.generateSequence(Testimonial.SEQUENCE_NAME));
		testimonial = testimonialRepository.save(testimonial);
		return testimonial.getId();
	}
	
	
	@PostMapping("/{id}/testimonialImages")
	public String testimonialImages(@PathVariable Integer id, @RequestParam("testimonialImages") MultipartFile photos[])
			throws IOException {
		Optional<Testimonial> optional = testimonialRepository.findById(id);
		if (optional.isPresent()) {
			Testimonial t = optional.get();
			String destination = imageFolder + "/" + id + "/testimonialImages";
			File file = new File(destination);
			if (!file.exists()) {
				file.mkdirs();
			}
			int i = t.getTestimonialImageCount() + 1;
			for (MultipartFile photo : photos) {
				String fileExtention = Utility.getFileExtension(photo.getOriginalFilename());
				String entityPhoto = imageFolder + "/" + id + "/testimonialImages/" + i + "." + fileExtention;
				File photoFile = new File(entityPhoto);
				if (photoFile.exists()) {
					photoFile.delete();
				}
				photo.transferTo(photoFile);
				if (t.getTestimonialImageNames() == null) {
					t.setTestimonialImageNames(new HashMap<Integer, String>());
				}
				t.getTestimonialImageNames().put(i, entityPhoto);
				i++;
			}
			t.setTestimonialImageCount(i - 1);

			testimonialRepository.save(t);
			return "success";
		} else {
			return "package not found";
		}
	}
	
	@GetMapping(value = "/{id}/testimonialImages/{imageId}")
	public String getTestimonialImage(@PathVariable Integer id, @PathVariable Integer imageId, HttpServletResponse response)
			throws IOException {
		String msg = "success";
		Optional<Testimonial> optional = testimonialRepository.findById(id);
		if (optional.isPresent()) {
			Testimonial t = optional.get();
			if (t.getTestimonialImageCount() > 0) {
				if (imageId <= t.getTestimonialImageCount()) {
					try (InputStream is = new FileInputStream(t.getTestimonialImageNames().get(imageId))) {
						StreamUtils.copy(is, response.getOutputStream());
						msg = "success";
					} catch (FileNotFoundException fileNotFoundException) {
						msg = "file not found";
					}
				} else {
					msg = "no image found at this index";
				}
			} else {
				msg = "no other images found for the package";
			}
		} else {
			msg = "package not found";
		}
		return msg;
	}
	
	@GetMapping
	public List<Testimonial> getAll(){
		return testimonialRepository.findAll();
	}

}
