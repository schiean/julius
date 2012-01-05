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

import java.util.Locale;
import java.util.Map;

/**
 * Convenience methods for Boolean
 */
public class BooleanHelper {

	/** static instance */
	public static  final BooleanHelper booleanHelper = new BooleanHelper();
	
	private final  Map<String, Boolean> translateTable = CollectionHelper.createHashMap();
	
	protected BooleanHelper(){
		translateTable.put("1", Boolean.TRUE);
		translateTable.put("true", Boolean.TRUE);
		translateTable.put("y", Boolean.TRUE);
		translateTable.put("t", Boolean.TRUE);
		translateTable.put("j", Boolean.TRUE);
		translateTable.put("ja", Boolean.TRUE);
		translateTable.put("yes", Boolean.TRUE);
		translateTable.put("waar", Boolean.TRUE);
		
		translateTable.put("0", Boolean.FALSE);
		translateTable.put("false", Boolean.FALSE);
		translateTable.put("n", Boolean.FALSE);
		translateTable.put("f", Boolean.FALSE);
		translateTable.put("no", Boolean.FALSE);
		translateTable.put("nee", Boolean.FALSE);
		translateTable.put("onwaar", Boolean.FALSE);
	}
	
	/**
	 * uses pre-filled table to get Boolean value for String or null in absence
	 * @param val
	 * @return
	 */
	public  Boolean map(final String val){
		if(val == null){
			return null;
		}
		return translateTable.get(val.trim().toLowerCase(Locale.ENGLISH));
	}
	
	/**
	 * @param val
	 * @return null => '' true=>"Y", false => "N"
	 */
	public String toYN(final Boolean val){
		if(val==null){
			return "";
		}
		if(val){
			return "Y";
		}else{
			return "N";
		}
	}

	/**
	 * @param val
	 * @return null => '' true=>"J", false => "N"
	 */
	public String toJN(final Boolean val){
		if(val==null){
			return "";
		}
		if(val){
			return "J";
		}else{
			return "N";
		}		
	}
}
