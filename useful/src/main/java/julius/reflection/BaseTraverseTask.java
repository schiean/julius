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

import java.util.HashSet;
import java.util.Set;

import julius.utilities.CollectionHelper;

public abstract class BaseTraverseTask implements TraverseTask {
    private final int maxDepth;

    private final Set<String> packagesOfInterest;

    private final Set<String> skipPaths;
    private final Set<String> skipMethods;
    private final Set<String> skipClasses;

    public BaseTraverseTask() {
        this(1024, new HashSet<String>(), new HashSet<String>(), new HashSet<String>(), CollectionHelper.set("nl", "java.util"));
    }

    /**
     *
     * @param maxDepth
     *            optional to prevent extremely large trees
     * @param skipPaths
     * @param skipMethods
     * @param skipClasses
     * @param interestingPackages
     *            something like "nl." or "com.mypackage."
     */
    public BaseTraverseTask(final int maxDepth, final Set<String> skipPaths, final Set<String> skipMethods, final Set<String> skipClasses, final Set<String> interestingPackages) {
        this.maxDepth = maxDepth;
        this.skipPaths = skipPaths;
        this.skipMethods = skipMethods;
        this.skipClasses = skipClasses;
        this.packagesOfInterest = interestingPackages;
    }

    @Override
    public boolean shouldTraverse(final String objectName, final String methodName, final Object returnValue, final int depth) {
        boolean typeOk = true;
        if (returnValue != null) {
            for (String s : skipClasses) {
                typeOk = typeOk && !returnValue.getClass().getName().contains(s);
            }
        }
        boolean pathOk = true;
        for (String s : skipPaths) {
            pathOk = pathOk && !(objectName + "." + methodName).contains(s);
        }
        boolean methodOk = true;
        for (String s : skipMethods) {
            methodOk = methodOk && !methodName.contains(s);
        }
        return depth <= maxDepth && methodOk && pathOk && typeOk && isNormalClass(returnValue);
    }

    public boolean isNormalClass(final Object object) {
        if (object == null) {
            return false;
        }
        String objectName = object.getClass().toString();
        // check if we support the object
        if (!matchesPackages(objectName)) {
            return false; // probably a framework or java internal class, don't check getters
        }
        if (objectName.contains("CGLIB")) {
            return false; // CG lib is for generated classes like mockito which we dont check
        }
        return true;
    }

    public boolean matchesPackages(final String objectName) {
        for (String prefix : packagesOfInterest) {
            if (objectName.startsWith("class " + prefix)) {
                return true;
            }
        }
        return false;
    }
}
