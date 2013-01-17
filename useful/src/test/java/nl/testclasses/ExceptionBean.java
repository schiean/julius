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

public class ExceptionBean {

	private int i;
	private String s;
	
	public ExceptionBean(final int i, final String s) {
		super();
		this.i = i;
		this.s = s;
	}
	public int getI() {
		return i;
	}
	public void setI(final int i) {
		this.i = i;
	}
	public String getS() {
		return s;
	}
	public void setS(final String s) {
		this.s = s;
	}
	
	public Integer getFakeBeanMethod(){
		throw new RuntimeException();
	}
	
	private Integer getIllegalAcces(){
		return 5; // should not be called...
	}
	
}
