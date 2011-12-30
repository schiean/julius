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

package julius.identifiable;

import julius.test.BDDTestCase;

public class TestIdentifiableBaseWithNaturalKey extends BDDTestCase{

	public class NaturalKeyX implements NaturalKey{
		Long id1;
		Long id2;
		
		public NaturalKeyX(final Long x){
			this.id1 = x;
			this.id2 = x;
		}
		
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + getOuterType().hashCode();
			result = prime * result + ((id1 == null) ? 0 : id1.hashCode());
			result = prime * result + ((id2 == null) ? 0 : id2.hashCode());
			return result;
		}
		@Override
		public boolean equals(final Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			NaturalKeyX other = (NaturalKeyX) obj;
			if (!getOuterType().equals(other.getOuterType()))
				return false;
			if (id1 == null) {
				if (other.id1 != null)
					return false;
			} else if (!id1.equals(other.id1))
				return false;
			if (id2 == null) {
				if (other.id2 != null)
					return false;
			} else if (!id2.equals(other.id2))
				return false;
			return true;
		}
		private TestIdentifiableBaseWithNaturalKey getOuterType() {
			return TestIdentifiableBaseWithNaturalKey.this;
		}
		
	}
	
	public class NIdent2 extends IdentifiableBase<NaturalKeyX> implements NaturalIdentifiable<NaturalKeyX>{

		NaturalKeyX id;

		public NIdent2(final Long id) {
			super();
			if(id!=null){
				this.id = new NaturalKeyX(id);
			}else{
				this.id = null;
			}
		}

		@Override
		public NaturalKeyX getId() {
			return id;
		}
		
	}
	
	public class NIdent extends IdentifiableBase<NaturalKeyX> implements NaturalIdentifiable<NaturalKeyX>{
		private final NaturalKeyX id;
		private final String name;
		
		public String getName() {
			return name;
		}

		public NIdent(final Long id) {
			this(id, "");
		}
		
		public NIdent(final Long id, final String name) {
			super();			
			if(id!=null){
				this.id = new NaturalKeyX(id);
			}else{
				this.id = null;
			}
			this.name = name;
		}

		@Override
		public NaturalKeyX getId() {
			return id;
		}

	}
	
	private NIdent aNULL_1;
	private NIdent aNULL_2;
	private NIdent a1_1;
	private NIdent a1_2;
	private NIdent a1_3extra;
	private NIdent a2_2;

	private NIdent2 bNULL_1;
	private NIdent2 b1_1;
	private NIdent2 b1_2;
	private NIdent2 b2_1;

	@Override
	public void setUp(){
		aNULL_1 = new NIdent(null);
		aNULL_2 = new NIdent(null);
		a1_1 = new NIdent(1L);
		a1_2 = new NIdent(1L);
		a2_2 = new NIdent(2L);
		a1_3extra = new NIdent(1L,"a");


		bNULL_1 = new NIdent2(null);
		b1_1  = new NIdent2(1L);
		b1_2  = new NIdent2(1L);
		b2_1  = new NIdent2(2L);
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
		NIdent copy = a1_1;
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
