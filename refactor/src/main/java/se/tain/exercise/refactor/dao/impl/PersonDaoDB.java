package se.tain.exercise.refactor.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.management.MalformedObjectNameException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import se.tain.exercise.refactor.dao.PersonDao;
import se.tain.exercise.refactor.model.Person;

public class PersonDaoDB implements PersonDao {

    /**
     * 
     */
    static final String PHONE_NUMBER_PARAM = "phoneNumber";

    /**
     * 
     */
    static final String NAME_PARAM = "name";

    /**
     * 
     */
    private static final String APOSTROPHE = "'";

    /**
     * 
     */
    private static final String SELECT_BY_NAME_SQL = "select * from AddressEntry where name = ";

    /**
     * 
     */
    static final String INSERT_SQL = "insert into AddressEntry values (?, ?)";

    private static final String ERROR_MESSAGE = "Connection MUST BE valid";

    private static final Log LOGGER = LogFactory.getLog(PersonDaoDB.class);
    private final Connection connection;

    /**
     * @param connection
     * @throws SQLException
     */
    public PersonDaoDB(final Connection connection) throws SQLException {
        LOGGER.trace("PersonDaoDB.instatiate object for connection.start");
        if (connection == null || !connection.isValid(0)) {
            LOGGER.error("Can't create object for connection: " + connection);
            throw new IllegalArgumentException(ERROR_MESSAGE);
        }
        this.connection = connection;
    }

    @Override
    public void create(final Person person) throws SQLException {
        LOGGER.trace("PersonDaoDB.create.start: " + person);
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(INSERT_SQL);
            statement.setString(1, person.getName());
            statement.setString(2, person.getPhoneNumber().getNumber());
            statement.executeUpdate();
            LOGGER.info("Person was created: " + person);
        } finally {
            if (statement != null) {
                statement.close();
                LOGGER.trace("PersonDaoDB.create.statement.close");
            }
        }
    }

    /**
     * Looks up the given person, null if not found.
     * 
     * @throws SQLException
     * @throws MalformedObjectNameException
     */
    @Override
    public Person findByName(final String name) throws SQLException,
            MalformedObjectNameException {
        LOGGER.trace("PersonDaoDB.findByName.start: " + name);
        PreparedStatement statement = null;
        ResultSet rs = null;
        Person person = null;
        try {
            statement = connection.prepareStatement(SELECT_BY_NAME_SQL
                    + APOSTROPHE + name + APOSTROPHE);
            rs = statement.executeQuery();
            if (rs.next()) {
                person = toPerson(rs);
            }
            LOGGER.info("person was found: " + person);
        } finally {
            if (rs != null) {
                rs.close();
                LOGGER.trace("PersonDaoDB.findByName.rs.close");
            }
            if (statement != null) {
                statement.close();
                LOGGER.trace("PersonDaoDB.findByName.statement.close");
            }
        }

        return person;
    }

    @Override
    public List<Person> findAll() throws MalformedObjectNameException,
            SQLException {
        LOGGER.trace("PersonDaoDB.findAll.start");
        PreparedStatement statement = null;
        ResultSet rs = null;
        final List<Person> personList = new ArrayList<Person>();

        try {
            statement = connection
                    .prepareStatement("select * from AddressEntry");

            rs = statement.executeQuery();
            while (rs.next()) {
                personList.add(toPerson(rs));
            }
            LOGGER.info("person's list size: " + personList.size());
        } finally {
            if (rs != null) {
                rs.close();
                LOGGER.trace("PersonDaoDB.findAll.rs.close");
            }
            if (statement != null) {
                statement.close();
                LOGGER.trace("PersonDaoDB.findAll.statement.close");
            }
        }

        return personList;
    }

    /**
     * @param rs
     * @return
     * @throws MalformedObjectNameException
     * @throws SQLException
     */
    private Person toPerson(final ResultSet rs)
            throws MalformedObjectNameException, SQLException {
        return new Person(rs.getString(NAME_PARAM),
                rs.getString(PHONE_NUMBER_PARAM));
    }

}
