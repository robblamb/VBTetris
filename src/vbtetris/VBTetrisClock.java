package vbtetris;

// http://www.csc.kth.se/utbildning/kth/kurser/DD1339/inda13/vtuppgift/Stopwatch.java

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * A simple Stopwatch utility for measuring time in milliseconds.
 *
 * @author  Stefan Nilsson
 * @version 2011-02-07
 */
public class VBTetrisClock {
	/**
	 * Time when start() was called. Contains a valid time
	 * only if the clock is running.
	 */
	private long startTime;

	/**
	 * Holds the total accumulated time since last reset.
	 * Does not include time since start() if clock is running.
	 */
	private long totalTime = 0;

	private boolean isRunning = false;

	/**
	 * Constructs a new Stopwatch. The new clock is not
	 * running and the total time is set to 0.
	 */
	public VBTetrisClock() {}

	/**
	 * Turns this clock on.
	 * Has no effect if the clock is already running.
	 *
	 * @return a reference to this Stopwatch.
	 */
	public VBTetrisClock start() {
		if (!isRunning) {
			isRunning = true;
			startTime = System.nanoTime();
		}
		return this;
	}

	/**
	 * Turns this clock off.
	 * Has no effect if the clock is not running.
	 *
	 * @return a reference to this Stopwatch.
	 */
	public VBTetrisClock stop() {
		if (isRunning) {
			totalTime += System.nanoTime() - startTime;
			isRunning = false;
		}
		return this;
	}

	/**
	 * Resets this clock.
	 * The clock is stopped and the total time is set to 0.
	 *
	 * @return a reference to this Stopwatch.
	 */
	public VBTetrisClock reset() {
		isRunning = false;
		totalTime = 0;
		return this;
	}

	/**
	 * Returns the total time that this clock has been running since
	 * last reset.
	 * Does not affect the running status of the clock; if the clock
	 * is running when this method is called, it continues to run.
	 *
	 * @return the time in milliseconds.
	 */
	public long milliseconds() {
		return nanoseconds() / 1000000;
	}

	public long seconds() {
		return nanoseconds() / 1000000000;
	}
	
	public double minutes() {
		return nanoseconds() / (double)60000000000L;
	}
	
	/**
	 * Returns the total time that this clock has been running since
	 * last reset.
	 * Does not affect the running status of the clock; if the clock
	 * is running when this method is called, it continues to run.
	 *
	 * @return the time in nanoseconds.
	 */
	public long nanoseconds() {
		return totalTime +
			(isRunning ? System.nanoTime() - startTime : 0);
	}

	/**
	 * Tests if this clock is running.
	 *
	 * @return <code>true</code> if this clock is running;
	 *		 <code>false</code> otherwise.
	 */
	public boolean isRunning() {
		return isRunning;
	}

	/**
	 * Returns a string description of this clock. The exact details
	 * of the representation are unspecified and subject to change,
	 * but this is typical: "25 ms (running)".
	 */
	@Override
	public String toString() {
		DateFormat outFormat = new SimpleDateFormat("HH:mm:ss");
		outFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
		Date d = new Date(milliseconds());
		return outFormat.format(d);
	}
	
}