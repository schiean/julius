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
import julius.validation.Validatable;
import julius.validation.ValidationException;
import julius.validation.internal.CheckedAsserter;


/**
 * tests the wrapping of runtime asserter by checked asserter
 * 
 */
public class TestCheckedAsserter  extends BDDTestCase {
	
	private final CheckedAsserter asserter = new CheckedAsserter();
	
    class A implements Validatable {

        private final boolean v;

        public A(final boolean v) {
            this.v = v;
        }

        @Override
        public void assertValid() {
            if (v) {
                throw new IllegalStateException("");
            }
        }
    }

    public void testAssertAllNotNull() throws ValidationException {
        List<Integer> l = null;

        note("a null list should pass");

        asserter.assertAllNotNull(l, "");

        note("an empty list should pass");

        l = CollectionHelper.createLinkedList();
        asserter.assertAllNotNull(l, "");

        note("a list without null's should pass");

        l.add(5);
        asserter.assertAllNotNull(l, "");

        note("a list with null's should fail");
        try {
            l.add(null);
            asserter.assertAllNotNull(l, "");

            fail("exception expected");
        } catch (ValidationException e) {

        }
    }

    public void testAssertAllValidNotNull() throws ValidationException {
        List<A> l = null;

        note("a null list should pass");

        asserter.assertAllValidNotNull(l, "");

        note("an empty list should pass");

        l = CollectionHelper.createLinkedList();
        asserter.assertAllValidNotNull(l, "");

        note("a list without null's and only a valid object should pass");

        l.add(new A(false));
        asserter.assertAllValidNotNull(l, "");

        note("a list with null's should fail");
        try {
            l.add(null);
            asserter.assertAllValidNotNull(l, "");

            fail("exception expected");
        } catch (ValidationException e) {

        }

        note("a list with invalids should fail");
        try {
            l.add(new A(true));
            asserter.assertAllValidNotNull(l, "");

            fail("exception expected");
        } catch (ValidationException e) {

        }
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    public void testAssertNotEmpty() throws ValidationException {
        note("null list should fail");

        try {
            asserter.assertNotEmpty((List) null, "");

            fail("exception expected");
        } catch (ValidationException e) {
        }

        note("empty list should fail");

        try {
            asserter.assertNotEmpty(CollectionHelper.createLinkedList(), "");

            fail("exception expected");
        } catch (ValidationException e) {
        }

        note("non empty list should pass");

        List<Object> l = CollectionHelper.createLinkedList();
        l.add(1);
        asserter.assertNotEmpty(l, "");
    }

    public void testAssertNotNull() throws ValidationException {
        note("null should fail");
        try {
            asserter.assertNotNull(null, "");

            fail("exception expected");
        } catch (ValidationException e) {
        }

        note("non null should pass");
        asserter.assertNotNull(5, "");
    }
    

    public void testAssertTrue() throws ValidationException {
    	note("false should fail");
    	try {
    		asserter.assertTrue(false, "");
        	
            fail("exception expected");
        } catch (ValidationException e) {
        }
        
        note("true should pass");
        asserter.assertTrue(true, "should pass");
    }

}
