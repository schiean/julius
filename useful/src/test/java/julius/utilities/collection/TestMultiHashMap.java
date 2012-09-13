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

import java.util.HashSet;
import java.util.Set;

import julius.test.BDDTestCase;
import julius.utilities.CollectionHelper;

public class TestMultiHashMap extends BDDTestCase{

	public void testNormalUseEmpty(){
		given("an empty multimap");
		MultiMap<Integer,String> map = new MultiHashMap<Integer, String>();
		
		when("queried for 1");
		then("we expect null");
		assertNull(map.get(1));
		
		when("queried for 1 or empty");
		then("we expect empty");
		assertEquals(new HashSet(), map.getOrEmpty(1));
		
		when("queried for entries");
		then("we expect empty");		
		assertEquals(new HashSet(), map.entrySet());
		assertEquals(new HashSet(), map.flatEntrySet());		
	}


	public void testNormalUseAddGet(){
		given("multimap with (1->'a' , 2->'a','b') ");
		MultiMap<Integer,String> map = new MultiHashMap<Integer, String>();
		map.addForKey(1, "a");
		map.addForKey(2, "a");
		map.addForKey(2, "b");
		
		when("queried for 1");
		then("we expect 'a'");
		assertEquals(CollectionHelper.set("a"), map.get(1));
		
		when("queried for 1 or empty");
		then("we expect 'a'");
		assertEquals(CollectionHelper.set("a"),  map.getOrEmpty(1));
		
		when("queried for 2");
		then("we expect 'a','b'");
		assertEquals(CollectionHelper.set("a","b"), map.get(2));
		
		when("queried for 1 or empty");
		then("we expect 'a','b'");
		assertEquals(CollectionHelper.set("a","b"),  map.getOrEmpty(2));
		
		
		when("queried for entries");
		then("we expect 3");		
		assertEquals(CollectionHelper.set(new Pair(1,"a"),new Pair(2,"a"),new Pair(2,"b")), map.flatEntrySet());		

		when("queried for unique values");
		then("we expect a,b");
		assertEquals(CollectionHelper.set("a","b"),  map.mergedValues());
		
		when("adding a already existing key-value");
		then("the entries should remain equal");
		map.addForKey(1, "a");
		assertEquals(CollectionHelper.set(new Pair(1,"a"),new Pair(2,"a"),new Pair(2,"b")), map.flatEntrySet());
		assertEquals(CollectionHelper.set("a"), map.get(1));
	}

	public void testNormalUseNotEmptyFindRemove(){
		given("multimap with (1->'a' , 2->'a','b') ");
		MultiMap<Integer,String> map = new MultiHashMap<Integer, String>();
		map.addForKey(1, "a");
		map.addForKey(2, "a");
		map.addForKey(2, "b");
		
		when("searched for value a");
		then("we expect 1,2");
		assertEquals(CollectionHelper.set(1,2),  map.findKeysForValue("a"));
		
		
		when("searched for value b");
		then("we expect 2");
		assertEquals(CollectionHelper.set(2),  map.findKeysForValue("b"));

		when("removing non existing key");
		then(" nothing happens");
		map.removeByKeyAndValue(3, "a");
		assertEquals(CollectionHelper.set(new Pair(1,"a"),new Pair(2,"a"),new Pair(2,"b")), map.flatEntrySet());
		
		when("removing non existing value");
		then(" nothing happens");
		map.removeByKeyAndValue(1, "b");
		assertEquals(CollectionHelper.set(new Pair(1,"a"),new Pair(2,"a"),new Pair(2,"b")), map.flatEntrySet());

		when("removing 1,a");
		then("we expect 2,a and 2,b remain");
		map.removeByKeyAndValue(1, "a");
		assertNull(map.get(1));
		assertEquals(CollectionHelper.set(new Pair(2,"a"),new Pair(2,"b")), map.flatEntrySet());

		when("removing 2,a");
		then("we expect 2,b remains");
		map.removeByKeyAndValue(2, "a");		
		assertEquals(CollectionHelper.set(new Pair(2,"b")), map.flatEntrySet());

		when("removing 2,b");
		then("we expect nothing remains");
		map.removeByKeyAndValue(2, "b");
		assertNull(map.get(2));
		assertEquals(CollectionHelper.set(), map.flatEntrySet());

	}
	
	
	public void testCopy(){
		given("multimap with (1->'a' , 2->'a','b') ");
		MultiMap<Integer,String> map = new MultiHashMap<Integer, String>();
		map.addForKey(1, "a");
		map.addForKey(2, "a");
		map.addForKey(2, "b");
	
		when("copied");
		MultiMap<Integer, String> copy = new MultiHashMap<Integer, String>(map);
		
		then("the copy should contain 3 entries");
		assertEquals(CollectionHelper.set(new Pair(1,"a"),new Pair(2,"a"),new Pair(2,"b")), copy.flatEntrySet());
		
		and("should be equal");
		assertEquals(copy, map);
		assertEquals(copy.hashCode(), map.hashCode());
	
		when("added value to original or key");
		then("copy should not change");
		map.addForKey(1, "b");
		map.addForKey(3, "c");
		assertEquals(CollectionHelper.set(new Pair(1,"a"),new Pair(2,"a"),new Pair(2,"b")), copy.flatEntrySet());
		assertEquals(CollectionHelper.set(new Pair(1,"a"),new Pair(1,"b"),new Pair(2,"a"),new Pair(2,"b"), new Pair(3,"c")), map.flatEntrySet());
		
	}

	public void testGetOrEmptyIsCopy(){
		given("multimap with (1->'a' , 2->'a','b') ");
		MultiMap<Integer,String> map = new MultiHashMap<Integer, String>();
		map.addForKey(1, "a");
		map.addForKey(2, "a");
		map.addForKey(2, "b");
		
		when("the result of getOrEmpty is changed");
		then("the original map remains");
		Set<String> res = map.getOrEmpty(1);
		res.clear();
		assertEquals(CollectionHelper.set("a"), map.getOrEmpty(1));
		
	}
	
	public void testFlattenEntryIsCopy(){
		given("multimap with (1->'a' , 2->'a','b') ");
		MultiMap<Integer,String> map = new MultiHashMap<Integer, String>();
		map.addForKey(1, "a");
		map.addForKey(2, "a");
		map.addForKey(2, "b");
		
		when("the result of flattenEntry is changed");
		then("the original map remains");
		map.flatEntrySet().clear();
		assertEquals(CollectionHelper.set(new Pair(1,"a"),new Pair(2,"a"),new Pair(2,"b")), map.flatEntrySet());
	}
	
}
