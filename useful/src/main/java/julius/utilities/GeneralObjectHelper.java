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

package julius.utilities;

/**
 * Contains convenience methods which operate on Object references.
 * They can be useful to implement business rules more compactly
 * or to implement equals (alternatively use commons lang equalsBuilder ) 
 * 
 * more on equals/hashcode http://www.ibm.com/developerworks/java/library/j-jtp05273/index.html
 */
public final class GeneralObjectHelper {
	
	private GeneralObjectHelper(){
		// static methods only
	}
	
	/**
	 * true if the objects are both null or equal, false if either is null
	 * 
	 * usage example:
	 * 	assertTrue(nullSafeEqual(o1,o2),"o1 and o2 must be equal")
	 * 
	 * 
	 * 	public boolean equals(final Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Some other = (Some) obj;
			return 	nullSafeEqual(methodName, other.methodName) &&
					nullSafeEqual(source, other.source) &&
					nullSafeEqual(target, other.target) &&
					nullSafeEqual(type, other.type);		
		}

	 * @param one  null or object
	 * @param two  null or object
	 * @return 
	 */
	public static boolean nullSafeEquals(final Object one, final Object two){
		if(one == two){ // works for equal refs, or null==null
			return true;
		}
		if (one == null || two == null){
			return false;
		} else{
			return one.equals(two);
		}
	}
	
	
	/**
	 * @param one  null or object
	 * @param two  null or object
	 * @return  false if either is null or not equal. true only for both not null and both equal
	 */
	public static boolean nullSafeEqualsAndNotNull(final Object one, final Object two){
		return one != null && two != null && one.equals(two);
	}
	
	/**
	 * @see #nullSafeEquals(Object, Object)
	 * @param one
	 * @param two
	 * @return
	 */
	public static boolean equals(final Object one, final Object two){
		return nullSafeEquals(one, two);
	}
	
	/**
	 * @see #nullSafeEqualsAndNotNull(Object, Object)
	 * @param one
	 * @param two
	 * @return
	 */
	public static boolean equalsNotNull(final Object one, final Object two){
		return nullSafeEqualsAndNotNull(one, two);
	}
}
