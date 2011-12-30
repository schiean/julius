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

package julius.validation;

import java.util.List;

import julius.lambda.Functional;
import julius.test.BDDTestCase;
import julius.utilities.CollectionHelper;

public class TestValidatableWithBoolean extends BDDTestCase {

	public static class X implements ValidatableWithBoolean{
		private final boolean v;

		public X(final boolean v) {
			super();
			this.v = v;
		}

		@Override
		public boolean isValid() {
			return v;
		}

		@Override
		public String toString() {
			return "X [v=" + v + "]";
		}
		
	}
	
	public void testFilters(){
		List<X> items = CollectionHelper.list(new X(true),new X(false),new X(true),new X(false),new X(false));
		
		given("items:"+items);
		when("filtered valid");
		then("expect 2");
		
			assertEquals(2, new Functional().filter(items, new ValidatableWithBoolean.ValidFilter<X>()).size());
		
		when("filtered invalid");
		then("expect 3");
			
			assertEquals(3, new Functional().filter(items, new ValidatableWithBoolean.InvalidFilter<X>()).size());
		
	}
}
