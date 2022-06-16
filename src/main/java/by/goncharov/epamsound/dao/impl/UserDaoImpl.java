package by.goncharov.epamsound.dao.impl;

import by.goncharov.epamsound.beans.Comment;
import by.goncharov.epamsound.beans.Track;
import by.goncharov.epamsound.beans.User;
import by.goncharov.epamsound.dao.DaoException;
import by.goncharov.epamsound.util.HibernateUtil;
import org.hibernate.Transaction;
import org.hibernate.Session;
import by.goncharov.epamsound.dao.UserDao;
import java.util.List;
import java.util.Optional;

/**
 * Class for user DAO.
 * @author Goncharov Daniil
 * @version 1.0
 * @see UserDao
 * @see User
 * @see Transaction
 * @see Comment
 */
@SuppressWarnings("Duplicates")
public class UserDaoImpl implements UserDao {

    @Override
    public void add(final User user) throws DaoException {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession();) {
            transaction = session.beginTransaction();
            session.save(user);
            transaction.commit();
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }

    @Override
    public void remove(final int id) throws DaoException {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession();) {
            transaction = session.beginTransaction();
            User user = session.get(User.class, id);
            session.delete(user);
            transaction.commit();
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }
    @Override
    public void update(final User user) throws DaoException {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession();) {
            transaction = session.beginTransaction();
            session.update(user);
            transaction.commit();
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }

    @Override
    public List<User> findAll() throws DaoException {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession();) {
            transaction = session.beginTransaction();
            List<User> userList = session.createQuery("FROM User").list();
            transaction.commit();
            return userList;
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }

    @Override
    public Optional<User> findById(final int id) throws DaoException {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession();) {
            transaction = session.beginTransaction();
            User user = session.get(User.class, id);
            transaction.commit();
            return Optional.ofNullable(user);
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }

    public String findPassword(final String login) throws DaoException {
        String password;
        Transaction transaction;
        try (Session session = HibernateUtil.getSessionFactory().openSession();) {
            transaction = session.beginTransaction();
            User user = session.createQuery("FROM User WHERE login = :login", User.class)
                    .setParameter("login", login)
                    .uniqueResult();
            password = user.getPassword();
            transaction.commit();
            return password;
        } catch (Exception e) {
            throw new DaoException(e);
        }

    }

    @Override
    public User findByEmail(final String email) throws DaoException {
        Transaction transaction;
        try (Session session = HibernateUtil.getSessionFactory().openSession();) {
            transaction = session.beginTransaction();
            User user = session.createQuery("FROM User WHERE email = :email", User.class).setParameter("email", email).uniqueResult();
            transaction.commit();
            return user;
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }

    @Override
    public User findByLogin(final String login) throws DaoException {
        try (Session session = HibernateUtil.getSessionFactory().openSession();) {
            session.beginTransaction();
            User user = session.createQuery("FROM User WHERE login = :login", User.class)
                    .setParameter("login", login).uniqueResult();
            return user;
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }

    public void changeCash(final int userId, final Double cash)
            throws DaoException {
        Transaction transaction;
        try (Session session = HibernateUtil.getSessionFactory().openSession();) {
            transaction = session.beginTransaction();
            User user = session.get(User.class, userId);
            user.setCash(cash);
            session.update(user);
            transaction.commit();
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }

    public void addComment(final int userId, final String text, final int trackId)
            throws DaoException {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession();) {
            transaction = session.beginTransaction();
            User user = session.get(User.class, userId);
            Track track = session.get(Track.class, trackId);
            Comment comment = new Comment(text, user, track);
            session.save(comment);
            transaction.commit();
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }

    public void changeEmail(final int userId, final String newEmail)
            throws DaoException {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession();) {
            transaction = session.beginTransaction();
            User user = session.get(User.class, userId);
            user.setEmail(newEmail);
            session.update(user);
            transaction.commit();
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }

    public void changeLogin(final int userId, final String newLogin)
            throws DaoException {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession();) {
            transaction = session.beginTransaction();
            User user = session.get(User.class, userId);
            user.setLogin(newLogin);
            session.update(user);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DaoException(e);
        }
    }

    @Override
    public void changePassword(final int userId, final String newPass)
            throws DaoException {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession();) {
            transaction = session.beginTransaction();
            User user = session.get(User.class, userId);
            user.setPassword(newPass);
            session.update(user);
            transaction.commit();
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }

    /**
     * Find cash double.
     *
     * @param userId the user id
     * @return the double
     * @throws DaoException the dao exception
     */
    public double findCash(final int userId) throws DaoException {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession();) {
            transaction = session.beginTransaction();
            User user = session.get(User.class, userId);
            double cash = user.getCash();
            transaction.commit();
            return cash;
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }

    @Override
    public void setBonus(final int userId, final int bonus)
            throws DaoException {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession();) {
            transaction = session.beginTransaction();
            User user = session.get(User.class, userId);
            user.setDiscount(bonus);
            session.update(user);
            transaction.commit();
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }
}
