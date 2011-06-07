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

public class TestFileHelper extends BDDTestCase {

	private static final String loc = "src/test/java/julius/utilities/";
	private static final String fileExist = loc+"test.csv";
	private static final String fileNotExist = "asdasdasdasdasd.csv";


	public void testGetInputStream() {
		given("an existing file");
		when("inputstream is requested");
		then("we expect non null result");
			assertNotNull(FileHelper.getInputStream(fileExist));
		successFullStory();

		given("an unexisting file");
		when("inputstream is requested");
		then("we expect an exception");
			try{
				FileHelper.getInputStream(fileNotExist);
				fail("expecting error");
			}catch(IllegalArgumentException e){}
		successFullStory();
	}

	public void testReadCsvFile() {
		given("an csv file with 6 rows 5 columns");
		when("read as csv ");
			List<String[]> r = FileHelper.readCsvFile(fileExist);
		then("we expect a string list with an array of size three");
			assertEquals(6, r.size());
			assertEquals(5, r.get(0).length);
			assertEquals(5, r.get(1).length);
		successFullStory();


	}

	public void testReadFileAsString() {
		given("an csv file with 6 rows 5 columns");
		when("read as string");
			String r = FileHelper.readFileAsString(fileExist);
		then("we expect a non empty string");
			assertFalse(r.trim().isEmpty());
		successFullStory();
	}

	public void testReadFile() {
		given("an csv file with 6 rows 5 columns");
		when("read as string list");
			List<String> r = FileHelper.readFile(fileExist);
		then("we expect a list with 6 non empty strings");
			assertEquals(6, r.size());
			assertFalse(r.get(0).trim().isEmpty());
			assertFalse(r.get(1).trim().isEmpty());
			assertFalse(r.get(2).trim().isEmpty());
			assertFalse(r.get(3).trim().isEmpty());
			assertFalse(r.get(4).trim().isEmpty());
			assertFalse(r.get(5).trim().isEmpty());
		successFullStory();

		given("a non existing file");
		when("read as string list");
		then("we expect an illegal arg exception");
			try{
				FileHelper.readFile(fileNotExist);
				fail("exception expected");
			}catch(IllegalArgumentException e){}
		successFullStory();
	}

	public void testWriteFile() {
			String msg = "hi world";
	
		given("a text:"+msg);
		
			String filename = loc+ "hi.txt";
		
		when("written to file"+filename);

			FileHelper.writeFile(msg, filename);

		then("when read again, we expect the same contents");

			assertEquals(msg+"\n" , FileHelper.readFileAsString(filename) );

		successFullStory();
	}

}
