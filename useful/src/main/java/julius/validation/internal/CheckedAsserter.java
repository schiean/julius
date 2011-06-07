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

package julius.validation.internal;

import java.util.Collection;

import julius.validation.Validatable;
import julius.validation.ValidationException;


/**
 * class useful for validating the correctness/nullness of objects
 * 
 * wraps around unchecked version, cannot extend the base class because the type signature is different
 * for checked exceptions.
 * 
 */
public final class CheckedAsserter {

    private static final BaseAsserter asserter = new IllegalArgumentAsserter();
	
    /**
     * 
	 * @see Asserter#assertAllNotNull(Collection, String)
     * 
     * @param <T>
     * @param collection
     * @param objectName
     * @throws ValidationException
     */
	public <T> void assertAllNotNull(final Collection<T> collection, final String objectName) throws ValidationException{
		try{
			asserter.assertAllNotNull(collection, objectName);
		}catch(IllegalArgumentException e){
			throw new ValidationException(e);
		}
	}
	
	/**
	 * 
	 * @see Asserter#assertAllValidNotNull(Collection, String)
	 * 
	 * @param <T>
	 * @param collection
	 * @param objectName
	 * @throws ValidationException
	 */
	public <T extends Validatable> void assertAllValidNotNull(final Collection<T> collection, final String objectName) throws ValidationException{
		try{
			asserter.assertAllValidNotNull(collection, objectName);
		}catch(IllegalArgumentException e){
			throw new ValidationException(e);
		}
	}
	
	/**
	 * @see Asserter#assertNotEmpty(Collection, String)
	 * 
	 * @param <T>
	 * @param collection
	 * @param objectName
	 * @throws ValidationException
	 */
	public <T> void assertNotEmpty(final Collection<T> collection, final String objectName) throws ValidationException{
		try{
			asserter.assertNotEmpty(collection, objectName);
		}catch(IllegalArgumentException e){
			throw new ValidationException(e);
		}
	}

	/**
	 * @see Asserter#assertNotNull(Object, String)
	 * 
	 * @param object
	 * @param objectName
	 * @throws ValidationException
	 */
	public void assertNotNull(final Object object, final String objectName) throws ValidationException{
		try{
			asserter.assertNotNull(object, objectName);
		}catch(IllegalArgumentException e){
			throw new ValidationException(e);
		}
	}
	
	/**
	 * @see Asserter#assertTrue(boolean, String)
	 * 
	 * @param condition
	 * @param msg
	 * @throws ValidationException
	 */
	public void assertTrue(final boolean condition, final String msg) throws ValidationException{
		try{
			asserter.assertTrue(condition, msg);
		}catch(IllegalArgumentException e){
			throw new ValidationException(e);
		}
	}	   

}
