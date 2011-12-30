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

package julius.validation;

import julius.lambda.Filter;

/**
 * looks like validatable but this one does not throw exceptions but return a boolean
 * this makes sense when invalid objects are regular and handling them with a boolean is easier then an exception
 */
public interface ValidatableWithBoolean {

	/**
	 * @return true for valid, false for invalid
	 */
	boolean isValid();
	
	/**
	 * Useful with Functional.filter to get only the objects for which isValid yields true
	 */
	public static class ValidFilter<T extends ValidatableWithBoolean> implements Filter<T>{
		@Override
		public boolean applicable(final T val) {
			return val.isValid();
		}
	};
	
	/**
	 * Useful with Functional.filter to get only the objects for which isValid yields false
	 */
	public static class InvalidFilter<T extends ValidatableWithBoolean> implements Filter<T>{
		@Override
		public boolean applicable(final T val) {
			return !val.isValid();
		}
	};

}
