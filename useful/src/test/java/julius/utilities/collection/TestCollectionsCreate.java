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

import static julius.utilities.CollectionHelper.entry;
import static julius.utilities.CollectionHelper.pair;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import julius.test.BDDTestCase;
import julius.test.TestHelperCollections;
import julius.utilities.CollectionHelper;


// TODO LOW: should use proper given/when/then

@SuppressWarnings({"unchecked","rawtypes"})
public class TestCollectionsCreate extends BDDTestCase {

	public void testUnique(){
		note("turning a list unique should keep order");
		
		List<Integer> list = CollectionHelper.list(1,3,4,5,5,3,6,7,3,8);
		List<Integer> exp = CollectionHelper.list(1,3,4,5,6,7,8);
		
		List<Integer> res = CollectionHelper.unique(list);
		TestHelperCollections.assertSameOrderAndSize(exp, res);
		TestHelperCollections.assertContainsAll(res, new HashSet<Integer>(list));
		TestHelperCollections.assertSameSize(res, new HashSet<Integer>(list));
		

		res = CollectionHelper.createListWithoutDoubleValues(list);
		TestHelperCollections.assertSameOrderAndSize(exp, res);
		TestHelperCollections.assertContainsAll(res, new HashSet<Integer>(list));
		TestHelperCollections.assertSameSize(res, new HashSet<Integer>(list));
	}
	
	public void testWithoutNulls(){
		note("list with nulls should have them removed, but all remains the same order");
		List<Integer> integer = CollectionHelper.list(1,2,3,null,4,5);
		List<Integer> exp = CollectionHelper.list(1,2,3,4,5);
		List<Integer> result = CollectionHelper.createListCopyWithoutNulls(integer);
		
		assertFalse(result.contains(null));
		
		TestHelperCollections.assertSameOrderAndSize(exp, result);			
	}
	
	
	public void testMap(){
		note("map should return a mutable hashmap");
			Map<String, Integer> l1 = CollectionHelper.map();
			assertNotNull(l1);
			assertTrue(l1.isEmpty());
			l1.put("a",5);// no exception
			assertTrue(l1.getClass().equals(HashMap.class));
		}


	public void testCreateMap() {
        note("createMap() without params (or null param) should return empty Map");
        	assertTrue(CollectionHelper.map().isEmpty());
        	Pair[] a = null;
        	assertTrue(CollectionHelper.map(a).isEmpty());
        	assertTrue(CollectionHelper.map(new Pair[] {}).isEmpty());
        note("createMap() with entry(a,'1'),entry(c,3) should return map{(a,1),(c,3)}");
        	Map<String, Integer> i = CollectionHelper.map(entry("a", 1), entry("c", 3));
        	assertTrue(i.size() == 2);
        	assertEquals(Integer.valueOf(1), i.get("a"));
        	assertEquals(Integer.valueOf(3), i.get("c"));
        note("createMap() with entry(a,'1'),entry(c,3), entry(null,null) should return map{(a,1),(c,3),(null,null)}");
            String strNull = null;
			Integer intNull = null;
			Map<String, Integer> j = CollectionHelper.map(pair("a", 1), pair("c", 3), pair(strNull, intNull));
            assertTrue(j.size() == 3);
            assertEquals(Integer.valueOf(1), i.get("a"));
            assertEquals(Integer.valueOf(3), i.get("c"));
            assertEquals(null, i.get(null));
      	successFullStory();

    }
	
	public void testSet(){
		note("set should return a mutable hashset");
			Set<Integer> l1 = CollectionHelper.set();
			assertNotNull(l1);
			assertTrue(l1.isEmpty());
			l1.add(5);
			assertTrue(l1.getClass().equals(HashSet.class));
		}

