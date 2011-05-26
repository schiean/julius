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

import java.lang.reflect.ParameterizedType;

import julius.validation.Assertions;

/**
 * This class is useful as a simpler variant of Class.java 
 * for most reflective usage it would be enough to now the return type of method in terms of class and generic aspect
 * which is exactly the only information this simple container can carry
 */
@SuppressWarnings("rawtypes")
public class JavaType {
   
	private final Class clasz;
    private final Class genericClasz;

    /**
     * Use this constructor for normal (non-generic types)
     */
    public JavaType(final Class claszObject) {
        this(claszObject, null);
    }

    /**
     * use this constructor for generic types such as List<A>, Set<B>
     * 
     * @param claszObject
     *            List Set
     * @param genericClaszObject
     *            A B
     */
    public JavaType(final Class claszObject, final Class genericClaszObject) {
        this.clasz = claszObject;
        this.genericClasz = genericClaszObject;
    }

    /**
     * Mapping constructor from ParameterizedType to this
     * only uses the first found generic type
     * @param pt
     */
    public JavaType(final ParameterizedType pt) {
        // TODO probably there is a cleaner way to get Classes from a Parametrized type
        Assertions.argument.assertTrue(pt.getActualTypeArguments().length<=1, "only supporting one generic type per JavaType");
        
        String typeDescr = pt.getRawType().toString();
        String genericType = pt.getActualTypeArguments()[0].toString(); // TODO we used to take the last not first
        
        typeDescr = typeDescr.replace("interface ", ""); 
        genericType = genericType.replace("class ", "");
        
        try {
			clasz = Class.forName(typeDescr);
			genericClasz = Class.forName(genericType);
		} catch (ClassNotFoundException e) {
			throw new IllegalArgumentException(e.getMessage());
		}
        
    }

    /**
     * @return Returns the clasz.
     */
    public Class getClasz() {
        return this.clasz;
    }

    /**
     * @return Returns the genericClasz.
     */
    public Class getGenericClasz() {
        return this.genericClasz;
    }
    
    public boolean isGeneric(){
    	return this.genericClasz != null;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((clasz == null) ? 0 : clasz.hashCode());
        result = prime * result + ((genericClasz == null) ? 0 : genericClasz.hashCode());
        return result;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        JavaType other = (JavaType) obj;
        if (clasz == null) {
            if (other.clasz != null)
                return false;
        } else if (!clasz.equals(other.clasz))
            return false;
        if (genericClasz == null) {
            if (other.genericClasz != null)
                return false;
        } else if (!genericClasz.equals(other.genericClasz))
            return false;
        return true;
    }

    @Override
    public String toString() {
        String t = "null";
        String t2 = "null";
        if (getClasz() == null) {
            return "new";
        } else {
            t = getClasz().toString();
        }
        if (getGenericClasz() == null) {
            return t;
        } else {
            t2 = getGenericClasz().toString();
            return t + " generic-" + t2;
        }

    }

}
