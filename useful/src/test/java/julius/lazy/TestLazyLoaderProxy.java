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

import java.util.Collections;
import java.util.List;

import julius.lambda.Functional;
import julius.test.BDDTestCase;
import julius.utilities.CollectionHelper;

public class TestLazyLoaderProxy extends BDDTestCase{

	private final static String odd = "odd";
	
	public static class OddProxy extends LazyLoaderProxy<Integer, String>{

		public OddProxy(final Integer key) {
			super(key);
		}

		@Override
		public String findElement() {
			if(getKey()%2==1){
				return odd;
			}
			return null;
		}
		
		
	}	
	
	public void testKeyMandatory(){
		try{
			new OddProxy(null);
			fail("not there");
		}catch(IllegalStateException e){
			
		}
	}
	
	public void testNormalScenario(){
		OddProxy proxy = new OddProxy(13);
		
		given("a proxy for which key and value exist");
		when("exist is called");		
		then("we expect true");
		
			assertTrue(proxy.exists());
		
		when("find is called");
		then("we expect a value");
		
			assertEquals(odd, proxy.findElement());
		
		when("get is called");
		then("we expect a value");
		
			assertEquals(odd, proxy.getElement());
			
		when("toString is called");
		then("it should contain the key");
		
			assertTrue(proxy.toString().contains(proxy.getKey().toString()));
	}
	
	public void testNotThereScenario(){
		OddProxy proxy = new OddProxy(12);
		
		given("a proxy for which key and value not-exist");
		when("exist is called");		
		then("we expect false");
		
			assertFalse(proxy.exists());
		
		when("find is called");
		then("we expect no value");
		
			assertNull( proxy.findElement());
		
		when("get is called");
		then("we expect an exception");
		
			try{
				proxy.getElement();
				fail("exception expected");
			}catch(IllegalStateException e){}
			
		when("toString is called");
		then("it should contain the key, and 'unavaillable'");
		
			assertTrue(proxy.toString().contains(proxy.getKey().toString()));
			assertTrue(proxy.toString().contains("unavaillable"));
	}
	
	public void testFunctions(){
			List<OddProxy> proxies = CollectionHelper.list(new OddProxy(1), new OddProxy(2), new OddProxy(3));
			List<OddProxy> proxiesEven = CollectionHelper.list(new OddProxy(2));
			List<OddProxy> proxiesOdd = CollectionHelper.list(new OddProxy(1), new OddProxy(3));
			List<OddProxy> res;
			
		given("proxies:"+proxies);
		when("filtered not loaded(even)");
			res = new Functional().filter(proxies, new LazyLoaderProxy.NotLoaded<OddProxy>());
		then("expect "+proxiesEven);
			assertEquals(proxiesEven, Collections.unmodifiableList(res));
		
		when("filtered loaded(odd)");
			res = new Functional().filter(proxies, new LazyLoaderProxy.Loaded<OddProxy>());
		then("expect "+proxiesOdd);
			assertEquals(proxiesOdd, Collections.unmodifiableList(res));
	
		when("values extracted");
		then("we expect String list with null and odd");
			List<String> vals = new Functional().map(proxies, new LazyLoaderProxy.ValueExtracter<OddProxy, String>());
			assertEquals(proxies.size(), vals.size());
			int count = 0;
			for(String v:vals){
				if(v!=null){
					assertEquals(odd, v);
					count++;
				}
			}
			assertEquals(proxiesOdd.size(), count);
	}
	
	
	
}
