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
 * For a lot of classes it makes sense to have a technical identifier of the type long
 * Hibernate classes have a common practice for this.
 * 
 * This interface and IdentifiableBase help to implement this practice
 */
public interface TechnicalIdentifiable extends Identifiable<Long> {

	/**
	 * @return Unique number identifying the object
	 */
	@Override
	Long getId();
}