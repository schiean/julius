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

import julius.test.BDDTestCase;

public class TestGeneralObjectHelper extends BDDTestCase {

    public void testEqual() {
        note("null, 1 should be false");
        assertFalse(GeneralObjectHelper.equals(null, 1));
        note("1, null should be false");
        assertFalse(GeneralObjectHelper.equals(1, null));
        note("1 , 1 should be true");
        assertTrue(GeneralObjectHelper.equals(1, 1));
        note("null, null should be true");
        assertTrue(GeneralObjectHelper.equals(null, null));

    }

    public void testEqualNotNull() {
        note("null, 1 should be false");
        assertFalse(GeneralObjectHelper.equalsNotNull(null, 1));
        note("1, null should be false");
        assertFalse(GeneralObjectHelper.equalsNotNull(1, null));
        note("1 , 1 should be true");
        assertTrue(GeneralObjectHelper.equalsNotNull(1, 1));
        note("null, null should be false");
        assertFalse(GeneralObjectHelper.equalsNotNull(null, null));

    }

    public void testSameInstance() {

        note("different objects should return false");
        Integer o1 = 1;
        Integer o2 = 2;
        assertFalse(GeneralObjectHelper.areSameInstance(o1, o2));
        assertTrue(GeneralObjectHelper.areNotSameInstance(o1, o2));

        note("two refs for the same object should return true");

        Integer o1_c = o1;
        assertTrue(GeneralObjectHelper.areSameInstance(o1, o1_c));
        assertFalse(GeneralObjectHelper.areNotSameInstance(o1, o1_c));

        note("equal objects but different instances should return false");
        o2 = new Integer(1);
        assertTrue(o1.equals(o2));
        assertFalse(GeneralObjectHelper.areSameInstance(o1, o2));
        assertTrue(GeneralObjectHelper.areNotSameInstance(o1, o2));

        note("should be null safe");
        assertTrue(GeneralObjectHelper.areSameInstance(null, null));
        assertFalse(GeneralObjectHelper.areNotSameInstance(null, null));

        note("self compare should return ok");
        assertTrue(GeneralObjectHelper.areSameInstance(o1, o1));
        assertFalse(GeneralObjectHelper.areNotSameInstance(o1, o1));

    }

    public void testNull() {
        note("null should return isnull true, other values false");
        assertTrue(GeneralObjectHelper.isNull(null));
        assertFalse(GeneralObjectHelper.isNull(5));
        assertTrue(GeneralObjectHelper.isNotNull(5));
        assertFalse(GeneralObjectHelper.isNotNull(null));

    }

    public void testGetOrDefault() {
        note("should use val if not null");
        assertEquals("", GeneralObjectHelper.getOrDefault("", "asd"));
        note("should use _default if val==null");
        assertEquals("asd", GeneralObjectHelper.getOrDefault(null, "asd"));
    }

    public void testIsAllNull() {
        assertTrue(GeneralObjectHelper.allNull((Object[]) null));
        assertTrue(GeneralObjectHelper.allNull(null, null, null));

        assertFalse(GeneralObjectHelper.allNull(1, 1));
        assertFalse(GeneralObjectHelper.allNull(null, 1, null));
        assertFalse(GeneralObjectHelper.allNull(null, null, 1));
    }

    public void testIsAnyNull() {
        assertTrue(GeneralObjectHelper.anyNull((Object[]) null));
        assertTrue(GeneralObjectHelper.anyNull(null, null, null));
        assertTrue(GeneralObjectHelper.anyNull(null, 1, null));
        assertTrue(GeneralObjectHelper.anyNull(null, null, 1));

        assertFalse(GeneralObjectHelper.anyNull(1, 1));

    }

    public void testIsAnyNotNull() {
        assertTrue(GeneralObjectHelper.anyNotNull(null, 1, null));
        assertTrue(GeneralObjectHelper.anyNotNull(null, null, 1));
        assertTrue(GeneralObjectHelper.anyNotNull(1, 1));

        assertFalse(GeneralObjectHelper.anyNotNull((Object[]) null));
        assertFalse(GeneralObjectHelper.anyNotNull(null, null, null));
    }

    public void testIsAllNotNull() {

        assertTrue(GeneralObjectHelper.allNotNull(1, 1));

        assertFalse(GeneralObjectHelper.allNotNull(null, 1, null));
        assertFalse(GeneralObjectHelper.allNotNull(null, null, 1));
        assertFalse(GeneralObjectHelper.allNotNull((Object[]) null));
        assertFalse(GeneralObjectHelper.allNotNull(null, null, null));
    }

}
