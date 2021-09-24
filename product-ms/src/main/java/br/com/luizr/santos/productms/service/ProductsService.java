package br.com.luizr.santos.productms.service;

import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import br.com.luizr.santos.productms.controller.dto.ProductsDto;
import br.com.luizr.santos.productms.controller.form.ProductsForm;
import br.com.luizr.santos.productms.model.Products;
import br.com.luizr.santos.productms.repository.ProductsRepository;

@Service
public class ProductsService {

	// Faz a injeção do DAO
	@Autowired
	private ProductsRepository productsRepository;

	// Cadastra um novo produto
	@Transactional // Faz o commit na base da dados
	public Optional<ProductsDto> createProduct(ProductsForm productsForm) {
		Products products = productsForm.conversor(productsForm);
		productsRepository.save(products);

		return ProductsDto.conversorDto(products);
	}

	// Atualiza um produto
	@Transactional // Faz o commit na base da dados
	public Optional<ProductsDto> updateProduct(Long id, ProductsForm productsForm) {
		Products products = productsRepository.getById(id);
		products.setName(productsForm.getName());
		products.setDescription(productsForm.getDescription());
		products.setPrice(productsForm.getPrice());
		productsRepository.save(products);
		
		return ProductsDto.conversorDto(products);
	}

	// Consulta um produto pelo id
	public Optional<ProductsDto> findById(Long id) {
		return ProductsDto.conversorDto(productsRepository.getById(id));
	}
	
	// Retorna todos os produtos cadastrados
	public List<ProductsDto> retrieveAllProducts() {
		return ProductsDto.conversorDtoList(productsRepository.findAll());
	}

	// Retorna os produtos de acordo com os parametros informados no search
	public List<ProductsDto> searchProducts(String q, Double min_price, Double max_price) {
        return ProductsDto.conversorDtoList(productsRepository.findBySearch(q, min_price, max_price));
	}	
	
	// Delete um produto
	@Transactional // Faz o commit na base da dados
	public void deleteProduct(Long id) {
        productsRepository.deleteById(id);
	}
}