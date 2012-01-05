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

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import julius.test.BDDTestCase;
import julius.test.TestHelperCollections;
import julius.utilities.CollectionHelper;
import julius.utilities.collection.CollectionsFunctional.Window;

@SuppressWarnings("unchecked")
public class TestCollectionsFunctional extends BDDTestCase {
	
	public void testFirstLast(){
		given("list (1,2,3,4)");
    	
			List<Integer> l = new LinkedList<Integer>();
			l.add(1);l.add(2);l.add(3);l.add(4);
	
		when("first is called");
	
			Integer result = CollectionHelper.first(l);
	
		then("we expect 1");
			assertEquals(Integer.valueOf(1),result);
			
		note("should be alias to head");
	
			result = CollectionHelper.head(l);
	
			assertEquals(Integer.valueOf(1),result);
			
		when("last is called");
			
			result = CollectionHelper.last(l);
	
		then("we expect 4");
			assertEquals(Integer.valueOf(4),result);
					
			
		successFullStory();
	}
	
	
    public void testAllExceptFirst(){
    	given("list (1,2,3,4)");
    	
    		List<Integer> l = new LinkedList<Integer>();
    		l.add(1);l.add(2);l.add(3);l.add(4);
    	
    	when("all except first is called");
    	
    		List<Integer> result = CollectionHelper.allExceptFirst(l);
    	
    	then("we expect 2,3,4");
    		assertEquals(l.size()-1,result.size());
    		assertFalse(result.contains(1));
    		assertTrue(result.get(0)==(2));
    		assertTrue(result.get(1)==(3));
    		assertTrue(result.get(2)==(4));
    	
    	note("should be alias to tail");
    	
    		result = CollectionHelper.tail(l);
    	
    		assertEquals(l.size()-1,result.size());
    		assertFalse(result.contains(1));
    		assertTrue(result.get(0)==(2));
    		assertTrue(result.get(1)==(3));
    		assertTrue(result.get(2)==(4));
    		
    	successFullStory();
    	
    }

    public void testAllExceptLast(){
    	given("list (1,2,3,4)");
    	
    		List<Integer> l = new LinkedList<Integer>();
    		l.add(1);l.add(2);l.add(3);l.add(4);
    	
    	when("all except last is called");
    	
    		List<Integer> result = CollectionHelper.allExceptLast(l);
    	
    	then("we expect 1,2,3");
    		assertEquals(l.size()-1,result.size());
    		assertFalse(result.contains(4));
    		assertTrue(result.get(0)==(1));
    		assertTrue(result.get(1)==(2));
    		assertTrue(result.get(2)==(3));
    	
    	successFullStory();
    	
    }
    
    public void testAllExceptLastEmptyOrOne(){
    	given("an empty colleciont");
    		List<String> s = CollectionHelper.createLinkedList();
    	when("allExceptLast is called");
    		List<String> r = CollectionHelper.allExceptLast(s);
    	then("we expect an empty collection");
    		assertNotNull(r);
    		assertTrue(r.isEmpty());
    		
    	given("a singleton collection");
    		s.add("a");
    	when("allExceptLast is called");
    		r = CollectionHelper.allExceptLast(s);    		
    	then("we expect an empty collection");
    		assertNotNull(r);
    		assertTrue(r.isEmpty());
		successFullStory();
    }
    

