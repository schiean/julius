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

public class PeriodicSignalSampleHolderTest extends BDDTestCase {
    long num = 0;
    int count = 0;

    public void testNormal() {
        PeriodicSignalSampler sampler = new PeriodicSignalSampler();

        sampler.registerSignalSamplerSec("id", new SignalSampleCallback() {

            @Override
            public void handleSample(final String id, final long signalCount, final long durationInMs) {
                System.out.println("print:" + id + " count:" + signalCount + " " + durationInMs);
                num = signalCount;
                count++;
            }
        }, 1);

        for (int i = 0; i < 2100; i++) {
            sampler.count("id");
            Sleep.sleep.milliseconds(1);
        }

        note("We call every msec for 2 seconds, so we expect 2 rotations (because resolution was set to 1sec)");
        assertTrue(num > 50);
        assertTrue(count >= 2);
    }

}
