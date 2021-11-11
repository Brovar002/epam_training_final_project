package by.goncharov.epamsound.service;

import by.goncharov.epamsound.dao.DAOException;
import by.goncharov.epamsound.dao.GenreDAO;
import by.goncharov.epamsound.manager.ConnectionPool;
import by.goncharov.epamsound.manager.ProxyConnection;
import java.util.List;

public class GenreService {
    public int findGenreId(final String genre) throws ServiceException {
        int id;
        ProxyConnection connection = ConnectionPool.getInstance()
                .getConnection();
        GenreDAO genreDAO = new GenreDAO(connection);
        try {
            id = genreDAO.findGenreId(genre);
        } catch (DAOException e) {

            throw new ServiceException("Exception during genre id search", e);
        } finally {
            genreDAO.closeConnection(connection);
        }
        return id;
    }
    public List<String> findGenres() throws ServiceException {
        ProxyConnection connection = ConnectionPool.getInstance()
                .getConnection();
        GenreDAO genreDAO = new GenreDAO(connection);
        try {
            return genreDAO.findGenres();
        } catch (DAOException e) {
            throw new ServiceException("Exception during genre id search", e);
        } finally {
            genreDAO.closeConnection(connection);
        }
    }
}
