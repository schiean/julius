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

public class TestBooleanHelper extends BDDTestCase{

	public void testToBool(){
		assertTrue(BooleanHelper.booleanHelper.map("tRuE"));
		assertTrue(BooleanHelper.booleanHelper.map("Y"));
		assertTrue(BooleanHelper.booleanHelper.map("J"));
		assertTrue(BooleanHelper.booleanHelper.map("j"));		
		assertTrue(BooleanHelper.booleanHelper.map("YeS"));
		assertTrue(BooleanHelper.booleanHelper.map("1"));
		assertTrue(BooleanHelper.booleanHelper.map("wAaR"));
		
		assertFalse(BooleanHelper.booleanHelper.map("N"));
		assertFalse(BooleanHelper.booleanHelper.map("n"));
		assertFalse(BooleanHelper.booleanHelper.map("false"));
		assertFalse(BooleanHelper.booleanHelper.map("F"));
		assertFalse(BooleanHelper.booleanHelper.map("0"));
		
		assertNull(BooleanHelper.booleanHelper.map(null));
		assertNull(BooleanHelper.booleanHelper.map("kermit the frog"));
		
	}
	
	public void testToJN(){
		assertEquals("J", BooleanHelper.booleanHelper.toJN(true));
		assertEquals("N", BooleanHelper.booleanHelper.toJN(false));
		assertEquals("", BooleanHelper.booleanHelper.toJN(null));
	}
	
	public void testToYN(){
		assertEquals("Y", BooleanHelper.booleanHelper.toYN(true));
		assertEquals("N", BooleanHelper.booleanHelper.toYN(false));
		assertEquals("", BooleanHelper.booleanHelper.toYN(null));
	}
}
