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
import java.util.HashSet;
import java.util.LinkedList;

import julius.test.BDDTestCase;
import julius.utilities.CollectionHelper;


public class TestReplace extends BDDTestCase {
	   
	public final void testReplaceSet() {
	    	// normal
	        given("a hash set with 1 entry");
	        	HashSet<String> currentSet = new HashSet<String>();
	        	currentSet.add("currentSet");

	        when("replace is called with a new set");
	        	HashSet<String> newSet = new HashSet<String>();
	        	newSet.add("newSet");
	        
	        	CollectionHelper.replace(currentSet, Collections.unmodifiableSet(newSet));
	        
	        then("currentSet should contain 1 entry from newSet");
	        	
	        	assertNotNull(currentSet);
	        	assertEquals(1, currentSet.size());
	        	assertTrue(currentSet.contains("newSet"));        	
	        	
	        successFullStory();	        
	    }

		public void testNullSafeReplacementSet(){
	        // null safe target
	        given("a hash set with 1 entry");
	        	
        		HashSet<String> currentSet = new HashSet<String>();
	        	currentSet.add("currentSet");
	        	
	        when("replace is called with a null ref");
	        	CollectionHelper.replace(currentSet, null);
	        	
	        then("currentSet should contain 0 entry from newSet");
	        
	        	assertNotNull(currentSet);
	        	assertEquals(0, currentSet.size());
	        	
	        successFullStory();

		}
	
	

	    public final void testReplaceList() {
	    	// normal
	        given("a List with 1 entry");
	        
	        	LinkedList<String> currentList = new LinkedList<String>();
	        	currentList.add("currentList");
	        	
	        when("replace is called with a new list");
	        
	        	LinkedList<String> newList = new LinkedList<String>();
	        	newList.add("newList");
	        	CollectionHelper.replace(currentList, Collections.unmodifiableList(newList));
	        	
	        then("currentList should contain 1 entry from newList");
	        
	        	assertNotNull(currentList);
	        	assertEquals(1, currentList.size());
	        	assertTrue(currentList.contains("newList"));
	        	
	        successFullStory();
	    }
	    
	    public void testNullSafeReplacementList(){
	        // null safe
	        given("a newList with 1 entry");
	        
	        	LinkedList<String> currentList = new LinkedList<String>();
	        	currentList.add("currentList");
	        	
	        when("replace is called with a null ref");
	        	
	        	CollectionHelper.replace(currentList, null);
	        	
	        then("currentList should contain 0 entry from newList");
	        	assertNotNull(currentList);
	        	assertEquals(0, currentList.size());
	        	
	        successFullStory();
	    }

	    
	    public final void testReplaceCollection() {
	    	// normal
	        given("a Collection with 1 entry");
	        
	        	Collection<String> currentCollection = new LinkedList<String>();
	        	currentCollection.add("currentList");
	        	
	        when("replace is called with a new Collection");
	        
	        	Collection<String> newList = new LinkedList<String>();
	        	newList.add("newCollection");
	        	CollectionHelper.replace(currentCollection, Collections.unmodifiableCollection(newList));
	        	
	        then("currentCollection should contain 1 entry from newCollection");
	        
	        	assertNotNull(currentCollection);
	        	assertEquals(1, currentCollection.size());
	        	assertTrue(currentCollection.contains("newCollection"));
	        	
	        successFullStory();
	    }
	    
	    public void testNullSafeReplacementCollection(){
	        // null safe
	        given("a newCollection with 1 entry");
	        
	       			Collection<String> currentCollection = new LinkedList<String>();
		        	currentCollection.add("currentCollection");
	        	
	        when("replace is called with a null ref");
	        	
	        	CollectionHelper.replace(currentCollection, null);
	        	
	        then("currentCollection should contain 0 entry from newCollection");
	        	assertNotNull(currentCollection);
	        	assertEquals(0, currentCollection.size());
	        	
	        successFullStory();
	    }

	    public void testThrowingExceptionForNullRefsOriginalCollection(){
	    	given("a null ref as original collection");
	    	when("a valid replacement set/list/collection is provided in a call to replace(ori,newCol) (where ori ==null)");
	    	then("we expect an exception because it can't replace the contents of null");
	    	
	    	try{
	    		CollectionHelper.replace(null, (Collection<String>) new LinkedList<String>());
	    		fail("expected exception");
	    	}catch(IllegalArgumentException ex){}
	    	try{
	    		CollectionHelper.replace(null, new LinkedList<String>());
	    		fail("expected exception");
	    	}catch(IllegalArgumentException ex){}
	    	try{
	    		CollectionHelper.replace(null, new HashSet<String>());
	    		fail("expected exception");
	    	}catch(IllegalArgumentException ex){}
	    	
	    	successFullStory();
	    }
}
