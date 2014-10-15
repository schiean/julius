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

import java.util.Map;

import julius.utilities.CollectionHelper;
import julius.utilities.DateHelper;

/**
 * This class could be used to calculate statistics per time unit. Like Frames Per Second, or messages per second.
 * 
 * It is not threadsafe. Although one could create a threadlocal instance of this class. It also makes sense to create a static
 * facade or use Spring to inject it throughout the code base.
 * 
 * It is not extremely efficient it should not be used to measure 'extremely' high frequencies. On a typical machine, empty loops
 * reach about 10.000.000 signals per second. (which is considerable overhead for some scenario's).
 * 
 * The current implementation does not use threads to call the callback but only calls the callback for timewindows with at least
 * one count(), and also it will call the callback when it realizes that the count() is not within the former count().
 * 
 * 
 */
public class PeriodicSignalSampler {

    private final Map<String, PeriodicSignalSampleHolder> samplers = CollectionHelper.map();

    /**
     * register the callback
     * 
     * @param id
     * @param callback
     * @param size
     *            Size (in seconds) of the window for which we sum/count the signals
     */
    public void registerSignalSamplerSec(final String id, final SignalSampleCallback callback, final int size) {
        samplers.put(id, new PeriodicSignalSampleHolder(id, callback, DateHelper.secondsToMillis(size)));
    }

    /**
     * register a count for a pre-registered id-callback (registerSignalSamplerSec). If it is not registered then it will skip the
     * call.
     * 
     * @param id
     */
    public void count(final String id) {
        PeriodicSignalSampleHolder holder = samplers.get(id);
        if (holder != null) {
            holder.signal();
        }
    }
}