    public void testAllExceptFirstEmptyOrOne(){
    	given("an empty collection");
    		List<String> s = CollectionHelper.createLinkedList();
    	when("allExceptFirst is called");
    		List<String> r = CollectionHelper.allExceptFirst(s);
    	then("we expect an empty collection");
    		assertNotNull(r);
    		assertTrue(r.isEmpty());
    		
    	given("a singleton collection");
    		s.add("a");
    	when("allExceptFirst is called");
    		r = CollectionHelper.allExceptFirst(s);    		
    	then("we expect an empty collection");
    		assertNotNull(r);
    		assertTrue(r.isEmpty());
		successFullStory();
    }

    
    public void testFirstLastEmptyOrOne(){
    	given("an empty collection");
    		List<String> s = CollectionHelper.createLinkedList();
    	when("last is called");
    		String r = CollectionHelper.last(s);
    	then("we expect null");
    		assertNull(r);
    	when("first is called");
    		r = CollectionHelper.first(s);
    	then("we expect null");
    		assertNull(r);
    			
    		
    	given("a singleton collection");
    		s.add("a");
    	when("last is called");
    		r = CollectionHelper.last(s);    		
    	then("we expect the single item");
    		assertNotNull(r);
    		assertTrue(r.equals("a"));
    	when("first is called");
    		r = CollectionHelper.first(s);    		
    	then("we expect the single item");
    		assertNotNull(r);
    		assertTrue(r.equals("a"));
    			
    }

    
    
    // TODO LOW: should use proper given/when/then
    public void testSplitFlatten() {
        note("should allow null");
        	CollectionHelper.split(null, 10);
        note("should allow empty");
        	assertTrue(CollectionHelper.split(new LinkedList<Integer>(), 10).size() == 0);
        note("only allow splitsize > 0 ");
        try {
            CollectionHelper.split(null, -1);
            fail("only allow positve splitsize");
        } catch (IllegalArgumentException e) {
        }
        note("after split the total amount should be the same");
        	List<Integer> l1 = CollectionHelper.createList(1, 2, 3, 4);
        	List<List<Integer>> splitted = CollectionHelper.split(l1, 2);
        	List<Integer> r1 = CollectionHelper.flatten(splitted);
        	assertEquals(l1.size(), r1.size());
        	assertEquals(l1, r1);

        note("after split the total amount should be the same ALSO WITH itemCount % splitsize != 0");
        	List<Integer> l2 = CollectionHelper.createList(1, 2, 3, 4);
        	List<List<Integer>> splitted2 = CollectionHelper.split(l2, 3);
        	List<Integer> r2 = CollectionHelper.flatten(splitted2);
        	assertEquals(l2.size(), r2.size());
        	assertEquals(l2, r2);
    }
    

	public void testFlattenSet() {
        note("(1,2),null,(3,4,5) should become (1,2,3,4,5)");
        	Set<Integer> l1 = CollectionHelper.set(1, 2);
        	Set<Integer> l2 = CollectionHelper.set(3, 4, 5);
        	Set<Set<Integer>> splitted = CollectionHelper.set(l1, null, l2);
        	Set<Integer> r1 = CollectionHelper.flattenToSet(splitted);
        	assertEquals(l1.size() + l2.size(), r1.size());
        	assertEquals(CollectionHelper.set(1,2,3,4,5), r1);
        	
        	// more complex with set/list combinations
        	List<Integer> r2 = CollectionHelper.flattenToList(splitted);
        	assertEquals(l1.size() + l2.size(), r2.size());
        	assertEquals(CollectionHelper.list(1,2,3,4,5), r2);
        	
        	List<Set<Integer>> splitted2 = CollectionHelper.list(l1,null,l2);
        	Set<Integer> r3 = CollectionHelper.flattenToSet(splitted2);
        	assertEquals(l1.size() + l2.size(), r3.size());
        	assertEquals(CollectionHelper.set(1,2,3,4,5), r3);
        	
    }
    
	public void testMovingWindow(){
		note("(a,b,c,d,e) => (a,b)(b,c)(c,d)(d,e)");
		
		List<String> items = CollectionHelper.list("a","b","c","d","e");
		List<Window<String>> exp = CollectionHelper.list(window("a","b"),window("b","c"),window("c","d"),window("d","e"));
		
		List<Window<String>> res = CollectionHelper.getMovingWindow(items);
		System.out.println(res);
		TestHelperCollections.assertSameOrderAndSize(exp,res);
		
	}
    
	private Window<String> window(final String a,final String b){
		return new Window<String>(a,b);
	}
    
    
}
