package com.kudriashov.pgss.task3.util;

import com.kudriashov.pgss.task3.domain.Role;
import com.kudriashov.pgss.task3.domain.User;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class DaoUtils {

    private static SessionFactory sessionFactory;

    static {
        Configuration config = new Configuration();
        config.addAnnotatedClass(Role.class);
        config.addAnnotatedClass(User.class);
        sessionFactory = config.configure().buildSessionFactory();
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }




}
