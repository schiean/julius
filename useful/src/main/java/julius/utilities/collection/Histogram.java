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

package julius.utilities.collection;

import java.util.HashMap;

/**
 * Map of T to Integer with a register method
 * It allows to keep a count of  of the provided values.
 * use register(key) to adjust the count  and getCount(key) to retrieve
 */
public class Histogram<T> extends HashMap<T,Integer> {
	
	private static final long serialVersionUID = -5683996183844690282L;

	/**
	 * increase the count for the 'key' in the map
	 * @param key
	 */
	public void register(final T key){
		Integer count = this.getCount(key) + 1;			
		this.put(key, count);
	}
	
	/**
	 * @param key
	 * @return 0 or higher value (absent keys are 0)
	 */
	public Integer getCount(final T key){
		Integer count = this.get(key);
		if(count!=null){
			return count;
		}else{
			return 0;
		}
	}
	
}

