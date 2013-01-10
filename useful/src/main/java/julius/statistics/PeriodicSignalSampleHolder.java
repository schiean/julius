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

package julius.statistics;

import julius.utilities.TimeCountdown;

/**
 * Data object to hold the state for a signal type 
 */
public class PeriodicSignalSampleHolder {
	
	private final String id;
	private long count = 0;
	private final TimeCountdown counter;
	private final SignalSampleCallback callback;
	private final long durationInMs;
	
	/**
	 * 
	 * @param id
	 * @param callback
	 * @param durationInMs
	 */
	public PeriodicSignalSampleHolder(final String id, final SignalSampleCallback callback, final long durationInMs){
		this.callback = callback;
		this.id = id;
		this.durationInMs = durationInMs;
		this.counter = new TimeCountdown(durationInMs);
		counter.start();
	}
	
	/**
	 * handle a signal 
	 * either increase the counter, or call the callback if the current signal is not in the same window as the former/last signal before and reset counter
	 */
	public void signal(){
		if(counter.isCompleted()){
			callback.handleSample(id, count, durationInMs);
			counter.restart();
			count = 0;
		}
		count++;			
	}

}
