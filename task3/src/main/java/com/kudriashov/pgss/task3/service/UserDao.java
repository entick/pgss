package com.kudriashov.pgss.task3.service;

import com.kudriashov.pgss.task3.domain.User;
import com.kudriashov.pgss.task3.util.DaoUtils;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import org.hibernate.query.Query;

import java.util.List;

public class UserDao {

    private static String FIND_ALL_QUERY = "FROM User";
    private static String FIND_BY_LOGIN_QUERY = "From User u WHERE u.login=:login";
    private static String FIND_BY_EMAIL_QUERY = "From User u WHERE u.email=:email";
    private static String FIND_BY_ID_QUERY = "From User u WHERE u.id=:id";

    public void create(User user) {
        Session session = DaoUtils.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            Long userId = (Long) session.save(user);
            tx.commit();
            user.setId(userId);
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            throw new RuntimeException(e);
        } finally {
            session.close();
        }

    }

    public void update(User user) {
        Session session = DaoUtils.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.update(user);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            throw new RuntimeException(e);
        } finally {
            session.close();
        }
    }

    public void remove(User user) {
        Session session = DaoUtils.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.delete(user);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            throw new RuntimeException(e);
        } finally {
            session.close();
        }
    }

    public List<User> findAll() {
        Session session = DaoUtils.getSessionFactory().openSession();
        Transaction tx = null;
        List<User> users = null;
        try {
            tx = session.beginTransaction();
            users = session.createQuery(FIND_ALL_QUERY).list();
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            throw new RuntimeException(e);
        } finally {
            session.close();
        }
        return users;
    }

    public User findByLogin(String login) {
        Session session = DaoUtils.getSessionFactory().openSession();
        Transaction tx = null;
        User user = null;
        try {
            tx = session.beginTransaction();
            Query query = session.createQuery(FIND_BY_LOGIN_QUERY);
            query.setParameter("login", login);
            user = (User) query.uniqueResult();
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            throw new RuntimeException(e);
        } finally {
            session.close();
        }
        return user;
    }

    public User findByEmail(String email) {
        Session session = DaoUtils.getSessionFactory().openSession();
        Transaction tx = null;
        User user = null;
        try {
            tx = session.beginTransaction();
            Query query = session.createQuery(FIND_BY_EMAIL_QUERY);
            query.setParameter("email", email);
            user = (User) query.uniqueResult();
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            throw new RuntimeException(e);
        } finally {
            session.close();
        }
        return user;
    }

    public User findById(Long id) {
        Session session = DaoUtils.getSessionFactory().openSession();
        Transaction tx = null;
        User user = null;
        try {
            tx = session.beginTransaction();
            Query query = session.createQuery(FIND_BY_ID_QUERY);
            query.setParameter("id", id);
            user = (User) query.uniqueResult();
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            throw new RuntimeException(e);
        } finally {
            session.close();
        }
        return user;
    }


}
