package by.goncharov.brovarsound.repository;

import by.goncharov.brovarsound.model.Song;
import org.springframework.data.repository.CrudRepository;

public interface SongCrudRepository extends CrudRepository<Song, Integer> {
	public Song findSongBySongName(String name);
	public Song findSongById(Integer id);
}
