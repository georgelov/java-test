package se.tain.exercise.refactor.model;

import javax.management.MalformedObjectNameException;

/**
 * @author Anonymous
 * @author Last changed by: George (georgiy.lovyagin@gmail.com)
 * @version Mar 23, 2014
 */
public class Person {

    private String name;

    private PhoneNumber phoneNumber;

    /**
     * @param name
     * @param phoneNumber
     * @throws MalformedObjectNameException
     */
    public Person(final String name, final String phoneNumber)
            throws MalformedObjectNameException {
        this.name = name;
        this.phoneNumber = new PhoneNumber(phoneNumber);
    }

    /**
     * @param name
     * @param phoneNumber
     */
    public Person(final String name, final PhoneNumber phoneNumber) {
        this.name = name;
        this.phoneNumber = phoneNumber;
    }

    /**
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     * @param name
     */
    public void setName(final String name) {
        this.name = name;
    }

    /**
     * @return
     */
    public PhoneNumber getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * @param phoneNumber
     */
    public void setPhoneNumber(final PhoneNumber phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "Person [name=" + name + ", phoneNumber=" + phoneNumber + "]";
    }

}
