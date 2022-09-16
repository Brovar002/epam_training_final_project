package by.goncharov.brovarsound.repository;

import by.goncharov.brovarsound.model.CartSongItems;
import org.springframework.data.repository.CrudRepository;

public interface CartSongItemsCrudRepository extends CrudRepository<CartSongItems, Integer> {
	public CartSongItems findCartSongItemsById(Integer id);
}
