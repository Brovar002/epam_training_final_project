package by.goncharov.brovarsound.repository;

import by.goncharov.brovarsound.model.ShoppingCart;
import org.springframework.data.repository.CrudRepository;

public interface ShoppingCartCrudRepository extends CrudRepository<ShoppingCart, Integer> {
	public ShoppingCart findShoppingCartById(Integer id);
}
