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

import julius.Constants;

import org.apache.log4j.Logger;

/**
 * In java the code needed to let a thread sleep ain't pretty.
 * It needs a checked exception, the interface is in milliseconds, most of the time the catch block is empty (although this
 * might not be the best way to handle interupted exceptions see http://www.ibm.com/developerworks/java/library/j-jtp05236/index.html)
 * 
 * This class makes the code easier and better readable
 * 
 * import static julius.Sleep.sleep;
 * 
 * sleep.seconds(52);
 * sleep.minutes(2);
 * sleep.minutes(2).and().seconds(30);
 * 
 * for use in loops a scheduleAtFixedTime might be an alternative
 */
public class Sleep {
	
	private static Logger log = Logger.getLogger(Sleep.class);
	
	/** static instance which is useful for static imports **/
	public static final Sleep sleep = new Sleep();
	
	/**
	 * @param milliseconds to sleep
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
		milliseconds(Constants.MILLIS_IN_SEC * seconds);
		return this;
	}
	
	/**
	 * @param minutes to sleep
	 * @return self
	 */
	public Sleep minutes(final long minutes){
		seconds(Constants.SEC_IN_MIN * minutes);
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
