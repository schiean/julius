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

package julius.utilities.collection;

import java.util.LinkedList;

import julius.utilities.NumberHelper.NumberWrapper;
import julius.utilities.collection.Range.Step;
import julius.validation.Assertions;

/**
 * Range allows for DSL for loops like ruby/scala/javafx
 * 
 * for(Step s: new Range(1,5){...}
 * for(Step s: new Range(5,1){...}
 * for(Step s: new Range(0,10, new StepSize(2)){...}
 * 
 */
public class Range extends LinkedList<Step>{
	
	private static final long serialVersionUID = 6792107464831006531L;
	
	private static final int DEFAULT_STEP = 1;
	
	private final int from;
	private final int to;
	private final int stepSize;	
	
	/**
	 * create a default range (stepsize +/- 1)
	 * @param from
	 * @param to
	 */
	public Range(final int from, final int to){
		this(from,to, new StepSize(DEFAULT_STEP));
	}
	
	/**
	 * @param from
	 * @param to
	 * @param stepSize !=0 && != null
	 */
	public Range(final int from, final int to, final StepSize stepSize) {
		super();
		this.from = from;
		this.to = to;
		Assertions.argument.assertTrue(stepSize != null , "stepSize should be unequal to NULL");		
		this.stepSize = getStepSize(from, to, stepSize);
		Assertions.argument.assertTrue(this.stepSize !=0 , "stepSize should be unequal to 0");
		Assertions.argument.assertTrue(notEndless() , "stepSize should bring 'from' to 'to'");
		fill();
		
	}

	private  int getStepSize(final int from, final int to, final StepSize stepSize) {
		if(from > to && stepSize.value() > 0){
			return stepSize.value() * -1;
		}else{
			return stepSize.value();
		}
	}

	private  void fill() {
		if(stepSize > 0){
			for(int i = from; i <= to ; i = i + stepSize){
				add( new Step(i)) ;
			}
		}else{
			for(int i = from; i >= to ; i = i + stepSize){
				add( new Step(i)) ;
			}
		}
	}

	private  boolean notEndless() {
		if(stepSize > 0){
			return from < to;
		}else{
			return from > to; // reverse				
		}
	}
	
	/**
	 * TypeDef/Alias for NumberWrapper / Integer
	 */
	public static class StepSize extends NumberWrapper{
		/**
		 * @param value stepsize
		 */
		public StepSize(final int value) {
			super(value);
		}
	};
	
	
	/**
	 * TypeDef/Alias for NumberWrapper / Integer
	 */
	public static class Step  extends NumberWrapper{
		/**
		 * @param value step-value
		 */
		public Step(final int value) {
			super(value);
		}
	};	
}
