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

package julius.test;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;

import julius.utilities.CollectionHelper;
import junit.framework.AssertionFailedError;

public class TestTestCollectionHelper extends BDDTestCase {

	Collection<Integer> oneTwo = CollectionHelper.list(1,2);
	Collection<Integer> three = CollectionHelper.list(3);
	Collection<Integer> oneTwoThree = CollectionHelper.list(1,2,3);
	Collection<Integer> oneTwoTwoThree = CollectionHelper.list(1,2,2,3);
	Collection<Integer> oneTwoThreeThree = CollectionHelper.list(1,2,3,3);
	Collection<Integer> oneThreeTwoThree = CollectionHelper.list(1,2,3,3);
	Collection<Integer> threeTwoOne = CollectionHelper.list(3,2,1);
	Collection<Integer> oneTwoThreeFour = CollectionHelper.list(1,2,3,4);

	
	
	public void testNotEmpty(){
		TestHelperCollections.assertNotEmpty(oneTwoThree);
		boolean raisedException = true;
		try{
			TestHelperCollections.assertNotEmpty(new LinkedList<Integer>());
			raisedException = false;
		}catch(AssertionFailedError e){
		}
		assertTrue(raisedException);
		
		raisedException = true;
		try{
			TestHelperCollections.assertNotEmpty(null);
			raisedException = false;
		}catch(AssertionFailedError e){
		}
		assertTrue(raisedException);
	}
	
	public void testEmpty(){
		TestHelperCollections.assertEmpty(new LinkedList<Integer>());
		boolean raisedException = true;
		try{
			TestHelperCollections.assertEmpty(oneTwoThree);
			raisedException = false;
		}catch(AssertionFailedError e){
		}
		assertTrue(raisedException);
		
		raisedException = true;
		try{
			TestHelperCollections.assertEmpty(null);
			raisedException = false;
		}catch(AssertionFailedError e){
		}
		assertTrue(raisedException);
	}
	
	@SuppressWarnings("unchecked")
	public void testSize(){
		TestHelperCollections.assertSameSize(oneTwoThree, oneTwoThree);
		TestHelperCollections.assertSameSize(oneTwoThree, threeTwoOne);
		TestHelperCollections.assertSameSize(oneTwoThree, oneTwo, three);
		
		
		boolean raisedException = true;
		try{
			TestHelperCollections.assertSameSize(oneTwoThree, oneTwoThreeFour);
			raisedException = false;
		}catch(AssertionFailedError e){
		}
		assertTrue(raisedException);
	}
	
	
	@SuppressWarnings("unchecked")
	public void testInOrder(){
		TestHelperCollections.assertInSameOrder(oneTwoThree, Collections.EMPTY_LIST);
		TestHelperCollections.assertInSameOrder(oneTwoThree, new LinkedList<Integer>(oneTwoThree));
		TestHelperCollections.assertInSameOrder(oneTwoThree, oneTwo, three);
		TestHelperCollections.assertInSameOrder(oneTwoThree, oneTwo);
		
		boolean raisedException = true;
		try{
			TestHelperCollections.assertInSameOrder(oneTwoThree, oneTwoThreeFour);
			raisedException = false;
		}catch(AssertionFailedError e){
		}
		assertTrue(raisedException);
		
		raisedException = true;
		try{
			TestHelperCollections.assertInSameOrder(oneTwoThree, threeTwoOne);
			raisedException = false;
		}catch(AssertionFailedError e){
		}
		assertTrue(raisedException);
	}
	
	@SuppressWarnings("unchecked")
	public void testContainAll(){
		TestHelperCollections.assertContainsAll(oneTwoThree, Collections.EMPTY_SET);
		TestHelperCollections.assertContainsAll(oneTwoThree, new LinkedList<Integer>(oneTwoThree));
		TestHelperCollections.assertContainsAll(oneTwoThree, oneTwo, three);
		TestHelperCollections.assertContainsAll(oneTwoThree, three, oneTwo);		
		TestHelperCollections.assertContainsAll(oneTwoThree, oneTwo);

		boolean raisedException = true;
		try{
			TestHelperCollections.assertContainsAll(oneTwoThree, oneTwoThreeFour);
			raisedException = false;
		}catch(AssertionFailedError e){
		}
		assertTrue(raisedException);
	}


	public void testAssertSameElementsAndSize(){
		TestHelperCollections.assertSameElementsAndSize(oneTwoThree, threeTwoOne);
		
		boolean raisedException = true;
		try{
			TestHelperCollections.assertSameElementsAndSize(oneTwoThree, oneTwoThreeFour);
			raisedException = false;
		}catch(AssertionFailedError e){
		}
		assertTrue(raisedException);
		
		raisedException = true;
		try{
			TestHelperCollections.assertSameElementsAndSize(oneTwoTwoThree, oneTwoThreeThree);
			raisedException = false;
		}catch(AssertionFailedError e){
		}
		assertTrue(raisedException);
		
		raisedException = true;
		try{
			TestHelperCollections.assertSameElementsAndSize(oneTwoThreeThree, oneTwoTwoThree);
			raisedException = false;
		}catch(AssertionFailedError e){
		}
		assertTrue(raisedException);
		
			TestHelperCollections.assertSameElementsAndSize(oneTwoThreeThree, oneThreeTwoThree);
		
	}
}
