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

import java.io.PrintStream;

/**
 * Test output needs to be directed to the commandline to make them visible.
 * Since we later migth wanna change this to redirect to logging frameworks
 * we chose this class as system.out wrapper to make changes lateron easier
 */
public final class JuliusTestOut {

	private JuliusTestOut(){
		super();
	}
	
	private static final PrintStream out = System.out;

    /**
     * writes the message to system out, could later be changed to log4j if that is requested
     * @param msg
     */
    public static void print(final String msg){
    	out.println(msg);
    }
}
