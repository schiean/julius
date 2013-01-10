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

package julius.validation;

import java.util.List;

import julius.test.BDDTestCase;
import julius.utilities.CollectionHelper;
import julius.validation.internal.Asserter;
import julius.validation.internal.BaseAsserter;

 
/**
 * checks the behavior of the generic base assert class
 * 
 */
public class TestAsserter extends BDDTestCase {

	private final Asserter runAssert = new BaseAsserter(){

		@Override
		public RuntimeException createException(final String msg) {
			return new RuntimeException(msg);
		}
		
	};
	
	class A implements Validatable {

        private final boolean v;

        public A(final boolean v) {
            this.v = v;
        }

        @Override
        public void assertValid(){
            if (v) {
                throw new RuntimeException("hi");
            }
        }
    }

    public void testAssertAllNotNull() throws ValidationException {
        List<Integer> l = null;

        note("a null list should pass");

        runAssert.assertAllNotNull(l, "");

        note("an empty list should pass");

        l = CollectionHelper.createLinkedList();
        runAssert.assertAllNotNull(l, "");

        note("a list without null's should pass");

        l.add(5);
        runAssert.assertAllNotNull(l, "");

        note("a list with null's should fail");
        try {
            l.add(null);
            runAssert.assertAllNotNull(l, "");

            fail("exception expected");
        } catch (RuntimeException e) {

        }
    }

    public void testAssertAllValidNotNull() throws ValidationException {
        List<A> l = null;

        note("a null list should pass");

        runAssert.assertAllValidNotNull(l, "");

        note("an empty list should pass");

        l = CollectionHelper.createLinkedList();
        runAssert.assertAllValidNotNull(l, "");

        note("a list without null's and only a valid object should pass");

        l.add(new A(false));
        runAssert.assertAllValidNotNull(l, "");

        note("a list with null's should fail");
        try {
            l.add(null);
            runAssert.assertAllValidNotNull(l, "");

            fail("exception expected");
        } catch (RuntimeException e) {

        }

        note("a list with invalids should fail");
        try {
            l.add(new A(true));
            runAssert.assertAllValidNotNull(l, "");

            fail("exception expected");
        } catch (RuntimeException e) {

        }
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    public void testAssertNotEmpty() throws ValidationException {
        note("null list should fail");

        try {
            runAssert.assertNotEmpty((List) null, "");

            fail("exception expected");
        } catch (RuntimeException e) {
        }

        note("empty list should fail");

        try {
            runAssert.assertNotEmpty(CollectionHelper.createLinkedList(), "");

            fail("exception expected");
        } catch (RuntimeException e) {
        }

        note("non empty list should pass");

        List<Object> l = CollectionHelper.createLinkedList();
        l.add(1);
        runAssert.assertNotEmpty(l, "");
    }

    public void testAssertNotNull() {
        note("null should fail");
        try {
            runAssert.assertNotNull(null, "");

            fail("exception expected");
        } catch (RuntimeException e) {
        }

        note("non null should pass");
        runAssert.assertNotNull(5, "");
    }
    
    public void testAssertTrue(){
    	note("false should fail");
    	try {
    		runAssert.assertTrue(false, "");
        	
            fail("exception expected");
        } catch (RuntimeException e) {
        }
        
        note("true should pass");
        runAssert.assertTrue(true, "should pass");
    }

    
    public void testAssertFalse(){
    	note("true should fail");
    	try {
    		runAssert.assertFalse(true, "should fail");
        	
            fail("exception expected");
        } catch (RuntimeException e) {
        }
        
        note("false should pass");
        runAssert.assertFalse(false, "should pass");
    }

}
