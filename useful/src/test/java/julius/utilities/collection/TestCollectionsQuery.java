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

import static julius.utilities.CollectionHelper.values;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import julius.test.BDDTestCase;
import julius.utilities.CollectionHelper;

//TODO LOW: should use proper given/when/then
public class TestCollectionsQuery extends BDDTestCase {

    public static class Item {
        private final int i;

        public Item(final int j) {
            i = j;
        }

        @Override
        public boolean equals(final Object o) {
            return ((Item) o).i == i;
        }

        @Override
        public String toString() {
            return "val:" + i;
        }
    }

    public void testContainsObjectByRef() {
        Item i1 = new Item(1);
        Item i2 = new Item(2);
        Collection<Item> col = CollectionHelper.createList(i1, i2);

        given("collection:" + col);
        when("queried for exact same object == ");
        then("return true");
        assertTrue(CollectionHelper.containsAnyByRef(col, i1));
        assertTrue(CollectionHelper.containsAnyByRef(col, i2));

        when("queried for not exact same object ==, but equal ");
        then("return false");

        Item i3 = new Item(1);
        assertTrue(i1.equals(i3));
        assertFalse(CollectionHelper.containsAnyByRef(col, i3));

        when("queried for not exact same object ==, also not equal ");
        then("return false");

        Item i4 = new Item(4);
        assertFalse(CollectionHelper.containsAnyByRef(col, i4));

        note("should work with collections");
        assertTrue(CollectionHelper.containsAnyByRef(col, CollectionHelper.list(i2, i4)));

        successFullStory();
    }

    public void testContainsAny() {
        Item i1 = new Item(1);
        Item i2 = new Item(2);
        Item i3 = new Item(3);
        Item i4 = new Item(4);
        Item i5 = new Item(5);

        Collection<Item> col = CollectionHelper.createList(i1, i2, i3, i4, i5);

        given("collection:" + col);
        when("queried for exact same object == ");
        then("return true");
        assertTrue(CollectionHelper.containsAny(col, i1));
        assertTrue(CollectionHelper.containsAny(col, i2));

        when("queried for not exact same object ==, but equal ");
        then("return true");

        Item i1copy = new Item(1);
        assertTrue(i1.equals(i1copy));
        assertTrue(CollectionHelper.containsAny(col, i1copy));

        when("queried with multiple values");
        then("return true");

        assertTrue(CollectionHelper.containsAny(col, i1copy, i2));
        assertTrue(CollectionHelper.containsAny(col, values(i2, i1copy)));
        assertTrue(CollectionHelper.containsAny(col, values(null, i2, i1copy)));
        assertFalse(CollectionHelper.containsAny(col, (Item) null));

        when("queried for not exact same object ==, also not equal ");
        then("return false");

        Item i10 = new Item(10);
        assertFalse(CollectionHelper.containsAny(col, i10));

        note("should work with collections");
        assertTrue(CollectionHelper.containsAny(col, CollectionHelper.list(i2, i1copy)));

        successFullStory();
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    public void testModifiable() {
        note("normal collections should be mutable");

        assertTrue(CollectionHelper.isMutable(new LinkedList()));
        assertTrue(CollectionHelper.isMutable(new ArrayList()));
        assertTrue(CollectionHelper.isMutable(new HashSet()));
        assertTrue(CollectionHelper.isMutable(new TreeSet()));

        assertFalse(CollectionHelper.isImmutable(new LinkedList()));
        assertFalse(CollectionHelper.isImmutable(new ArrayList()));
        assertFalse(CollectionHelper.isImmutable(new HashSet()));
        assertFalse(CollectionHelper.isImmutable(new TreeSet()));

        note("wrapped collections should be immutable");

        assertFalse(CollectionHelper.isMutable(Collections.unmodifiableCollection(new LinkedList())));
        assertFalse(CollectionHelper.isMutable(Collections.unmodifiableList(new ArrayList())));
        assertFalse(CollectionHelper.isMutable(Collections.unmodifiableSet(new HashSet())));
        assertFalse(CollectionHelper.isMutable(Collections.unmodifiableSortedSet(new TreeSet())));
        assertFalse(CollectionHelper.isMutable(CollectionHelper.list(1, 2, 3, 4, 5)));

        assertTrue(CollectionHelper.isImmutable(Collections.unmodifiableCollection(new LinkedList())));
        assertTrue(CollectionHelper.isImmutable(Collections.unmodifiableList(new ArrayList())));
        assertTrue(CollectionHelper.isImmutable(Collections.unmodifiableSet(new HashSet())));
        assertTrue(CollectionHelper.isImmutable(Collections.unmodifiableSortedSet(new TreeSet())));
        assertTrue(CollectionHelper.isImmutable(CollectionHelper.list(1, 2, 3, 4, 5)));
    }

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
        assertTrue(input == res);
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
        assertTrue(input == res);
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
        result = CollectionHelper.getOrEmpty(input);
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
        assertTrue(result == input);
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
        assertTrue(input == result);
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

    public final void testIsNotEmpty() {
        given("a null ref");
        Collection<String> collection = null;
        when("tested for empty");
        then("isNotEmpty is false");
        assertFalse(CollectionHelper.isNotEmpty(collection));
        successFullStory();

        given("a new List");
        collection = new ArrayList<String>();
        when("tested for empty");
        then("isNotEmpty is false");
        assertFalse(CollectionHelper.isNotEmpty(Collections.unmodifiableCollection(collection)));
        successFullStory();

        given("a list with one item");
        collection.add("test");
        when("tested for empty");
        then("isNotEmpty is true");
        assertTrue(CollectionHelper.isNotEmpty(Collections.unmodifiableCollection(collection)));
        successFullStory();
    }
}
