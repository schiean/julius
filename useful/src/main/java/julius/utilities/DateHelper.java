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

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeConstants;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import julius.Constants;

/**
 * useful methods for java util date.
 * Alternatively use Joda Time
 */
public final class DateHelper {

    private DateHelper() {
        // no instances needed
    }

    /**
     * Copies the date into a new instance (useful since it's not immutable)
     * 
     * returns null if the input was null
     * 
     * @param original
     * @return
     */
    public static Date copy(final Date original) {
        if (original == null) {
            return null;
        }
        return new Date(original.getTime());
    }

    /**
     * parses a xml gregorian date into a java date
     * 
     * @param xmlDate
     *            (null safe)
     * @return
     */
    public static Date gregorianToDate(final XMLGregorianCalendar xmlDate) {
        Date date = null;
        if (xmlDate != null) {
            date = xmlDate.toGregorianCalendar().getTime();
        }
        return date;
    }


    
	/**
	 * @Returns a new date which has the value input + numberOfDays
	 */
	public static Date addDays(final Date input, final int numberOfDays){
		Calendar cal = Calendar.getInstance();
		cal.setTime(input);
		cal.add(Calendar.DATE, numberOfDays);
		return copy(cal.getTime());
	}
	
	/**
	 * prints dd-MM-yyyy
	 * @param date (null safe, return null for null)
	 * @return
	 */
	public static String getDateString(final Date date) {
		if(date == null){
			return null;
		}
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.US );
		return dateFormat.format(date);
	}

	/**
	 * @return TimeZone object for UTC, which is Zulu by convention
	 */
    public static TimeZone getUTC() {
        return TimeZone.getTimeZone("Zulu");
    }

    /**
     * Translate a date (WITH TIME) to XML GregorianCalendar
     * @param date (or null)
     * @return XMLGregorianCalendar (or null if the date was null)
     */
    public static XMLGregorianCalendar datetimeToXMLGregorian(final Date date, final TimeZone zone) {
        if (date == null) {
            return null;
        }
        return createFactory().newXMLGregorianCalendar(toGregorian(date, zone));
    }


    /**
     * Translate a date (WITH TIME) to XML GregorianCalendar
     * @param date (or null)
     * @return XMLGregorianCalendar (or null if the date was null)
     */
    public static XMLGregorianCalendar datetimeToXMLGregorian(final Date date) {
        return datetimeToXMLGregorian(date, null);
    }

    /**
     * Translate a date (NO TIME) to XML GregorianCalendar
     * @param date (or null)
     * @param zone (or null)
     * @return XMLGregorianCalendar (or null if the date was null)
     */
    public static XMLGregorianCalendar dateToXMLGregorian(final Date date, final TimeZone zone) {
        if (date == null) {
            return null;
        }
        XMLGregorianCalendar xmlGC = createFactory().newXMLGregorianCalendar(toGregorian(date, zone));
        // @XmlSchemaType(name = "date")
        // http://blog.jonasbandi.net/2009/05/jaxb-quicktip-xmlgregoriancalendar.html
        if (zone == null) {
            xmlGC.setTimezone(DatatypeConstants.FIELD_UNDEFINED);
        }
        xmlGC.setTime(DatatypeConstants.FIELD_UNDEFINED, DatatypeConstants.FIELD_UNDEFINED, DatatypeConstants.FIELD_UNDEFINED);
        return xmlGC;
    }

    /**
     * Transform a date(NO TIME) to XML GregorianCalendar
     * @param date (or null)
     * @return XMLGregorianCalendar (or null if the date was null)
     */
    public static XMLGregorianCalendar dateToXMLGregorian(final Date date) {
        return dateToXMLGregorian(date, null);
    }

    /**
     * @param seconds
     * @return a rounded amount of minutes based on the provided seconds
     */
    public static long secondsToMinutes(final long seconds){
		// add half a minute to make sure the number is rounded instead of 'cut of'
		return (seconds + ( Constants.SEC_IN_MIN / 2 ) ) /  Constants.SEC_IN_MIN;
    }
    

    /**
     * @param minutes
     * @return amount of seconds based on the provided minutes
     */
    public static long minutesToSeconds(final long minutes){
    	return minutes * Constants.SEC_IN_MIN;
    }
    
    /**
     * @param millis
     * @return a rounded amount of seconds based on the provided millis
     */
	public static long millisToSeconds(final long millis) {
		// add half a sec to make sure the number is rounded instead of 'cut of'
		return (millis + ( Constants.MILLIS_IN_SEC / 2 ) ) /  Constants.MILLIS_IN_SEC;
	}

    /**
     * @param seconds
     * @return amount of millis based on the provided seconds
     */
	public static long secondsToMillis(final long seconds) {
		return seconds * Constants.MILLIS_IN_SEC;
	}
    
    /**
     * creates a gregorian calendar with optional timezone
     * @param date (required !=null)
     * @param zone (optional)
     * @return GregorianCalendar
     */
    private static GregorianCalendar toGregorian(final Date date, final TimeZone zone) {
        GregorianCalendar gc = new GregorianCalendar();
        gc.setTimeInMillis(date.getTime());
        if (zone != null) {
            gc.setTimeZone(zone);
        }
        return gc;
    }

    private static DatatypeFactory createFactory() {
        DatatypeFactory dataTypeFactory;
        try {
            dataTypeFactory = DatatypeFactory.newInstance();
        } catch (final DatatypeConfigurationException e) {
            throw new IllegalStateException(e);
        }
        return dataTypeFactory;
    }

 

}
