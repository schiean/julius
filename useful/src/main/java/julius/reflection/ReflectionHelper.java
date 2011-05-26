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

import java.lang.reflect.Method;
import java.util.List;

/**
 * Interface to ReflectionHelperImpl 
 */
@SuppressWarnings("rawtypes")
public interface ReflectionHelper {

	/**
	 * Will turn a settername into a gettername
	 * setNumber => getNumber
	 * (javabean standard supports both isBoolean and setBoolean, this method doesnt know types and allways returns setXXX)
	 * @param setterMethodName
	 * @return getterName
	 */
    public String getGetterName(final String setterMethodName);

    /**
     * Will return true for methods with one argument of abstract type
     * will use T in case of generic type Set<T>
     * 
     * this method is useful to determine of you can call a reflective constructor for the type
     * @param aClass
     * @param methodName
     * @return
     */
    boolean hasAbstractFirstParamType(final Class aClass, final String methodName);

    /**
     * @param method
     * @return true if the method is public
     */
    boolean isPublic(final Method method);

    /**
     * @param aClass
     * @param getterMethodName
     * @return true if the method exists
     */
    boolean hasGetter(final Class aClass, final String getterMethodName);

    /**
     * @param method
     * @return true for all methods with name starting with 'set' and 1 arg
     */
    boolean isSetter(final Method method);

    /**
     * @param method
     * @return true for all methods with name starting with 'get'and 0 args
     */
    boolean isGetter(final Method method);

    /**
     * @param aClass
     * @param methodName
     * @return the type of the method as JavaType
     */
    JavaType getReturnType(final Class aClass, final String methodName);

    /**
     * @param aClass
     * @param methodName
     * @return the JavaType of the first param
     */
    JavaType getFirstParamType(final Class aClass, final String methodName);

    /**
     * method returning list with all(public,private,protected) methods of the class and its supertypes getDeclaredmethods on a
     * class returns all methods, but only for the class not supertypes getMethods on a class returns all public methods (not
     * private)
     * 
     * @param clasz
     * @return list of all methods (posibly with double methods if overrides are there)
     */
    List<Method> getAllDeclaredMethods(final Class clasz);
}
