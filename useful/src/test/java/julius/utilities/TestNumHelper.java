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

public class TestNumHelper extends BDDTestCase{

	private final NumberHelper numhelper = new NumberHelper();
	
	public void testNull(){
		note("should be null safe");
		assertEquals("null",numhelper.toString(null));
		assertEquals("null",numhelper.toDutchString(null));
		
	}
	
	public void testInteger(){
		note("should support positive and negative numbers");
		assertEquals("-4",numhelper.toString(-4));
		assertEquals("4",numhelper.toString(4));
		assertEquals("-4,123",numhelper.toString(-4123));
		assertEquals("4,123",numhelper.toString(4123));
		assertEquals("4,123,789",numhelper.toString(4123789));
	}

	public void testLong(){
		note("should support the same as int");
		assertEquals("4",numhelper.toString(4L));
		assertEquals("4,123",numhelper.toString(4123L));
		assertEquals("4,123,789",numhelper.toString(4123789L));
		note("should support really long numbers");
		assertEquals("4,123,123,123,123",numhelper.toString(4123123123123L));
		assertEquals("4,123,123,123,123,456,456",numhelper.toString(4123123123123456456L));

	}
	
	public void testDouble(){
		note("should support ,0 for round numbers");
		assertEquals("4.0",numhelper.toString(4D));
		note("should support grouping like int/long");
		assertEquals("4,123.0",numhelper.toString(4123D));
		assertEquals("4,123,789.0",numhelper.toString(4123789D));

		note("should support a lot of decimals");
		assertEquals("4.123789",numhelper.toString(4.123789D));

		assertEquals("4,123,123,123,123.0",numhelper.toString(4123123123123D));

	}
	
	public void testIntegerDucth(){
		note("should support positive and negative numbers");
		assertEquals("-4",numhelper.toDutchString(-4));
		assertEquals("4",numhelper.toDutchString(4));
		assertEquals("-4.123",numhelper.toDutchString(-4123));
		assertEquals("4.123",numhelper.toDutchString(4123));
		assertEquals("4.123.789",numhelper.toDutchString(4123789));
	}

	public void testLongDutch(){
		note("should support the same as int");
		assertEquals("4",numhelper.toDutchString(4L));
		assertEquals("4.123",numhelper.toDutchString(4123L));
		assertEquals("4.123.789",numhelper.toDutchString(4123789L));

		note("should support really long numbers");
		assertEquals("4.123.123.123.123",numhelper.toDutchString(4123123123123L));
		assertEquals("4.123.123.123.123.456.456",numhelper.toDutchString(4123123123123456456L));
	}
	
	public void testDoubleDutch(){
		note("should support ,0 for round numbers");
		assertEquals("4,0",numhelper.toDutchString(4D));
		note("should support grouping like int/long");
		assertEquals("4.123,0",numhelper.toDutchString(4123D));
		assertEquals("4.123.789,0",numhelper.toDutchString(4123789D));

		note("should support a lot of decimals");
		assertEquals("4,123789",numhelper.toDutchString(4.123789D));

		assertEquals("4.123.123.123.123,0",numhelper.toDutchString(4123123123123D));

	}
	
	public void testAllmostEqual(){
		note("little to high");
		assertTrue(numhelper.almostEqual(100, 103, 5));
		assertFalse(numhelper.almostEqual(100, 106, 5));
		
		note("little to low");
		assertTrue(numhelper.almostEqual(100, 97, 5));
		assertFalse(numhelper.almostEqual(100, 94, 5));

		note("exact");
		assertTrue(numhelper.almostEqual(100, 100, 5));
		note("large %");
		assertTrue(numhelper.almostEqual(1, 2, 100));
	
		try{
			assertTrue(numhelper.almostEqual(1, 2, 0));
			fail("only percentage > 0");
		}catch(IllegalArgumentException e){}
		
		
		note("little to high");
		assertTrue(numhelper.almostEqual(100.5, 103.5, 5));
		assertFalse(numhelper.almostEqual(100.5, 106.5, 5));
		
		note("little to low");
		assertTrue(numhelper.almostEqual(100.5, 97.5, 5));
		assertFalse(numhelper.almostEqual(100.5, 94.5, 5));

		note("exact");
		assertTrue(numhelper.almostEqual(100.5, 100.5, 5));
		note("large %");
		assertTrue(numhelper.almostEqual(1.5, 2.5, 100));
	
		try{
			assertTrue(numhelper.almostEqual(1.6, 2, 0));
			fail("only percentage > 0");
		}catch(IllegalArgumentException e){}
		
		try{
			assertTrue(numhelper.almostEqual(1.88, 2, 0));
			fail("only percentage > 0");
		}catch(IllegalArgumentException e){}
		
		
		try{
			assertTrue(numhelper.almostEqual(1.99, 2, 0));
			fail("only percentage > 0");
		}catch(IllegalArgumentException e){}
		

			assertTrue(numhelper.almostEqual(1.99, 2, 1));
	}
	
}
