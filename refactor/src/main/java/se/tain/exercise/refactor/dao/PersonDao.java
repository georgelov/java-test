/**
 * 
 */
package se.tain.exercise.refactor.dao;

import java.sql.SQLException;
import java.util.List;

import javax.management.MalformedObjectNameException;

import se.tain.exercise.refactor.model.Person;

/**
 * @author George (georgiy.lovyagin@gmail.com)
 * @author Last changed by: George
 * @version Mar 23, 2014
 */
public interface PersonDao {

    /**
     * @param person
     * @throws SQLException
     */
    void create(Person person) throws SQLException;

    /**
     * @param name
     * @return
     * @throws SQLException
     * @throws MalformedObjectNameException
     */
    Person findByName(String name) throws SQLException,
            MalformedObjectNameException;

    /**
     * @return
     * @throws MalformedObjectNameException
     * @throws SQLException
     */
    List<Person> findAll() throws MalformedObjectNameException, SQLException;
}
