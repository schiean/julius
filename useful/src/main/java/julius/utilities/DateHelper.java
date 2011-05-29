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

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

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
     * Transform a date in a long to a GregorianCalendar
     * 
     * @param date
     *            (null safe)
     * @return
     */
    public static XMLGregorianCalendar dateToGregorian(final Date date) {
        if (date == null) {
            return null;
        }
        DatatypeFactory dataTypeFactory;
        try {
            dataTypeFactory = DatatypeFactory.newInstance();
        } catch (final DatatypeConfigurationException e) {
            throw new RuntimeException(e);
        }
        GregorianCalendar gc = new GregorianCalendar();
        gc.setTimeInMillis(date.getTime());
        return dataTypeFactory.newXMLGregorianCalendar(gc);
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

}
