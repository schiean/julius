/**
 * Copyright 2011 AJG van Schie
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package julius.benchmark;

import julius.validation.Assertions;

/**
 * simple stopwatch implementation
 * 
 * supports multiple start/stop sequences to accumulate the elapsed time 
 * this can be useful to measure the total amount of time in a method or block
 * FEATURE Calculate the average for multiple start/stop sequences
 */
public class StopWatch {

	private long start;
	private long stop;
	private long elapsed;
	
	/**
	 * Constructor it is without auto-start
	 */
	public StopWatch(){
		reset();
	}

	/**
	 * resets the stopwatch for new measurements
	 */
	public final void reset() {
		start = 0;
		stop = 0;
		elapsed = 0;
	}
	
	/**
	 * starts the timing (or resumes)
	 */
	public void start(){
		start = System.currentTimeMillis();
	}
	
	/**
	 * Convenience method/alias for start, because start() is already usable for resuming
	 */
	public void resume(){
		start();
	}
	
	/**
	 * stop or pause the timing
	 * @return the time between start and stop (or the total time between start1-stop1, start2-stop2 etc.)
	 */
	public long stop(){
		stop = System.currentTimeMillis();
		Assertions.state.assertTrue(start > 0, "first call start, then stop");
		elapsed = calculateProgress(stop);
		return elapsed;
	}

	/**
	 * @return time between 'start' and 'now' (+ elapsed in case the timer was resumed)
	 */
	public long currentProgress(){
		return calculateProgress(System.currentTimeMillis());
	}
	
	/**
	 * call after stop to get the elapsed time in millis
	 * or use currentProgress
	 * @return
	 */
	public long elapsedInMillis(){
		Assertions.state.assertTrue(elapsed>0, "first call 'start', then 'stop', then 'elapsed'");
		return elapsed;
	}
	
	/**
	 * call after stop to get the elapsed time in millis
	 * @return
	 */
	public long elapsedInSeconds(){
		return elapsedInMillis() / 1000;
	}
	
	private long calculateProgress(final long end) {
		return end - start + elapsed;
	}
	
}
