package by.goncharov.brovarsound.repository;

import by.goncharov.brovarsound.model.Product;
import org.springframework.data.repository.CrudRepository;

public interface ProductCrudRepository extends CrudRepository<Product, Integer> {
	public Product findByProductName(String name);
	public Product findProductById(Integer id);
}
