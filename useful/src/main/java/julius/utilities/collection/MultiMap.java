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

import java.util.Map;
import java.util.Set;

/**
 * Declares maps that have multiple values for one key.
 * 
 * This is not part of the Java platform, but could be seen as Map<Key, Collection<Value>>
 * however, the API would not be very friendly.
 * As a result this interface was introduced to provide extra methods on Map<Key, Collection<Value>>
 * that are more user friendly while still integrating with the normal Map/Collection interface
 * 
 * since this implements the Map, we also try to maintain its contract
 * this means we keep the behavior of the methods of Map, and for instance get() exposes the 
 * original Collection instead of a safe copy. 
 * 
 * Methods defined in this interface however wont return original data, but copies.
 */
		
public interface MultiMap<K, V> extends Map<K, Set<V>>{
	
	/**
	 * returns original Value set or null, better use MultiMap#getOrEmpty()
	 */
	@Override
	@Deprecated
	Set<V> get(Object key);
	
	/**
	 * alternative to get(). this one always returns a (copied) Set, never null.
	 * Most of the time this is the method you want.
	 * 
	 * @param key
	 * @return copy of the value set, or an empty set for unexisting keys 
	 */
	Set<V> getOrEmpty(K key);
	
	/**
	 * Could be used to replace/add the set for Key, but it is most likely
	 * that you want to use MultiMap#addForKey(K,V) (this automatically creates sets if not
	 * existant for the key)
	 */
	@Override
	@Deprecated
	Set<V> put(K key, Set<V> value);

	/**
	 * either creates an entryset for 'key' with this value, or
	 * adds the 'value' to the current entryset.
	 * @param key
	 * @param value
	 */
     void addForKey(final K key, final V value) ;

    /**
     * @param value
     * @return a set of all keys for which the value exists (or an empty set in case non has it)
     */
     Set<K> findKeysForValue(V value);

    /**
     * removes the specific value for the specific key.
     * if all occurrences of the value need to be removed use MultiMap#findKeysForValue(V);
     * if all values for one key should be removed use Map#remove()
     * 
     * if the last value for a key is removed, the whole entry is removed Map#contains(K)==false
     * 
     * @param key
     * @param value
     * @return true if the key,value combination has been removed
     */
     boolean removeByKeyAndValue(final K key, final V value) ;

    /**
     * @return a new Set with all values of all keys combined
     */
     Set<V> mergedValues() ;

    /**
     * a flat view of the map
     * 
     * entrySet()
     * 1 -> a
     * 2 -> a,b
     * 
     * flattenEntrySet()
     * =>
     * 1 -> a
     * 2 -> a
     * 2 -> b
     * 
     * 
     * @return a copy of all entries in the described format
     */
     Set<Map.Entry<K, V>> flatEntrySet() ;
	
}
