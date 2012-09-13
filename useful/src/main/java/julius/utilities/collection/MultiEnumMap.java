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

import java.util.EnumMap;
import java.util.Set;


/**
 * Class implements MultiMap with a EnumMap and HashSet backing
 */
public class MultiEnumMap<K extends Enum<K>,V>  extends GenericMultiMap<K, V> {
	
	private static final long serialVersionUID = 7435046269889333388L;
	
	private Class<K> keyType;
	
	/**
	 * Creates an empty enum map with the specified key type.
	 * 
	 */
	public MultiEnumMap(final Class<K> keyType) {
		super(new EnumMap<K,Set<V>>(keyType));
		this.keyType = keyType;
	}

	/**
	 * Copy constructor
	 * @throws RuntimeExceptions for copies of empty maps
	 * @param map
	 */
	public MultiEnumMap(final MultiEnumMap<K, V> map) {
		this(map.keyType);
		copy(map);
	}	
}
