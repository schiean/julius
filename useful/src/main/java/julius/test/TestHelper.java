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

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import julius.reflection.ReflectionHelper;
import julius.reflection.ReflectionHelperImpl;
import julius.utilities.CollectionHelper;


/**
 * TODO needs some clean up and should support more then just "nl.*"
 * 
 * Some smarter 'not null'-assert methods for junit
 * 
 */
public final class TestHelper {

    private TestHelper() {
        super();
    }

    private static ReflectionHelper reflectionHelper = new ReflectionHelperImpl();

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
                            throw new RuntimeException(
                                    "Error: this method didn't return a 'null result reference' for input 'null'");
                        }
                        System.out.println("succes:" + m);
                    } else if (m.getParameterTypes().length == 2) {
                        m.invoke(testableObject, new Object[] { null, null }); // map into, no exception
                        System.out.println("succes:" + m);
                    }
                }
            } catch (Throwable e) {
                System.out.println("FAIL:" + m);
                System.out.println("=>" + e);
                failures = true;
            }
        }
        if (failures) {
            throw new RuntimeException("Error: some methods have a problem handling 'null-params'");
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
        List<String> allowed = CollectionHelper.getOrEmpty(Arrays.asList(allowedItems));
        List<String> nullReturningMethods = new LinkedList<String>();
        try {
            if (o == null) {
                nullReturningMethods.add("(no method: provided object was null)");
            } else if (o instanceof Collection) {
                handleCollection(o, nullReturningMethods, "(provided object collection)");
            } else {
                assertNotNullRecursive(o, nullReturningMethods);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        handleResults(allowed, nullReturningMethods);
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
            System.out.println("Info: null/empty (or collection containing 'null') method:" + methodName + "(" + count + ")");
        }
        for (String methodName : filteredError) {
            int count = Collections.frequency(nullReturningMethods, methodName);
            System.out.println("Error: null/empty (or collection containing 'null') method:" + methodName + "(" + count + ")");
        }
        if (!filteredError.isEmpty()) {
            // junit.framework.Assert.fail("Error: some fields are null");
            throw new RuntimeException("Error: some fields are null");
        }
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

    private static void assertNotNullRecursive(final Object o, final List<String> listOfNullReturningMethods) throws IllegalArgumentException,
            IllegalAccessException,
            InvocationTargetException {
    	if(o.getClass().getPackage()==null){ // classes loaded by a different class loader have no package
    		return;
    	}
        // check if we support the object
        String packageNM = o.getClass().getPackage().toString();
        if (!packageNM.startsWith("package nl.")) {
            return; // probably a framework or java internal class, dont check getters
        }
        for (Method method : reflectionHelper.getAllDeclaredMethods(o.getClass())) {
            if (reflectionHelper.isGetter(method) && !method.getName().equals("getClass")) {
                Object retval = method.invoke(o, (Object[]) null);
                if (retval == null) {
                    listOfNullReturningMethods.add(method.getName());
                } else if (retval instanceof Collection) {
                    handleCollection(retval, listOfNullReturningMethods, method.getName());
                } else {
                	if(!retval.getClass().getSimpleName().contains("CGLIB")){ // CG lib is for generated classes like mockito which we dont check
                		assertNotNullRecursive(retval, listOfNullReturningMethods);
                	}
                }
            }
        }
    }

    @SuppressWarnings("rawtypes")
    private static void handleCollection(final Object collection,
            final List<String> listOfNullReturningMethods,
            final String methodName) throws IllegalAccessException, InvocationTargetException {
        for (Object c : (Collection) collection) {
            if (c == null) {
                listOfNullReturningMethods.add(methodName);
            } else {
                assertNotNullRecursive(c, listOfNullReturningMethods);
            }
        }
        if (((Collection) collection).isEmpty()) {
            listOfNullReturningMethods.add(methodName);
        }
    }
}
