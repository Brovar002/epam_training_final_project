package by.goncharov.brovarsound.repository;

import by.goncharov.brovarsound.model.Album;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AlbumCrudRepository extends CrudRepository<Album, Integer> {
	public Album findByAlbumName(String name);
	public Album findAlbumById(Integer id);
}
