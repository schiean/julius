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

package julius.reflection;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import julius.utilities.CollectionHelper;

/**
 * Class for traversing Bean(Get/Set)
 *
 * printing/asserting tasks are useful example usages
 */
public class BeanTraverser {

    private static ReflectionHelper reflectionHelper = new ReflectionHelperImpl();

    /**
     * Will recursively traverse root and call task methods for the appropriate cases.
     *
     * Will call all getters to traverse, unless CGLIB, not starting with nl.* or when already traversed in the tree
     *
     * @param task
     * @param root
     */
    public static void traverseRecursive(final TraverseTask task, final Object root) {
        List<Object> processed = new LinkedList<Object>();
        int depth = 0;
        try {
            task.handle(root, "this", depth);
            if (root != null && task.shouldTraverse(getClassName(root), "root", root, depth + 1)) {
                if (root instanceof Collection) {
                    traverseCollection(task, (Collection<?>) root, processed, "(provided object collection)", depth + 1);
                } else {
                    traverseObject(task, root, processed, depth + 1);
                }
            }
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }

    @SuppressWarnings("rawtypes")
    /**
     *
     * @param task
     *            called for all getters on o
     * @param o
     *            object for which it is decided to further dive into this object
     * @param processed
     *            objects already processed
     * @param depth
     *            level for o, not getters of o
     *
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     */
    private static void traverseObject(final TraverseTask task, final Object o, final List<Object> processed, final int depth) throws IllegalAccessException, InvocationTargetException {

        if (ifAlreadyProcessed(o, processed)) {
            task.handleAlreadyProcessed(o, "skip", depth);
            return;
        }

        processed.add(o); // to prevent cycles we flag as processed upfront to the actual processing

        for (Method method : reflectionHelper.getAllDeclaredMethods(o.getClass())) {
            if (reflectionHelper.isGetter(method) && reflectionHelper.isPublic(method) && !method.getName().equals("getClass")) {
                try {
                    Object retval = method.invoke(o, (Object[]) null);
                    task.handle(retval, method.getName(), depth);
                    if (task.shouldTraverse(getClassName(o), method.getName(), retval, depth + 1)) {
                        if (retval instanceof Collection) {
                            traverseCollection(task, (Collection) retval, processed, method.getName(), depth + 1);
                        } else if (retval != null) {
                            traverseObject(task, retval, processed, depth + 1);
                        }
                    }
                } catch (InvocationTargetException e) {
                    task.handleException(e.getCause().getClass().getCanonicalName(), method.getName(), depth);
                }
            }
        }
    }

    private static String getClassName(final Object o) {
        return o.getClass().toString();
    }

    private static boolean ifAlreadyProcessed(final Object o, final List<Object> processed) {
        return CollectionHelper.containsAnyByRef(processed, o);
    }

    @SuppressWarnings("rawtypes")
    /**
     *
     * @param task
     *            called for all getters on o
     * @param collection
     *            object for which it is decided to further dive into this object
     * @param processed
     *            objects already processed
     * @param methodName
     *            name of the collection providing method
     * @param depth
     *            level for list members
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     */
    private static void traverseCollection(final TraverseTask task, final Collection collection, final List<Object> processed, final String methodName, final int depth) throws IllegalAccessException, InvocationTargetException {
        if (ifAlreadyProcessed(collection, processed)) {
            task.handleAlreadyProcessed(collection, "skip", depth);
            return;
        }
        processed.add(collection); // to prevent cycles we flag as processed upfront, the actual processing

        for (Object c : collection) {

            task.handle(c, methodName, depth);
            if (task.shouldTraverse(getClassName(collection), "part of " + methodName, c, depth + 1)) {
                if (c instanceof Collection) {
                    traverseCollection(task, (Collection) c, processed, "part of " + methodName, depth + 1);
                } else if (c != null) {
                    traverseObject(task, c, processed, depth + 1);
                }
            }
        }
    }

}
