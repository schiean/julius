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
 * declares an interface declaring a method getId that returns an instance of NaturalKey
 * see TechnicalIdentifiable for the simple Long key variant
 */
public interface NaturalIdentifiable<T extends NaturalKey> extends Identifiable<T>{

	/**
	 * @return Unique NaturalKey identifying the object
	 */
	@Override
	T getId();
}
