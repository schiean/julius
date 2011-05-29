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

package julius.benchmark;

import julius.test.BDDTestCase;

public class TestMemoryHelper extends BDDTestCase{

	public void testGetMB(){
		MemoryHelper mh = new MemoryHelper();
		long some1 = mh.getUsedMB();

		note("we expect a memory usage between 0.1 and 10 MB");
		assertTrue(some1 > 1);
		assertTrue(some1 < 10);
		
		note("calling report should not decrease it(no GC)");
		System.out.println(mh.getReport(false));
		long some2 = mh.getUsedMB();

		assertTrue(some2 >= some1);
		
		note("calling report with clean up should decrease it");
		System.out.println(mh.getReport(true));
		long some3 = mh.getUsedMB();
		
		assertTrue(some3 <= some2);
		
	}
	
}
