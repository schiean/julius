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

import java.util.List;

import julius.test.BDDTestCase;
import julius.test.TestHelperCollections;
import julius.utilities.CollectionHelper;
import julius.utilities.collection.Range.Step;
import julius.utilities.collection.Range.StepSize;

public class TestRange extends BDDTestCase{

	private final List<Step> oneToFive = CollectionHelper.list(new Step(1),new Step(2),new Step(3),new Step(4),new Step(5));
	private final List<Step> fiveToOne = CollectionHelper.list(new Step(5),new Step(4),new Step(3),new Step(2),new Step(1));
	private final List<Step> zeroToTenStep2 = CollectionHelper.list(new Step(0),new Step(2),new Step(4),new Step(6),new Step(8),new Step(10));

	public void testNoZeroStepsize(){
		note("stepsize should be != 0");
		try{
			new Range(1,2,new StepSize(0));
		}catch(IllegalArgumentException e){
			
		}
	}
	
	public void testNoEndless(){
		try{
			new Range(1,2, new StepSize(-1));
		}catch(IllegalArgumentException e){
			
		}
	}
	
	public void testNormalReverse(){
		int min = 5;
		int to  = 1;
		
		given("params "+min +" "+to );
		when("range constructed");
		then("it should equal to "+fiveToOne);
		
		TestHelperCollections.assertSameOrderAndSize(fiveToOne, new Range(min,to));
		TestHelperCollections.assertSameOrderAndSize(fiveToOne, new Range(min,to,new StepSize(-1)));
	}
	
	public void testNormal(){
		int min = 1;
		int to  = 5;
		
		given("params "+min +" "+to );
		when("range constructed");
		then("it should equal to "+oneToFive);
		
		TestHelperCollections.assertSameOrderAndSize(oneToFive, new Range(min,to));
		TestHelperCollections.assertSameOrderAndSize(oneToFive, new Range(min,to,new StepSize(1)));
	}
	
	public void testDoubleStepSize(){
		int min = 0;
		int to  = 10;
		int stepSize = 2;
		
		given("params "+min +" "+to );
		when("range constructed with step "+stepSize);
		then("it should equal to "+zeroToTenStep2);
		
		Range res = new Range(min,to,new StepSize(stepSize));
		System.out.println(res);
		TestHelperCollections.assertSameOrderAndSize(zeroToTenStep2, res);
		
	}
	
	public void testDoubleStepSizePlus1(){
		int min = 0;
		int to  = 10;
		int stepSize = 2;
		
		given("params "+min +" "+to );
		when("range constructed with step "+stepSize);
		then("it should equal to "+zeroToTenStep2);
		and("no half step for 11");
		TestHelperCollections.assertSameOrderAndSize(zeroToTenStep2, new Range(min,to,new StepSize(stepSize)));
		
	}
}
