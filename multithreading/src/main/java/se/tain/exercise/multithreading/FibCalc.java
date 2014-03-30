package se.tain.exercise.multithreading;

/**
 * A fibonacci calculator.
 */
public interface FibCalc {

	/**
	 * Calculates the given fibonacci number.
	 * Examples:
	 * fib(1) = 1    <br>
	 * fib(2) = 1    <br>
	 * fib(3) = 2    <br>
	 * fib(4) = 3    <br>
	 * fib(5) = 5    <br>
	 */
	public long fib(int n);
}
