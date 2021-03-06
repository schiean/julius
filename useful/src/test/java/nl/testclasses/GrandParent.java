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

package nl.testclasses;

public class GrandParent {
	   private Parent c;// grandparent has a child which is also a parent (does not make much sense, but its only testcase)
	    private Integer val;

	    public Parent getC() {
	        return c;
	    }

	    public void setC(final Parent c) {
	        this.c = c;
	    }

	    public Integer getVal() {
	        return val;
	    }

	    public void setVal(final Integer val) {
	        this.val = val;
	    }
}
