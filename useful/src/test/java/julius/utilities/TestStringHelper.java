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

package julius.utilities;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

import julius.test.BDDTestCase;
import nl.testclasses.Child;
import nl.testclasses.Cycle;
import nl.testclasses.ExceptionBean;
import nl.testclasses.GrandParent;
import nl.testclasses.MultiParent;
import nl.testclasses.MultiParentList;
import nl.testclasses.Parent;

public class TestStringHelper extends BDDTestCase{

	public void testParse(){
		String whole="<html><body><p>something</p><p>important</p></body></html>";

		given("the text:"+whole);
			String s="<p>";
			String e="</p";
			int nr = 1;
		when("queried for text between the "+nr+"th occurence of "+s+" and the next "+e);
			
			String result = StringHelper.parse(whole, s, e, nr);		
			String expected = "something"; 
			
		then("we expect the result:"+expected);
			
			assertEquals(expected, result);
			
			nr = 2;
		when("queried for text between the "+nr+"th occurence of "+s+" and the next "+e);
			
			result = StringHelper.parse(whole, s, e, nr);		
			expected = "important"; 
			
		then("we expect the result:"+expected);
			assertEquals(expected, result);
			
			s="body>";
			e="</p";
			nr = 1;
		when("queried for text between the "+nr+"th occurence of "+s+" and the next "+e);
			result = StringHelper.parse(whole, s, e, nr);		
			expected = "<p>something"; 
			
		then("we expect the result:"+expected);
			assertEquals(expected, result);

			nr = 10000;
		when("queried for N("+nr+") larger then actual occurences");
		
		then("we should get an exception");
			try{
				result = StringHelper.parse(whole, s, e, nr);						
				fail("exception expected not:"+result);
			}catch(IllegalArgumentException easd){}
			
		
		successFullStory();
	}
	
	public void testGetNposition(){
		String w = "<a>asd</a> <b>ad</b>";
		note("non existing string should return -1 position");
		assertEquals(-1, StringHelper.getPositionOfNthOccurence(w, "asdasdasd",1));
		note("existing string but not enough times, should return -1 position");
		assertEquals(-1, StringHelper.getPositionOfNthOccurence(w, "a",1000));
		note("normal first occurence");
		assertEquals(1, StringHelper.getPositionOfNthOccurence(w, "a",1));
		note("normal n occurence");
		assertEquals(8, StringHelper.getPositionOfNthOccurence(w, "a",3));	
	}
	
	public void testFlatten(){
		String separator = ";";
		List<Integer> lNull = null;
		note("flatten "+lNull+" should be empty ''");
		assertEquals(StringHelper.EMPTY, StringHelper.flatten(lNull, separator));
		
		List<Integer> l = CollectionHelper.list();
		note("flatten "+l+" should be empty ''");
		assertEquals(StringHelper.EMPTY, StringHelper.flatten(l, separator));
		
		List<Integer> l1 = CollectionHelper.list(1);
		note("flatten "+l1+" item should be '1'");
		assertEquals("1", StringHelper.flatten(l1, separator));
		
		List<Integer> l13 = CollectionHelper.list(1,3);
		note("flatten "+l13+" item should be '1;3'");
		assertEquals("1;3", StringHelper.flatten(l13, separator));
		
		
		List<Integer> l13456 = CollectionHelper.list(1,3,4,5,6);
		note("flatten "+l13456+" item should be '1;3'");
		assertEquals("1;3;4;5;6", StringHelper.flatten(l13456, separator));
		
	}
	
	
	public void testPrintCyclic(){
		Cycle c1 = new Cycle(1);
		c1.setCycle(c1);
		Cycle c2 =new Cycle(2);
		c2.setCycle(c1);

//		System.out.println(StringHelper.toStringRecursive(c1));
//		System.out.println(StringHelper.toStringRecursive(c2));
		
		
		Cycle c3 = new Cycle(3);
		Cycle c4 = new Cycle(4);
		Cycle c5 = new Cycle(5);
		c3.setCycle(c4);
		c4.setCycle(c5);
		c5.setCycle(c2);
		c1.setCycle(c3);
//		System.out.println(StringHelper.toStringRecursive(c1));
//		System.out.println(StringHelper.toStringRecursive(c2));
//		System.out.println(StringHelper.toStringRecursive(c3));
//		System.out.println(StringHelper.toStringRecursive(c4));
		System.out.println(StringHelper.toStringRecursive(c5));
		String res =StringHelper.toStringRecursive(c5);
		assertTrue(containsOnce(res,"getI: 1"));
		assertTrue(containsOnce(res,"getI: 2"));
		assertTrue(containsOnce(res,"getI: 3"));
		assertTrue(containsOnce(res,"getI: 4"));
		assertTrue(containsOnce(res,"getI: 5"));
	}
	
