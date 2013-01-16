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

import static julius.test.JuliusTestOut.print;

import java.lang.reflect.Method;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import julius.reflection.BeanTraverser;
import julius.reflection.NullOrEmptyTraverseTask;
import julius.reflection.ReflectionHelper;
import julius.reflection.ReflectionHelperImpl;
import julius.utilities.CollectionHelper;
import julius.utilities.NumberHelper;

/**
 * TODO needs some clean up and should support more then just "nl.*"
 * 
 * Some smarter 'not null'-assert methods for junit
 */
public final class TestHelper {

    private TestHelper() {
        super();
    }

    private static ReflectionHelper reflectionHelper = new ReflectionHelperImpl();

    private static NumberHelper numberHelper = new NumberHelper();
    
	
    
    /**
     * Allows for comparisons where the result should be in the range of some value not particular exactly the expected value.
     * This makes sense for heuristic algorithms or benchmark tests.
     * 
     * (expected - percentage) <= actual => (expected + percentage) 
     * 
     * @param expected
     * @param actual
     * @param percentage
     * 
     */
    public static void allmostEquals(final long expected, final long actual, final int percentage){
    	if(!numberHelper.almostEqual(expected, actual, percentage)){
    		throw new IllegalArgumentException("We expected:"+numberHelper.toString(expected)+" with an offset of "+percentage+"% but got: "+numberHelper.toString(actual));
    	}
    }
    
    /**
     * this will call all 1 or 2 param accepting methods on the provided object with a null param and checks if the result is also
     * null
     * 
     * this is useful to test (generated) mappers for their handling of null params in an easy way
     * @param testableObject 
     */
    public static void testAllNullSafeMethods(final Object testableObject) {
        List<Method> ms = reflectionHelper.getAllDeclaredMethods(testableObject.getClass());
        boolean failures = false;
        for (Method m : ms) {
            try {
                if (m.getName().startsWith("map") && !m.getName().endsWith("Set")) { // map methods should return null for input

                    // should return empty list/set
                    if (m.getParameterTypes().length == 1) {
                        Object returnVal = m.invoke(testableObject, new Object[] { null }); // map, result = null and no exception
                        if (returnVal != null) {
                            throw new IllegalArgumentException(
                                    "Error: this method didn't return a 'null result reference' for input 'null'");
                        }
                        print("succes:" + m);
                    } else if (m.getParameterTypes().length == 2) {
                        m.invoke(testableObject, new Object[] { null, null }); // map into, no exception
                        print("succes:" + m);
                    }
                }
            } catch (Exception e) {
                print("FAIL:" + m);
                print("=>" + e);
                failures = true;
            }
        }
        if (failures) {
            throw new IllegalArgumentException("Error: some methods have a problem handling 'null-params'");
        }
    }

    /**
     * see assertNotNullRecursive but this variant allows you to provide a number of methods names that are allowed to return null.
     * Sometimes you want to check a large object graph for 'not-nullness' but also have some methods on it for which it is ok to
     * return null for a given usecase.
     * 
     * Has a link with package names that start with "nl."
     * 
     * @param o
     * @param allowedItems
     *            0 or more methodnames for which no error will be thrown in case their return value == null
     */
    public static void assertNotNullRecursiveWithAllowed(final Object o, final String... allowedItems) {
    	NullOrEmptyTraverseTask task = new NullOrEmptyTraverseTask();
    	BeanTraverser.traverseRecursive(task, o);
        handleResults(CollectionHelper.asList(CollectionHelper.asCollection(allowedItems)), task.getNullReturningMethods());
    }


    /**
     * This method traverses all getters (recursive) on an object prints a list of methods that return null and if this list is not
     * empty it throws an assertion error (this method could be used to see if an mapper maps all fields)
     * 
     * @param o
     */
    public static void assertNotNullRecursive(final Object o) {
        // this is actually the same as notNullRecursiveWithAllowed(Nothing)
        assertNotNullRecursiveWithAllowed(o, new String[0]);
    }

    private static void handleResults(final List<String> allowed, final List<String> nullReturningMethods) {
        Set<String> filteredError = new HashSet<String>();
        Set<String> filteredInfo = new HashSet<String>();
        for (String methodName : nullReturningMethods) {
            if (allowed.contains(methodName)) {
                filteredInfo.add(methodName);
            } else {
                filteredError.add(methodName);
            }
        }
        for (String methodName : filteredInfo) {
            int count = Collections.frequency(nullReturningMethods, methodName);
            print("Info: null/empty (or collection containing 'null') method:" + methodName + "(" + count + ")");
        }
        for (String methodName : filteredError) {
            int count = Collections.frequency(nullReturningMethods, methodName);
            print("Error: null/empty (or collection containing 'null') method:" + methodName + "(" + count + ")");
        }
        if (!filteredError.isEmpty()) {
            // junit.framework.Assert.fail("Error: some fields are null");
            throw new IllegalArgumentException("Error: some fields are null");
        }
    }

}
