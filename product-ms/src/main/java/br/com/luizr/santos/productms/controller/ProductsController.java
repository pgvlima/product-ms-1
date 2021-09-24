package br.com.luizr.santos.productms.controller;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.validation.constraints.Null;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;
import br.com.luizr.santos.productms.controller.dto.ProductsDto;
import br.com.luizr.santos.productms.controller.form.ProductsForm;
import br.com.luizr.santos.productms.model.Products;
import br.com.luizr.santos.productms.repository.ProductsRepository;
import br.com.luizr.santos.productms.service.ProductsService;

@RestController
@RequestMapping("/products")
public class ProductsController {
	
	@Autowired
	private ProductsService productsService;
	
	// Método: POST - Cadastra um novo produto
	@PostMapping
	@Transactional // Faz o commit na base da dados
	public ResponseEntity<ProductsDto> create(@RequestBody @Valid ProductsForm productsForm, UriComponentsBuilder uriBuilder) {


		URI uri = uriBuilder.path("/products/{id}").buildAndExpand(productsForm.g).toUri();
		return ResponseEntity.created(uri).body(productsService.createProduct(productsForm));
	}

	// Método: PUT - Atualiza um produto, de acordo com o id recebido como parametro
	@PutMapping("/{id}") // Faz o mapeamento de um parametro via PUT
	@Transactional // Faz o commit na base da dados
	public ResponseEntity<ProductsDto> update(@PathVariable Long id, @RequestBody @Valid ProductsForm productsForm) {
	    Optional<Products> optional = productsRepository.findById(id);
        if (optional.isPresent()) {
        	Products products = productsForm.update(id, productsRepository);
	        return ResponseEntity.ok(new ProductsDto(products));
        }
       return ResponseEntity.notFound().build();
	}

	// Método: GET - Consulta um produto por id, de acordo com o id recebido como parametro
	@GetMapping("/{id}") // Faz o mapeamento de um parametro via GET
	public ResponseEntity<?> retrieveById(@PathVariable Long id) {
		Optional<Products> products = productsRepository.findById(id);
		if (products.isPresent()) {
		    return ResponseEntity.ok(new ProductsDto(products.get()));
		}
		return ResponseEntity.notFound().build();
	}
	
	// Método: GET - Lista todos os produtos cadastrados na base
	@GetMapping
	public List<ProductsDto> list() {
		return ProductsDto.conversor(productsRepository.findAll());
	}

	// Método: GET - Lista os produtos de acordo com os parametros de filtros informados
	@GetMapping("/search") // Faz o mapeamento de um parametro via GET
	public List<ProductsDto> search(@RequestParam(required = false) Double min_price, @RequestParam(required = false) Double max_price, @RequestParam(required = false) String q) {
		
		if(!(min_price == null && max_price == null && q == null)) {
			List<Products> products = productsRepository.findBySearch(q, min_price, max_price);
			return ProductsDto.conversor(products);
		}
		return null;
	}
	
	// Método: DELETE - Faz a exclusao de um produto, de acordo com o parametro recebido
	@DeleteMapping("/{id}") // Faz o mapeamento de um parametro via DELETE
	@Transactional // Faz o commit na base da dados
	public ResponseEntity<?> remove(@PathVariable Long id) {
	    Optional<Products> optional = productsRepository.findById(id);
        if (optional.isPresent()) {
        		productsRepository.deleteById(id);
            	return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
	}
	
}