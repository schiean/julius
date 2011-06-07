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
import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * Contains methods to query a collection
 */
public class CollectionsQuery {

    /**
     * will either return the original collection or an empty immutable collection.
     * 
     * This is useful for null-safe collection iteration with the for each loop
     * 
     * for(SubItem subItem: getOrEmpty(items)){ 
     * 		subItem.setItem(this); 
     * }
     * 
     * @param <E>
     *            Collection
     * @param collection
     *            or null
     * @return the same list or a new List of type <E> when the list parameter was null
     */
    public <E> Collection<E> getOrEmpty(final Collection<E> collection) {
        if (collection == null) {
            return Collections.emptyList();
        } else {
            return collection;
        }
    }

    /**
     * will either return the original List or an empty immutable List.
     * 
     * This is useful for null-safe collection iteration with the for each loop
     * 
     * for(SubItem subItem: getOrEmpty(items)){ 
     * 		subItem.setItem(this); 
     * }
     * @param <E>
     *            Listtype
     * @param list
     *            or null
     * @return the same list or a new List of type <E> when the list param was null
     */
    public <E> List<E> getOrEmpty(final List<E> list) {
        if (list == null) {
            return Collections.emptyList();
        } else {
            return list;
        }
    }

    /**
     * will either return the original Set or an empty immutable Set.
     * 
     * This is useful for null-safe collection iteration with the for each loop
     * 
     * for(SubItem subItem: getOrEmpty(items)){ 
     * 		subItem.setItem(this); 
     * }
     * 
     * @param <E>
     *            Set-type
     * @param set
     *            or null
     * @return the same set or a new Set of type <E> when the set parameter was null
     */
    public <E> Set<E> getOrEmpty(final Set<E> set) {
        if (set == null) {
            return Collections.emptySet();
        } else {
            return set;
        }
    }

    /**
     * Returns null if the input Set is empty, otherwise the input Set itself. 
     * This is useful if the object is to be transformed in
     * XML and has the rule 'either a Set with more then one item' or Null
     * 
     * @param set
     *            the input Set
     * @return null if the input Set is empty, otherwise the input Set itself.
     */
    public <T> Set<T> getNullIfEmpty(final Set<T> set) {
        if (isEmpty(set)) {
            return null;
        }
        return set;
    }

    /**
     * Returns null if the input List is empty, otherwise the input List itself. 
     * This is useful if the object is to be transformed
     * in XML and has the rule 'either a List with more then one item' or Null
     * 
     * @param list
     *            the input List
     * @return null if the input List is empty, otherwise the input List itself.
     */
    public <T> List<T> getNullIfEmpty(final List<T> list) {
        if (isEmpty(list)) {
            return null;
        }
        return list;
    }

    /**
     * 
     * @param <E>
     *            Collection
     * @param list
     *            or null
     * @return true when the collection parameter was null or empty
     */
    public <E> boolean isEmpty(final Collection<E> list) {
        return list == null || list.isEmpty();
    }

}
