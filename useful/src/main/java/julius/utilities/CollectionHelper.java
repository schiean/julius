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

package julius.utilities;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import julius.utilities.collection.CollectionsCreate;
import julius.utilities.collection.CollectionsFunctional;
import julius.utilities.collection.CollectionsQuery;
import julius.utilities.collection.CollectionsReplace;
import julius.utilities.collection.Numbered;



/**
 * class with methods that could have been on the collection classes of the jdk.
 * most of them are in functional programming style and therefore all methods are
 * static, which is also useful for static imports
 * 
 * the algorithms themselves are in separate classes, since we provide
 * a lot of overloaded versions List/Set/Collection which are more or less equal
 * we copy their implementation locally within those files
 */
public final class CollectionHelper {
	 
	private static CollectionsReplace replace = new CollectionsReplace();
	private static CollectionsCreate create = new CollectionsCreate();
	private static CollectionsQuery query = new CollectionsQuery();
	private static CollectionsFunctional functional = new CollectionsFunctional();
	
    private CollectionHelper() {
        // only static methods
    }

    /**
     * @see CollectionsCreate#createLinkedList()
     */
    public static <T> List<T> list() {
        return create.createLinkedList();
    }

    /**
     * @see CollectionsCreate#createList(Object...)
     */
    public static <T> List<T> list(final T... vars) {
        return create.createList(vars);
    }

    /**
     * @see CollectionsCreate#createHashSet()
     */
    public static <T> Set<T> set() {
        return create.createHashSet();
    }

    /**
     * @see CollectionsCreate#createSet(Object...)
     */
    public static <T> Set<T> set(final T... vars) {
        return create.createSet(vars);
    }
    
    /**
     * @see CollectionsCreate#createNumberedList(List)
     */
    public static <T> List<Numbered<T>> numbered(final List<T> original){
    	return create.createNumberedList(original);
    }
    
    /**
     * @see CollectionsCreate#createLinkedList()
     */
    public static <T> List<T> createLinkedList() {
        return create.createLinkedList();
    }

    /**
     * @see CollectionsCreate#createList(Object...)
     */
    public static <T> List<T> createList(final T... vars) {
        return create.createList(vars);
    }

    /**
     * @see CollectionsCreate#createListWithoutNulls(Object...) 
     */
    public static <T> List<T> createListWithoutNulls(final T... vars) {
        return create.createListWithoutNulls(vars);
    }    
    
    
    
    /**
	 * @see CollectionsReplace#replace(Set, Set)
     */
    public static <T> void replace(final Set<T> currentSet, final Set<T> newSet) {
        replace.replace(currentSet, newSet);
    }


    /**
	 * @see CollectionsReplace#replace(List, List)
     */
    public static <T> void replace(final List<T> currentList, final List<T> newList) {
    	replace.replace(currentList, newList);
    }

    /**
     * @see CollectionsReplace#replace(Collection, Collection) 
     */    
    public static <T> void replace(final Collection<T> currentCollection, final Collection<T> newCollection){
    	replace.replace(currentCollection, newCollection);
    }
    

    /**
     * @see CollectionsQuery#getOrEmpty(Collection)
	 */
    public static <E> Collection<E> getOrEmpty(final Collection<E> collection) {
        return query.getOrEmpty(collection);
    }

    /**
     * @see CollectionsQuery#getOrEmpty(List)
     */
    public static <E> List<E> getOrEmpty(final List<E> list) {
        return query.getOrEmpty(list);
    }

    /**
     * @see CollectionsQuery#getOrEmpty(Set)
     */
    public static <E> Set<E> getOrEmpty(final Set<E> set) {
        return query.getOrEmpty(set);
    }

    /**
     * @see CollectionsQuery#getNullIfEmpty(Set)
     */
    public static <T> Set<T> getNullIfEmpty(final Set<T> set) {
        return query.getNullIfEmpty(set);
    }

    /**
     * @see CollectionsQuery#getNullIfEmpty(List)
     */
    public static <T> List<T> getNullIfEmpty(final List<T> list) {
        return query.getNullIfEmpty(list);
    }

    /**
     * @see CollectionsQuery#isEmpty(Collection)
     */
    public static <E> boolean isEmpty(final Collection<E> list) {
        return query.isEmpty(list);
    }

    /**
     * @see CollectionsQuery#containsObjectByRef(Collection, Object)
     */
    public static <T> boolean containsObjectByRef(final Collection<T> collection, final T item) {
    	   return query.containsObjectByRef(collection,item);
    	    
    }

    /**
     * @see CollectionsFunctional#split(List, int)
	 */
    public static <T> List<List<T>> split(final List<T> original, final int size) {
        return functional.split(original, size);
    }

    /**
     * @see CollectionsFunctional#flatten(List)
     */
    public static <T> List<T> flatten(final List<List<T>> original) {
        return functional.flatten(original);
    }

    /**
     * @see CollectionsFunctional#allExceptFirst(List)
     */
    public static <T> List<T> allExceptFirst(final List<T> original){
    	return functional.allExceptFirst(original);
    }

    /**
     * @see CollectionsFunctional#allExceptFirst(List)
     */
    public static <T> List<T> tail(final List<T> original){
    	return allExceptFirst(original);
    }
    
    /**
     * @see CollectionsFunctional#allExceptLast(List)
     */
    public static <T> List<T> allExceptLast(final List<T> original){
    	return functional.allExceptLast(original);
    }
    
///
    /**
     * @see CollectionsFunctional#first(List)
     */
    public static <T> T first(final List<T> original){
    	return functional.first(original);
    }

    /**
     * @see CollectionsFunctional#first(List)
     */
    public static <T> T head(final List<T> original){
    	return first(original);
    }
    
    /**
     * @see CollectionsFunctional#last(List)
     */
    public static <T> T last(final List<T> original){
    	return functional.last(original);
    }

}
