/**
 * 
 */
package se.tain.exercise.multithreading.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import se.tain.exercise.multithreading.FibCalc;

/**
 * @author George (georgiy.lovyagin@gmail.com)
 * @author Last changed by: George
 * @version Mar 29, 2014
 */
public final class FibCalcImpl implements FibCalc {

    private static final Log LOGGER = LogFactory.getLog(FibCalcImpl.class);

    /*
     * (non-Javadoc)
     * 
     * @see se.tain.exercise.multithreading.FibCalc#fib(int)
     */
    @Override
    public long fib(int n) {
        LOGGER.trace("FibCalcImpl.fib.start: " + n);
        final long fiboachiValue = Double.valueOf(
                Math.floor(Math.pow((1 + Math.sqrt(5)) / 2, n) / Math.sqrt(5)
                        + 0.5)).longValue();
        LOGGER.info("Fibonachi was calculated for: " + n + ", it's "
                + fiboachiValue);
        return fiboachiValue;
    }

}
