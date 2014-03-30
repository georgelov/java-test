/**
 * 
 */
package se.tain.exercise.refactor.service;

import java.util.List;

import se.tain.exercise.refactor.model.Person;

/**
 * @author George (georgiy.lovyagin@gmail.com)
 * @author Last changed by: George
 * @version Mar 23, 2014
 */
public interface AddressBookService {

    /**
     * @param name
     * @return
     * @throws ServiceUnavailale
     */
    public boolean hasMobile(String name) throws ServiceUnavailale;

    /**
     * @return
     * @throws ServiceUnavailale
     */
    public int getSize() throws ServiceUnavailale;

    /**
     * Get the given user's mobile phone number, or null if he doesn't have one.
     * 
     * @param name
     * @return
     * @throws ServiceUnavailale
     */
    public String getMobile(String name) throws ServiceUnavailale;

    /**
     * Returns all names in the book. Truncates to the given length.
     * 
     * @param maxLength
     * @return
     * @throws ServiceUnavailale
     */
    public List<String> getNames(int maxLength) throws ServiceUnavailale;

    /**
     * Returns all people who have mobile phone numbers.
     * 
     * @throws ServiceUnavailale
     */
    public List<Person> getPersonsWithMobile() throws ServiceUnavailale;
}
