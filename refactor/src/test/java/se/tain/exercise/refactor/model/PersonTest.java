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
public class PersonTest {

    private static final String TEST_NUMBER = "+38099-0000000";
    private static final String TEST_NAME = "TEST_NAME";
    private static final String NEXT_TEST_NAME = "another test name";
    private static final String NEXT_TEST_NUMBER = "+38099-0000001";
    private static final String TEST_WRONG_NUMBER = "wrong number";

    /**
     * Test method for
     * {@link se.tain.exercise.refactor.model.Person#Person(java.lang.String, se.tain.exercise.refactor.model.PhoneNumber)}
     * .
     * 
     * @throws MalformedObjectNameException
     */
    @Test
    public void testPerson() throws MalformedObjectNameException {
        new PhoneNumber(null);
        final PhoneNumber phoneNumber = new PhoneNumber(TEST_NUMBER);
        final Person person = new Person(TEST_NAME, phoneNumber);
        Assert.assertEquals(TEST_NAME, person.getName());
        Assert.assertEquals(phoneNumber, person.getPhoneNumber());
    }

    /**
     * Test method for {@link
     * se.tain.exercise.refactor.model.Person#Person(java.lang.String,
     * java.lang.String} .
     * 
     * @throws MalformedObjectNameException
     */
    @Test
    public void testPersonStringString() throws MalformedObjectNameException {
        new Person(null, (String) null);
        new Person(null, (PhoneNumber) null);
        new Person(TEST_NAME, TEST_WRONG_NUMBER);
        final Person person = new Person(TEST_NAME, TEST_NUMBER);
        Assert.assertEquals(TEST_NAME, person.getName());
        Assert.assertNotNull("person.getPhoneNumber() MUST NOT BE NULL",
                person.getPhoneNumber());
        Assert.assertEquals(TEST_NUMBER, person.getPhoneNumber().getNumber());
    }

    /**
     * Test method for
     * {@link se.tain.exercise.refactor.model.Person#setName(java.lang.String)}.
     * 
     * @throws MalformedObjectNameException
     */
    @Test
    public void testSetName() throws MalformedObjectNameException {
        final Person person = new Person(TEST_NAME,
                new PhoneNumber(TEST_NUMBER));
        person.setName(NEXT_TEST_NAME);
        Assert.assertEquals(NEXT_TEST_NAME, person.getName());
    }

    /**
     * Test method for
     * {@link se.tain.exercise.refactor.model.Person#setPhoneNumber(se.tain.exercise.refactor.model.PhoneNumber)}
     * .
     * 
     * @throws MalformedObjectNameException
     */
    @Test
    public void testSetPhoneNumber() throws MalformedObjectNameException {
        final Person person = new Person(TEST_NAME,
                new PhoneNumber(TEST_NUMBER));
        final PhoneNumber phoneNumber = new PhoneNumber(NEXT_TEST_NUMBER);
        person.setPhoneNumber(phoneNumber);
        Assert.assertEquals(phoneNumber, person.getPhoneNumber());
    }

    /**
     * Test method for {@link se.tain.exercise.refactor.model.Person#toString()}
     * .
     * 
     * @throws MalformedObjectNameException
     */
    @Test
    public void testToString() throws MalformedObjectNameException {
        final Person person = new Person(TEST_NAME,
                new PhoneNumber(TEST_NUMBER));
        Assert.assertTrue(person.toString().contains(TEST_NAME));
        Assert.assertTrue(person.toString().contains(TEST_NUMBER));
    }

}
