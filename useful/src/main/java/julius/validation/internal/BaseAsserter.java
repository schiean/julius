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

import static julius.utilities.CollectionHelper.getOrEmpty;
import static julius.utilities.CollectionHelper.isEmpty;

import java.util.Collection;

import julius.validation.Validatable;


/**
 * Base implementation of the asserter interface.
 * Which uses a factory method to instantiate the actual runtime derived exception
 * This makes it easy to create multiple types of asserters with different 
 * exceptions (i.e state asserter, argument asserter)
 * 
 */
public abstract class BaseAsserter implements Asserter {

	/** factory method to create exceptions */
	public abstract RuntimeException createException(String msg);
	

    /* (non-Javadoc)
	 * @see julius.validation.AsserterInterface#assertAllNotNull(java.util.Collection, java.lang.String)
	 */
    @Override
	public <T> void assertAllNotNull(final Collection<T> collection, final String objectName) {
        for (T t : getOrEmpty(collection)) {
            assertNotNull(t, objectName);
        }
    }

    /* (non-Javadoc)
	 * @see julius.validation.AsserterInterface#assertAllValidNotNull(java.util.Collection, java.lang.String)
	 */
    @Override
	public <T extends Validatable> void assertAllValidNotNull(final Collection<T> collection, final String objectName){
        for (T t : getOrEmpty(collection)) {
            assertNotNull(t, objectName);
            t.assertValid();
        }
    }

    /* (non-Javadoc)
	 * @see julius.validation.AsserterInterface#assertNotEmpty(java.util.Collection, java.lang.String)
	 */
    @Override
	public <T> void assertNotEmpty(final Collection<T> collection, final String objectName){
        if (isEmpty(collection)) {
            throw createException(objectName + " should not be empty");
        }
    }

    /* (non-Javadoc)
	 * @see julius.validation.AsserterInterface#assertNotNull(java.lang.Object, java.lang.String)
	 */
    @Override
	public void assertNotNull(final Object object, final String objectName){
        if (object == null) {
            throw createException(objectName + " should not be null");
        }
    }
    
    /* (non-Javadoc)
	 * @see julius.validation.AsserterInterface#assertTrue(boolean, java.lang.String)
	 */
    @Override
	public void assertTrue(final boolean condition, final String msg){
    	if(!condition){
    		throw createException(msg);
    	}
    }
}
