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

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import julius.utilities.CollectionHelper;


/**
 * Implementation of MultiMap that uses a HashSet to store the values.
 * 
 * 
 * 
 * Since it also exposes the Map<K, Set<V>> implementation
 * the methods cannot assume anything that is not promised by the Map.
 * 
 * We could limit this exposure by overriding all methods and perform checks
 * but we want to keep in line with default Map behavior for better integration.
 * 
 *  For Example; This class uses HashSet but it is possible to contain other sets
 *  if the put() method is used. It could also contain null values(null-refs for sets).
 *  
 *  For now all multi-maps have the same Value type: HashSet<V>, however a simple
 *  abstract factory method createValueCollection() could solve this. 
 */
public class GenericMultiMap<K,V> extends MapDecorator<K, Set<V>> implements MultiMap<K,V>{
	
	protected GenericMultiMap(final Map<K, Set<V>> map) {
		super(map);
	}
	
	protected void copy(final MultiMap<K, V> map) {
		for(Map.Entry<K, Set<V>> entry: map.entrySet()){
			put(entry.getKey(), new HashSet<V>(CollectionHelper.getOrEmpty(entry.getValue())));
		}
	}

	@Override
	public void addForKey(final K key, final V value) {
		Set<V> newset; 
		Set<V> oldset = get(key);
		if(oldset != null){
			newset = new HashSet<V>(oldset); 
		}else{
			newset = new HashSet<V>();
		}
		newset.add(value);
		put(key, newset);
	}

	@Override
	public Set<K> findKeysForValue(final V value) {
		Set<K> keys = CollectionHelper.set();
		for(Map.Entry<K, Set<V>> entry:entrySet()){
			if(entry.getValue().contains(value)){
				keys.add(entry.getKey());
			}
		}
		return keys;
	}

	@Override
	public boolean removeByKeyAndValue(final K key, final V value) {
		Set<V> ori = get(key);
		if(ori!=null && ori.contains(value)){
			HashSet<V> repl = new HashSet<V>(ori);
			repl.remove(value);			
			if(repl.isEmpty()){
				remove(key);
			}else{
				put(key, repl);				
			}
			return true;
		}
		return false;
	}

	@Override
	public Set<V> mergedValues() {
		return new HashSet<V>(CollectionHelper.flatten(values()));
	}

	@Override
	public Set<java.util.Map.Entry<K, V>> flatEntrySet() {
		Set<java.util.Map.Entry<K, V>> res = new HashSet<java.util.Map.Entry<K, V>>(); 
		for(Map.Entry<K, Set<V>> entry:entrySet()){
			for(V value: entry.getValue()){
				res.add(new Pair<K, V>(entry.getKey(), value));
			}
		}
		return res;
	}

	@Override
	public Set<V> getOrEmpty(final K key) {
		return new HashSet<V>(CollectionHelper.getOrEmpty(get(key)));
	}

}
