package com.kudriashov.pgss.task3.service;

import com.kudriashov.pgss.task3.domain.Role;
import org.dbunit.Assertion;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class RoleDaoTest extends AbstractDaoTest {

    private RoleDao roleDao;

    public RoleDaoTest(String name) {
        super(name);
        roleDao = new RoleDao();
    }

    @Before
    public void setUp() throws Exception {
        beforeData = new FlatXmlDataSetBuilder()
                .build(Thread.currentThread().getContextClassLoader()
                        .getResourceAsStream("role_data/role-data.xml"));
        super.setUp();
        DatabaseOperation.CLEAN_INSERT
                .execute(tester.getConnection(), beforeData);
    }

    @Test
    public void testCreateRole() throws Exception {
        Role role = new Role();
        Role role1 = new Role();

        role.setName("third");
        role1.setName("fourth");

        roleDao.create(role);
        roleDao.create(role1);

        IDataSet expectedData = new FlatXmlDataSetBuilder()
                .build(Thread.currentThread().getContextClassLoader()
                        .getResourceAsStream("role_data/role-data-create.xml"));
        ITable expectedTable = expectedData.getTable("ROLES");

        IDataSet actualData = tester.getConnection().createDataSet();
        ITable actualTable = actualData.getTable("ROLES");

        Assertion.assertEquals(expectedTable, actualTable);
    }

    @Test
    public void testUpdateRole() throws Exception {
        Role role = new Role();
        role.setName("third");
        role.setId(1L);
        roleDao.update(role);

        Role role1 = new Role();
        role1.setName("fourth");
        role1.setId(2L);
        roleDao.update(role1);

        IDataSet expectedData = new FlatXmlDataSetBuilder()
                .build(Thread.currentThread().getContextClassLoader()
                        .getResourceAsStream("role_data/role-data-update.xml"));
        ITable expectedTable = expectedData.getTable("ROLES");

        IDataSet actualData = tester.getConnection().createDataSet();
        ITable actualTable = actualData.getTable("ROLES");

        Assertion.assertEquals(expectedTable, actualTable);
    }

    @Test
    public void testRemoveRole() throws Exception {
        Role role = new Role();
        role.setId(1L);
        roleDao.remove(role);

        IDataSet expectedData = new FlatXmlDataSetBuilder()
                .build(Thread.currentThread().getContextClassLoader()
                        .getResourceAsStream("role_data/role-data-remove.xml"));
        ITable expectedTable = expectedData.getTable("ROLES");

        IDataSet actualData = tester.getConnection().createDataSet();
        ITable actualTable = actualData.getTable("ROLES");

        Assertion.assertEquals(expectedTable, actualTable);
    }

    @Test
    public void testFindByName() throws Exception {
        Role role1 = roleDao.findByName("first");
        Role role2 = roleDao.findByName("second");

        ITable expectedTable = beforeData.getTable("ROLES");
        Assert.assertEquals(
                Long.valueOf((String) expectedTable.getValue(0, "id")),
                role1.getId());
        Assert.assertEquals(
                Long.valueOf((String) expectedTable.getValue(1, "id")),
                role2.getId());
    }

    @Test
    public void testFindById() throws Exception {
        Role role1 = roleDao.findById(1L);
        Role role2 = roleDao.findById(2L);

        ITable expectedTable = beforeData.getTable("ROLES");
        Assert.assertEquals(expectedTable.getValue(0, "name"), role1.getName());
        Assert.assertEquals(expectedTable.getValue(1, "name"), role2.getName());
    }
}
