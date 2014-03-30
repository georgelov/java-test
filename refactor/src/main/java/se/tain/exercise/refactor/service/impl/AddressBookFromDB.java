package se.tain.exercise.refactor.service.impl;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import se.tain.exercise.refactor.dao.PersonDao;
import se.tain.exercise.refactor.dao.impl.PersonDaoDB;
import se.tain.exercise.refactor.model.Person;
import se.tain.exercise.refactor.service.AddressBookService;
import se.tain.exercise.refactor.service.ServiceUnavailale;

public class AddressBookFromDB implements AddressBookService {

    private final PersonDao personDao;
    private final Properties properties = new Properties();
    private static final Log LOGGER = LogFactory
            .getLog(AddressBookFromDB.class);
    private final String countryCode;
    private static final String PROPERTIES_FILE_NAME = "refactor.properties";
    private static final String COUNTRY_CODE_PROPERTY_NAME = "refactor.phonenumber.validation.code";

    /**
     * @param connectionString
     * @throws SQLException
     * @throws IOException
     */
    public AddressBookFromDB(final String connectionString)
            throws SQLException, IOException {
        final Connection connection = DriverManager.getConnection(
                "jdbc:oracle:thin:@prod", "admin", "beefhead");
        personDao = new PersonDaoDB(connection);
        properties.load(getClass().getResourceAsStream(PROPERTIES_FILE_NAME));
        countryCode = properties.getProperty(COUNTRY_CODE_PROPERTY_NAME);
    }

    @Override
    public boolean hasMobile(final String name) throws ServiceUnavailale {
        try {
            return isMobileValid(personDao.findByName(name));
        } catch (final Exception e) {
            LOGGER.error(e);
            throw new ServiceUnavailale(e);
        }
    }

    @Override
    public int getSize() throws ServiceUnavailale {
        List<Person> people = null;
        try {
            people = personDao.findAll();
            return people.size();
        } catch (final Exception e) {
            LOGGER.error(e);
            throw new ServiceUnavailale(e);
        }
    }

    @Override
    public String getMobile(final String name) throws ServiceUnavailale {
        Person person = null;
        try {
            person = personDao.findByName(name);
        } catch (final Exception e) {
            LOGGER.error(e);
            throw new ServiceUnavailale(e);
        }

        if (person != null && person.getPhoneNumber() != null) {
            return person.getPhoneNumber().getNumber();
        }
        return null;
    }

    @Override
    public List<String> getNames(final int maxLength) throws ServiceUnavailale {
        List<Person> people = null;
        try {
            people = personDao.findAll();
        } catch (final Exception e) {
            LOGGER.error(e);
            throw new ServiceUnavailale(e);
        }
        final List<String> names = new ArrayList<String>();
        if (people != null) {
            for (final Person person : people) {
                String name = person.getName();
                if (name.length() > maxLength) {
                    name = name.substring(0, maxLength);
                }
                names.add(name);
            }
        }
        return names;

    }

    @Override
    public List<Person> getPersonsWithMobile() throws ServiceUnavailale {
        List<Person> people = null;
        try {
            people = personDao.findAll();
        } catch (final Exception e) {
            LOGGER.error(e);
            throw new ServiceUnavailale(e);
        }

        final List<Person> filteredList = new ArrayList<Person>();
        for (final Person person : people) {
            if (isMobileValid(person)) {
                filteredList.add(person);
            }
        }

        return filteredList;
    }

    /**
     * @param person
     * @return
     */
    private boolean isMobileValid(final Person person) {
        return (person != null && person.getPhoneNumber() != null
                && person.getPhoneNumber().getNumber() != null && person
                .getPhoneNumber().getNumber().startsWith(countryCode));
    }

}
