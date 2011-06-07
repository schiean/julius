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

import junit.framework.TestCase;

/**
 * JUNIT 3
 * 
 * This class provides some methods to help create readable testcase specifications.
 * As a result it helps thinking about what to test in a TDD approach and it
 * provides documentation to your testcases.
 * 
 * The idea behind this is that normally testcode is useful until a test fails and the 
 * original author 'has left the building', when that happens it can be daunting to find out
 * what it exactly is that the test is covering and whether or the code needs to be fixed or
 * the test needs to be adjusted. 
 * By documenting in a larger functional description what you are testing it is easier to know
 * the context and purpose of the test.
 * 
 * example from wikipedia: http://en.wikipedia.org/wiki/Behavior_Driven_Development
 * 
 * Scenario 1: Refunded items should be returned to stock
 *
 *   GIVEN a customer previously bought a black sweater from me
 *   and I currently have three black sweaters left in stock
 *   WHEN he returns the sweater for a refund
 *   THEN I should have four black sweaters in stock
 *
 * with this class it would be implemented as
 * 
 * given("");
 * and("");
 * when("");
 * then("");
 * succesfulStory();
 * 
 * for some cases this is a bit overkill, and we should use note();
 * 
 * note("this test asserts that the method is null safe");
 * or 
 * note("this test asserts that it support iterating over empty collections")
 */
public abstract class BDDTestCase extends TestCase {
	
	/**
	 * example given("we have a mutable empty collection object")
	 * @param precondition
	 */
    public void given(final String precondition) {
        System.out.println("--------------------------------------");
        System.out.println("Given " + precondition);
    }

    /**
     * example when("we call add('some value') on the collection ")
     * @param action
     */
    public void when(final String action) {
        System.out.println("When " + action);
    }

    /**
     * @param action
     */
    public void and(final String action) {
        System.out.println("and " + action);
    }

    /**
     * example then("we expect the collection to contain this item")
     * and("have a length of 1")
     * @param postcondition
     */
    public void then(final String postcondition) {
        System.out.println("Then " + postcondition);
    }

    /**
     * prints a nice separator and notice of test success (useful if you read the test output)
     */
    public void successFullStory() {
        System.out.println("(implemented)");
        System.out.println("--------------------------------------");
    }

    /**
     * useful to document small testcases
     * @param note
     */
    public void note(final String note) {
        System.out.println("(Note: " + note + " )");
    }

}
