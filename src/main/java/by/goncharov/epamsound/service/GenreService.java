package by.goncharov.epamsound.service;

import by.goncharov.epamsound.beans.Genre;

import java.util.List;

public interface GenreService {
    void save(Genre genre);
    List<Genre> findGenres() throws ServiceException;
}