	public void testCreateSet() {
        note("createSet() without params (or null param) should return empty set");
        	assertTrue(CollectionHelper.set().isEmpty());
        	String[] a = null;
        	assertTrue(CollectionHelper.set(a).isEmpty());
        	assertTrue(CollectionHelper.set(new String[] {}).isEmpty());
        note("createSet() with '1','2' should return list{1,2}");
        	Set<Integer> i = CollectionHelper.set(1, 2);
        	assertTrue(i.size() == 2);
        	assertTrue(i.contains(1));
        	assertTrue(i.contains(2));
        note("createSet() with '1','2',null should return set{1,2, null}");
        	Set<Integer> j = CollectionHelper.set(1, 2, null);
        	assertTrue(j.size() == 3);
        	assertTrue(j.contains(1));
        	assertTrue(j.contains(2));
        	assertTrue(j.contains(null));
      	successFullStory();

    }

	
	public void testList(){
		note("list should return a mutable linkedList");
			List<Integer> l1 = CollectionHelper.list();
			assertNotNull(l1);
			assertTrue(l1.isEmpty());
			l1.add(5);
			assertTrue(l1.getClass().equals(LinkedList.class));
		note("createList should return a mutable LinkedList");
			List<Integer> l2 = CollectionHelper.createLinkedList();
			assertNotNull(l2);
			assertTrue(l2.isEmpty());
			l2.add(12);
			assertTrue(l2.getClass().equals(LinkedList.class));
	}

	public void testCreateList() {
        note("createList() without params (or null param) should return empty list");
        	assertTrue(CollectionHelper.createList().isEmpty());
        	String[] a = null;
        	assertTrue(CollectionHelper.createList(a).isEmpty());
        	assertTrue(CollectionHelper.createList(new String[] {}).isEmpty());
        note("createList() with '1','2' should return list{1,2}");
        	List<Integer> i = CollectionHelper.createList(1, 2);
        	assertTrue(i.size() == 2);
        	assertTrue(i.contains(1));
        	assertTrue(i.contains(2));
        note("createList() with '1','2',null should return list{1,2, null}");
        	List<Integer> j = CollectionHelper.createList(1, 2, null);
        	assertTrue(j.size() == 3);
        	assertTrue(j.contains(1));
        	assertTrue(j.contains(2));
        	assertTrue(j.contains(null));
      	successFullStory();

    }

    public void testCreateListWithoutNulls() {
        note("createListWithoutNulls should support empty or null params and return an empty list");
        	List<Integer> r1 = CollectionHelper.createListWithoutNulls((Integer[]) null);
        	assertTrue(r1.isEmpty());
        	List<Integer> r2 = CollectionHelper.createListWithoutNulls(new Integer[] {});
        	assertTrue(r2.isEmpty());

        note("createListWithoutNulls should accept 4 non null elements and return a list of those");
        	List<Integer> r3 = CollectionHelper.createListWithoutNulls(new Integer[] { 1, 2, 3, 4 });
        	assertEquals(4, r3.size());

        note("createListWithoutNulls should accept 5 non null and 2 null elements and return a list of only non null");
        	List<Integer> r4 = CollectionHelper.createListWithoutNulls(new Integer[] { 1, 2, null, 5, 3, null, 4 });
        	assertEquals(5, r4.size());
        
        successFullStory();

    }
    
    public void testCreateNumbered(){
    	given("a list a,b,c,d,e");
    		List<String> l = new LinkedList<String>();
    		l.add("a");l.add("b");l.add("c");l.add("d");l.add("e");
    	
    	when("numbered");
    		List<Numbered<String>> res = CollectionHelper.numbered(l);
    		
    	then("result should be numbered 0..n");
    		assertEquals(l.size(), res.size());
    		int i=0;
    		for(Numbered<String> val: res){
    			assertEquals(l.get(i), val.getValue());
    			assertEquals(i, val.getPosition());
    			i++;
    		}
    		
    	successFullStory();
    	
    }

    public void testUnnumbered(){
    	note("should support normal -> numbered -> normal' where normal equals normal'");
    	List<String> l = new LinkedList<String>();
		l.add("a");l.add("b");l.add("c");l.add("d");l.add("e");
		List<Numbered<String>> temp = CollectionHelper.numbered(l);
		List<String> res = CollectionHelper.unnumbered(temp);
		assertEquals(l.size(),res.size());
		for(int i=0;i<l.size();i++){
			assertEquals(l.get(i),res.get(i));
		}
	    
    }
}
