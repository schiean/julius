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

import julius.lambda.Operation;

/**
 * For a lot of classes it makes sense to have an identifier.
 * Hibernate classes have a common practice for this to use Long.
 * for Hibernate most of the time TechnicalIdentifiable should be used, but NaturalIdentifiable allows for NaturalKey identification
 * 
 * This interface and IdentifiableBase help to implement this practice
 */
public interface Identifiable<U> {

	/**
	 * @return Unique identifier identifying the object
	 */
	U getId();
	
	/**
	 * function object for extracting an Id from a Item useful in combination with Functional.map to turn a collection of Identifiable into a collection of their identifiers
	 */
	public static class IdExtracter<T extends Identifiable<U>,U> implements Operation<T,U>{
		@Override
		public  U apply(final T val) {			
			return val.getId();
		}
	};	

}
