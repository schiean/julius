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

package julius.reflection;

import java.lang.reflect.Method;
import java.util.List;

import julius.test.BDDTestCase;
import julius.utilities.CollectionHelper;




@SuppressWarnings("unused")
public class TestReflectionHelper extends BDDTestCase {

    ReflectionHelper refHelp = new ReflectionHelperImpl();

    private void hiPrivate() {
    }

    protected void hiProtected() {
    }

    public void hiPublic() {
    }

    public void setHi(final String a) {
    }

    public void setHiNotSetter(final String a, final String b) {
    }

    public Integer getId() {
        return null;
    }

    private Method getMethod(final String name) {
        Method[] methodsTarget = this.getClass().getDeclaredMethods();
        for (Method m : methodsTarget) {
            if (m.getName().equals(name)) {
                return m;
            }
        }
        return null;
    }

    public void testGetGetterName() {
        String setter = "setName";
        String getter = "getName";
        note("calling getter for string:" + setter + " should return " + getter);
        assertEquals(getter, refHelp.getGetterName(setter));
    }

    public void testIsPublic() {
        note("private method should return true");
        assertFalse(refHelp.isPublic(getMethod("hiPrivate")));
        note("public method should return false");
        assertTrue(refHelp.isPublic(getMethod("hiPublic")));
        note("protected method should return false");
        assertFalse(refHelp.isPublic(getMethod("hiProtected")));
    }

    public void testHasGetter() {
        note("hasgetter should return true for class A method getId");
        assertTrue(refHelp.hasGetter(TestReflectionHelper.class, "getId"));
        note("hasgetter should return false for class A method getIdMethodNotExist");
        assertFalse(refHelp.hasGetter(TestReflectionHelper.class, "getIdMethodNotExist"));
    }

    public void testIsSetter() {
        note("isSetter should return true for setHi");
        assertTrue(refHelp.isSetter(getMethod("setHi")));
        note("isSetter should return false for method getIdMethodNotExist");
        assertFalse(refHelp.isSetter(getMethod("setHiNotSetter")));
        note("isSetter should return false for method hiPublic");
        assertFalse(refHelp.isSetter(getMethod("hiPublic")));
    }
    
    public void testGetDeclaredMethods(){
    	note("TestReflectionHelper should return all methods of itself but also from BDDTestcase and TestCase");
    	List<Method> ms = refHelp.getAllDeclaredMethods(TestReflectionHelper.class);
    	assertNotNull(ms);
    	List<String> names = CollectionHelper.createLinkedList();
    	for(Method m:ms){
    		names.add(m.getName());
    	}
    	assertTrue(names.contains("testIsSetter")); // this
    	assertTrue(names.contains("given")); // BDDTestCase
    	assertTrue(names.contains("run"));// TestCase
    }
}
