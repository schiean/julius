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

import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Contains methods for some category/set theory operations
 *  
 */
public class CollectionsLogical {
	
	private final CollectionsCreate create = new CollectionsCreate();
	private final CollectionsQuery query = new CollectionsQuery();
	private final CollectionsFunctional functional = new CollectionsFunctional();
	
	/**
	 * Merges the elements from multiple collections into one list
	 * The element order will remain if the collection type has a certain order
	 * 
	 * http://en.wikipedia.org/wiki/Union_(set_theory)
	 * 
	 * @param <T>
	 * @param others
	 * @return list containing all combined items from 'others'
	 */
	public <T> List<T> union(final Collection<T>... others){		
		List<Collection<T>> othersAsList = create.createList(others);		
		return functional.flattenToList(othersAsList);
	}
	
	/**
	 * Merges the elements from multiple collections into one set
	 * It won't contain any double values (based on the HashSet implementation)
	 * http://en.wikipedia.org/wiki/Union_(set_theory)
	 * 
	 * @param <T>
	 * @param others
	 * @return list containing all combined items from 'others'
	 */
	public <T> Set<T> unionAsSet(final Collection<T>... others){		
		return new HashSet<T>(union(others));
	}
	
	/**
	 * Keep only the items that are in all(both) collections, possibly with double values
	 * The result will be in the order (if any) of the first param.
	 *
	 * http://en.wikipedia.org/wiki/Intersection_%28set_theory%29
	 * 
	 * @param <T>
	 * @param others
	 * @return
	 */
	public <T> List<T> intersection(final Collection<T>... others){
		List<Collection<T>> othersAsList = create.createList(others);		
		List<T> first = new LinkedList<T>(query.getOrEmpty(functional.first(othersAsList))); // copy list since we don't want to change the original
		for(Collection<T> next: functional.allExceptFirst(othersAsList)){
			first.retainAll(next); // keep only those that are in both
		}
		return first;
	}
	
	/**
	 * Keep only the items that are in both collections
	 * 
	 * It won't contain any double values (based on the HashSet implementation)
	 * 
	 * http://en.wikipedia.org/wiki/Intersection_%28set_theory%29
	 * 
	 * @param <T>
	 * @param others
	 * @return
	 */
	public <T> Set<T> intersectionAsSet(final Collection<T>... others){
		return new HashSet<T>(intersection(others));
	}
	
	/**
	 * 
	 * variant of http://en.wikipedia.org/wiki/Cardinality
	 * 
	 * @param <T>
	 * @param items
	 * @return a map with Key->Integer where the Integer represents the number of occurrence
	 */
	public <T> Map<T,Integer> cardinality(final Collection<T> items){
		Histogram<T> histo = new Histogram<T>();
		for(T item:query.getOrEmpty(items)){
			histo.register(item);
		}
		return histo;
	}
	
	
	/**
	 * also known as 'difference'
	 * All items from 'other' that are not in original, order of the result is equal to 'other'
	 * 
	 * http://en.wikipedia.org/wiki/Complement_%28set_theory%29
	 * 
	 * @param <T>
	 * @param original
	 * @param other
	 * @return a subset of Other (all items in other that were not in original) 
	 */
	public <T> List<T> relativeComplement(final Collection<T> original, final Collection<T> other){
		List<T> otherCopy = new LinkedList<T>(query.getOrEmpty(other));
		otherCopy.removeAll(original);
		return otherCopy;
	}
	
	/**
	 * find all items that are in only one of the collections
	 * 
	 * http://en.wikipedia.org/wiki/Symmetric_difference
	 * 
	 * @param <T>
	 * @param others
	 * @return
	 */
	public <T> Collection<T> symetricDifference(final Collection<T>... others){
		Collection<T> unionL  =  union(others);
		Collection<T> intersectionL = intersection(others);
		return relativeComplement(intersectionL, unionL);
	}

}