	public boolean containsOnce(final String res, final String part){
		return res.indexOf(part) >-1 && res.indexOf(part, res.indexOf(part)+1)==-1;
	}
	
	
    public void testPrintRecursive() {
        note("should support partial filled object");

        Child c = new Child();
        c.setVal(1);
        c.setVal2(2);
        String res = StringHelper.toStringRecursive(c);

        assertTrue(containsOnce(res,"Child"));
        assertTrue(containsOnce(res,"getVal: 1"));
        assertTrue(containsOnce(res,"getVal2: 2"));

        c.setVal(null);
        c.setVal2(null);
        
        res = StringHelper.toStringRecursive(c);

        assertTrue(containsOnce(res,"Child"));
        assertTrue(containsOnce(res,"getVal: null"));
        
        note("a fully filled object with filled sub object should print");

        Parent p = new Parent();
        p.setVal(1);
        p.setC(c);
        
        res = StringHelper.toStringRecursive(p);
        System.out.println(res);
        assertTrue(containsOnce(res,"Parent"));
        assertTrue(containsOnce(res,"getC: "));
        assertTrue(containsOnce(res,"getVal: null"));
        
        res = StringHelper.toStringRecursive(p,1, new HashSet<String>(), new HashSet<String>(), new HashSet<String>());
        System.out.println(res);

        assertTrue(containsOnce(res,"Parent"));
        assertTrue(containsOnce(res,"getC: "));
        assertFalse(containsOnce(res,"getVal: null"));

       note("a (multi) parent class with empty list of children should print");

        MultiParent mp = new MultiParent();
        mp.setVal(1);
        mp.setC(new HashSet<Child>());
        
        res = StringHelper.toStringRecursive(mp);
        System.out.println(res);
        assertTrue(containsOnce(res,"MultiParent"));
        assertTrue(containsOnce(res,"getC: []"));
        assertTrue(containsOnce(res,"getVal: 1"));
        
        note("a (multi) parent class with list of filled children should print");
        mp.setC(new HashSet<Child>());
        c.setVal(1);
        c.setVal2(2);
        mp.getC().add(c);

        res = StringHelper.toStringRecursive(mp);
        System.out.println(res);
        assertTrue(containsOnce(res,"MultiParent"));
        assertTrue(containsOnce(res,"getC: [nl."));

        note("a (multi) parent class with list of filled children and null should print");
        
        mp.getC().add(null);
        Child c2 = new Child();
        c2.setVal(3);
        mp.getC().add(c2);
        
        res = StringHelper.toStringRecursive(mp);
        System.out.println(res);
        assertTrue(containsOnce(res,"MultiParent"));
        assertTrue(containsOnce(res,"getC: [n"));
        assertTrue(containsOnce(res,"getVal2: 2"));

        note("a (multi) parent class with list of filled children [DOUBLE] and null should print");
        MultiParentList mpl = new MultiParentList();
        mpl.setVal(1123);
        mpl.setC(new LinkedList<Child>());
        mpl.getC().add(c);
        mpl.getC().add(c2);
        mpl.getC().add(null);
        mpl.getC().add(c2);
        
        res = StringHelper.toStringRecursive(mpl);
        System.out.println(res);
        assertTrue(containsOnce(res,"MultiParentList"));
        assertTrue(containsOnce(res,"skip"));
        
        note("support surpress exceptions");
        ExceptionBean b = new ExceptionBean(1, "a");
        res = StringHelper.toStringRecursive(b);
        System.out.println(res);
        assertTrue(containsOnce(res,"ExceptionBean"));
        assertTrue(containsOnce(res,"FakeBeanMethod: threw"));
        
        note("support skip types");
        Parent parent = new Parent();
        parent.setVal(10);
        parent.setC(new Child(8,9));
        
        res = StringHelper.toStringRecursive(parent, 1024, new HashSet<String>(), new HashSet<String>(), CollectionHelper.set("Child"));
        System.out.println(res);
        assertTrue(containsOnce(res,"Parent"));
        assertTrue(containsOnce(res,"getVal: 10"));
        assertFalse(containsOnce(res,"getVal: 8"));
        
        note("support skip methods");
        
        res = StringHelper.toStringRecursive(parent, 1024, new HashSet<String>(), CollectionHelper.set("getC"), new HashSet<String>());
        System.out.println(res);
        assertTrue(containsOnce(res,"Parent"));
        assertTrue(containsOnce(res,"getVal: 10"));
        assertFalse(containsOnce(res,"getVal: 8"));
        
        note("support skip methods on certain types");
        GrandParent gp = new GrandParent();
        gp.setC(parent);
        
        res = StringHelper.toStringRecursive(gp, 1024, CollectionHelper.set("nl.testclasses.Parent.getC"), new HashSet<String>(), new HashSet<String>());
        System.out.println(res);
        assertTrue(containsOnce(res,"nl.testclasses.GrandParent"));
        assertTrue(containsOnce(res,"nl.testclasses.Parent")); // tostring and members
        assertTrue(containsOnce(res,"getVal: 10"));
        assertTrue(containsOnce(res,"nl.testclasses.Child")); // only toString not members
        assertFalse(containsOnce(res,"getVal: 8"));
        
    }

}
