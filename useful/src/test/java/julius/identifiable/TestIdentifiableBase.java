/**
 * Copyright 2011 AJG van Schie
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain Ident copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package julius.identifiable;

import julius.test.BDDTestCase;

public class TestIdentifiableBase  extends BDDTestCase {

	private Ident aNULL_1;
	private Ident aNULL_2;
	private Ident a1_1;
	private Ident a1_2;
	private Ident a1_3extra;
	private Ident a2_2;

	private Ident2 bNULL_1;
	private Ident2 b1_1;
	private Ident2 b1_2;
	private Ident2 b2_1;

	@Override
	public void setUp(){
		aNULL_1 = new Ident(null);
		aNULL_2 = new Ident(null);
		a1_1 = new Ident(1L);
		a1_2 = new Ident(1L);
		a2_2 = new Ident(2L);
		a1_3extra = new Ident(1L,"a");


		bNULL_1 = new Ident2(null);
		b1_1  = new Ident2(1L);
		b1_2  = new Ident2(1L);
		b2_1  = new Ident2(2L);
	}



	public void testHashCodeIdentifiableInt() {
		note("two objects of the same type with the same value should have equal hashcode");
		assertEquals(a1_1.hashCode(), a1_2.hashCode());
		note("two objects of the same type with different value should have different hashcode");
		assertNotSame(a1_1.hashCode(), a2_2.hashCode());
		note("two objects of different type with the same value should have equal hashcode");
		assertEquals(a1_1.hashCode(), b1_1.hashCode());
		note("two objects of different type with different value should have different hashcode");
		assertNotSame(a1_1.hashCode(), b2_1.hashCode());
		note("two objects of the same type with Null value should have unequal hashcode(use parents)");
		assertNotSame(aNULL_1.hashCode(), aNULL_2.hashCode());
		assertEquals(System.identityHashCode(aNULL_1), aNULL_1.hashCode());
		assertEquals(System.identityHashCode(aNULL_2), aNULL_2.hashCode());
		note("extra fields should be ignored");
		assertEquals(a1_1.hashCode(), a1_3extra.hashCode());
		
	}

	public void testEqualsIdentifiableObject() {
		note("two objects of the same type with the same value should be equal");
		assertTrue(a1_1.equals(a1_2));
		note("two objects of the same type with different value should be unequal");
		assertFalse(a1_1.equals(a2_2));
		note("two objects of different type with the same value should be unequal");
		assertFalse(a1_1.equals(b1_1));
		note("two objects of different type with different value should be unequal");
		assertFalse(a1_1.equals(b1_2));
		note("two objects of the same type with Null value should be unequal");
		assertFalse(aNULL_1.equals(aNULL_2));
		note("two objects of different type with Null value should be unequal");
		assertFalse(aNULL_1.equals(bNULL_1));
		note("two object refs to the same object should be equal");
		Ident copy = a1_1;
		assertTrue(copy.equals(a1_1));
		note("other object is Null, should be unequal");
		assertFalse(a1_1.equals(null));
		note("if object has no id,  should be unequal");
		assertFalse(aNULL_1.equals(a1_1));
		note("extra fields should be ignored");
		assertTrue(a1_1.equals(a1_3extra));
		

	}

	public void testToStringIdentifiable() {
		note("to string with id should work");
		assertNotNull(a1_1.toString());
		note("to string without id should work");
		assertNotNull(aNULL_1.toString());
	}

}
