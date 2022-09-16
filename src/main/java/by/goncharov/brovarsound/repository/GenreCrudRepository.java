package by.goncharov.brovarsound.repository;

import by.goncharov.brovarsound.model.Genre;
import org.springframework.data.repository.CrudRepository;

public interface GenreCrudRepository extends CrudRepository<Genre, Integer> {
	public Genre findGenreById(Integer id);
}
