package se.tain.exercise.refactor.model;

import javax.management.MalformedObjectNameException;

/**
 * @author Anonymous
 * @author Last changed by: George (georgiy.lovyagin@gmail.com)
 * @version Mar 23, 2014
 */
public class PhoneNumber {
    private String number;

    /**
     * @param number
     * @throws MalformedObjectNameException
     */
    public PhoneNumber(final String number) {
        this.setNumber(number);
    }

    /**
     * @return
     */
    public String getNumber() {
        return number;
    }

    /**
     * @param number
     *            the number to set
     */
    public void setNumber(final String number) {
        this.number = number;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "PhoneNumber [number=" + getNumber() + "]";
    }

}
