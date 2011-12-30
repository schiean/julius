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

import julius.test.BDDTestCase;

public class TestCachingLazyLoader extends BDDTestCase{

	public static class CountProxy extends LazyLoaderProxy<Integer, Integer>{
		public CountProxy(final Integer key) {
			super(key);
		}

		int count = 1;
		
		@Override
		public Integer findElement() {
			return count++;
		}		
	}	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void testCache(){
		CountProxy original = new CountProxy(10);
		
		CachingLazyLoaderProxy cache = new CachingLazyLoaderProxy(original);
		
		given("a proxy that returns invocation counts");
		when("wrapped and find is called repeatably");
		
			for(int i=0;i<10;i++){
				cache.findElement();
				cache.exists();
				cache.getElement();
			}
			
		then("we expect the same number");
		
			assertEquals(1, cache.findElement());
		
		
		when("called without wrapper");
			int val = original.findElement();
		then("number should increase for original, not for cache");
		
			assertEquals(2, val);
			assertEquals(1, cache.findElement());
			
		
		when("flushed and calling find");
			cache.flush();
		then("we expect a new value");
		
			assertEquals(3, cache.findElement());
		
	}
	
}
