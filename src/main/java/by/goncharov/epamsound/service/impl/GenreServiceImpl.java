package by.goncharov.epamsound.service.impl;

import by.goncharov.epamsound.beans.Genre;
import by.goncharov.epamsound.dao.GenreRepository;
import by.goncharov.epamsound.service.GenreService;
import by.goncharov.epamsound.service.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * The type Genre service.
 * @author Goncharov Daniil
 * @see by.goncharov.epamsound.beans.Genre
 * @see Genre
 */
@Service
public class GenreServiceImpl implements GenreService {
    @Autowired
    GenreRepository genreRepository;

    @Override
    public void save(final Genre genre) {
        genreRepository.save(genre);
    }

    /**
     * Find genres list.
     *
     * @return the list
     * @throws ServiceException the service exception
     */

    @Override
    public List<Genre> findGenres() throws ServiceException {
        return genreRepository.findAll();
    }
}
