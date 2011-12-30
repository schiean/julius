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

package julius.lambda;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import julius.utilities.collection.CollectionsCreate;
import julius.utilities.collection.CollectionsQuery;

/**
 * contains some methods that help simulate function pointers/lambda's with some other interfaces (Filter, Operation,FoldOperation)
 * 
 * Although it should not be seen as a fully fledged functional addition to java. There are other frameworks like FunctionalJava, Guava
 * that are probably better suited, or just go for clojure, scala, groovy.
 * 
 */
public class Functional {

	
	private final CollectionsCreate create = new CollectionsCreate();
	private final CollectionsQuery query = new CollectionsQuery();
	
	/**
	 * walks over the collection and returns true as soon as one element f(el) yields true
	 * @param <T>
	 * @param collection
	 * @param filter
	 * @return 
	 */
	public<T> boolean containsAny(final Collection<T> collection, final Filter<T> filter ){
		for(T t:query.getOrEmpty(collection)){
			if(filter.applicable(t)){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * see containsAny
	 * 
	 * @param <T>
	 * @param collection
	 * @param filter
	 * @return
	 */
	public<T> boolean exists(final Collection<T> collection, final Filter<T> filter ){
		return containsAny(collection, filter);
	}
	
	/**
	 * walks over the collection and returns false as soon as one element f(el) yields false
	 * 
	 * @param <T>
	 * @param collection
	 * @param filter
	 * @return
	 */
	public<T> boolean containsOnly(final Collection<T> collection, final Filter<T> filter ){		
		for(T t:query.getOrEmpty(collection)){
			if(!filter.applicable(t)){
				return false;
			}
		}
		return true;
	}
	
	/**
	 * see containsOnly
	 * @param <T>
	 * @param collection
	 * @param filter
	 * @return
	 */
	public <T> boolean forAll(final Collection<T> collection, final Filter<T> filter ){
		return containsOnly(collection, filter);
	}
	
	/**
	 * @param <T>
	 * @param collection
	 * @param filter
	 * @return a list with all elements for which filter(el) is true
	 */
	public<T> List<T> filter(final Collection<T> collection, final Filter<T> filter ){		
		List<T> result = create.createLinkedList();
		for(T t:query.getOrEmpty(collection)){
			if(filter.applicable(t)){
				result.add(t);
			}
		}
		return result;
	}
	
	/**
	 * walks over the collection and adds the elements to the list until one filter(el) returns false;
	 * @param <T>
	 * @param collection
	 * @param filter
	 * @return subList
	 */
	public<T> List<T> takeWhile(final Collection<T> collection, final Filter<T> filter ){		
		List<T> result = create.createLinkedList();
		for(T t:query.getOrEmpty(collection)){			
			if(!filter.applicable(t)){
				return result;
			}
			result.add(t);
		}
		return result;
	}
	
	
	/**
	 * turns a collection(T) into collection(U)
	 * @param <T>
	 * @param <U>
	 * @param collection
	 * @param operation T->U
	 * @return
	 */
	public<T,U> List<U> map(final Collection<T> collection, final Operation<T,U> operation ){		
		List<U> result = create.createLinkedList();
		for(T t:query.getOrEmpty(collection)){
			result.add(operation.apply(t));
		}
		return result;
	}
	
	/**
	 * fold/reduce the collection into value U
	 * walk over the elements and then take initial/current U and apply current = operation(el, U)
	 * @param <T>
	 * @param <U>
	 * @param collection
	 * @param operation
	 * @param initial
	 * @return
	 */
	public <T,U> U foldLeft(final Collection<T> collection, final FoldOperation<T,U> operation, final U initial){
		U current = initial;
		for(T el: query.getOrEmpty(collection)){
			current = operation.apply(el, current);
		}
		return current;
	}

	/**
	 * fold/reduce the collection into value U
	 * walk over the elements and then take initial/current U and apply current = operation(el, U)
	 * @param <T>
	 * @param <U>
	 * @param collection
	 * @param operation
	 * @param initial
	 * @return
	 */
	public <T,U> U foldRight(final Collection<T> collection, final FoldOperation<T,U> operation, final U initial){
		List<T> temp = new ArrayList<T>(query.getOrEmpty(collection));
		Collections.reverse(temp);
		return foldLeft(temp, operation, initial);
	}
		
}
