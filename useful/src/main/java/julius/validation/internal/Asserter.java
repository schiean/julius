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

/**
 * Defines the methods for the assertions
 */
public interface Asserter {

	/**
	 * check all elements on the list to be != null
	 * 
	 * @param <T>
	 * @param collection
	 *            (null safe: null and empty list will pass)
	 * @param objectName
	 *            descriptive name of the collection
	 * @throws RuntimeException
	 *             if one of the list elements is null
	 */
	 <T> void assertAllNotNull(final Collection<T> collection, final String objectName);

	/**
	 * check all elements on the list to be valid
	 * 
	 * @param <T>
	 * @param collection
	 *            (null safe: null and empty list will pass)
	 * @param objectName
	 *            descriptive name of the collection
	 * @throws RuntimeException
	 *             if one of the list elements is null
	 */
	<T extends Validatable> void assertAllValidNotNull(final Collection<T> collection, final String objectName);

	/**
	 * check for empty collections
	 * 
	 * @param <T>
	 * @param collection 
	 * 				(null safe, but null or empty will throw runtime-exception)
	 * @param objectName
	 *            descriptive name of the collection
	 * 
	 * @throws RuntimeException
	 *             for null or empty collections
	 */
	<T> void assertNotEmpty(final Collection<T> collection, final String objectName);

	/**
	 * check for null value
	 * 
	 * @param object
	 * @param objectName
	 *            descriptive name of the object
	 * @throws RuntimeException
	 *             for null values
	 */
	void assertNotNull(final Object object, final String objectName);
	
	/**
	 * check for true
	 * @param condition
	 * @param msg
	 * @throws RuntimeException
	 * 				for false values
	 */
    void assertTrue(final boolean condition, final String msg);


}