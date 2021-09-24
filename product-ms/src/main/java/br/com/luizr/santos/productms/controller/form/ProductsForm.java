package br.com.luizr.santos.productms.controller.form;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import org.hibernate.validator.constraints.Length;
import br.com.luizr.santos.productms.model.Products;
import br.com.luizr.santos.productms.repository.ProductsRepository;

public class ProductsForm {
	
	@NotNull(message = "O campo name não pode ser nulo")
	@NotEmpty(message = "O campo name não pode ser vazio")
	private String name;
	@NotNull(message = "O campo description não pode ser nulo")
	@NotEmpty(message = "O campo description não pode ser vazio")
	@Length(min = 10, message = "O campo description deve ter 10 caracteres no mínimo")
	private String description;
	@Positive(message = "O campo price não pode ser negativo")
	private Double price;
	
	public void setName(String name) {
		this.name = name;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public Double getPrice() {
		return price;
	}

	// Converte o ProductsForm em Products
	public Products conversor() {
		return new Products(name, description, price);
	}

	// Faz a atualização do produto
	public Products update(Long id, ProductsRepository productsRepository) {
		Products products = productsRepository.getById(id);
		products.setName(this.name);
		products.setDescription(this.description);
		products.setPrice(this.price);
		return products;
	}
}