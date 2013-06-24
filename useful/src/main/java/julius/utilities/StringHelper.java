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

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import julius.reflection.BeanTraverser;
import julius.reflection.ToStringTask;
import julius.validation.Assertions;

/**
 * provides methods to parse text (to easily parse valid.invalid html for example) or flatten a
 * List<Object> to String
 * 
 * @author AJG van Schie
 * @author Yosuf Haydary
 */
public final class StringHelper {

	private StringHelper() {
		super();
	}

	// some string constants which are used a lot (some are also on StringUtils)
	/** empty = ''; */
	public static final String EMPTY = "";
	/** single whitespace ' ' */
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
	 * 
	 * @param list
	 *            null/empty safe
	 * @param separator
	 * @return
	 */
	public static <T> String flatten(final List<T> list, final String separator) {
		if (CollectionHelper.isEmpty(list)) {
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
	public static String parse(final String wholeString, final String startExpression,
			final String endExpression, final int nrOfOccurence) {
		int startExpPosition = getPositionOfNthOccurence(wholeString, startExpression,
				nrOfOccurence);
		Assertions.argument.assertTrue(startExpPosition >= 0, "start expression ("
				+ startExpression + ") could not be found N (" + nrOfOccurence
				+ ") times in the string");
		int substringStart = startExpPosition + startExpression.length();
		int substringEnd = wholeString.indexOf(endExpression, substringStart);// start
																				// searching
																				// for
																				// end
																				// after
																				// start
																				// expression
		return wholeString.substring(substringStart, substringEnd);
	}

	/**
	 * finds the Nth occurrence of the expression in the wholeString and returns its position or -1
	 * 
	 * @param wholeString
	 * @param expression
	 * @param nrOfOccurence
	 * @return position or -1
	 */
	public static int getPositionOfNthOccurence(final String wholeString, final String expression,
			final int nrOfOccurence) {
		Assertions.argument.assertTrue(nrOfOccurence > 0, "nr of occurences must be > 0");
		Assertions.argument.assertNotNull(wholeString, "the string to be searched");
		Assertions.argument.assertNotNull(expression, "the expression to be searched for");

		int pos = 0;
		for (int i = 0; i < nrOfOccurence && pos >= 0; i++) {
			pos = wholeString.indexOf(expression, pos + 1);
		}
		return pos;
	}

	/**
	 * recursively traverse getters and collections to create a string for root supports multiple
	 * object occurrence(only print once)
	 * 
	 * @param root
	 * @return
	 */
	public static String toStringRecursive(final Object root) {
		ToStringTask task = new ToStringTask();
		BeanTraverser.traverseRecursive(task, root);
		return task.toString();
	}

	/**
	 * 
	 * recursively traverse getters and collections to create a string for root supports multiple
	 * object occurrence(only print once)
	 * 
	 * @param root
	 * @param maxDepth
	 *            prevent to large trees
	 * @param skipPaths
	 * @param skipMethods
	 * @param skipClasses
	 * @return
	 */
	public static String toStringRecursive(final Object root, final int maxDepth,
			final Set<String> skipPaths, final Set<String> skipMethods,
			final Set<String> skipClasses) {
		ToStringTask task = new ToStringTask(maxDepth, skipPaths, skipMethods, skipClasses);
		BeanTraverser.traverseRecursive(task, root);
		return task.toString();
	}

	/**
	 * A useful method to format the lines to line up neatly the columns which are separated by a
	 * tab for example. Example:
	 * 
	 * Given : ["4444\t333\t22" , "1\t22\t4444" ] with "\t" as splitter
	 * 
	 * Return: ["4444\t333\t22  " , "1   \t22 \t4444" ]
	 * 
	 * Which can be nicely printed
	 * 
	 * @param lines
	 *            (not null)
	 * @param splitter
	 *            (not null) regex
	 * @return
	 */
	public static List<String> formatLines(final Collection<String> lines, final String splitter) {
		Assertions.argument.assertNotNull(lines, "lines");
		Assertions.argument.assertNotNull(splitter, "splitter");

		List<Integer> maxIndexLengths = determineMaxIndexLengths(lines, splitter);
		List<String> allLinesFormatted = CollectionHelper.list();

		for (String line : lines) {
			StringBuilder lineFormatted = new StringBuilder();

			String[] lineSplitted = line.split(splitter);

			for (int index = 0; index < lineSplitted.length; index++) {
				String indexVal = lineSplitted[index];

				lineFormatted.append(indexVal).append(
						createWhiteSpace(maxIndexLengths.get(index) - indexVal.length()));

				if (index < lineSplitted.length - 1) {
					lineFormatted.append(splitter);
				}

			}
			allLinesFormatted.add(lineFormatted.toString());
		}

		return allLinesFormatted;
	}

	/**
	 * For the list of given lines this method determines what the maximum length of characters per
	 * split of each index can be.
	 * 
	 * Example: line 1: "abc,a,da" line 2: "a,bc" splitChar: ","
	 * 
	 * Result: [3,2,2] because the zeroth index of first line has the max chars; the first index of
	 * second line has max characters; and only the first line has second index
	 * 
	 * @param lines
	 *            (not null)
	 * @param splitter
	 *            (not null) Regex: The character(s) which is used to split the lines by
	 * @return (not null), but maybe empty
	 */
	protected static List<Integer> determineMaxIndexLengths(final Collection<String> lines,
			final String splitter) {

		List<Integer> maxCellLengthsPerColumn = new ArrayList<Integer>();

		for (String line : lines) {

			String[] lineSplitted = line.split(splitter);

			for (int index = 0; index < lineSplitted.length; index++) {

				Integer existingCellLenght = null;
				// check whether this index exists in the list yet
				if (index < maxCellLengthsPerColumn.size()) {
					existingCellLenght = maxCellLengthsPerColumn.get(index);
				}

				int currentIndexLength = lineSplitted[index].length();

				if (existingCellLenght == null) {
					maxCellLengthsPerColumn.add(index, currentIndexLength);
				} else if (currentIndexLength > existingCellLenght) {
					maxCellLengthsPerColumn.set(index, currentIndexLength);
				}
			}
		}

		return maxCellLengthsPerColumn;
	}

	/**
	 * Creates a string existing out of white spaces for the given count.
	 * 
	 * @param count
	 * @return (not null), but maybe empty
	 */
	protected static String createWhiteSpace(final int count) {
		String result = EMPTY;

		for (int i = 0; i < count; i++) {
			result += SPACE;
		}
		return result;
	}
}
