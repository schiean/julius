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

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.xml.datatype.XMLGregorianCalendar;

import julius.test.BDDTestCase;


public class TestDateHelper extends BDDTestCase {

    public void testCopy() {
        given("a valid date");
        	Date currentDate = new Date();

        when("date is copied");
        	Date dateCopy = DateHelper.copy(currentDate);

        then("the reference should not be equal but the contents should");
        	assertEquals(dateCopy, currentDate);
        	assertFalse(dateCopy == currentDate);

        successFullStory();
    }

    public void testCopyNull() {
        given("a null date");
        	Date nullDate = null;

        when("date is copied");
        	Date dateCopy = DateHelper.copy(nullDate);

        then("the copy should be null");
        	assertTrue(nullDate == dateCopy);
        	assertNull(dateCopy);

        successFullStory();
    }

    public void testDate2Gregorian() {
        	final Date d = new Date();

        given("a normal date:" + d);

        when("converted to gregorian format (yyyy-mm-dd)");
        	XMLGregorianCalendar cal = DateHelper.dateToGregorian(d);

        	DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        	String gregStr = formatter.format(d);

        then("we expect the calendar to contain the gregorian value " + gregStr);
        	assertTrue(cal.toString().startsWith(gregStr));

        and("reverse should return original date");

        	Date d2 = DateHelper.gregorianToDate(cal);
        	assertEquals(d, d2);

        successFullStory();
    }

    public void testToString(){
    	DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
    	Date d = new Date();
        assertEquals(formatter.format(d), DateHelper.getDateString(d));
    }
    
    @SuppressWarnings("deprecation")
	public void testAddDay(){
    	given("a current date but with day==5");
    		Date d = new Date();
    		d.setDate(5);
    	when("a day is added");
    	
    		Date result = DateHelper.addDays(d, 1);
    	
    	then("the result should be day = 6");
    	
    		d.setDate(6);
    		assertEquals(d, result);
    	
    	successFullStory();
    }
}
