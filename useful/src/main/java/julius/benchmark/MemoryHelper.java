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

import julius.Constants;
import julius.annotations.Experimental;
import julius.utilities.Sleep;

/**
 * utility class to enable logging of memory usage
 * 
 * KEEP IN MIND THAT USING THIS CLASS DOES INTERFERE WITH THE GARBAGE COLLECTOR AND MIGHT HURT PERFORMANCE
 * DON'T USE IN PRODUCTION WHERE PERFORMANCE IS IMPORTANT
 */
@Experimental("obtrusive, only use in debug environment")
public class MemoryHelper {
	

	/**
	 * @return a string telling the amount of used memory (in MB) before and after GC
	 */
	public String getFullReport(){
		return "Before Garbage Collection "+getReport(false) +"\nAfter Garbage Collection "+ getReport(true);
	}
	
	/**
	 * The amount of memory used in MB
	 * @param afterCleanUp (true => call System.GC)
	 * @return used MB: XX
	 */
	public String getReport(final boolean afterCleanUp){
		if(afterCleanUp){
			tryCleanUp();
		}
		long used = getUsedMB();
		return "used MB:"+used;
	}

	/**
	 * @return the amount of used MB
	 */
	public long getUsedMB() {
		long totalBytes = Runtime.getRuntime().totalMemory() ;
		long freeWithinTotalBytes = Runtime.getRuntime().freeMemory();
		return (totalBytes - freeWithinTotalBytes) / Constants.BYTES_IN_KILOBYTE / Constants.KILOBYTES_IN_MEGABYTE ; // bytes => kilobytes => megabytes		
	}
	
	/**
	 * suggests the VM to clean up memory
	 */
	public void tryCleanUp(){
		System.gc(); System.gc(); System.gc(); System.gc();
	    System.gc(); System.gc(); System.gc(); System.gc();
	    Sleep.sleep.milliseconds(100);
	    System.gc(); System.gc(); System.gc(); System.gc();
	    System.gc(); System.gc(); System.gc(); System.gc();
	}
	
}
