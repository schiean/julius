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

package julius.reflection;

/**
 * Task for recursive travering object trees
 */
public  interface TraverseTask{
	/**
	 * called when o is already processed once
	 * 
	 * @param o object to handle
	 * @param methodName method that returns o
	 * @param depth level of recursion
	 */
	public void handleAlreadyProcessed(Object o, String methodName, int depth);

	/**
	 * called for each result of a getter
	 * 
	 * @param o object to handle
	 * @param methodName method that returns o
	 * @param depth level of recursion
	 */
	public void handle(Object o, String methodName, int depth);
	
	/**
	 * useful to exclude types or certain methods or depths
	 * @param o
	 * @param methodName
	 * @param depth
	 * @return true if the o itself should be inspected
	 */
	public boolean shouldTraverse(Object o, String methodName, int depth);
}

