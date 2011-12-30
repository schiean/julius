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

import julius.test.BDDTestCase;

public class TestOptional extends BDDTestCase{

	public void testValue(){
		Optional<Integer> o = Optional.create(4);
		assertTrue(o.hasValue());
		assertEquals(4, o.getValue().intValue());
	}
	
	public void testNoValue(){
		Optional<Integer> o = Optional.create(null);
		assertFalse(o.hasValue());
		try{
			o.getValue().intValue();		
		}catch(IllegalStateException e){}
	}
	
	public void testNesting(){		
		Optional<Optional<Integer>> o1 = Optional.create(Optional.create(4));
		assertTrue(o1.hasValue());
		assertTrue(o1.getValue().hasValue());
		assertEquals(4, (int) o1.getValue().getValue());
	}
	
	
}
