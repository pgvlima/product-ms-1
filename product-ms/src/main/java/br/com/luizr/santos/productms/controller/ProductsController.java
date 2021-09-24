package br.com.luizr.santos.productms.controller;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
import br.com.luizr.santos.productms.service.ProductsService;
import br.com.luizr.santos.productms.validation.ErrorFormDto;

@RestController
@RequestMapping("/products")
public class ProductsController {

	@Autowired
	private ProductsService productsService;
	
	
	// Método: POST - Cadastra um novo produto
	@PostMapping
	public ResponseEntity<Optional<ProductsDto>> create(@RequestBody @Valid ProductsForm productsForm, UriComponentsBuilder uriBuilder) {
		Optional<ProductsDto> productsDto = productsService.createProduct(productsForm);
		URI uri = uriBuilder.path("/products/{id}").buildAndExpand(productsDto).toUri();
		
		return ResponseEntity.created(uri).body(productsDto);
	}

	
	// Método: PUT - Atualiza um produto, de acordo com o id recebido como parametro
	@PutMapping("/{id}") // Faz o mapeamento de um parametro via PUT
	public ResponseEntity<Optional<ProductsDto>> update(@PathVariable Long id, @RequestBody @Valid ProductsForm productsForm) {
		Optional<ProductsDto> productsDto = productsService.findById(id);
        if (productsDto.isPresent()) {
	        return ResponseEntity.ok(productsService.updateProduct(id, productsForm));
        }
       return ResponseEntity.notFound().build();
	}

	
	// Método: GET - Consulta um produto por id, de acordo com o id recebido como parametro
	@GetMapping("/{id}") // Faz o mapeamento de um parametro via GET
	public ResponseEntity<?> retrieveById(@PathVariable Long id) {
		Optional<ProductsDto> productsDto = productsService.findById(id);
		if (productsDto.isPresent()) {
		    return ResponseEntity.ok(productsDto);
		}
		return ResponseEntity.notFound().build();
	}
	
	
	// Método: GET - Lista todos os produtos cadastrados na base
	@GetMapping
	public List<ProductsDto> retrieveAllProducts() {
		return productsService.retrieveAllProducts();
	}

	
	// Método: GET - Lista os produtos de acordo com os parametros de filtros informados
	@GetMapping("/search") // Faz o mapeamento de um parametro via GET
	public ResponseEntity<Object> search(@RequestParam(required = false) Double min_price, @RequestParam(required = false) Double max_price, @RequestParam(required = false) String q) {
		if(!(min_price == null && max_price == null && q == null)) {
			return  ResponseEntity.ok(productsService.searchProducts(q, min_price, max_price));
		}
		return new ResponseEntity<Object>(new ErrorFormDto(HttpStatus.BAD_REQUEST.value(), "Nenhum parâmetro informado"), HttpStatus.BAD_REQUEST);
	}
	
	
	// Método: DELETE - Faz a exclusao de um produto, de acordo com o parametro recebido
	@DeleteMapping("/{id}") // Faz o mapeamento de um parametro via DELETE
	public ResponseEntity<?> delete(@PathVariable Long id) {
	    Optional<ProductsDto> productsDto = productsService.findById(id);
        if (productsDto.isPresent()) {
        	productsService.deleteProduct(id);
        	return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
	}
	
}