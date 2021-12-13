package by.goncharov.epamsound.service;

import by.goncharov.epamsound.dao.DaoException;
import by.goncharov.epamsound.dao.impl.GenreDaoImpl;

import java.util.List;

public class GenreService {
    public int findGenreId(final String genre) throws ServiceException {
        int id;
        GenreDaoImpl genreDao = new GenreDaoImpl();
        try {
            id = genreDao.findGenreId(genre);
        } catch (DaoException e) {

            throw new ServiceException("Exception during genre id search", e);
        }
        return id;
    }
    public List<String> findGenres() throws ServiceException {
        GenreDaoImpl genreDao = new GenreDaoImpl();
        try {
            return genreDao.findGenres();
        } catch (DaoException e) {
            throw new ServiceException("Exception during genre id search", e);
        }
    }
}
