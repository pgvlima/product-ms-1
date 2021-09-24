package br.com.luizr.santos.productms.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import br.com.luizr.santos.productms.model.Products;

public interface ProductsRepository extends JpaRepository<Products, Long> {
	
	// Query que faz o Search através do campos recebidos como parametros
	@Query("SELECT p FROM Products p WHERE (:q is null or p.name like %:q% or p.description like %:q%) and (:min_price is null or p.price>=:min_price) and (:max_price is null or p.price<=:max_price)")
	List<Products> findBySearch (String q, Double min_price, Double max_price);

}