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
import java.util.Map;
import java.util.Set;

import julius.test.BDDTestCase;
import julius.test.TestHelperCollections;
import julius.utilities.CollectionHelper;

@SuppressWarnings("unchecked")
public class TestCollectionsLogical extends BDDTestCase{

	private static final Set<Integer> NULL_SET = null;
	private static final List<Integer> NULL_LIST = null;
	
	List<Integer> l1 = CollectionHelper.list(1,2,3,4,5);
	List<Integer> l2 = CollectionHelper.list(6,7,8,9);
	List<Integer> l3 = CollectionHelper.list(10, 11);
	List<Integer> l1and3 = CollectionHelper.list(1,2,3,4,5,10,11); // values from 1 and 3
	List<Integer> l2and3 = CollectionHelper.list(6,7,8,9,10,11); // values from 2 and 3
	List<Integer> l1and2 = CollectionHelper.list(1,2,3,4,5,6,7,8,9); // values from 1 and 2

	
	public void testUnionListSimple(){		
		given("two lists:"+l1+" & "+l2);
		
		when("merged");
		
			List<Integer> res = CollectionHelper.union(l1,l2);
		
		then("we expect a result with the contents of both lists");
		
			TestHelperCollections.assertSameSize(res, l1,l2);
			TestHelperCollections.assertContainsAll(res, l1,l2);
			System.out.println(l1.getClass());
		successFullStory();		
	}
	
	public void testUnionListKeepOrder(){
		given("two lists:"+l1+" & "+l2);
		
		when("merged");
		
			List<Integer> res = CollectionHelper.union(l1,l2);
		
		then("we expect a result with the contents of both lists");
			TestHelperCollections.assertSameSize(res ,l1 , l2);
			TestHelperCollections.assertContainsAll(res, l1,l2);
	
		and("the order remained");
			
			TestHelperCollections.assertInSameOrder(res, l1,l2);
			
		successFullStory();

	}
	
	public void testUnionListHandleDoubleValues(){
		given("two lists:"+l1+" & "+l1and3);
		
		when("merged");
		
			List<Integer> res = CollectionHelper.union(l1,l1and3);
		
		then("we expect a result with the contents of both lists, including double values");
			TestHelperCollections.assertContainsAll(res, l1,l1and3);
			
		successFullStory();
		
	}
	
	public void testUnionSet(){
		given("two lists:"+l1+" & "+l1and3);
		
		when("merged");
		
			Set<Integer> res = CollectionHelper.unionAsSet(l1,l1and3);
		
		then("we expect a result with the contents of both lists, excluding double values");
		
			Set<Integer> exp = new HashSet<Integer>();
			exp.addAll(l1);
			exp.addAll(l1and3);
		
			TestHelperCollections.assertSameSize(res, exp);
			TestHelperCollections.assertContainsAll(res, l1,l1and3);
			
		successFullStory();
	}

	public void testNullUnion(){
		note("should support null union");
		TestHelperCollections.assertEmpty(CollectionHelper.union(NULL_LIST));
		TestHelperCollections.assertEmpty(CollectionHelper.union(NULL_LIST,NULL_LIST));
		TestHelperCollections.assertEmpty(CollectionHelper.union(NULL_LIST,new LinkedList<Integer>()));
		TestHelperCollections.assertEmpty(CollectionHelper.unionAsSet(NULL_SET));
		TestHelperCollections.assertEmpty(CollectionHelper.unionAsSet(NULL_SET,NULL_SET));
		TestHelperCollections.assertEmpty(CollectionHelper.unionAsSet(NULL_SET,new LinkedList<Integer>()));
		
		TestHelperCollections.assertEmpty(CollectionHelper.intersection(NULL_LIST));
		TestHelperCollections.assertEmpty(CollectionHelper.intersection(NULL_LIST,NULL_LIST));
		TestHelperCollections.assertEmpty(CollectionHelper.intersection(NULL_LIST,new LinkedList<Integer>()));
		TestHelperCollections.assertEmpty(CollectionHelper.intersectionAsSet(NULL_LIST));
		TestHelperCollections.assertEmpty(CollectionHelper.intersectionAsSet(NULL_LIST,NULL_LIST));
		TestHelperCollections.assertEmpty(CollectionHelper.intersectionAsSet(NULL_LIST,new LinkedList<Integer>()));
		
		TestHelperCollections.assertEmpty(CollectionHelper.relativeComplement(NULL_LIST,NULL_LIST));
		TestHelperCollections.assertEmpty(CollectionHelper.relativeComplement(NULL_LIST,new LinkedList<Integer>()));
		
		TestHelperCollections.assertEmpty(CollectionHelper.symetricDifference(NULL_LIST));
		TestHelperCollections.assertEmpty(CollectionHelper.symetricDifference(NULL_LIST,NULL_LIST));
		TestHelperCollections.assertEmpty(CollectionHelper.symetricDifference(NULL_LIST,new LinkedList<Integer>()));
		
		assertNotNull(CollectionHelper.histogram(NULL_LIST));
		assertNotNull(CollectionHelper.histogram(CollectionHelper.list(NULL_LIST)));
	}
	

