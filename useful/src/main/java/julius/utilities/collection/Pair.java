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

import java.util.AbstractMap;

/**
 * Simple immutable class that will store two values (a pair)
 * For example useful as map entry key, value.
 * Re-Uses the ImmutableMapEntry (throws exception in setValue)
 * 
 * Could also be useful in prototyping phases where you don't know how a relation from K -> V should
 * be named. Or highly technical algorithms like code generators where there are a lot of relations 
 * from X->X1 without a functional name. 
 * Normally it is better to subclass and introduce a more domain specific name for the relation
 */
public class Pair<K, V> extends AbstractMap.SimpleImmutableEntry<K, V> {

	private static final long serialVersionUID = -3524114719977454198L;

	/**
	 * @param key
	 * @param value
	 */
	public Pair(final K key, final V value){
		super(key,value);
	}
	
	/**
	 * @return K the first param
	 */
	public K getFirst(){
		return getKey();
	}
	
	/**
	 * @return V the second param
	 */
	public V getSecond(){
		return getValue();
	}

}
