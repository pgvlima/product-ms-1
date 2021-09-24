package br.com.luizr.santos.productms.service;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import br.com.luizr.santos.productms.controller.dto.ProductsDto;
import br.com.luizr.santos.productms.controller.form.ProductsForm;
import br.com.luizr.santos.productms.model.Products;
import br.com.luizr.santos.productms.repository.ProductsRepository;

public class ProductsService {

	// Faz a injeção do DAO
	@Autowired
	private ProductsRepository productsRepository;
	
	
	public ProductsDto createProduct(ProductsForm productsForm) {
		Products products = productsForm.conversor();
		productsRepository.save(products);
		return new ProductsDto(products);
	}
}
