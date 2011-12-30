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
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import julius.test.BDDTestCase;
import julius.utilities.CollectionHelper;

@SuppressWarnings("unchecked")
public class TestCollectionsLogical extends BDDTestCase{

	private static final Set<Integer> NULL_SET = null;
	private static final List<Integer> NULL_LIST = null;
	
	CollectionsLogical logical = new CollectionsLogical();
	List<Integer> l1 = CollectionHelper.list(1,2,3,4,5);
	List<Integer> l2 = CollectionHelper.list(6,7,8,9);
	List<Integer> l3 = CollectionHelper.list(10, 11);
	List<Integer> l1and3 = CollectionHelper.list(1,2,3,4,5,10,11); // values from 1 and 3
	List<Integer> l2and3 = CollectionHelper.list(6,7,8,9,10,11); // values from 2 and 3
	List<Integer> l1and2 = CollectionHelper.list(1,2,3,4,5,6,7,8,9); // values from 1 and 2

	
	public void testUnionListSimple(){		
		given("two lists:"+l1+" & "+l2);
		
		when("merged");
		
			List<Integer> res = logical.union(l1,l2);
		
		then("we expect a result with the contents of both lists");
		
			assertSameSize(res, l1,l2);
			assertContainsAll(res, l1,l2);
		
		successFullStory();		
	}
	
	public void testUnionListKeepOrder(){
		given("two lists:"+l1+" & "+l2);
		
		when("merged");
		
			List<Integer> res = logical.union(l1,l2);
		
		then("we expect a result with the contents of both lists");
			assertSameSize(res ,l1 , l2);
			assertContainsAll(res, l1,l2);
	
		and("the order remained");
			
			assertInSameOrder(res, l1,l2);
			
		successFullStory();

	}
	
	public void testUnionListHandleDoubleValues(){
		given("two lists:"+l1+" & "+l1and3);
		
		when("merged");
		
			List<Integer> res = logical.union(l1,l1and3);
		
		then("we expect a result with the contents of both lists, including double values");
			assertContainsAll(res, l1,l1and3);
			
		successFullStory();
		
	}
	
	public void testUnionSet(){
		given("two lists:"+l1+" & "+l1and3);
		
		when("merged");
		
			Set<Integer> res = logical.unionAsSet(l1,l1and3);
		
		then("we expect a result with the contents of both lists, excluding double values");
		
			Set<Integer> exp = new HashSet<Integer>();
			exp.addAll(l1);
			exp.addAll(l1and3);
		
			assertSameSize(res, exp);
			assertContainsAll(res, l1,l1and3);
			
		successFullStory();
	}

	public void testNullUnion(){
		note("should support null union");
		assertEmpty(logical.union(NULL_LIST));
		assertEmpty(logical.union(NULL_LIST,NULL_LIST));
		assertEmpty(logical.union(NULL_LIST,new LinkedList<Integer>()));
		assertEmpty(logical.unionAsSet(NULL_SET));
		assertEmpty(logical.unionAsSet(NULL_SET,NULL_SET));
		assertEmpty(logical.unionAsSet(NULL_SET,new LinkedList<Integer>()));
		
		assertEmpty(logical.intersection(NULL_LIST));
		assertEmpty(logical.intersection(NULL_LIST,NULL_LIST));
		assertEmpty(logical.intersection(NULL_LIST,new LinkedList<Integer>()));
		assertEmpty(logical.intersectionAsSet(NULL_LIST));
		assertEmpty(logical.intersectionAsSet(NULL_LIST,NULL_LIST));
		assertEmpty(logical.intersectionAsSet(NULL_LIST,new LinkedList<Integer>()));
		
		assertEmpty(logical.relativeComplement(NULL_LIST,NULL_LIST));
		assertEmpty(logical.relativeComplement(NULL_LIST,new LinkedList<Integer>()));
		
		assertEmpty(logical.symetricDifference(NULL_LIST));
		assertEmpty(logical.symetricDifference(NULL_LIST,NULL_LIST));
		assertEmpty(logical.symetricDifference(NULL_LIST,new LinkedList<Integer>()));
		
		assertNotNull(logical.cardinality(NULL_LIST));
		assertNotNull(logical.cardinality(CollectionHelper.list(NULL_LIST)));
	}
	

	public void testIntersection(){		
		given("the collection "+l1and3);
		
		when("the intersection is called for "+l1);
		
			Collection<Integer> res = logical.intersection(l1and3,l1);
		
		then("we expect "+l1);
		
			assertSameSize(res, l1);
			assertContainsAll(res, l1);
		
		when("the intersection is called for "+l2);
			
			res = logical.intersection(l1and3,l2);
		
		then("we expect []");
		
			assertEmpty(res);
		
			Collection<Integer> vals = CollectionHelper.list(l1.get(0),l3.get(0));			
			Collection<Integer> valsExtra = CollectionHelper.list(l1.get(0),l3.get(0), 99999);
			
		when("the intersection is called for "+valsExtra);
			
			res = logical.intersection(l1and3,valsExtra);
		
		then("we expect "+vals);
		
			assertSameSize(res, vals);
			assertContainsAll(res, vals);
	
		successFullStory();
	}
	
