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

package julius.lazy;

import static julius.validation.Assertions.state;

/**
 * This class decorates another proxy by adding caching. It will try to limit the calls
 * to the actual findElement by keeping the first non-null value.
 */
public class CachingLazyLoaderProxy<K,V> extends LazyLoaderProxy<K, V>{

	private final LazyLoaderProxy<K,V> original;
	private final Object valueLock  = new Object();
	private volatile V value;
	
	/**
	 * @param original
	 */
	public CachingLazyLoaderProxy(final LazyLoaderProxy<K,V> original){
		super(original.getKey());
		this.original = original;
		state.assertNotNull(this.original, "original");
	}

	@Override
	public V findElement() {
		synchronized (valueLock) {
			if( value == null){
				value = original.findElement();
			}
			return value;				
		}
	}
	
	/**
	 * resets the cache
	 */
	public void flush(){
		synchronized (valueLock) {
			value = null;
		}
	}
	
}
