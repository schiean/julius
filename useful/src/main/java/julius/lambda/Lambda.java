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

package julius.lambda;

/** 
 * Java does not contain a construction for lambda;s or function pointers.
 * These classes/interfaces declare a simple method to allow anonymous inner-classes as a workaround for function pointers and lambda's
 *  
 * Marker interface to declare other interfaces that implement some kind of function-object
 * 
 * NOTE: keep in mind that anonymous classes are not extremely expensive, but still it is better to declare them as static class member
 * then as inline-method body member. (for cases where they are heavily used)  
 */
public interface Lambda {

}
