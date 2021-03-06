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

package julius.lambda;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import julius.test.BDDTestCase;
import julius.test.TestHelperCollections;
import julius.utilities.CollectionHelper;
@SuppressWarnings("unchecked")
public class TestFunctional extends BDDTestCase{

	Functional functional = new Functional();
	
	List<Integer> numbers = CollectionHelper.list(1,2,3,4,5,6,7,8,9,10);
	List<Integer> even = CollectionHelper.list(2,4,6,8,10);
	List<Integer> odd = CollectionHelper.list(1,3,5,7,9);

	
	static class Even implements Filter<Integer>{
		@Override
		public boolean applicable(final Integer val) {
			return val % 2 == 0;
		}		
	}
	
	static class Odd implements Filter<Integer>{
		@Override
		public boolean applicable(final Integer val) {
			return val % 2 == 1;
		}		
	}

	static class Doubler implements Operation<Integer, Integer>{
		@Override
		public Integer apply(final Integer val) {
			return val * 2;
		}		
	}
	
	static class ToString implements FoldOperation<Integer, String>{
		@Override
		public String apply(final Integer val, final String current){
			if(current.length() >= 3){
				return current;
			}
			return current + val.toString();
		}
	}

	
	
	public void testExists(){
		given("numbers:"+even);
		when("exist even is called");
		then("we expect true");
		assertTrue(functional.exists(even, new Even()));
		
		given("numbers:"+numbers);
		when("exist even is called");
		then("we expect true");
		assertTrue(functional.exists(numbers, new Even()));
		

		given("numbers:"+odd);
		when("exist even is called");
		then("we expect false");
		assertFalse(functional.exists(odd, new Even()));
		
	}
	
	public void testForAll(){
		given("numbers:"+even);
		when("forAll even is called");
		then("we expect true");
		assertTrue(functional.forAll(even, new Even()));
		
		given("numbers:"+numbers);
		when("forAll even is called");
		then("we expect false");
		assertFalse(functional.forAll(numbers, new Even()));
		

		given("numbers:"+odd);
		when("forAll even is called");
		then("we expect false");
		assertFalse(functional.forAll(odd, new Even()));
		
	}
	
	
	public void testFilter(){
		given("numbers:"+numbers);
		when("filtered even");
		then("we expect "+even);
	
		Collection<Integer> res = functional.filter(numbers, new Even());
		
		TestHelperCollections.assertSameSize(res, even);
		TestHelperCollections.assertContainsAll(res, even);
		TestHelperCollections.assertInSameOrder(res, even);
	}
	
	public void testMap(){
		given("numbers:"+numbers);
		when("mapped double");
			List<Integer> numDoub = CollectionHelper.list();
			for(Integer v: numbers){
				numDoub.add(v*2);
			}
			then("we expect "+numDoub);
	
		Collection<Integer> res = functional.map(numbers, new Doubler());

		TestHelperCollections.assertSameSize(res, numDoub);
		TestHelperCollections.assertContainsAll(res, numDoub);
		TestHelperCollections.assertInSameOrder(res, numDoub);
		
	}

	public void testTakeWhile(){

		List<Integer> oddThenEven = new LinkedList<Integer>(odd);
		oddThenEven.addAll(even);
		
		given("numbers "+oddThenEven);
		when("takeWhile even");
		then("expect empty");
		
			TestHelperCollections.assertEmpty(functional.takeWhile(oddThenEven, new Even()));
		
		given("numbers "+even);
		when("takeWhile even");
		then("expect even");
		
			Collection<Integer> r1 =  functional.takeWhile(even, new Even());

			TestHelperCollections.assertSameSize(r1, even);
			TestHelperCollections.assertContainsAll(r1, even);
			TestHelperCollections.assertInSameOrder(r1, even);
		
		List<Integer> evenThenOdd = new LinkedList<Integer>(even);
		evenThenOdd.addAll(odd);
		
		given("numbers "+evenThenOdd);
		when("takeWhile even");
		then("expect even");
		
			Collection<Integer> r2 =  functional.takeWhile(evenThenOdd, new Even());

		
			TestHelperCollections.assertSameSize(r2, even);
			TestHelperCollections.assertContainsAll(r2, even);
			TestHelperCollections.assertInSameOrder(r2, even);
		
	}
	
	public void testFoldLeft(){
		given("numbers: "+odd);
		when("fold/reduced with first3Num");
		
			String exp = odd.get(0).toString()+odd.get(1).toString()+odd.get(2).toString();
			String act = functional.foldLeft(odd, new ToString(), "");
			
		then("we expect"+exp);
		
			assertEquals(exp, act);
	}

	public void testFoldRight(){
		given("numbers: "+odd);
		when("fold/reduced RIGHT with first3Num");
		
			int l = odd.size();
			String exp = odd.get(l-1).toString()+odd.get(l-2).toString()+odd.get(l-3).toString();
			String act = functional.foldRight(odd, new ToString(), "");
			
		then("we expect"+exp);
		
			assertEquals(exp, act);
	}
	
	
	
}
