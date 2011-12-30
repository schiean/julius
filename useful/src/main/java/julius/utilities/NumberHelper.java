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

import java.text.DecimalFormat;
import java.text.NumberFormat;

import julius.validation.Assertions;

/**
 * Utility functions around numbers
 */
public class NumberHelper {
	private static final int _100_PERC = 100;
	private static final String PLACEHOLDER = "TEMP";
	
	/**
	 * formats the provided number by grouping with ,
	 * and if the number was not long or integer it will also keep the first x decimal numbers upto the first 0 (if it is a round number one 0 is written)
	 * 
	 * @param num null safe
	 * @return formatted number or 'null'
	 */
	public String toString(final Number num){
		if(num==null){
			return "null";
		}
		if(num instanceof Integer || num instanceof Long){
			NumberFormat format = new DecimalFormat(",###");
			return format.format(num);	
		}else{
			NumberFormat format = new DecimalFormat(",###.0############################");
			return format.format(num);			
		}
	}
	
	/**
	 * @see #toString(Number)
	 * but with , and . used they other way, like in the dutch language
	 * @param num
	 * @return
	 */
	public String toDutchString(final Number num){
		return toString(num).replace(".", PLACEHOLDER).replace(",", ".").replace(PLACEHOLDER, ",");
	}
	
	/**
	 * true IF value2 BETWEEN value1 + x % AND value1 - x %
	 * @param value1
	 * @param value2
	 * @param percentageTo1
	 * @return
	 */
	public boolean almostEqual(final long value1, final long value2, final int percentageTo1){
		Assertions.argument.assertTrue(percentageTo1 > 0, "only values larger then 0 are allowed");
		if(value1==value2){
			return true;
		}
		long upper = addXPercent(value1, percentageTo1);
		long lower = addXPercent(value1,-percentageTo1);
		return (value2 >= lower && value2 <= upper);
	}
	
	/**
	 * @param val
	 * @param xPercentage (can be + or -)
	 * @return value + xPercentage
	 */
	public long addXPercent(final long val, final int xPercentage){
		return val * (_100_PERC + xPercentage) / _100_PERC;
	}
	
}
