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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * Contains methods to transform collections into different types.
 * although this are mostly one-liners, its usefulness is in type-inference
 * all returned collection are mutable and it is optimized to return the original instance if it is both the right type and mutable
 */
public class CollectionsTransform {
	
	private final CollectionsCreate create = new CollectionsCreate();
	private final CollectionsQuery query = new CollectionsQuery();
	
	/**
	 * create a List for the collection
	 * or return the original collection of it is the right type and mutable
	 * @param <T>
	 * @param collection
	 * @return
	 */
	public <T> List<T> asList(final Collection<T> collection){
		if(collection instanceof List && query.isMutable(collection)){
			return (List<T>) collection;
		} else {
			List<T> res = create.createDefaultListType();
			res.addAll(collection);
			return res;
		}
	}
	
	/**
	 * create a Set for the collection
	 * or return the original collection of it is the right type and mutable
	 * @param <T>
	 * @param collection
	 * @return
	 */
	public <T> Set<T> asSet(final Collection<T> collection){
		if(collection instanceof Set && query.isMutable(collection)){
			return (Set<T>) collection;
		} else {
			Set<T> res = create.createDefaultSetType();
			res.addAll(collection);
			return res;
		}
	}

	/**
	 * create a LinkedList for the collection
	 * or return the original collection of it is the right type and mutable
	 * @param <T>
	 * @param collection
	 * @return
	 */
	public <T> LinkedList<T> asLinkedList(final Collection<T> collection){
		if(collection instanceof LinkedList && query.isMutable(collection)){
			return (LinkedList<T>) collection;
		} else {
			LinkedList<T> res = create.createLinkedList();
			res.addAll(collection);
			return res;
		}
	}
	
	/**
	 * create a HashSet for the collection
	 * or return the original collection of it is the right type and mutable
	 * @param <T>
	 * @param collection
	 * @return
	 */
	public <T> HashSet<T> asHashSet(final Collection<T> collection){
		if(collection instanceof HashSet && query.isMutable(collection)){
			return (HashSet<T>) collection;
		} else {
			HashSet<T> res = create.createHashSet();
			res.addAll(collection);
			return res;
		}
	}
	
	/**
	 * turns the array into a mutable Collection
	 * decouples the original array from the collection by making a copy
	 * but keeps ordering
	 * @param <T>
	 * @param objArr (null => empty collection)
	 * @return
	 */
	public <T> Collection<T> asCollection(final T[] objArr){
		ArrayList<T> result;
		if( objArr == null || objArr.length == 0 ){
			result = new ArrayList<T>();
		} else {			
			result = new ArrayList<T>(Arrays.asList(objArr));
		}
		return result;		
	}
	
}
