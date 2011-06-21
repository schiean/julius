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

package julius.identifiable;

/**
 * Reusable equals, hashCode and toString for Identifiables
 * 
 * users of this class could use the constant 'helper' through static import
 * this class is useful if inheritance is not possible else you could use IdentifiableBase and inheritance
 * 
 * more on equals/hashcode http://www.ibm.com/developerworks/java/library/j-jtp05273/index.html
 */
public class IdentifiableHelper {
	
	public static final IdentifiableHelper helper = new IdentifiableHelper();
	
	/**
	 * implements a generic hashcode method, where hashcode is based on getId()
	 * in absence of id uses Object.getHashCode();
	 *  
	 * @param original		  most likely called with 'this'
	 * @return
	 */
	public int hashCode(final Identifiable original) {
		int prime = 31;
		if (original.getId() != null) {
			return prime + original.getId().hashCode();
		}else{
			return System.identityHashCode(original);
		}
		
	}
	
	/**
	 * returns true if 
	 * 			- its two references to the same object 
	 * 				OR
	 * 			- the objects are the same type AND 
	 * 			  both have the same getId() AND 
	 * 			  getId() is not null
	 * 
	 * @param original
	 * @param obj
	 * @return
	 */
	public boolean equals(final Identifiable original, final Object obj) {
		if (original == obj){
			return true;
		}
		if (obj == null){
			return false;
		}
		if (original.getClass() != obj.getClass()){
			return false;
		}
		Identifiable other = (Identifiable) obj;
		if (original.getId() == null) {
			return false;
		} else {
			return original.getId().equals(other.getId());			
		}
	}
	
	/**
	 * returns a string with NAMEOFTHECLASS(ID:VALUE)
	 * @param original
	 * @return
	 */
	public String toString(final Identifiable original) {
		return original.getClass().getName()+"(" + original.getId() + ")";
	}

}
