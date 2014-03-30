/**
 * 
 */
package se.tain.exercise.multithreading.impl;

import org.junit.Assert;
import org.junit.Test;

import se.tain.exercise.multithreading.FibCalc;

/**
 * @author George (georgiy.lovyagin@gmail.com)
 * @author Last changed by: George
 * @version Mar 29, 2014
 */
public class FibCalcImplTest {

    private final FibCalc fibCalc = new FibCalcImpl();

    private final static int[] FIBONACHI_VALUES = { 0, 1, 1, 2, 3, 5 };
    private final static int MAX_OF_FIBONACHI = 5;

    /**
     * Test method for
     * {@link se.tain.exercise.multithreading.impl.FibCalcImpl#fib(int)}.
     */
    @Test
    public final void testFib() {
        for (int i = 0; i <= MAX_OF_FIBONACHI; i++) {
            Assert.assertEquals(FIBONACHI_VALUES[i], fibCalc.fib(i));
        }
    }

}
