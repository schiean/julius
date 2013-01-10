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

package julius.statistics;

import julius.test.BDDTestCase;
import julius.utilities.Sleep;

public class PeriodicSignalSampleHolderTest extends BDDTestCase{
	long num=0;
	int count=0;
	
	public void testNormal(){
		PeriodicSignalSampler sampler = new PeriodicSignalSampler();
		
		sampler.registerSignalSamplerSec("id", new SignalSampleCallback(){

			@Override
			public void handleSample(final String id, final long signalCount,
					final long durationInMs) {
				System.out.println("print:"+id+" count:"+signalCount+" "+durationInMs);
				num = signalCount;
				count++;
			}}, 1);
		int y=0;
		for(int i=0;i<20000000;i++){
			sampler.count("id");
		}
		
		Sleep.sleep.milliseconds(500);
		sampler.count("id");
		
		note("on average we process 10.000.000 counts per second with print");
		note("so we expect at least 2 counts");
		assertTrue(num > 7000000);
		assertTrue(count>=2);
	}

}
