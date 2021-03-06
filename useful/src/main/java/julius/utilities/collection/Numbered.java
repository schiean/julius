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

package julius.utilities.collection;

import julius.validation.Assertions;

/**
 * Class wrapper to bind a Number with another data type
 * @param <T> 
 */
public class Numbered<T> {

	private final T value;
	private final int position;

	/**
	 * Constructor, both fields are mandatory
	 * @param value
	 * @param position
	 */
	public Numbered(final T value, final int position) {
		super();
		this.value = value;
		this.position = position;
		Assertions.argument.assertNotNull(this.value, "Numbered.value");
		Assertions.argument.assertNotNull(this.position, "Numbered.position");
	}
 
	/**
	 * @return the value
	 */
	public T getValue() {
		return value;
	}

	/**
	 * @return position
	 */
	public int getPosition() {
		return position;
	}
	
	
	
}
