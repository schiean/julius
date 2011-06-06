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

package julius.utilities;

import julius.benchmark.StopWatch;
import julius.validation.Assertions;

/**
 * TimeCountdown is a utility that helps creating an alternative to the JDK Timer class.
 * It's useful to implement something like: perform 'x' every 10 seconds
 * This could be done with a timer that is part of the JDK, but that would spawn a new thread
 * This class would be an alternative that does not involve extra threads
 * 
 * TimeCountdown countdown = TimeCountdown.toSeconds(10);
 * 
 * while(someCondition()){
 * 
 * 		if(countdown.isCompleted()){
 * 			x();
 * 			countdown.restart();
 * 		}
 * 		.....
 * 		sleep.milliseconds(100);
 * 		.....
 * }
 *  
 */
public class TimeCountdown {

	private final long milliseconds;
	private final StopWatch stopwatch;
	
	public static TimeCountdown toSeconds(final long seconds){
		return new TimeCountdown(1000 * seconds);
	}	
	
	public static TimeCountdown toMinutes(final long seconds){
		return toSeconds(60 * seconds);
	}
	
	public TimeCountdown(final long milliseconds){
		this.milliseconds = milliseconds;
		Assertions.argument.assertTrue(milliseconds > 0 , "the countdown time should be > 0");
		this.stopwatch = new StopWatch();
	}
	
	public void restart(){
		stopwatch.reset();
	}
	
	public boolean isCompleted(){
		return stopwatch.elapsedInMillis() > milliseconds;
	}
	
}
