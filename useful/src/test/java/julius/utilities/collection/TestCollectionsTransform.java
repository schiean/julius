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
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import julius.test.BDDTestCase;
import julius.test.TestHelperCollections;
import julius.utilities.CollectionHelper;

public class TestCollectionsTransform extends BDDTestCase{
    
	Collection<Integer> originalWrapped = CollectionHelper.list(1,2,3,4,5);
	Collection<Integer> originalList = new LinkedList<Integer>(originalWrapped);
	Collection<Integer> originalSet = new HashSet<Integer>(originalWrapped);
	Integer[] orginalArray = {1,2,3,4,5};
	
	public void testAsList(){
		note("should return original when it is a requested type and mutable");		
		assertSame(originalList, CollectionHelper.asList(originalList)); 
		
		note("else should return decoupled copy");
		List<Integer> res = CollectionHelper.asList(originalWrapped);
		assertNotSame(originalWrapped, res);
		TestHelperCollections.assertSameOrderAndSize(originalWrapped, res);
		assertDecoupledAndMutable(originalWrapped, res);
		
	}
	
	public void testAsSet(){
		note("should return original when it is a requested type and mutable");		
		assertSame(originalSet, CollectionHelper.asSet(originalSet)); 
		
		note("else should return decoupled copy");
		Set<Integer> res = CollectionHelper.asSet(originalWrapped);
		assertNotSame(originalWrapped, res);
		TestHelperCollections.assertSameElementsAndSize(originalWrapped, res);
		assertDecoupledAndMutable(originalWrapped, res);
	}

	public void testAsLinkedList(){
		note("should return original when it is a requested type and mutable");		
		assertSame(originalList, CollectionHelper.asLinkedList(originalList)); 
		
		note("else should return decoupled copy");
		LinkedList<Integer> res = CollectionHelper.asLinkedList(originalWrapped);
		assertNotSame(originalWrapped, res);
		TestHelperCollections.assertSameOrderAndSize(originalWrapped, res);
		assertDecoupledAndMutable(originalWrapped, res);
	}
	
	public void testAsHashSet(){
		note("should return original when it is a requested type and mutable");		
		assertSame(originalSet, CollectionHelper.asHashSet(originalSet)); 
		
		note("else should return decoupled copy");
		Set<Integer> res = CollectionHelper.asHashSet(originalWrapped);
		assertNotSame(originalWrapped, res);
		TestHelperCollections.assertSameElementsAndSize(originalWrapped, res);
		assertDecoupledAndMutable(originalWrapped, res);
	}
	    
	public void testAsCollection(){
		note("array to collection should return decoupled copy in the same order");
		Collection<Integer> res = CollectionHelper.asCollection(orginalArray);
		TestHelperCollections.assertSameOrderAndSize(originalWrapped, res);
		assertDecoupledAndMutable(originalWrapped, res);		
	}

	public void assertDecoupledAndMutable(final Collection<Integer> original, final Collection<Integer> result){
		Integer o = 99999;
		result.add(o);
		assertFalse(original.contains(o));
		assertTrue(result.contains(o));
	}
}
