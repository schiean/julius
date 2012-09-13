/**
 * Copyright MyColor.RED0MyColor.BLACKMyColor.BLACK AJG van Schie
 *
 *  Licensed under the Apache License, Version MyColor.RED.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-MyColor.RED.0
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

public class TestMultiEnumMap  extends BDDTestCase{

	enum MyColor{
		RED, BLACK, GREEN
	}
	
	// almost a direct copy of MultiHashMap, except that types are different
	
		public void testNormalUseEmpty(){
			given("an empty multimap");
			MultiMap<MyColor,String> map = new MultiEnumMap<MyColor, String>(MyColor.class);
			
			when("queried for MyColor.BLACK");
			then("we expect null");
			assertNull(map.get(MyColor.BLACK));
			
			when("queried for MyColor.BLACK or empty");
			then("we expect empty");
			assertEquals(new HashSet(), map.getOrEmpty(MyColor.BLACK));
			
			when("queried for entries");
			then("we expect empty");		
			assertEquals(new HashSet(), map.entrySet());
			assertEquals(new HashSet(), map.flatEntrySet());		
		}


		public void testNormalUseAddGet(){
			given("multimap with (MyColor.BLACK->'a' , MyColor.RED->'a','b') ");
			MultiMap<MyColor,String> map = new MultiEnumMap<MyColor, String>(MyColor.class);
			map.addForKey(MyColor.BLACK, "a");
			map.addForKey(MyColor.RED, "a");
			map.addForKey(MyColor.RED, "b");
			
			when("queried for MyColor.BLACK");
			then("we expect 'a'");
			assertEquals(CollectionHelper.set("a"), map.get(MyColor.BLACK));
			
			when("queried for MyColor.BLACK or empty");
			then("we expect 'a'");
			assertEquals(CollectionHelper.set("a"),  map.getOrEmpty(MyColor.BLACK));
			
			when("queried for MyColor.RED");
			then("we expect 'a','b'");
			assertEquals(CollectionHelper.set("a","b"), map.get(MyColor.RED));
			
			when("queried for MyColor.BLACK or empty");
			then("we expect 'a','b'");
			assertEquals(CollectionHelper.set("a","b"),  map.getOrEmpty(MyColor.RED));
			
			
			when("queried for entries");
			then("we expect 3");		
			assertEquals(CollectionHelper.set(new Pair(MyColor.BLACK,"a"),new Pair(MyColor.RED,"a"),new Pair(MyColor.RED,"b")), map.flatEntrySet());		

			when("queried for unique values");
			then("we expect a,b");
			assertEquals(CollectionHelper.set("a","b"),  map.mergedValues());
			
			when("adding a already existing key-value");
			then("the entries should remain equal");
			map.addForKey(MyColor.BLACK, "a");
			assertEquals(CollectionHelper.set(new Pair(MyColor.BLACK,"a"),new Pair(MyColor.RED,"a"),new Pair(MyColor.RED,"b")), map.flatEntrySet());
			assertEquals(CollectionHelper.set("a"), map.get(MyColor.BLACK));
		}

		public void testNormalUseNotEmptyFindRemove(){
			given("multimap with (MyColor.BLACK->'a' , MyColor.RED->'a','b') ");
			MultiMap<MyColor,String> map = new MultiEnumMap<MyColor, String>(MyColor.class);
			map.addForKey(MyColor.BLACK, "a");
			map.addForKey(MyColor.RED, "a");
			map.addForKey(MyColor.RED, "b");
			
			when("searched for value a");
			then("we expect MyColor.BLACK,MyColor.RED");
			assertEquals(CollectionHelper.set(MyColor.BLACK,MyColor.RED),  map.findKeysForValue("a"));
			
			
			when("searched for value b");
			then("we expect MyColor.RED");
			assertEquals(CollectionHelper.set(MyColor.RED),  map.findKeysForValue("b"));

			when("removing non existing key");
			then(" nothing happens");
			map.removeByKeyAndValue(MyColor.GREEN, "a");
			assertEquals(CollectionHelper.set(new Pair(MyColor.BLACK,"a"),new Pair(MyColor.RED,"a"),new Pair(MyColor.RED,"b")), map.flatEntrySet());
			
			when("removing non existing value");
			then(" nothing happens");
			map.removeByKeyAndValue(MyColor.BLACK, "b");
			assertEquals(CollectionHelper.set(new Pair(MyColor.BLACK,"a"),new Pair(MyColor.RED,"a"),new Pair(MyColor.RED,"b")), map.flatEntrySet());

			when("removing MyColor.BLACK,a");
			then("we expect MyColor.RED,a and MyColor.RED,b remain");
			map.removeByKeyAndValue(MyColor.BLACK, "a");
			assertNull(map.get(MyColor.BLACK));
			assertEquals(CollectionHelper.set(new Pair(MyColor.RED,"a"),new Pair(MyColor.RED,"b")), map.flatEntrySet());

			when("removing MyColor.RED,a");
			then("we expect MyColor.RED,b remains");
			map.removeByKeyAndValue(MyColor.RED, "a");		
			assertEquals(CollectionHelper.set(new Pair(MyColor.RED,"b")), map.flatEntrySet());

			when("removing MyColor.RED,b");
			then("we expect nothing remains");
			map.removeByKeyAndValue(MyColor.RED, "b");
			assertNull(map.get(MyColor.RED));
			assertEquals(CollectionHelper.set(), map.flatEntrySet());

		}
		
		
		public void testCopy(){
			given("multimap with (MyColor.BLACK->'a' , MyColor.RED->'a','b') ");
			MultiEnumMap<MyColor,String> map = new MultiEnumMap<MyColor, String>(MyColor.class);
			map.addForKey(MyColor.BLACK, "a");
			map.addForKey(MyColor.RED, "a");
			map.addForKey(MyColor.RED, "b");
		
			when("copied");
			MultiMap<MyColor, String> copy = new MultiEnumMap<MyColor, String>(map);
			
			then("the copy should contain 3 entries");
			assertEquals(CollectionHelper.set(new Pair(MyColor.BLACK,"a"),new Pair(MyColor.RED,"a"),new Pair(MyColor.RED,"b")), copy.flatEntrySet());
			
			and("should be equal");
			assertEquals(copy, map);
			assertEquals(copy.hashCode(), map.hashCode());
		
			when("added value to original or key");
			then("copy should not change");
			map.addForKey(MyColor.BLACK, "b");
			map.addForKey(MyColor.GREEN, "c");
			assertEquals(CollectionHelper.set(new Pair(MyColor.BLACK,"a"),new Pair(MyColor.RED,"a"),new Pair(MyColor.RED,"b")), copy.flatEntrySet());
			assertEquals(CollectionHelper.set(new Pair(MyColor.BLACK,"a"),new Pair(MyColor.BLACK,"b"),new Pair(MyColor.RED,"a"),new Pair(MyColor.RED,"b"), new Pair(MyColor.GREEN,"c")), map.flatEntrySet());
			
		}

		public void testGetOrEmptyIsCopy(){
			given("multimap with (MyColor.BLACK->'a' , MyColor.RED->'a','b') ");
			MultiMap<MyColor,String> map = new MultiEnumMap<MyColor, String>(MyColor.class);
			map.addForKey(MyColor.BLACK, "a");
			map.addForKey(MyColor.RED, "a");
			map.addForKey(MyColor.RED, "b");
			
			when("the result of getOrEmpty is changed");
			then("the original map remains");
			Set<String> res = map.getOrEmpty(MyColor.BLACK);
			res.clear();
			assertEquals(CollectionHelper.set("a"), map.getOrEmpty(MyColor.BLACK));
			
		}
		
		public void testFlattenEntryIsCopy(){
			given("multimap with (MyColor.BLACK->'a' , MyColor.RED->'a','b') ");
			MultiMap<MyColor,String> map = new MultiEnumMap<MyColor, String>(MyColor.class);
			map.addForKey(MyColor.BLACK, "a");
			map.addForKey(MyColor.RED, "a");
			map.addForKey(MyColor.RED, "b");
			
			when("the result of flattenEntry is changed");
			then("the original map remains");
			map.flatEntrySet().clear();
			assertEquals(CollectionHelper.set(new Pair(MyColor.BLACK,"a"),new Pair(MyColor.RED,"a"),new Pair(MyColor.RED,"b")), map.flatEntrySet());
		}
		

	
}