	public void testIntersectionHandleDoubleValues(){
		Collection<Integer> l  = CollectionHelper.list(1,1,1);
		Collection<Integer> c1 = CollectionHelper.list(1,1,1,2);
		Collection<Integer> c2 = CollectionHelper.list(1,1,1,3);
		
		given("the collection "+c1);
		when("intersected with "+c2);
		
			Collection<Integer> res = logical.intersection(c1,c2);
		
		then("we expect "+l );
		
			assertSameSize(res, l);
			assertContainsAll(res, l);
		
		successFullStory();
		
	}
	
	public void testIntersectionInOrder(){
		given("the collection "+l1and3);
			
			Collection<Integer> vals = CollectionHelper.list(l1.get(0),l3.get(0));			
			Collection<Integer> valsExtra = CollectionHelper.list(l1.get(0),l3.get(0), 99999);
			
		when("the intersection is called for "+valsExtra);
			
			Collection<Integer> res = logical.intersection(l1and3,valsExtra);
		
		then("we expect "+vals);
		
			assertSameSize(res, vals);
			assertContainsAll(res, vals);
			assertInSameOrder(res, vals);
	
	
		successFullStory();
		
	}

	public void testIntersectionSetHandleDoubleValues(){
		Collection<Integer> l  = CollectionHelper.list(1);
		Collection<Integer> c1 = CollectionHelper.list(1,1,1,2);
		Collection<Integer> c2 = CollectionHelper.list(1,1,1,3);
		
		given("the collection "+c1);
		when("intersected with "+c2);
		
			Collection<Integer> res = logical.intersectionAsSet(c1,c2);
		
		then("we expect "+l );
		
			assertSameSize(res, l);
			assertContainsAll(res, l);
		
		successFullStory();
		
	}
	
	
	public void testCardinality(){
		List<Integer> l = CollectionHelper.list();
		int ones = 4;
		int threes = 2;
		int nulls = 2;
		for(int i=0;i<ones;i++){
			l.add(1);
		}
		for(int i=0;i<threes;i++){
			l.add(3);
		}
		for(int i=0;i<nulls;i++){
			l.add(null);
		}
		
		given("the collection "+l);
		when("cardinality is retrieved");
		
			Map<Integer, Integer> map = logical.cardinality(l);
		
		then("we expect 1*" +ones +" 3*"+threes+" null*"+nulls);
		
			assertEquals((Integer)ones, map.get(1));
			assertEquals((Integer)threes, map.get(3));
			assertEquals((Integer)nulls, map.get(null));
			
			assertEquals(null, map.get(4));
			assertEquals((Integer)0, ((Histogram<Integer>)map).getCount(4));
			
		successFullStory();
	}

	public void testRelativeComplement(){
		given("the collections "+l1+" "+l1and3);
		when("the difference is created");
		
			Collection<Integer> dif = logical.relativeComplement( l1, l1and3);
		
		then("we expect only "+l3);
		
			assertSameSize(dif, l3);
			assertContainsAll(dif, l3);
		
		and("an empty collection for the otherway arround");
		
			dif = logical.relativeComplement( l1and3, l1);
			
			assertEmpty(dif);		
		
		successFullStory();
		
	}
	
	public void testSymetricDifference(){
		given("the collections "+l1and2+" "+l1and3);
		when("the difference is created");
		
			Collection<Integer> dif = logical.symetricDifference(l1and2, l1and3);
		
		then("we expect only "+l2and3);
		
			assertSameSize(dif, l2and3);
			assertContainsAll(dif, l2and3);
		
		and("an the same for the otherway arround");
		
			dif = logical.symetricDifference(l1and3, l1and2);		

			assertSameSize(dif, l2and3);
			assertContainsAll(dif, l2and3);
	
		successFullStory();
		
	}
	
	/**
	 * asserts that all elements of all parts are contained in all (possibly more)
	 * @param <T>
	 * @param all
	 * @param parts
	 */
	private <T> void assertContainsAll(final Collection<T> all, final Collection<T>... parts){
		for(Collection<T> part:parts){
			for(T val: part){
				assertTrue(all.contains(val));
			}
		}		
	}
	
	/**
	 * asserts that all (starts with) the values from parts in the same order as provided
	 * @param <T>
	 * @param all
	 * @param parts
	 */
	private <T> void assertInSameOrder(final Collection<T> all, final Collection<T>... parts){
		Iterator<T> iterator = all.iterator();
		for(Collection<T> part:parts){
			for(T val: part){
				T allVal = iterator.next();
				assertEquals( val,allVal);
			}
		}		
	}
	

	private <T> void assertSameSize(final Collection<T> res,final Collection<T>... parts) {
		int size = 0;
		for(Collection<T> part:parts){
			size += part.size();
		}
		assertEquals(size, res.size());
	}
	

	private <T> void assertEmpty(final Collection<T> union) {
		assertNotNull(union);
		assertTrue("should be empty, not "+union,union.isEmpty());
	}
	
}
