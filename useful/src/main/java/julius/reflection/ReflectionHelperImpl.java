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
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import julius.validation.Assertions;


/**
 * @see ReflectionHelper
 * 
 * All methods used in TestHelper are tested, all others are not used yet, testcode needs to be written
 * 
 */
@SuppressWarnings("rawtypes")
public class ReflectionHelperImpl implements ReflectionHelper {

    @Override
    public String getGetterName(final String setterMethodName) {
    	Assertions.argument.assertNotNull(setterMethodName,"setterMethodName");
    	Assertions.argument.assertTrue(setterMethodName.startsWith("set"), "method should start with 'set'"); 
        return "get" + setterMethodName.substring(3);
    }

    @Override
    public boolean hasAbstractFirstParamType(final Class aClass, final String methodName) {
    	Assertions.argument.assertNotNull(aClass,"aClass");
    	Assertions.argument.assertNotNull(methodName,"methodName");
    	boolean ok = false;
        Method method = getMethod(aClass, methodName);
        if (method != null) {

            Type[] types = method.getGenericParameterTypes();
            if (types != null && types.length == 1) {
                Class clasz = null;
                if (types[0] instanceof ParameterizedType) {
                	JavaType jt = new JavaType((ParameterizedType) types[0]);
                    clasz = jt.getGenericClasz();
                } else {
                    Class[] cs = method.getParameterTypes();
                    clasz = cs[0];
                }
                if (clasz != null && !clasz.isPrimitive()) {
                    ok = Modifier.isAbstract(clasz.getModifiers()) && !clasz.isArray() && clasz != byte.class; // there was a bug with byte[]
                                                                                                   // being classified as abstract,
                                                                                                   // not sure if this is the best
                                                                                                   // fix
                }
            }

        }
        return ok;
    }

    @Override
    public boolean isPublic(final Method method) {
    	Assertions.argument.assertNotNull(method,"method");
        int modifiers = method.getModifiers();
        if (Modifier.isPublic(modifiers)) {
            return true;
        }
        return false;
    }

    @Override
    public boolean hasGetter(final Class aClass, final String getterMethodName) {
    	Assertions.argument.assertNotNull(aClass,"aClass");
    	Assertions.argument.assertNotNull(getterMethodName,"getterMethodName");
    	Method m = null;
    	try{
    		m = getMethod(aClass, getterMethodName);
    	}catch(IllegalArgumentException ex){
    		return false;
    	}
        return isGetter(m);
    }

    @Override
    public boolean isSetter(final Method method) {
    	Assertions.argument.assertNotNull(method,"method");
    	if (!method.getName().startsWith("set")) {
            return false;
        }
        if (method.getParameterTypes().length != 1) {
            return false;
        }
        return true;
    }

    @Override
    public boolean isGetter(final Method method) {
    	Assertions.argument.assertNotNull(method,"method");
    	if (!method.getName().startsWith("get")) {
            return false;
        }
        if (method.getParameterTypes().length != 0) {
            return false;
        }
        return true;
    }

    @Override
    public JavaType getReturnType(final Class aClass, final String methodName) {
    	Assertions.argument.assertNotNull(aClass,"aClass");
    	Assertions.argument.assertNotNull(methodName,"methodName");
    	
    	JavaType type = null;
        Method method = getMethod(aClass, methodName);
        if (method != null && method.getName().equals(methodName)) {
            Type returnType = method.getGenericReturnType();
            if (returnType instanceof ParameterizedType) {
                type = new JavaType((ParameterizedType) returnType);
            } else {
                type = new JavaType(method.getReturnType());
            }
        }
        return type;
    }

    private Method getMethod(final Class aClass, final String methodName) {
    	Assertions.argument.assertNotNull(aClass,"aClass");
    	Assertions.argument.assertNotNull(methodName,"methodName");
    	
    	List<Method> methods = getAllDeclaredMethods(aClass);
        for (Method method : methods) {
            if (method.getName().equals(methodName)) {
                return method;
            }
        }
        throw new IllegalArgumentException("method not found "+methodName);
    }

    @Override
    public JavaType getFirstParamType(final Class aClass, final String methodName) {
    	Assertions.argument.assertNotNull(aClass,"aClass");
    	Assertions.argument.assertNotNull(methodName,"methodName");
    	
    	JavaType type = null;
        Method method = getMethod(aClass, methodName);
        if(method!=null){
        	Type[] r = method.getGenericParameterTypes();
            if (r != null && r.length == 1) {
            	if (r[0] instanceof ParameterizedType) {
            		type = new JavaType((ParameterizedType) r[0]);                        
                } else {
                    Class[] c = method.getParameterTypes();
                    type = new JavaType(c[0]);
                }
            }
        }
        return type;
    }

    @Override
    public List<Method> getAllDeclaredMethods(final Class aClass) {
    	Assertions.argument.assertNotNull(aClass,"aClass");
    	
    	List<Method> methods = new LinkedList<Method>();
        Class claszIter = aClass; // used to traverse the inheritance hierarchy
        while (claszIter != null) {
            methods.addAll(Arrays.asList(claszIter.getDeclaredMethods()));
            claszIter = claszIter.getSuperclass();
        }
        return methods;
    }

}
