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

import static julius.validation.Assertions.state;

/**
 * Kinda like a simplified Maybe/Option implementation.
 * its useful to document your api with this to state that methods could 
 * be 'partial functions' which means that there are cases in which it should return null
 * because there is no return value for the given input.
 * 
 * example:
 * 
 * Person findByName(String name); // this does not show what happens if nothing is found
 * 
 * Optional<Person> findByName(String name); // shows that there is a possibility where it returns nothing
 * 
 * in other languages this datatype is made into an inheritance structure and most of them also 
 * mandates the user to handle both situations (value and no value).
 * In java this would be possible by using checked exceptions (thrown when 'no value') but this
 * seems wrong because exceptions should be error cases, whereas 'no value' is broader then just errors.
 * 
 * 
 * One should never (!) return null for a method that returns type Optional.
 * That would ruin the whole idea of this type. Nesting them might be a better solution
 * Optional<Optional<Person>> findIfNameProvided(String nameOrNull); 
 * // return person, 
 * // return OptionalWithEmptyOptional because there was a name but nothing was found
 * // return OptionalEmpty because no name was supplied
 * 
 * usage:
 * 
 * public Optional<Person> find(String name){
 * 		Person p = dao.findByName(name);
 * 		return Optional.create(p);
 * }
 * 
 * String name = "James Dean";
 * Optional<Person> optPerson = service.find(name);
 * if(optPerson.hasValue()){
 * 		System.out.println(optPerson.getValue());
 * }
 * 
 * EXPERIMENTAL: Not sure how practical it would be in a project since its value declines when not all methods that
 * return null are using it.... But you wouldn't want to use it for all the fields of your pojo's 
 * 
 * 
 * Another option would be to use an empty interface with two derived classes (Nothing and Definite) which
 * would have you explicitly check the type and cast before you could use it. 
 * 
 * It does not force the caller to mess up their interface like with checked exceptions, does point out
 * very clearly when you're doing something risky (cast without instance of). Only introduces
 * an InstanceOf check but otherwise that should have been there as null-check
 * 
 */
public class Optional<T> {

	private final T value;
	
	/**
	 * @param v either null or a value
	 */
	public Optional(final T v){
		this.value = v;
	}
	
	/**
	 * @param t
	 */
	public static <T> Optional<T> create(final T t){
		return new Optional<T>(t);
	}
	
	/**
	 * @return true if it contains a value
	 */
	public boolean hasValue(){
		return value != null;
	}
	
	/**
	 * @return the value, or throws IllegalStateException if it is not available
	 */
	public T getValue(){
		state.assertTrue(hasValue(), "tried to read value from empty optional datatype");
		return value;
	}
	
}
