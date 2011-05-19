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
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import julius.test.BDDTestCase;
import julius.utilities.CollectionHelper;


//TODO LOW: should use proper given/when/then
public class TestQuery extends BDDTestCase {
	
    public void testGetNullIfEmptyList() {
        note("when provided null, return null");
        	List<String> o = null;
        	assertNull(CollectionHelper.getNullIfEmpty(o));

        note("when provided empty, return null");
            o = new LinkedList<String>();
            assertNull(CollectionHelper.getNullIfEmpty(o));
            	 
        	
        note("when provided not null, return original");
        	o = new LinkedList<String>();
        	o.add("hoi");
        	List<String> input = Collections.unmodifiableList(o);
			List<String> res = CollectionHelper.getNullIfEmpty(input);
        	assertNotNull(res);
        	assertEquals(o, res);
        	assertTrue(input==res);
        successFullStory();
    }

    public void testGetNullIfEmptySet() {
        note("when provided null, return null");
        	Set<String> o = null;
        	assertNull(CollectionHelper.getNullIfEmpty(o));
        
        note("when provided empty, return null");
        	o = new HashSet<String>();
        	assertNull(CollectionHelper.getNullIfEmpty(o));
        	
        	
        note("when provided not null, return original");
        	o = new HashSet<String>();
        	o.add("hoi");
        	Set<String> input = Collections.unmodifiableSet(o);
			Set<String> res = CollectionHelper.getNullIfEmpty(input);
        	assertNotNull(res);
        	assertEquals(o, res);
        	assertTrue(input==res);
        successFullStory();
    }

    public final void testGetOrEmptyCollection() {
        given("a null collection");
        	Collection<String> collection = null;
        when("get is called");
        	Collection<String> result = CollectionHelper.getOrEmpty(collection);
        then("result should be an empty list");
        	assertNotNull(result);
        	assertEquals(0, result.size());
        successFullStory();

        given("a non null collection");
        	collection = new HashSet<String>();
        when("get is called");
        	Collection<String> input = Collections.unmodifiableCollection(collection);
			result = CollectionHelper.getOrEmpty( input);
        then("result should be the same collection");
        	assertEquals(input, result);
        	assertTrue(input == result);
        successFullStory();

    }

    public final void testGetOrEmptyList() {
        given("a null list");
        	List<String> list = null;
        when("get is called");
        	List<String> result = CollectionHelper.getOrEmpty(list);
        then("result should be an empty list");
        	assertNotNull(result);
        	assertEquals(0, result.size());
        successFullStory();

        given("a non null List");
        	list = new ArrayList<String>();
        when("get is called");
        	List<String> input = Collections.unmodifiableList(list);
			result = CollectionHelper.getOrEmpty(input);
        then("result should be the same list");
        	assertEquals(list, result);
        	assertTrue(result==input);
        successFullStory();
    }

    public final void testGetOrEmptySet() {
        given("a null set");
        	Set<String> set = null;
        when("get is called");
        	Set<String> result = CollectionHelper.getOrEmpty(set);
        then("result should be an empty set");
        	assertNotNull(result);
        	assertEquals(0, result.size());
        successFullStory();

        given("a non null set");
        	set = new HashSet<String>();
        when("get is called");
        	Set<String> input = Collections.unmodifiableSet(set);
			result = CollectionHelper.getOrEmpty(input);
        then("result should be the same set");
        	assertEquals(set, result);
        	assertTrue(input==result);
        successFullStory();
    }

    public final void testIsEmpty() {
        given("a null ref");
        	Collection<String> collection = null;
        when("tested for empty");
        then("isEmpty is true");
        	assertTrue(CollectionHelper.isEmpty(collection));
        successFullStory();

        given("a new List");
        	collection = new ArrayList<String>();
        when("tested for empty");
        then("isEmpty is true");
        	assertTrue(CollectionHelper.isEmpty(Collections.unmodifiableCollection(collection)));
        successFullStory();

        given("a list with one item");
        	collection.add("test");
        when("tested for empty");
        then("isEmpty is false");
        	assertFalse(CollectionHelper.isEmpty(Collections.unmodifiableCollection(collection)));
        successFullStory();
    }

}
