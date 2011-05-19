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

import julius.validation.internal.CheckedAsserter;
import julius.validation.internal.IllegalArgumentAsserter;
import julius.validation.internal.IllegalStateAsserter;

/**
 * provides access to some static Asserter objects.
 * you can use static imports on the objects to create the best readable/fluent code
 * 
 * 
 * import static CheckedAssert;
 * 
 * ....
 * assertNotNull(someObj,"someName");
 * ....
 *
 */
public class Assertions {
	
	protected Assertions(){
		super();
	}
	
	public static final CheckedAsserter checked =  new CheckedAsserter();
	public static final IllegalArgumentAsserter argument = new IllegalArgumentAsserter();
	public static final IllegalStateAsserter state = new IllegalStateAsserter();
	
}
