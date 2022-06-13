package by.goncharov.epamsound.dao;

import by.goncharov.epamsound.beans.Genre;

import java.util.List;

/**
 * The interface Genre dao.
 * @author Goncharov Daniil
 * @see BaseDao
 * @see Genre
 */
public interface GenreDao extends BaseDao<Genre> {

    /**
     * Find genre id int.
     *
     * @param genre the genre
     * @return the int
     * @throws DaoException the dao exception
     */
    int findGenreId(Genre genre) throws DaoException;

    /**
     * Find genres list.
     *
     * @return the list
     * @throws DaoException the dao exception
     */
    List<String> findGenres() throws DaoException;
}
