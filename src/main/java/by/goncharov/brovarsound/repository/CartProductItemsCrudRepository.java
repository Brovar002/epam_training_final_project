package by.goncharov.brovarsound.repository;

import by.goncharov.brovarsound.model.CartProductItems;
import org.springframework.data.repository.CrudRepository;

public interface CartProductItemsCrudRepository extends CrudRepository<CartProductItems, Integer> {
	public CartProductItems findCartProductItemsById(Integer id);
}
