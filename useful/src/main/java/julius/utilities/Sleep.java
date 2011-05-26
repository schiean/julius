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

import org.apache.log4j.Logger;

/**
 * In java the code needed to let a thread sleep aint pretty.
 * It needs a checked exception, the interface is in milliseconds, most of the time the catch block is empty
 * 
 * This class makes the code easier and better readable
 * 
 * Sleep.seconds(52);
 * Sleep.minutes(2);
 * Sleep.minutes(2).and().seconds(30);
 * 
 * TODO add reference to ibm article about interrupted
 */
public class Sleep {
	
	private static Logger log = Logger.getLogger(Sleep.class);
	
	public static final Sleep sleep = new Sleep();
	
	/**
	 * @param millis to sleep
	 * @return self
	 */
	public Sleep milliseconds(final long milliseconds){
		try {
			Thread.sleep(milliseconds);
		} catch (InterruptedException e) {
			log.error("interrupted while sleeping", e);
		}
		return this;
	}
	
	/**
	 * @param seconds to sleep
	 * @return self
	 */
	public Sleep seconds(final long seconds){
		milliseconds(1000*seconds);
		return this;
	}
	
	/**
	 * @param minutes to sleep
	 * @return self
	 */
	public Sleep minutes(final long minutes){
		seconds(60*minutes);
		return this;
	}
	
	/**
	 * does nothing but makes the api better readable see class comments
	 * @return
	 */
	public Sleep and(){
		return this;
	}
	
	
	
}
