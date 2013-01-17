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

import java.util.HashSet;
import java.util.Set;

/**
 * Recursive printing of an object, does indenting while recursively walking along getters and collections
 * objects are only printed once, per task.
 */
public class ToStringTask implements TraverseTask{

	private final StringBuilder sb = new StringBuilder();

	private final int maxDepth;
	private final Set<String> skipPaths;
	private final Set<String> skipMethods;
	private final Set<String> skipClasses;
	
	public ToStringTask(){
		this(1024, new HashSet<String>(),new HashSet<String>(),new HashSet<String>());		
	}
	
	/**
	 * 
	 * @param maxDepth optional to prevent extremely large trees
	 * @param skipPaths
	 * @param skipMethods
	 * @param skipClasses
	 */
	public ToStringTask(final int maxDepth, final Set<String> skipPaths, final Set<String> skipMethods, final Set<String> skipClasses){
		this.maxDepth = maxDepth;
		this.skipPaths = skipPaths;
		this.skipMethods = skipMethods;
		this.skipClasses = skipClasses;
	}
	
	@Override
	public void handle(final Object o, final String methodName, final int depth) {
		for(int i=0;i<depth;i++){
			sb.append("\t");
		}
		sb.append(methodName);
		sb.append(": ");
		sb.append(o);
		sb.append("\n");
	}

	@Override
	public String toString(){
		return sb.toString();
	}
	
	@Override
	public boolean shouldTraverse(final String objectName, final String methodName, final Object returnValue, final int depth){
		boolean typeOk = true;
		if(returnValue != null){
			for(String s: skipClasses){
				typeOk = typeOk && !returnValue.getClass().getName().contains(s);
			}
		}
		boolean pathOk = true;
		for(String s: skipPaths){
			pathOk = pathOk && !(objectName+"."+methodName).contains(s);
		}
		boolean methodOk = true;
		for(String s: skipMethods){
			methodOk = methodOk && !methodName.contains(s);
		}
		return depth <= maxDepth && methodOk && pathOk && typeOk;
	}

	@Override
	public void handleAlreadyProcessed(final Object o, final String methodName, final int depth) {
		handle(o, methodName, depth);
		
	}

	@Override
	public void handleException(final String errorName, final String methodName, final int depth) {
		handle("threw "+errorName, methodName, depth);
	}

}
