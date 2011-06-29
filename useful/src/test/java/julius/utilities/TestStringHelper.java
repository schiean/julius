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

import julius.test.BDDTestCase;

public class TestStringHelper extends BDDTestCase{

	public void testParse(){
		String whole="<html><body><p>something</p><p>important</p></body></html>";

		given("the text:"+whole);
			String s="<p>";
			String e="</p";
			int nr = 1;
		when("queried for text between the "+nr+"th occurence of "+s+" and the next "+e);
			
			String result = StringHelper.parse(whole, s, e, nr);		
			String expected = "something"; 
			
		then("we expect the result:"+expected);
			
			assertEquals(expected, result);
			
			nr = 2;
		when("queried for text between the "+nr+"th occurence of "+s+" and the next "+e);
			
			result = StringHelper.parse(whole, s, e, nr);		
			expected = "important"; 
			
		then("we expect the result:"+expected);
			assertEquals(expected, result);
			
			s="body>";
			e="</p";
			nr = 1;
		when("queried for text between the "+nr+"th occurence of "+s+" and the next "+e);
			result = StringHelper.parse(whole, s, e, nr);		
			expected = "<p>something"; 
			
		then("we expect the result:"+expected);
			assertEquals(expected, result);

			nr = 10000;
		when("queried for N("+nr+") larger then actual occurences");
		
		then("we should get an exception");
			try{
				result = StringHelper.parse(whole, s, e, nr);						
				fail("exception expected not:"+result);
			}catch(IllegalArgumentException easd){}
			
		
		successFullStory();
	}
	
	public void testGetNposition(){
		String w = "<a>asd</a> <b>ad</b>";
		note("non existing string should return -1 position");
		assertEquals(-1, StringHelper.getPositionOfNthOccurence(w, "asdasdasd",1));
		note("existing string but not enough times, should return -1 position");
		assertEquals(-1, StringHelper.getPositionOfNthOccurence(w, "a",1000));
		note("normal first occurence");
		assertEquals(1, StringHelper.getPositionOfNthOccurence(w, "a",1));
		note("normal n occurence");
		assertEquals(8, StringHelper.getPositionOfNthOccurence(w, "a",3));	
	}
	
	public void testFlatten(){
		String separator = ";";
		List<Integer> lNull = null;
		note("flatten "+lNull+" should be empty ''");
		assertEquals(StringHelper.EMPTY, StringHelper.flatten(lNull, separator));
		
		List<Integer> l = CollectionHelper.list();
		note("flatten "+l+" should be empty ''");
		assertEquals(StringHelper.EMPTY, StringHelper.flatten(l, separator));
		
		List<Integer> l1 = CollectionHelper.list(1);
		note("flatten "+l1+" item should be '1'");
		assertEquals("1", StringHelper.flatten(l1, separator));
		
		List<Integer> l13 = CollectionHelper.list(1,3);
		note("flatten "+l13+" item should be '1;3'");
		assertEquals("1;3", StringHelper.flatten(l13, separator));
		
		
		List<Integer> l13456 = CollectionHelper.list(1,3,4,5,6);
		note("flatten "+l13456+" item should be '1;3'");
		assertEquals("1;3;4;5;6", StringHelper.flatten(l13456, separator));
		
	}
	
}
