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

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import julius.utilities.CollectionHelper;

/**
 * utilities to create collections
 */
public class CollectionsCreate {
	
	/**
	 * this method will wrap the collection element with a Numbered Type,
	 * which contains the position information.
	 * @param <T>
	 * @param original List(a,b,c,d,e,f)
	 * @return  List({0,a},{1,b},{2,c},{3,d},{4,e},{5,f})
	 */
	public <T> List<Numbered<T>> createNumberedList(final List<T> original){
		List<Numbered<T>> target = createLinkedList();
		int i=0;
		for(T item:CollectionHelper.getOrEmpty(original)){
			target.add(new Numbered<T>(item, i++));
		}
		return target;
	}

	/**
	 * this method will unwrap the collection that was created by {@link #createNumberedList(List)}
	 * @param <T>
	 * @param original List({0,a},{1,b},{2,c},{3,d},{4,e},{5,f})
	 * @return  List(a,b,c,d,e,f)
	 */
	public <T> List<T> createUnnumberedList(final List<Numbered<T>> original){
		List<T> target = createLinkedList();
		for(Numbered<T> item:CollectionHelper.getOrEmpty(original)){
			target.add(item.getValue());
		}
		return target;
	}
	
	/**
     * Will create a new mutable linked list, useful because this does type inference meaning you don't have
     * to declare the type twice per collection.
     * 
     * List<Integer> l = new LinkedList<Integer>();
     * 
     * becomes
     * 
     * List<Integer> l = createLinkedList();
     * 
     * @param <T>
     * @return
     */
    public  <T> List<T> createLinkedList() {
        return new LinkedList<T>();
    }
    
    /**
     * useful to create an immutable list in a DSL style
     * 
     * List<Integer> dutchColors = createList(Color.Red, Color.White, Color.Blue); 
     * 
     * @param <T>
     * @param vars 0...x elements
     * @return new List with 0..x elements
     */
    public <T> List<T> createList(final T... vars) {
        if (vars != null && vars.length > 0) {
            return Collections.unmodifiableList(Arrays.asList(vars));
        } else {
            return Collections.emptyList();
        }
    }
    
	/**
     * Will create a new mutable hash set, useful because this does type inference meaning you don't have
     * to declare the type twice per collection.
     * 
     * Set<Integer> l = new HashSet<Integer>();
     * 
     * becomes
     * 
     * Set<Integer> l = createHashSet();
     * 
     * @param <T>
     * @return
     */
    public  <T> Set<T> createHashSet() {
        return new HashSet<T>();
    }
    
    /**
     * useful to create an immutable set in a DSL style
     * 
     * Set<Integer> dutchColors = createSet(Color.Red, Color.White, Color.Blue); 
     * 
     * @param <T>
     * @param vars 0...x elements
     * @return new Set with 0..x elements
     */
    public <T> Set<T> createSet(final T... vars) {
        if (vars != null && vars.length > 0) {
        	Set<T> set = createHashSet();
        	set.addAll(createList(vars));
            return Collections.unmodifiableSet(set);
        } else {
            return Collections.emptySet();
        }
    }
    
    /**
     * Will create a new mutable hash map, useful because this does type inference meaning you don't have
     * to declare the type twice per collection.
     * 
     * Map<String,Integer> l = new HashMap<String,Integer>();
     * 
     * becomes
     * 
     * Map<String,Integer> l = createHashMap();
     * 
     * @param <K>
     * @param <V>
     * @return
     */
    public <K, V> Map<K,V> createHashMap(){
    	return new HashMap<K,V>();
    }
    
    /**
     * Create an immutable hashmap in DSL Style
     * 
     * Map<Color,Integer> dutchColors = createMap( entry(Color.Red, 1), entry(Color.White, 2), entry(Color.Blue, 3) ); 
     * 
     * @param <K>
     * @param <V>
     * @param entries
     * @return new hashmap with copy of entries
     */
    public <K, V> Map<K, V> createMap(final Map.Entry<? extends K, ? extends V>... entries) {
        if (entries != null && entries.length > 0) {
        	Map<K, V> result = createHashMap();            
        	for (Map.Entry<? extends K, ? extends V> entry : entries){
        		result.put(entry.getKey(), entry.getValue());
        	}
        	return Collections.unmodifiableMap(result);
        }else{
        	return Collections.emptyMap();
        }
    }

    /**
     * creates an immutable Map.Entry
     * @param <K>
     * @param <V>
     * @param key
     * @param value
     * @return
     */
    public <K, V> Map.Entry<K, V> entry(final K key, final V value) {
        return pair(key, value);
    }
    
    /**
     * creates an immutable Pair-object
     * @param <K>
     * @param <V>
     * @param first value (for instance key)
     * @param second value (for instance value)
     * @return
     */
    public <K, V> Pair<K, V> pair(final K first, final V second){
    	return new Pair<K,V>(first,second);
    }

    // TODO seems a bit unuseful, better have something like    List withoutNulls(List);
    /**
     * @param vars 
     * @param <T> 
     * @return 
     * @see julius.utilities.collection.CollectionsCreate#createList(Object...) but this one won't add 'nulls' 
     */
    public <T> List<T> createListWithoutNulls(final T... vars) {
        if (vars != null && vars.length > 0) {
            List<T> result = new LinkedList<T>();
            List<T> ori = Arrays.asList(vars);
            for (T element : ori) {
                if (element != null) {
                    result.add(element);
                }
            }
            return result;
        } else {
            return new LinkedList<T>();
        }
    }

}
