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

import static julius.identifiable.IdentifiableHelper.helper;

/**
 * Base class which implements toString, equals and hashcode on getId (and only getId)
 * 
 * keep in mind that this implementation will see all object with the same ID as equal
 * and use Object.equals/hashcode in other situations (which is reference based equals, ==)
 * 
 * use IdentifiableHelper as a composition flavored alternative
 * 
 * more on equals/hashcode http://www.ibm.com/developerworks/java/library/j-jtp05273/index.html
 */
public abstract class IdentifiableBase<T> implements Identifiable<T>{

	@Override
	public boolean equals(final Object obj){
		return helper.equals(this, obj);
	}
	
	@Override
	public int hashCode(){
		return helper.hashCode(this);
	}
	
	@Override
	public String toString(){
		return helper.toString(this);
	}
	
}
