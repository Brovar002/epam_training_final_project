package by.goncharov.brovarsound.repository;

import by.goncharov.brovarsound.model.Category;
import org.springframework.data.repository.CrudRepository;

public interface CategoryCrudRepository extends CrudRepository<Category, Integer> {
	public Category findCategoryById(Integer id);
}
