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
import java.util.List;
import java.util.Set;

import julius.validation.Assertions;


public class CollectionsReplace {

    /**
     * will clear the current set and add all items from the newSet.
     * @param <T>
     * @param currentSet not null Set
     * @param newSet Set or 'null' (when the newSet is null, the original will be cleared)
     */
    public <T> void replace(final Set<T> currentSet, final Set<T> newSet) {
    	Assertions.argument.assertTrue(currentSet!=null, "cannot replace a null reference");
        currentSet.clear();
        if (newSet != null) {
            currentSet.addAll(newSet);
        }
    }

    /**
     * will clear the current list and add all items from the newList.
     * @param <T>
     * @param currentList not null List
     * @param newList List or 'null' (when the newList is null, the original will be cleared)
     */
    public <T> void replace(final List<T> currentList, final List<T> newList) {
    	Assertions.argument.assertTrue(currentList!=null, "cannot replace a null reference");
    	currentList.clear();
        if (newList != null) {
            currentList.addAll(newList);
        }
    }

    /**
     * will clear the current collection and add all items from the newCollection.
     * @param <T>
     * @param currentCollection not null mutable Collection
     * @param newCollectino Collection or 'null' (when the newCollection is null, the original will be cleared)
     */    
    public <T> void replace(final Collection<T> currentCollection, final Collection<T> newCollection){
    	Assertions.argument.assertTrue(currentCollection!=null, "cannot replace a null reference");
    	currentCollection.clear();
    	if(newCollection != null){
    		currentCollection.addAll(newCollection);
    	}
    }

	
}
