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

    private static final String GREGFORMAT = "yyyy-MM-dd";
  
    public void testMinutesToSeconds(){
    	int min = 5;
    	int expSec = min * 60;
    	
    	given(min+"m");
    	when("transformed to seconds");
    	then("we expect "+expSec);
    	
    	assertEquals(expSec, DateHelper.minutesToSeconds(min));
    	
    	successFullStory();
    }
    public void testSecondsToMinutes(){
    	int sec = 360;
    	int expMinutes = sec / 60;
    	
    	given(sec+"s");
    	when("transformed to minutes");
    	then("we expect "+expMinutes);
    	
    	assertEquals(expMinutes, DateHelper.secondsToMinutes(sec));
    	
    	int secHalf = 390;
    	int expMinHalf = (secHalf + 30) / 60; // we want rounding no cut of
    	
    	given(secHalf+"s");
    	when("transformed to minutes");
    	then("we expect "+expMinHalf);
    	assertEquals(expMinHalf, DateHelper.secondsToMinutes(secHalf));
    	
    	
    	int secHalf2 = 380;
    	int expMinHalf2 = secHalf2 / 60; // we want rounding not upper/ceiling
    	
    	given(secHalf2+"s");
    	when("transformed to minutes");
    	then("we expect "+expMinHalf2);
    	assertEquals(expMinHalf2, DateHelper.secondsToMinutes(secHalf2));
    	
    	
    	successFullStory();
    }
    
    public void testSecondsToMillis(){
    	int sec = 5;
    	int expMillis = sec * 1000;
    	
    	given(sec+"s");
    	when("transformed to milliseconds");
    	then("we expect "+expMillis);
    	
    	assertEquals(expMillis, DateHelper.secondsToMillis(sec));
    	successFullStory();
    }
    
    public void testMillisToSeconds(){
    	int millis = 5000;
    	int expSec = millis / 1000;
    	
    	given(millis+"ms");
    	when("transformed to seconds");
    	then("we expect "+expSec);
    	
    	assertEquals(expSec, DateHelper.millisToSeconds(millis));
    	
    	int millisHalf = 5500;
    	int expSecHalf = (millisHalf + 500) / 1000; // we want rounding not
    	
    	given(millisHalf+"ms");
    	when("transformed to seconds");
    	then("we expect "+expSecHalf);
    	assertEquals(expSecHalf, DateHelper.millisToSeconds(millisHalf));
    	
    	
    	int millisHalf2 = 5040;
    	int expSecHalf2 = millisHalf2 / 1000; // we want rounding not upper/ceiling
    	
    	given(millisHalf2+"ms");
    	when("transformed to seconds");
    	then("we expect "+expSecHalf2);
    	assertEquals(expSecHalf2, DateHelper.millisToSeconds(millisHalf2));
    	
    	
    	successFullStory();

    }
    
    
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
        Date currentDate = new Date();

        given("a normal date object with current time:" + currentDate);

        when("converted to date in gregorian format (" + GREGFORMAT + ")");
        	XMLGregorianCalendar cal = DateHelper.dateToXMLGregorian(currentDate);

        	DateFormat formatter = new SimpleDateFormat(GREGFORMAT);
        	String gregStr = formatter.format(currentDate);

        then("we expect the calendar to contain the gregorian value " + gregStr);

        	assertEquals(gregStr, cal.toString());

        when("we supply timezone UTC");
        	XMLGregorianCalendar calZ = DateHelper.dateToXMLGregorian(currentDate, DateHelper.getUTC());
        then("we expect the pattern " + GREGFORMAT + "Z");
        	assertEquals(gregStr + "Z", calZ.toString());

        successFullStory();
    }

    public void testDateTime2Gregorian() {
        	Date currentDate = new Date();

        given("a normal datetime object with current time:" + currentDate);

        when("converted to datetime in gregorian format (" + GREGFORMAT + ")");
        	XMLGregorianCalendar cal = DateHelper.datetimeToXMLGregorian(currentDate, DateHelper.getUTC());

        	DateFormat formatter = new SimpleDateFormat(GREGFORMAT + "'T'HH:mm:ss.SSS'Z'");
        	formatter.setTimeZone(DateHelper.getUTC());
        	String gregStr = formatter.format(currentDate);

        then("we expect the calendar to contain the gregorian value " + gregStr);

        	assertEquals(gregStr, cal.toString());

        when("called without timezone UTC");
        	XMLGregorianCalendar cal2 = DateHelper.datetimeToXMLGregorian(currentDate);

        then("we expect a +x:xx");
        	DateFormat formatter2 = new SimpleDateFormat(GREGFORMAT + "'T'HH:mm:ss.SSS");
        	String gregStr2_zomer = formatter2.format(currentDate)+"+02:00";
        	String gregStr2_winter = formatter2.format(currentDate)+"+01:00";
        	assertTrue(gregStr2_zomer.equals(cal2.toString()) || gregStr2_winter.equals(cal2.toString()));
        	
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
    
    
    public void testNullSafe(){
    	assertNull(DateHelper.getDateString(null));
    	assertNull(DateHelper.copy(null));
    	assertNull(DateHelper.datetimeToXMLGregorian(null));
    	assertNull(DateHelper.datetimeToXMLGregorian(null, null));
    	assertNull(DateHelper.dateToXMLGregorian(null));
    	assertNull(DateHelper.dateToXMLGregorian(null,null));
    	assertNull(DateHelper.gregorianToDate(null));
    	
    }
    
}