	public void testIntersection(){		
		given("the collection "+l1and3);
		
		when("the intersection is called for "+l1);
		
			Collection<Integer> res = CollectionHelper.intersection(l1and3,l1);
		
		then("we expect "+l1);
		
			TestHelperCollections.assertSameSize(res, l1);
			TestHelperCollections.assertContainsAll(res, l1);
		
		when("the intersection is called for "+l2);
			
			res = CollectionHelper.intersection(l1and3,l2);
		
		then("we expect []");
		
			TestHelperCollections.assertEmpty(res);
		
			Collection<Integer> vals = CollectionHelper.list(l1.get(0),l3.get(0));			
			Collection<Integer> valsExtra = CollectionHelper.list(l1.get(0),l3.get(0), 99999);
			
		when("the intersection is called for "+valsExtra);
			
			res = CollectionHelper.intersection(l1and3,valsExtra);
		
		then("we expect "+vals);
		
			TestHelperCollections.assertSameSize(res, vals);
			TestHelperCollections.assertContainsAll(res, vals);
	
		successFullStory();
	}
	
	public void testIntersectionHandleDoubleValues(){
		Collection<Integer> l  = CollectionHelper.list(1,1,1);
		Collection<Integer> c1 = CollectionHelper.list(1,1,1,2);
		Collection<Integer> c2 = CollectionHelper.list(1,1,1,3);
		
		given("the collection "+c1);
		when("intersected with "+c2);
		
			Collection<Integer> res = CollectionHelper.intersection(c1,c2);
		
		then("we expect "+l );
		
			TestHelperCollections.assertSameSize(res, l);
			TestHelperCollections.assertContainsAll(res, l);
		
		successFullStory();
		
	}
	
	public void testIntersectionInOrder(){
		given("the collection "+l1and3);
			
			Collection<Integer> vals = CollectionHelper.list(l1.get(0),l3.get(0));			
			Collection<Integer> valsExtra = CollectionHelper.list(l1.get(0),l3.get(0), 99999);
			
		when("the intersection is called for "+valsExtra);
			
			Collection<Integer> res = CollectionHelper.intersection(l1and3,valsExtra);
		
		then("we expect "+vals);
		
			TestHelperCollections.assertSameSize(res, vals);
			TestHelperCollections.assertContainsAll(res, vals);
			TestHelperCollections.assertInSameOrder(res, vals);
	
	
		successFullStory();
		
	}

	public void testIntersectionSetHandleDoubleValues(){
		Collection<Integer> l  = CollectionHelper.list(1);
		Collection<Integer> c1 = CollectionHelper.list(1,1,1,2);
		Collection<Integer> c2 = CollectionHelper.list(1,1,1,3);
		
		given("the collection "+c1);
		when("intersected with "+c2);
		
			Collection<Integer> res = CollectionHelper.intersectionAsSet(c1,c2);
		
		then("we expect "+l );
		
			TestHelperCollections.assertSameSize(res, l);
			TestHelperCollections.assertContainsAll(res, l);
		
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
		
			Map<Integer, Integer> map = CollectionHelper.histogram(l);
		
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
		
			Collection<Integer> dif = CollectionHelper.relativeComplement( l1, l1and3);
		
		then("we expect only "+l3);
		
			TestHelperCollections.assertSameSize(dif, l3);
			TestHelperCollections.assertContainsAll(dif, l3);
		
		and("an empty collection for the otherway arround");
		
			dif = CollectionHelper.relativeComplement( l1and3, l1);
			
			TestHelperCollections.assertEmpty(dif);		
		
		successFullStory();
		
	}
	
	public void testSymetricDifference(){
		given("the collections "+l1and2+" "+l1and3);
		when("the difference is created");
		
			Collection<Integer> dif = CollectionHelper.symetricDifference(l1and2, l1and3);
		
		then("we expect only "+l2and3);
		
			TestHelperCollections.assertSameSize(dif, l2and3);
			TestHelperCollections.assertContainsAll(dif, l2and3);
		
		and("an the same for the otherway arround");
		
			dif = CollectionHelper.symetricDifference(l1and3, l1and2);		

			TestHelperCollections.assertSameSize(dif, l2and3);
			TestHelperCollections.assertContainsAll(dif, l2and3);
	
		successFullStory();
		
	}
	

	
}
