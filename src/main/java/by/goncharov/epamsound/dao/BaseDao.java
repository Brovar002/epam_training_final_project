package by.goncharov.epamsound.dao;

import by.goncharov.epamsound.beans.Entity;

import java.util.List;
import java.util.Optional;

/**
 * The interface Base dao.
 *
 * @param <K> the type parameter
 * @param <T> the type parameter
 * @author Goncharov Daniil
 */
public interface BaseDao<K, T extends Entity> {
    /**
     * Find all list.
     *
     * @return the list
     * @throws DaoException the dao exception
     */
    List<T> findAll() throws DaoException;

    /**
     * Find by id optional.
     *
     * @param id the id
     * @return the optional
     * @throws DaoException the dao exception
     */
    Optional<T> findById(K id) throws DaoException;

    /**
     * Add boolean.
     *
     * @param entity the entity
     * @return the boolean
     * @throws DaoException the dao exception
     */
    void add(T entity) throws DaoException;

    /**
     * Remove by id boolean.
     *
     * @param id the id
     * @return the boolean
     * @throws DaoException the dao exception
     */
    void remove(K id) throws DaoException;

    /**
     * Update boolean.
     *
     * @param entity the entity
     * @return the boolean
     * @throws DaoException the dao exception
     */
    void update(T entity) throws DaoException;
}
