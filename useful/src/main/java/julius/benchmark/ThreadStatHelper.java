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

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;
import java.util.Map;

import julius.annotations.Experimental;
import julius.utilities.CollectionHelper;

/**
 * Debug tool to see whats going on
 */
@Experimental("obtrusive, only use in debug environment")
public class ThreadStatHelper {

	/**
	 * @return multiline string with the thread and lock info
	 */
    public static String getLockThreadInfo() {
        return getThreadStatusInfo() + getLockInfo();
    }

    /**
     * @return multiline summary of all Threads
     */
    public static String getThreadStatusInfo() {
        StringBuilder builder = new StringBuilder();
        builder.append("== threadinfo ==\n");
        Map<Thread, StackTraceElement[]> map = Thread.getAllStackTraces();
        for (Map.Entry<Thread, StackTraceElement[]> threadEntry : map.entrySet()) {
            builder.append("Thread: ");
            builder.append(threadEntry.getKey().getState());            
            builder.append(" \t: ");
            builder.append(threadEntry.getKey().getName());
            if (threadEntry.getValue() != null && threadEntry.getValue().length > 0) {
                builder.append("\t--> ");
                builder.append(threadEntry.getValue()[0]);
            }
            builder.append("\n");
        }
        return builder.toString();
    }

    /**
     * @return info about held locks
     */
    public static String getLockInfo() {
        StringBuilder builder = new StringBuilder();
        builder.append("== lockinfo ==\n");
        ThreadMXBean bean = ManagementFactory.getThreadMXBean();
        ThreadInfo[] ti = bean.getThreadInfo(bean.getAllThreadIds(), true, true);
        for (ThreadInfo info : ti) {
            builder.append(info.getThreadName());
            builder.append("\twaiting on:");
            builder.append(info.getLockName());
            builder.append("\t holding:");
            builder.append(CollectionHelper.asCollection(info.getLockedMonitors()));
            builder.append(CollectionHelper.asCollection(info.getLockedSynchronizers()));
            builder.append("\n");
        }
        return builder.toString();
    }

}