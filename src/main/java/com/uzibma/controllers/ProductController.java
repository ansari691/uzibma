package com.uzibma.controllers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.uzibma.entities.Product;
import com.uzibma.repositories.ProductRepository;
import com.uzibma.services.SequenceGeneratorService;
import com.uzibma.util.Utility;

@RestController
@RequestMapping("api/products")
@CrossOrigin
public class ProductController {

	@Autowired
	SequenceGeneratorService sequenceGeneratorService;

	@Autowired
	ProductRepository productRepository;

	@Value("${uzibma.images.path}")
	private String imageFolder;

	public static final String SUCCESS = "success";

	public static final String PACKAGE_NOT_FOUND = "package not found";

	public static final String FILE_NOT_FOUND = "file not found";

	@PostMapping
	public int save(@RequestBody Product product) {
		int result = 0;
		try {
			product.setId(sequenceGeneratorService.generateSequence(Product.SEQUENCE_NAME));
			product.setProductUpdateDate(new Date());
			product = productRepository.save(product);
			result = product.getId();
		} catch (Exception exception) {
		}
		return result;
	}

	@PostMapping("/{id}/productImages")
	public String productImages(@PathVariable Integer id, @RequestParam("productImages") MultipartFile photos[])
			throws IOException {
		Optional<Product> optional = productRepository.findById(id);
		if (optional.isPresent()) {
			Product p = optional.get();
			String destination = imageFolder + "/" + id + "/productImages";
			File file = new File(destination);
			if (!file.exists()) {
				file.mkdirs();
			}
			int i = p.getProductImageCount() + 1;
			for (MultipartFile photo : photos) {
				String fileExtention = Utility.getFileExtension(photo.getOriginalFilename());
				String entityPhoto = imageFolder + "/" + id + "/productImages/" + i + "." + fileExtention;
				File photoFile = new File(entityPhoto);
				if (photoFile.exists()) {
					photoFile.delete();
				}
				photo.transferTo(photoFile);
				if (p.getProductImageNames() == null) {
					p.setProductImageNames(new HashMap<Integer, String>());
				}
				p.getProductImageNames().put(i, entityPhoto);
				i++;
			}
			p.setProductImageCount(i - 1);

			productRepository.save(p);
			return SUCCESS;
		} else {
			return PACKAGE_NOT_FOUND;
		}
	}

	@GetMapping(value = "/{id}/productImages/{imageId}")
	public String getProductImage(@PathVariable Integer id, @PathVariable Integer imageId, HttpServletResponse response)
			throws IOException {
		String msg = SUCCESS;
		Optional<Product> optional = productRepository.findById(id);
		if (optional.isPresent()) {
			Product p = optional.get();
			if (p.getProductImageCount() > 0) {
				if (imageId <= p.getProductImageCount()) {
					try (InputStream is = new FileInputStream(p.getProductImageNames().get(imageId))) {
						StreamUtils.copy(is, response.getOutputStream());
						msg = SUCCESS;
					} catch (FileNotFoundException fileNotFoundException) {
						msg = FILE_NOT_FOUND;
					}
				} else {
					msg = "no image found at this index";
				}
			} else {
				msg = "no other images found for the package";
			}
		} else {
			msg = PACKAGE_NOT_FOUND;
		}
		return msg;
	}

	@GetMapping
	public List<Product> getAll() {
		return productRepository.findAll();
	}

	@GetMapping("/{id}")
	public Product getById(@PathVariable Integer id) {
		Optional<Product> optional = productRepository.findById(id);
		return optional.get();
	}

	@GetMapping("/types/{type}")
	public List<Product> getByType(@PathVariable String type) {
		return productRepository.findByTypeIgnoreCase(type);
	}

	@GetMapping("/{type}/typeAhead/{searchTerm}")
	public List<Product> searchBy(@PathVariable String type, @PathVariable String searchTerm) {
		return productRepository.findByTypeAndProductNameLikeIgnoreCase(type, searchTerm);
	}

	@GetMapping("/{subType}/similar")
	public List<Product> similarProducts(@PathVariable String subType) {	
		
		List<Product> optional = productRepository.findBySubTypeIgnoreCase(subType);
		
		if(optional.size() == 0) {
			return productRepository.findByTypeIgnoreCase(subType);
		}
		return optional;
	}

	@GetMapping("{type}/page/{pageNo}/{pageSize}")
	public List<Product> similar(@PathVariable Integer pageNo, @PathVariable Integer pageSize,
			@PathVariable String type) {

		if (pageSize < 0 || pageNo < 0) {

			return Collections.emptyList();

		} else {

			Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by("prductUpdateDate"));

			Page<Product> pagedResult = productRepository.findByType(paging, type);

			return pagedResult.getContent();
		}

	}
	
	
	@GetMapping("/{type}/{pageSize}/pages")
	public long getPages(@PathVariable String type, @PathVariable Integer pageSize) {
		long total = productRepository.countByType(type);
		long result = total%pageSize;
		if(result == 0) {
			return total/pageSize;
		}
		else {
			return total/pageSize + 1;
		}
	}
	
	
	@DeleteMapping("{id}")
		public String deleteById(@PathVariable Integer id ){
		if(productRepository.existsById(id)) {
			productRepository.deleteById(id);
			return "success";
		}
		else {
			return "product not found";
		}
		
	}
	
}
