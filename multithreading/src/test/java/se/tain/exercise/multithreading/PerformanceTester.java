package se.tain.exercise.multithreading;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

/**
 * @author Anonymous
 * @author Last changed by: George
 * @version Mar 29, 2014
 */
public interface PerformanceTester {

    /**
     * Runs a performance test of the given task.
     * 
     * @param task
     *            which task to do performance tests on
     * @param executionCount
     *            how many times the task should be executed in total
     * @param threadPoolSize
     *            how many threads to use
     * @throws TimeoutException
     * @throws ExecutionException
     */
    public PerformanceTestResult runPerformanceTest(Runnable task,
            int executionCount, int threadPoolSize)
            throws InterruptedException, ExecutionException, TimeoutException;
}
