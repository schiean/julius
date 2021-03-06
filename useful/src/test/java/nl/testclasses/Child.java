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

/**
 * Simple custom type
 */
public class Child {
    private Integer val;
    private Integer val2;

    public Child(){};
    
    public Child(final Integer val, final Integer val2) {
		super();
		this.val = val;
		this.val2 = val2;
	}

	public Integer getVal2() {
        return val2;
    }

    public void setVal2(final Integer val2) {
        this.val2 = val2;
    }

    public Integer getVal() {
        return val;
    }

    public void setVal(final Integer a) {
        val = a;
    }
}
