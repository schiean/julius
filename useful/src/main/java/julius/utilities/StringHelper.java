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

package julius.utilities;

import java.util.List;
import java.util.Set;

import julius.reflection.BeanTraverser;
import julius.reflection.ToStringTask;
import julius.validation.Assertions;

/**
 * provides methods to parse text (to easily parse valid.invalid html for example)
 * or flatten a List<Object> to String
 */
public final class StringHelper {

	private StringHelper(){
		super();
	}
	
	// some string constants which are used a lot (some are also on StringUtils)
	/** empty = ''; */
	public static final String EMPTY = "";
	/** single whitespace ' '*/
	public static final String SPACE = " ";
	/** 'J' */
	public static final String J = "J";
	/** 'Y' */
	public static final String Y = "Y";
	/** 'N' */
	public static final String N = "N";
	
	/**
	 * puts all items of the list separated by separator in a string
	 * 
	 * alternatively use apache commons
	 * @param list null/empty safe
	 * @param separator
	 * @return
	 */
	public static <T> String flatten(final List<T> list, final String separator){
		if(CollectionHelper.isEmpty(list)){
			return EMPTY;
		}
		Object last = CollectionHelper.last(list);
		StringBuilder sb = new StringBuilder();
		for (Object o : CollectionHelper.allExceptLast(list)) {
				sb.append(o.toString());
				sb.append(separator);
		}
		sb.append(last.toString());
		return sb.toString();
	}
	
	/**
	 * takes the substring between the Nth occurrence of startExpression and the next endExpression
	 * 
	 * example
	 * 
	 * <html><body><p>useless</p><p>important</p></body></html>
	 * 
	 * start='<p>' and end ='</p' nr=2 should return 'important'
	 * 
	 * alternatively use regexp's
	 * 
	 * @param wholeString
	 * @param startExpression
	 * @param endExpression
	 * @param nrOfOccurence
	 * @return
	 */
	public static String parse(final String wholeString, final String startExpression, final String endExpression, final int nrOfOccurence) {
		int startExpPosition = getPositionOfNthOccurence(wholeString,startExpression,nrOfOccurence);
		Assertions.argument.assertTrue(startExpPosition >=0, "start expression ("+startExpression+") could not be found N ("+nrOfOccurence+") times in the string");
		int substringStart = startExpPosition+startExpression.length();  
		int substringEnd = wholeString.indexOf(endExpression, substringStart);// start searching for end after start expression
		return wholeString.substring(substringStart, substringEnd);
	}

	/**
	 * finds the Nth occurrence of the expression in the wholeString and returns its position or -1
	 * @param wholeString
	 * @param expression
	 * @param nrOfOccurence
	 * @return position or -1
	 */
	public static int getPositionOfNthOccurence(final String wholeString, final String expression, final int nrOfOccurence){
		Assertions.argument.assertTrue(nrOfOccurence >0, "nr of occurences must be > 0");
		Assertions.argument.assertNotNull(wholeString, "the string to be searched");
		Assertions.argument.assertNotNull(expression, "the expression to be searched for");
		
		int pos = 0;
		for(int i=0;i<nrOfOccurence && pos>=0 ;i++){
			pos = wholeString.indexOf(expression,pos+1);
		}
		return pos;
	}
	
	/**
	 * recursively traverse getters and collections to create a string for root
	 * supports multiple object occurrence(only print once)
	 * @param root
	 * @return
	 */
	public static String toStringRecursive(final Object root){
		ToStringTask task = new ToStringTask();
		BeanTraverser.traverseRecursive(task, root);
		return task.toString();
	}
	
	/**
	 * 
	 * recursively traverse getters and collections to create a string for root
	 * supports multiple object occurrence(only print once)
	 * @param root
	 * @param maxDepth prevent to large trees
	 * @param skipPaths
	 * @param skipMethods
	 * @param skipClasses
	 * @return
	 */
	public static String toStringRecursive(final Object root, final int maxDepth, final Set<String> skipPaths, final Set<String> skipMethods, final Set<String> skipClasses){
		ToStringTask task = new ToStringTask(maxDepth, skipPaths, skipMethods, skipClasses);
		BeanTraverser.traverseRecursive(task, root);
		return task.toString();
	}
	
	
}
