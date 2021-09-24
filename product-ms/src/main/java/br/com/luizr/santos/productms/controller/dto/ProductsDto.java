package br.com.luizr.santos.productms.controller.dto;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import br.com.luizr.santos.productms.model.Products;

public class ProductsDto {
	
	private Long id;
	private String name;
	private String description;
	private Double price;
	
	public ProductsDto(Products products) {
		this.id = products.getId();
		this.name = products.getName();
		this.description = products.getDescription();
		this.price = products.getPrice();
	}

	public Long getId() {
		return id;
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

	public static List<ProductsDto> conversorDtoList(List<Products> products) {
		return products.stream().map(ProductsDto::new).collect(Collectors.toList());
	}
	
	public static Optional<ProductsDto> conversorDto(Products products) {
		return Optional.ofNullable(new ProductsDto(products));
	}

}