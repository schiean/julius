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

import julius.test.BDDTestCase;

public class TestPair extends BDDTestCase{
	
	public void testSimple(){
		given("val1:1, val2:'a'");
			
			Pair<Integer, String> p = new Pair<Integer,String>(1,"a");
			
		when("first is called");
		then("1 is expected");
			
			assertEquals(Integer.valueOf(1), p.getFirst());

		and("when second is called");
		then("'a' is expected");
		
			assertEquals("a", p.getSecond());

		and("is immutable");
		
			try{
				p.setValue("b");
				fail("should be immutable");
			}catch(UnsupportedOperationException e){}
			
		successFullStory();
		
		
	}
}
