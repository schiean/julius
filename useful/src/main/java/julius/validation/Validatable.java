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

package julius.validation;

/**
 * Interface describing a validate method
 * 
 * can be called from the constructor of objects, but only on the lowest level in the inheritance three, else your
 * objects might not be fully initialized yet. (super() might only partially initialize and therefore its assertValid 
 * call will be on the derived object which is still initializing and fails). so for abstract classes do implement assertValid
 * but don't call it in the constructor. only call in constructors of final classes.
 */
public interface Validatable {

    /**
     * Assert that the object (and it's children, when applicable) is in a valid state 
     * Throws RuntimeException in case of inconsistent objects.
     */
    void assertValid();
}
