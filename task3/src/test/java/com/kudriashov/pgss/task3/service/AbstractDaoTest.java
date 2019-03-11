package com.kudriashov.pgss.task3.service;

import com.kudriashov.pgss.task3.util.DaoUtils;
import org.dbunit.DBTestCase;
import org.dbunit.IDatabaseTester;
import org.dbunit.JdbcDatabaseTester;
import org.dbunit.PropertiesBasedJdbcDatabaseTester;
import org.dbunit.dataset.IDataSet;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.Properties;

public class AbstractDaoTest extends DBTestCase {
    protected IDatabaseTester tester;
    private Properties prop;
    protected IDataSet beforeData;
    protected static String propFile = "db.hibernate.config.properties";

    @Before
    public void setUp() throws Exception {
        tester = new JdbcDatabaseTester(prop.getProperty("db.driver"),
                prop.getProperty("db.url"), prop.getProperty("db.username"),
                prop.getProperty("db.password"), prop.getProperty("db.schema"));
    }

    public AbstractDaoTest(String name) {
        super(name);
        prop = new Properties();
        try {
            prop.load(Thread.currentThread().getContextClassLoader()
                    .getResourceAsStream(propFile));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.setProperty(
                PropertiesBasedJdbcDatabaseTester.DBUNIT_DRIVER_CLASS,
                prop.getProperty("db.driver"));
        System.setProperty(
                PropertiesBasedJdbcDatabaseTester.DBUNIT_CONNECTION_URL,
                prop.getProperty("db.url"));
        System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_USERNAME,
                prop.getProperty("db.username"));
        System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_PASSWORD,
                prop.getProperty("db.password"));
        System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_SCHEMA,
                prop.getProperty("db.schema"));
    }

    @Override
    protected IDataSet getDataSet() {
        return beforeData;
    }

    @Test
    public void testDataBaseConnection() throws Exception {
        Assert.assertNotNull(tester.getConnection());
        Assert.assertNotNull(
                DaoUtils.getSessionFactory().openSession());
    }

}
