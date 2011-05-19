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

import julius.test.BDDTestCase;
import julius.utilities.CollectionHelper;


public class TestFunctional extends BDDTestCase {
	
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
}
