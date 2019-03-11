package com.kudriashov.pgss.task3.service;

import com.kudriashov.pgss.task3.domain.Role;
import com.kudriashov.pgss.task3.util.DaoUtils;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import org.hibernate.query.Query;

public class RoleDao {

    private static String FIND_BY_NAME_QUERY = "FROM Role r WHERE r.name=:name";
    private static String FIND_BY_ID_QUERY = "FROM Role r WHERE r.id=:id";

    public void create(Role role) {
        Session session = DaoUtils.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            Long roleId = (Long) session.save(role);
            tx.commit();
            role.setId(roleId);
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            throw new RuntimeException(e);
        } finally {
            session.close();
        }
    }

    public void update(Role role) {
        Session session = DaoUtils.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.update(role);
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

    public void remove(Role role) {
        Session session = DaoUtils.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.delete(role);
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

    public Role findByName(String name) {
        Session session = DaoUtils.getSessionFactory().openSession();
        Transaction tx = null;
        Role role;
        try {
            tx = session.beginTransaction();
            Query query = session.createQuery(FIND_BY_NAME_QUERY);
            query.setParameter("name", name);
            role = (Role) query.uniqueResult();
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            throw new RuntimeException(e);
        } finally {
            session.close();
        }
        return role;
    }

    public Role findById(Long id) {
        Session session = DaoUtils.getSessionFactory().openSession();
        Transaction tx = null;
        Role role;
        try {
            tx = session.beginTransaction();
            Query query = session.createQuery(FIND_BY_ID_QUERY);
            query.setParameter("id", id);
            role = (Role) query.uniqueResult();
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            throw new RuntimeException(e);
        } finally {
            session.close();
        }
        return role;
    }


}
