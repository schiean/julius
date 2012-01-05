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

package julius.test;

import java.util.Collection;
import java.util.Iterator;

import junit.framework.Assert;

/** methods to assert collections */

public final class TestHelperCollections {

    private TestHelperCollections() {
        super();
    }

    /**
     * assert the collections have the same contents and order
     * most useful for asserting when the actual is a List
     *
     * @param <T>
     * @param expected
     * @param actual
     */
    @SuppressWarnings("unchecked")
	public static <T> void assertSameOrderAndSize(final Collection<T> expected, final Collection<T> actual){
    	assertSameSize(actual, expected);
    	assertContainsAll(expected, actual);
    	assertContainsAll(actual, expected);
    	assertInSameOrder(actual, expected);
    }
    
    /**
     * assert the collections have the same size and elements (not order)
     * most useful for asserting when the actual is a Set 
     *
     * @param <T>
     * @param expected
     * @param actual
     */
    @SuppressWarnings("unchecked")
	public static <T> void assertSameElementsAndSize(final Collection<T> expected, final Collection<T> actual){
    	assertSameSize(actual, expected);
    	assertContainsAll(expected, actual);
    	assertContainsAll(actual, expected);
    }
    
	/**
	 * asserts that all elements of the provided parts are contained in 'all'.
	 * All might also contain extra elements.
	 * 
	 * NOTICE THE DIFFERENT PARAMETER ORDER, NEEDED BECAUSE VAR-ARG
	 * 
	 * @param <T>
	 * @param actual
	 * @param parts
	 */
	public static <T> void assertContainsAll(final Collection<T> actual, final Collection<T>... parts){
		for(Collection<T> part:parts){
			for(T val: part){
				Assert.assertTrue(actual.contains(val));
			}
		}		
	}
	
	/**
	 * asserts that all,the first collection, (starts with) the values from parts in the same order as provided
	 * 
	 * it will only check the values of the provided parts for order, so there might be more items in 'all' then in the combined part
	 * but only at the end
	 * 
	 * NOTICE THE DIFFERENT PARAMETER ORDER, NEEDED BECAUSE VAR-ARG
	 * 
	 * @param <T>
	 * @param actual
	 * @param parts
	 */
	public static <T> void assertInSameOrder(final Collection<T> actual, final Collection<T>... parts){
		Iterator<T> iterator = actual.iterator();
		for(Collection<T> part:parts){
			for(T val: part){
				Assert.assertTrue(iterator.hasNext());
				T allVal = iterator.next();
				Assert.assertEquals( val,allVal);
			}
		}		
	}
	
	/**
	 * asserts that the first collection size is equal to the SUM of all following collection sizes 
	 * 
	 * NOTICE THE DIFFERENT PARAMETER ORDER, NEEDED BECAUSE VAR-ARG
	 * 
	 * @param <T>
	 * @param actual
	 * @param parts 1 or more
	 */
	public static <T> void assertSameSize(final Collection<T> actual, final Collection<T>... parts) {
		int size = 0;
		for(Collection<T> part:parts){
			size += part.size();
		}
		Assert.assertEquals(size, actual.size());
	}
	
	/**
	 * asserts the collection !=null and empty() == true
	 * 
	 * @param <T>
	 * @param collection
	 */
	public static <T> void assertEmpty(final Collection<T> collection) {
		Assert.assertNotNull(collection);
		Assert.assertTrue("should be empty, not "+collection,collection.isEmpty());
	}
	
	/**
	 * asserts the collection !=null and !empty()
	 * @param <T>
	 * @param collection
	 */
	public static <T> void assertNotEmpty(final Collection<T> collection) {
		Assert.assertNotNull(collection);
		Assert.assertTrue("should not be empty", !collection.isEmpty());
	}

}
