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

import java.util.Collections;
import java.util.List;

import julius.validation.Assertions;

/**
 * contains methods to support a more functional programming style approach to collections
 */
public class CollectionsFunctional {
	
	private final CollectionsCreate create = new CollectionsCreate();
	private final CollectionsQuery query = new CollectionsQuery();
	
    /**
     * Will merge a list of lists by taking all elements of the provided lists into a new list.
     * Works only only level deep, but could be used multiple times for multiple levels of flattening.
     * @param <T>
     * @param original List of List (null safe for original and part lists)
     * @return List with all elements of the parts of the original
     */
    public <T> List<T> flatten(final List<List<T>> original) {
        List<T> fullList = create.createLinkedList();
        for (List<T> lPart : query.getOrEmpty(original)) {
            fullList.addAll(query.getOrEmpty(lPart));
        }
        return fullList;
    }

    /**
     * Will split a list into multiple parts of the provided size (where the last part is between 1 and size elements)
     * @param <T>
     * @param original	original list (null safe)
     * @param size		wanted list size (>1)
     * @return list of lists with all elements of the original list but split up in 'size'-sized list parts
     */
    public <T> List<List<T>> split(final List<T> original, final int size) {
        
    	Assertions.argument.assertTrue( size > 0, "split size should be > 0");
        
        final List<List<T>> splittedList = create.createLinkedList();
        final List<T> fullList = query.getOrEmpty(original); // null safe
        final int totalAmountOfItems = fullList.size();
        
        for (int index = 0; index < totalAmountOfItems; index = index + size) {
            int nextIndex = index + size;
            if (totalAmountOfItems < nextIndex) {
                splittedList.add(fullList.subList(index, totalAmountOfItems));
            } else {
                splittedList.add(fullList.subList(index, nextIndex));
            }
        }
        
        return splittedList;
    }
    
    
    /**
     * Element head = list.first(); // or head
	 * .. something special for first..
	 * for(Element b: exceptFirst(list)){ // or tail
	 * 		
	 * }
	 * 
	 *  will return an empty list if the collection has less then 2 items (a singleton list has only one item which is both first & last)
	 * 
     * @param <T>
     * @param fullList
     * @return a list with all items except the first/head
     */
    public <T> List<T> allExceptFirst(final List<T> fullList){
    	if(fullList.size() < 2){
    		return Collections.emptyList();
    	}
    	return Collections.unmodifiableList(fullList.subList(1,fullList.size()));
    }

    /**
     * example usage:
     * 
     * for(Elem b: exceptLast(list) ){
	 * 
	 * }  
	 * Element last = list.last();  
	 * 
	 * will return an empty list if the collection has less then 2 items  (a singleton list has only one item which is both first & last)
	 * 
     * @param <T>
     * @param fullList
     * @return a immutable list with all items except the last
     */
    public <T> List<T> allExceptLast(final List<T> fullList){
    	if(fullList.size() < 2){
    		return Collections.emptyList();
    	}
    	return Collections.unmodifiableList(fullList.subList(0,fullList.size()-1));
    }
    
    /**
     * the first item (or null if the list is empty)
     * if the list has one item it is both the first and last of the list
     * @param <T>
     * @param fullList
     * @return
     */
    public <T> T first(final List<T> fullList){
    	if(fullList.isEmpty()){
    		return null;
    	}
    	return fullList.get(0);// could use iterator instead to support more then list
    }
    
    /**
     * the last item (or null if the list is empty)
     * if the list has one item it is both the first and last of the list
     * @param <T>
     * @param fullList
     * @return
     */
    public <T> T last(final List<T> fullList){
    	if(fullList.isEmpty()){
    		return null;
    	}
    	return fullList.get(fullList.size()-1);
    }

}
