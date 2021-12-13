package by.goncharov.epamsound.dao.impl;

import by.goncharov.epamsound.beans.Genre;
import by.goncharov.epamsound.dao.*;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SuppressWarnings("Duplicates")
public class GenreDaoImpl implements GenreDao {
    private static final String SQL_INSERT_GENRE = "INSERT INTO"
            + " genre (`genre`) VALUES (?);";
    private static final String SQL_SELECT_GENRE_ID = "SELECT id"
            + " FROM genre WHERE genre=?";
    private static final String SQL_SELECT_GENRES = "SELECT `genre` FROM genre";
    @Override
    public int addGenre(final String genre) throws DaoException {
        try (Transaction connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_INSERT_GENRE)) {
            statement.setString(1, genre.toUpperCase());
            statement.executeUpdate();
            return findGenreId(genre);
        } catch (SQLException e) {
            throw new DaoException("Exception during genre addition ", e);
        }
    }

    @Override
    public int findGenreId(final String genre) throws DaoException {
        int id;
        try (Transaction connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_SELECT_GENRE_ID)) {
            statement.setString(1, genre.toUpperCase());
            ResultSet set = statement.executeQuery();
            if (set.next()) {
                id = set.getInt("id");
            } else {
                id = addGenre(genre);
            }
        } catch (SQLException e) {
            throw new DaoException("Exception during genre id search", e);
        }
        return id;
    }

    @Override
    public List<String> findGenres() throws DaoException {
        List<String> genreList = new ArrayList<>();
        try (Transaction connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_SELECT_GENRES)) {
            ResultSet set = statement.executeQuery();
            while (set.next()) {
                genreList.add(set.getString("genre"));
            }
        } catch (SQLException e) {
            throw new DaoException("Exception during genre id search", e);
        }
        return genreList;
    }

    @Override
    public List<Genre> findAll() throws DaoException {
        return null;
    }

    @Override
    public Optional<Genre> findById(Long id) throws DaoException {
        return Optional.empty();
    }

    @Override
    public boolean add(Genre entity) throws DaoException {
        return false;
    }

    @Override
    public boolean removeById(Long id) throws DaoException {
        return false;
    }

    @Override
    public boolean update(Genre entity) throws DaoException {
        return false;
    }
}
