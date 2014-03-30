/**
 * 
 */
package se.tain.exercise.multithreading.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import se.tain.exercise.multithreading.PerformanceTestResult;
import se.tain.exercise.multithreading.PerformanceTester;

/**
 * The test can run by command
 * {@code mvn clean package -e exec:java -Dexec.mainClass="se.tain.exercise.multithreading.impl.PerformanceTesterImpl" -Dexec.classpathScope="test" -Dexec.args="25 16 32"}
 * 
 * @author George (georgiy.lovyagin@gmail.com)
 * @author Last changed by: George
 * @version Mar 29, 2014
 */
public final class PerformanceTesterImpl implements PerformanceTester {

    private final static Log LOGGER = LogFactory
            .getLog(PerformanceTesterImpl.class);

    /**
     * @param args
     */
    public static void main(final String[] args) {
        LOGGER.trace("Params are: " + Arrays.toString(args));

        if (args == null || args.length < 3) {
            info();
            return;
        }

        int executionCount = 0;
        int numberOfThreads = 0;
        try {
            Integer.valueOf(args[0]);
            executionCount = Integer.valueOf(args[1]);
            numberOfThreads = Integer.valueOf(args[2]);
        } catch (NumberFormatException e1) {
            LOGGER.info("Expectes digits only!");
            info();
            return;
        }
        final PerformanceTester performanceTester = new PerformanceTesterImpl();
        final Runnable task = new Runnable() {

            @Override
            public void run() {
                new FibCalcImpl().fib(Integer.valueOf(args[0]));
            }
        };
        try {
            final PerformanceTestResult result = performanceTester
                    .runPerformanceTest(task, executionCount, numberOfThreads);
            LOGGER.info("Performance the next:\ntotalTime: "
                    + result.getTotalTime() + "\nminTime: "
                    + result.getMinTime() + "\nmaxTime: " + result.getMaxTime());
        } catch (final Exception e) {
            LOGGER.error(e);
        }
    }

    /**
     * 
     */
    private static void info() {
        LOGGER.info("the following arguments: <n> <calculationCount> <threadPoolSize>");
        LOGGER.info("   n = which fibonacci number to calculate");
        LOGGER.info("   calculationCount = how many fibonacci calculations to run in total during the test");
        LOGGER.info("   threadPoolSize = how many threads should be used to run the calculations");
        LOGGER.info("Example: 25 5 1 means \"calculate fib(25) five times using a single thread\".");
    }

    /**
     * 
     */
    private static final long DEFAULT_EXECUTABLE_TIME = 3200000;
    private static final long ACCURACY = 2;
    private final long maxExecutableTime;

    /**
     * @param maxExecutableTime
     */
    public PerformanceTesterImpl() {
        super();
        this.maxExecutableTime = DEFAULT_EXECUTABLE_TIME;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * se.tain.exercise.multithreading.PerformanceTester#runPerformanceTest(
     * java.lang.Runnable, int, int)
     */
    @Override
    public PerformanceTestResult runPerformanceTest(final Runnable task,
            final int executionCount, final int threadPoolSize)
            throws InterruptedException, ExecutionException, TimeoutException {
        LOGGER.info("Start the performance test for " + executionCount
                + " executions and " + threadPoolSize + " threads");
        final ExecutorService executor = Executors
                .newFixedThreadPool(threadPoolSize);
        final Set<Future<PerformanceTestResult>> set = new HashSet<Future<PerformanceTestResult>>();
        final Callable<PerformanceTestResult> callable = new Callable<PerformanceTestResult>() {

            @Override
            public PerformanceTestResult call() throws Exception {
                final ResultAccumulator result = new ResultAccumulator(0,
                        maxExecutableTime, 0);

                for (int i = 0; i < executionCount; i++) {
                    final long timeOfStart = System.currentTimeMillis();
                    task.run();
                    final long timeSpent = System.currentTimeMillis()
                            - timeOfStart;
                    result.addTotal(timeSpent);
                    result.addMin(timeSpent);
                    result.addMax(timeSpent);
                }
                return new PerformanceTestResult(result.getTotalTime(),
                        result.getMinTime(), result.getMaxTime());
            }

        };

        for (int i = 0; i < threadPoolSize; i++) {
            set.add(executor.submit(callable));
        }

        final List<PerformanceTestResult> list = new ArrayList<PerformanceTestResult>(
                executionCount);
        try {
            for (final Future<PerformanceTestResult> f : set) {
                list.add(f.get(maxExecutableTime * executionCount * ACCURACY,
                        TimeUnit.MILLISECONDS));
            }
        } finally {
            executor.shutdownNow();
        }
        return processResults(list);
    }

    /**
     * @param list
     * @return
     */
    private PerformanceTestResult processResults(
            final List<PerformanceTestResult> list) {
        final ResultAccumulator result = new ResultAccumulator(0,
                this.maxExecutableTime, 0);

        for (final PerformanceTestResult pResult : list) {
            result.addTotal(pResult.getTotalTime());
            result.addMin(pResult.getMinTime());
            result.addMax(pResult.getMaxTime());
        }

        return new PerformanceTestResult(result.getTotalTime(),
                result.getMinTime(), result.getMaxTime());
    }

    /**
     * The helper service for accumulating results
     */
    public static final class ResultAccumulator {
        private long totalTime;
        private long minTime;
        private long maxTime;

        /**
         * 
         */
        public ResultAccumulator() {
            super();
        }

        /**
         * @param totalTime
         * @param minTime
         * @param maxTime
         */
        public ResultAccumulator(final long totalTime, final long minTime,
                final long maxTime) {
            this.totalTime = totalTime;
            this.minTime = minTime;
            this.maxTime = maxTime;
        }

        /**
         * @return the totalTime
         */
        public long getTotalTime() {
            return totalTime;
        }

        /**
         * @param totalTime
         *            the totalTime to set
         */
        public void setTotalTime(final long totalTime) {
            this.totalTime = totalTime;
        }

        /**
         * @return the minTime
         */
        public long getMinTime() {
            return minTime;
        }

        /**
         * @param minTime
         *            the minTime to set
         */
        public void setMinTime(final long minTime) {
            this.minTime = minTime;
        }

        /**
         * @return the maxTime
         */
        public long getMaxTime() {
            return maxTime;
        }

        /**
         * @param maxTime
         *            the maxTime to set
         */
        public void setMaxTime(final long maxTime) {
            this.maxTime = maxTime;
        }

        /**
         * @param totalTime
         */
        public void addTotal(final long totalTime) {
            this.totalTime += totalTime;
        }

        /**
         * @param minTime
         */
        public void addMin(final long minTime) {
            this.minTime = Math.min(this.minTime, minTime);
        }

        /**
         * @param maxTime
         */
        public void addMax(final long maxTime) {
            this.maxTime = Math.max(this.maxTime, maxTime);
        }

        /*
         * (non-Javadoc)
         * 
         * @see java.lang.Object#toString()
         */
        @Override
        public String toString() {
            return "ResultTO [totalTime=" + totalTime + ", minTime=" + minTime
                    + ", maxTime=" + maxTime + "]";
        }

    }

}
