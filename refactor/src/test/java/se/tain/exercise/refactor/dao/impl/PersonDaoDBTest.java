/**
 * 
 */
package se.tain.exercise.refactor.dao.impl;

import static org.junit.Assert.fail;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.management.MalformedObjectNameException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import se.tain.exercise.refactor.dao.PersonDao;
import se.tain.exercise.refactor.model.Person;

/**
 * @author George (georgiy.lovyagin@gmail.com)
 * @author Last changed by: George
 * @version Mar 23, 2014
 */
public class PersonDaoDBTest {

    private static final String TEST_PHONENUMBER = "+38099-0000000";

    private static final String TEST_NAME = "test name";

    private PersonDao personDao;

    private final Person person;

    @Mock
    private Connection connection;

    @Mock
    private PreparedStatement statement;

    @Mock
    private ResultSet rs;

    /**
     * @throws MalformedObjectNameException
     * 
     */
    public PersonDaoDBTest() throws MalformedObjectNameException {
        super();
        person = new Person(TEST_NAME, TEST_PHONENUMBER);
    }

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        toInvariant();
        personDao = new PersonDaoDB(connection);
    }

    /**
     * Test method for
     * {@link se.tain.exercise.refactor.dao.impl.PersonDaoDB#PersonDaoDB(java.sql.Connection)}
     * .
     * 
     * @throws SQLException
     */
    @Test(expected = IllegalArgumentException.class)
    public final void testPersonDaoDB() throws SQLException {
        Assert.assertNotNull(personDao);
        Mockito.when(connection.isValid(0)).thenReturn(false);
        new PersonDaoDB(connection);
    }

    /**
     * Test method for
     * {@link se.tain.exercise.refactor.dao.impl.PersonDaoDB#create(se.tain.exercise.refactor.model.Person)}
     * .
     * 
     * @throws SQLException
     */
    @Test
    public final void testCreate() throws SQLException {
        // happy path
        Mockito.when(statement.executeUpdate()).thenReturn(0);
        personDao.create(person);

        // NPE expected
        try {
            personDao.create(null);
            fail("NPE expected");
        } catch (NullPointerException e) {
        }

        // NPE should not be thrown in final block
        Mockito.when(connection.prepareStatement(Mockito.anyString()))
                .thenThrow(new SQLException());
        try {
            personDao.create(person);
            fail("SQLException expected");
        } catch (final SQLException e) {
        }

        // SQLException thrown for executeUpdate
        toInvariant();
        Mockito.when(connection.prepareStatement(Mockito.anyString()))
                .thenReturn(statement);
        Mockito.when(statement.executeUpdate()).thenThrow(new SQLException());
        try {
            personDao.create(person);
            fail("SQLException expected");
        } catch (final SQLException e) {
            Mockito.verify(statement).close();
        }
    }

    /**
     * Test method for
     * {@link se.tain.exercise.refactor.dao.impl.PersonDaoDB#findByName(java.lang.String)}
     * .
     * 
     * @throws Exception
     */
    @Test
    public final void testFindByName() throws Exception {
        Mockito.when(statement.executeQuery()).thenReturn(rs);
        personDao.findByName(null);

        // happy path
        Assert.assertNull(personDao.findByName(TEST_NAME));
        Mockito.when(rs.next()).thenReturn(true);
        final Person prsn = personDao.findByName(TEST_NAME);
        Assert.assertEquals(TEST_NAME, prsn.getName());
        Assert.assertEquals(TEST_PHONENUMBER, prsn.getPhoneNumber().getNumber());

        testCommonFind(new Testable() {

            @Override
            public void test() throws Exception {
                personDao.findByName(TEST_NAME);
            }
        });
    }

    /**
     * Test method for
     * {@link se.tain.exercise.refactor.dao.impl.PersonDaoDB#findAll()}.
     * 
     * @throws Exception
     */
    @Test
    public final void testFindAll() throws Exception {
        // happy path
        Mockito.when(statement.executeQuery()).thenReturn(rs);
        Assert.assertNotNull(personDao.findAll());
        Mockito.when(rs.next()).thenReturn(true).thenReturn(false);
        final List<Person> personList = personDao.findAll();
        Assert.assertEquals(TEST_NAME, personList.get(0).getName());
        Assert.assertEquals(TEST_PHONENUMBER, personList.get(0)
                .getPhoneNumber().getNumber());

        testCommonFind(new Testable() {

            @Override
            public void test() throws Exception {
                personDao.findAll();
            }
        });
    }

    /**
     * @throws SQLException
     */
    private void toInvariant() throws SQLException {
        Mockito.reset(connection);
        Mockito.reset(statement);
        Mockito.reset(rs);
        Mockito.when(connection.isValid(0)).thenReturn(true);
        Mockito.when(connection.prepareStatement(Mockito.anyString()))
                .thenReturn(statement);
        Mockito.when(rs.getString(PersonDaoDB.NAME_PARAM))
                .thenReturn(TEST_NAME);
        Mockito.when(rs.getString(PersonDaoDB.PHONE_NUMBER_PARAM)).thenReturn(
                TEST_PHONENUMBER);
    }

    /**
     * @throws Exception
     */
    private void testCommonFind(Testable testRunner) throws Exception {
        // NPE should not be thrown in final block for the rs
        toInvariant();
        Mockito.when(statement.executeQuery()).thenThrow(new SQLException());
        try {
            testRunner.test();
            fail("SQLException expected");
        } catch (final SQLException e) {
        }

        // NPE should not be thrown in final block for the statement
        toInvariant();
        Mockito.when(statement.executeQuery()).thenReturn(rs);
        Mockito.when(connection.prepareStatement(Mockito.anyString()))
                .thenThrow(new SQLException());
        try {
            testRunner.test();
            fail("SQLException expected");
        } catch (final SQLException e) {
        }

        // SQLException thrown for the next()
        toInvariant();
        Mockito.when(connection.prepareStatement(Mockito.anyString()))
                .thenReturn(statement);
        Mockito.when(statement.executeQuery()).thenReturn(rs);
        Mockito.when(rs.next()).thenThrow(new SQLException());
        try {
            testRunner.test();
        } catch (final SQLException e) {
            Mockito.verify(rs).close();
            Mockito.verify(statement).close();
        }
    }

    /**
     */
    private interface Testable {
        void test() throws Exception;
    }
}
