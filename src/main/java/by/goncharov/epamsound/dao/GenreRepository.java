package by.goncharov.epamsound.dao;

import by.goncharov.epamsound.beans.Genre;
import org.springframework.data.jpa.repository.JpaRepository;


/**
 * The interface Genre dao.
 * @author Goncharov Daniil
 * @see Genre
 */
public interface GenreRepository extends JpaRepository<Genre, Integer> {

}
