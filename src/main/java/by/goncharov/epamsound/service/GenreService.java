package by.goncharov.epamsound.service;

import by.goncharov.epamsound.beans.Genre;
import by.goncharov.epamsound.dao.DaoException;
import by.goncharov.epamsound.dao.impl.GenreDaoImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * The type Genre service.
 * @author Goncharov Daniil
 * @see by.goncharov.epamsound.beans.Genre
 * @see GenreDaoImpl
 */
@Service
public class GenreService {
    @Autowired
    GenreDaoImpl genreDao;
    /**
     * Find genre id int.
     *
     * @param genre the genre
     * @return the int
     * @throws ServiceException the service exception
     */
    @Transactional
    public int findGenreId(final Genre genre) throws ServiceException {
        int id;
        try {
            id = genreDao.findGenreId(genre);
        } catch (DaoException e) {

            throw new ServiceException("Exception during genre id search", e);
        }
        return id;
    }

    /**
     * Find genres list.
     *
     * @return the list
     * @throws ServiceException the service exception
     */
    @Transactional
    public List<String> findGenres() throws ServiceException {
        try {
            return genreDao.findGenres();
        } catch (DaoException e) {
            throw new ServiceException("Exception during genre id search", e);
        }
    }
}
