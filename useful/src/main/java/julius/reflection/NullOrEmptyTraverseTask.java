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

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import julius.utilities.CollectionHelper;

/**
 * Keeps list of null or empty collection returning methods
 */
public class NullOrEmptyTraverseTask implements TraverseTask{

	protected final List<String> nulls = CollectionHelper.list();
	
	@Override
	public void handle(final Object o, final String methodName, final int depth) {
		if(o==null || isEmptyCollection(o)){
			nulls.add(methodName);
		}
	}

	public List<String> getNullReturningMethods(){
		return new LinkedList<String>(nulls);
	}
	
	private boolean isEmptyCollection(final Object o) {
		if(o instanceof Collection){
			Collection oc = (Collection)o;
			return oc.isEmpty();
		}
		return false;
	}

	@Override
	public boolean shouldTraverse(final Object o, final String methodName, final int depth) {
		return true;
	}

	@Override
	public void handleAlreadyProcessed(final Object o, final String methodName, final int depth) {
		// nothing		
	}
}