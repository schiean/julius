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

package julius.identifiable;

import java.util.List;

import julius.lambda.Functional;
import julius.test.BDDTestCase;
import julius.utilities.CollectionHelper;

public class TestIdentifiable extends BDDTestCase {
	
	Functional functional = new Functional();
	
	public static class X implements Identifiable<Integer>{
		int id;
		X(final Integer x){
			id =x;
		}
		@Override
		public Integer getId(){
			return id;
		}
		@Override
		public String toString(){
			return "X id:"+id;
		}

		public static IdExtracter<X, Integer> idExtracter = new IdExtracter<X, Integer>();
	}
	
	public void testIdExtractor(){
			X x = new X(10);
		given(x.toString());
		when("id is extracted");
		then("expect "+x.getId());
			assertEquals(x.getId(), X.idExtracter.apply(x));
		
		successFullStory();
			
			List<Integer> ids = CollectionHelper.createList(1, 10, 12);
			List<X> xs = CollectionHelper.createLinkedList();
			for(Integer id:ids){
				xs.add(new X(id));
			}
			
		given(""+xs);
		when("idExctract is applied");
		then("expect:"+ids);
			assertEquals(ids, functional.map(xs, X.idExtracter));

		successFullStory();
		
	}

}
