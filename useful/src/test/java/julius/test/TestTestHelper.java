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

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

import nl.testclasses.Child;
import nl.testclasses.CustomTypeWithLangTypeList;
import nl.testclasses.Cycle;
import nl.testclasses.MapperNOK1;
import nl.testclasses.MapperNOK2;
import nl.testclasses.MapperOK;
import nl.testclasses.MultiParent;
import nl.testclasses.Parent;


public class TestTestHelper extends BDDTestCase {

	public void testAssertNotNullRecursiveCyclic(){
		Cycle c1 = new Cycle(1);
		assertTrue(failed(c1));
		c1.setCycle(c1);
		assertTrue(pass(c1));
		
		Cycle c2 =new Cycle(2);
		c2.setCycle(c1);
		assertTrue(pass(c2));
		
		Cycle c3 = new Cycle(3);
		Cycle c4 = new Cycle(4);
		Cycle c5 = new Cycle(5);
		c3.setCycle(c4);
		c4.setCycle(c5);
		assertTrue(failed(c3));
		assertTrue(failed(c4));
		assertTrue(failed(c5));
		
		c5.setCycle(c3);
		assertTrue(pass(c3));
		assertTrue(pass(c4));
		assertTrue(pass(c5));
		
		c5.setCycle(c1);
		assertTrue(pass(c1));
		assertTrue(pass(c2));
		assertTrue(pass(c3));
		assertTrue(pass(c4));
		assertTrue(pass(c5));
		
		
	}
	
	
    public void testAssertNotNullRecursive() {
        note("a partial filled object should fail");

        Child c = new Child();
        assertTrue(failed(c));

        note("a fully filled object should pass");

        c.setVal(1);
        c.setVal2(2);
        assertTrue(pass(c));

        note("a fully filled object with filled sub object should pass");

        Parent p = new Parent();
        p.setVal(1);
        p.setC(c);
        assertTrue(pass(p));
        assertTrue(pass(c));

        note("a fully filled object without filled sub object should fail");

        p.setC(null);
        assertTrue(failed(p));

        note("a fully filled object without partial filled sub object should fail");

        c.setVal(1);
        c.setVal2(null);
        p.setC(c);
        assertTrue(failed(c));
        assertTrue(failed(p));

        note("a (multi) parent class with empty list of children should fail");

        MultiParent mp = new MultiParent();
        mp.setVal(1);
        mp.setC(new HashSet<Child>());
        assertTrue(failed(mp));

        note("a (multi) parent class with list with null item should fail");

        mp.setC(null);
        assertTrue(failed(mp));

        note("a (multi) parent class with list of filled children should pass");
        mp.setC(new HashSet<Child>());
        c.setVal(1);
        c.setVal2(2);
        mp.getC().add(c);
        assertTrue(pass(mp));

        note("a (multi) parent class with list of partial filled children should fail");
        c.setVal2(null);
        assertTrue(failed(mp));

        note("a empty list of integers should fail");

        CustomTypeWithLangTypeList X = new CustomTypeWithLangTypeList();
        assertTrue(failed(X));

        note("a non empty list of integers should pass");
        X.getListInteger().add(1);
        assertTrue(pass(X));

        note("should be null safe");
        assertTrue(failed(null));

        note("should fail on empty list Integer");
        assertTrue(failed(new LinkedList<Integer>()));

        note("should fail on list Integer with null");
        List<Integer> l1 = new LinkedList<Integer>();
        l1.add(null);
        l1.add(1);
        assertTrue(failed(l1));

        note("should pass check list Integer for null");
        List<Integer> l2 = new LinkedList<Integer>();
        l2.add(1);
        assertTrue(pass(l2));
    }

    public void testAssertNotNullRecursiveAllowed() {
        Child c = new Child();
        c.setVal(1);
        c.setVal2(null);

        note("a partial filled object should fail");

        assertTrue(failed(c));
        assertTrue(failed(c, ""));

        note("a partial filled object should pass when the getter is in the exception list");

        assertTrue(failed(c));
        assertTrue(pass(c, "getVal2"));

        note("a partial filled object should fail when the getter is not exact in the exception list");

        assertTrue(failed(c));
        assertTrue(failed(c, "getVal"));

    }

    



    public void testAssertNullSafeMethods() {
        TestHelper.testAllNullSafeMethods(new MapperOK());
        try {
            TestHelper.testAllNullSafeMethods(new MapperNOK1());
            fail("exception expected");
        } catch (Exception e) {
        }
        try {
            TestHelper.testAllNullSafeMethods(new MapperNOK2());
            fail("exception expected");
        } catch (Exception e) {
        }

    }

    private boolean pass(final Object c) {
        return !failed(c);
    }

    private boolean failed(final Object i) {
        try {
            TestHelper.assertNotNullRecursive(i);
            return false;
        } catch (RuntimeException e) {
            return true;
        }

    }

    private boolean pass(final Object c, final String exception) {
        return !failed(c, exception);
    }

    private boolean failed(final Object i, final String exception) {
        try {
            TestHelper.assertNotNullRecursiveWithAllowed(i, exception);
            return false;
        } catch (RuntimeException e) {
            return true;
        }

    }

}
