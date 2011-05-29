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

import julius.benchmark.StopWatch;
import julius.test.BDDTestCase;
import julius.utilities.Sleep;

public class TestStopWatch extends BDDTestCase{

	public void testNormalRun(){
		given("a stopwatch instance");
		
			StopWatch sw = new StopWatch();
			Sleep.sleep.milliseconds(60);
			
		when("start is called after 60 msec ");
		
			sw.start();
		
		and("stop is called after 120 msec");
		
			Sleep.sleep.milliseconds(120);
			long res = sw.stop();
			
		then("we expect the elapsed time to be equal to ~120");
		
			assertAboutEqual(120, res);
			assertAboutEqual(120, sw.elapsedInMillis());
		
		and("multiple calls to elapsed should give the same result");
		
			Sleep.sleep.milliseconds(120);
			assertAboutEqual(120, sw.elapsedInMillis());

			Sleep.sleep.milliseconds(120);
			assertAboutEqual(120, sw.elapsedInMillis());
			
		successFullStory();
	}

	public void testResumingRun(){
		given("a paused stopwatch with 120 msec");
		
			StopWatch sw = new StopWatch();
			sw.start();
			Sleep.sleep.milliseconds(120);
			sw.stop();
			Sleep.sleep.milliseconds(120);
			
		
		when("resumed and stopped again after 400 msec");
		
			sw.start();
			Sleep.sleep.milliseconds(400);
			long res = sw.stop();
	
		
		then("the result should be 520 msec");
		
		assertAboutEqual(520, res);
			assertAboutEqual(520, sw.elapsedInMillis());
		
		successFullStory();
	}
	
	public void testLongRun(){
		note("after running 2500 msec, we expect 3 sec elapse");
		
		StopWatch sw = new StopWatch();
		sw.start();
		Sleep.sleep.milliseconds(500).and().seconds(2);
		sw.stop();
	
		assertAboutEqual(2500, sw.elapsedInMillis());
		assertEquals(2, sw.elapsedInSeconds());
	}
	
	public void reset(){
		given("a paused stopwatch with 120 msec");
		
			StopWatch sw = new StopWatch();
			sw.start();
			Sleep.sleep.milliseconds(120);
			sw.stop();
			Sleep.sleep.milliseconds(120);
		
	
		when("reset and start/stopped again after 400 msec");
	
			sw.start();
			Sleep.sleep.milliseconds(400);
			long res = sw.stop();

	
		then("the result should be 400 msec");
	
		assertAboutEqual(400, res);
		assertAboutEqual(400, sw.elapsedInMillis());
	
		successFullStory();
	
	}
	
	
	public void testErrors(){
		try{
			new StopWatch().elapsedInMillis();
			fail("only allowed after start/stop");
		}catch(IllegalStateException w){};
		
		try{
			new StopWatch().elapsedInSeconds();
			fail("only allowed after start/stop");
		}catch(IllegalStateException w){};
		
		try{
			new StopWatch().stop();
			fail("only allowed after start");
		}catch(IllegalStateException w){};
		
		
	}

	
	public void assertAboutEqual(final long expected, final long actual){
		long max = (long)(expected * 1.025);
		long min = (long)(expected * 0.975);
		assertTrue("expected "+expected+" upto "+min+", got "+actual, actual > min);
		assertTrue("expected "+expected+" upto "+max+", got "+actual, actual < max);
	}
}
