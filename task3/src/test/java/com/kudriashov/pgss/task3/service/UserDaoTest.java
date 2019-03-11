package com.kudriashov.pgss.task3.service;

import com.kudriashov.pgss.task3.domain.Role;
import com.kudriashov.pgss.task3.domain.User;
import org.dbunit.Assertion;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class UserDaoTest extends AbstractDaoTest {

    private UserDao userDao;

    public UserDaoTest(String name) {
        super(name);
        userDao = new UserDao();
    }

    @Before
    public void setUp() throws Exception {
        beforeData = new FlatXmlDataSetBuilder()
                .build(Thread.currentThread().getContextClassLoader()
                        .getResourceAsStream("user_data/user-data.xml"));
        super.setUp();
        DatabaseOperation.CLEAN_INSERT
                .execute(tester.getConnection(), beforeData);
    }

    @Test
    public void testCreateUser() throws Exception {
        User user = new User();
        user.setLogin("Fourth");
        user.setPassword("Pwd");
        user.setEmail("fourth@email.com");
        Role role = new Role();
        role.setId(2L);
        role.setName("second");
        user.setRole(role);
        userDao.create(user);

        IDataSet expectedData = new FlatXmlDataSetBuilder()
                .build(Thread.currentThread().getContextClassLoader()
                        .getResourceAsStream("user_data/user-create.xml"));
        ITable expectedTable = expectedData.getTable("USERS");

        IDataSet actualData = tester.getConnection().createDataSet();
        ITable actualTable = actualData.getTable("USERS");

        Assertion.assertEquals(expectedTable, actualTable);
    }

    @Test
    public void testUpdateUser() throws Exception {
        User user = new User();
        user.setId(3L);
        user.setLogin("Fourth");
        user.setPassword("NewPwd");
        user.setEmail("fourth@email.com");

        Role role = new Role();
        role.setId(1L);
        role.setName("first");
        user.setRole(role);
        userDao.update(user);

        IDataSet expectedData = new FlatXmlDataSetBuilder()
                .build(Thread.currentThread().getContextClassLoader()
                        .getResourceAsStream("user_data/user-update.xml"));
        ITable expectedTable = expectedData.getTable("USERS");

        IDataSet actualData = tester.getConnection().createDataSet();
        ITable actualTable = actualData.getTable("USERS");

        Assertion.assertEquals(expectedTable, actualTable);
    }

    @Test
    public void testRemoveUser() throws Exception {
        User user = new User();
        user.setId(3L);
        userDao.remove(user);

        IDataSet expectedData = new FlatXmlDataSetBuilder()
                .build(Thread.currentThread().getContextClassLoader()
                        .getResourceAsStream("user_data/user-remove.xml"));
        ITable expectedTable = expectedData.getTable("USERS");

        IDataSet actualData = tester.getConnection().createDataSet();
        ITable actualTable = actualData.getTable("USERS");

        Assertion.assertEquals(expectedTable, actualTable);
    }

    @Test
    public void testFindAll() throws Exception {
        List<User> usersList = userDao.findAll();

        IDataSet expectedData = new FlatXmlDataSetBuilder()
                .build(Thread.currentThread().getContextClassLoader()
                        .getResourceAsStream("user_data/user-data.xml"));
        ITable expectedTable = expectedData.getTable("USERS");

        IDataSet actualData = tester.getConnection().createDataSet();
        ITable actualTable = actualData.getTable("USERS");

        Assertion.assertEquals(expectedTable, actualTable);
        Assert.assertEquals(expectedTable.getRowCount(), usersList.size());
    }

    @Test
    public void testFindByLogin() throws Exception {
        User user1 = userDao.findByLogin("First");
        User user2 = userDao.findByLogin("Second");
        User user3 = userDao.findByLogin("Third");

        ITable expectedTable = beforeData.getTable("USERS");
        Assert.assertEquals(
                Long.valueOf((String) expectedTable.getValue(0, "id")),
                user1.getId());
        Assert.assertEquals(
                Long.valueOf((String) expectedTable.getValue(1, "id")),
                user2.getId());
        Assert.assertEquals(
                Long.valueOf((String) expectedTable.getValue(2, "id")),
                user3.getId());
    }

    @Test
    public void testFindByEmail() throws Exception {
        User user1 = userDao.findByEmail("first@email.com");
        User user2 = userDao.findByEmail("second@email.com");
        User user3 = userDao.findByEmail("third@email.com");

        ITable expectedTable = beforeData.getTable("USERS");
        Assert.assertEquals(
                Long.valueOf((String) expectedTable.getValue(0, "id")),
                user1.getId());
        Assert.assertEquals(
                Long.valueOf((String) expectedTable.getValue(1, "id")),
                user2.getId());
        Assert.assertEquals(
                Long.valueOf((String) expectedTable.getValue(2, "id")),
                user3.getId());
    }
}
