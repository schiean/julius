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

import static julius.utilities.Sleep.sleep;
import julius.test.BDDTestCase;

public class TestSleep extends BDDTestCase{

	public void testMillis(){
		long d = System.currentTimeMillis();
		sleep.milliseconds(700);
		long d2 = System.currentTimeMillis();
		assertTrue(d2-d >= 700);
	}
	
	public void testSeconds(){
		long d = System.currentTimeMillis();
		sleep.seconds(3);
		long d2 = System.currentTimeMillis();
		assertTrue(d2-d >= 3000);
	}
	
	public void testAnd(){
		long d = System.currentTimeMillis();
		sleep.seconds(3).and().milliseconds(500);
		long d2 = System.currentTimeMillis();
		assertTrue(d2-d >= 3500);
	}
}
