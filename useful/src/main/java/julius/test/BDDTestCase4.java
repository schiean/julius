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


/**
 * JUNIT 4
 * 
 * Implements the BDD method pattern for given / when / then but does not extend Junit 3 TestCase.
 * 
 * @see BDDTestcase
 */
public class BDDTestCase4 {

	/**
	 * @see julius.test.BDDTestCase#given(String)
	 */
    public void given(final String precondition) {
        System.out.println("--------------------------------------");
        System.out.println("Given " + precondition);
    }


	/**
	 * @see julius.test.BDDTestCase#when(String)
	 */
    public void when(final String action) {
        System.out.println("When " + action);
    }


	/**
	 * @see julius.test.BDDTestCase#and(String)
	 */
    public void and(final String action) {
        System.out.println("and " + action);
    }


	/**
	 * @see julius.test.BDDTestCase#then(String)
	 */
    public void then(final String postcondition) {
        System.out.println("Then " + postcondition);
    }


	/**
	 * @see julius.test.BDDTestCase#successFullStory()
	 */
    public void successFullStory() {
        System.out.println("(implemented)");
        System.out.println("--------------------------------------");
    }


	/**
	 * @see julius.test.BDDTestCase#note(String)
	 */
    public void note(final String note) {
        System.out.println("(Note: " + note + " )");
    }

}
