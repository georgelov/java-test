/**
 * 
 */
package se.tain.exercise.refactor.model;

import javax.management.MalformedObjectNameException;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author George (georgiy.lovyagin@gmail.com)
 * @author Last changed by: George
 * @version Mar 23, 2014
 */
public class PhoneNumberTest {

    private static final String CORRECT_NUMBER = "+38099-0000000";
    private static final String NEXT_CORRECT_NUMBER = "+38099-0000001";

    /**
     * Test method for
     * {@link se.tain.exercise.refactor.model.PhoneNumber#PhoneNumber(java.lang.String)}
     * .
     * 
     * @throws MalformedObjectNameException
     */
    @Test
    public void testPhoneNumber() throws MalformedObjectNameException {
        new PhoneNumber(null);
        final PhoneNumber phoneNumber = new PhoneNumber(CORRECT_NUMBER);
        Assert.assertEquals(CORRECT_NUMBER, phoneNumber.getNumber());
    }

    /**
     * Test method for
     * {@link se.tain.exercise.refactor.model.PhoneNumber#setNumber(java.lang.String)}
     * .
     * 
     * @throws MalformedObjectNameException
     */
    @Test
    public void testSetNumber() throws MalformedObjectNameException {
        final PhoneNumber phoneNumber = new PhoneNumber(CORRECT_NUMBER);
        Assert.assertEquals(CORRECT_NUMBER, phoneNumber.getNumber());
        phoneNumber.setNumber(NEXT_CORRECT_NUMBER);
        Assert.assertEquals(NEXT_CORRECT_NUMBER, phoneNumber.getNumber());
    }

    /**
     * Test method for
     * {@link se.tain.exercise.refactor.model.PhoneNumber#toString()}.
     * 
     * @throws MalformedObjectNameException
     */
    @Test
    public void testToString() throws MalformedObjectNameException {
        final PhoneNumber phoneNumber = new PhoneNumber(CORRECT_NUMBER);
        Assert.assertTrue(phoneNumber.toString().contains(CORRECT_NUMBER));
    }

}
