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

package julius.lazy;


import static julius.validation.Assertions.state;
import julius.lambda.Filter;
import julius.lambda.Operation;


/**
 * This class is useful for something that resembles a Proxy, although a true proxy mimics the interface of the original object. The
 * target of this class is to create a loose coupling between the construction of this object and the actual wanted object.
 * An example might be a to use this to bind a key and a repository and have the actual value be retrieved on calling it.
 * This way you can create an instance of this object even though the real value is not in the repository just yet.
 * 
 * If you want you can use this to recalculate/retrieve the value at every 'get' or only once (the first makes sense if the
 * value is prone to change and you always want the most recent value, the latter makes sense if the value is not to change ever
 * afterwards or if you are not interested in updates).
 * It will save the first (non-null value in that case), this caching behavior is found in CachingGenericProxy
 * 
 * Besides the lazy aspect (the value does not need to be there and can be updated, potential expensive calculations are postponed) 
 * other benefits are the loose coupling between the user of this class and the repository used to retrieve or calculate the value.
 * This class mediates between them, so if instances of this class are injected to the user, then it does not need to know the repository
 * so it only couples with this class, which hides the repos. This class however is coupled to the repo, but with a hardcoded key making it
 * a hard but small coupling.
 * 
 * Teacher has a StudentProxy, but does not know where the children come from, it only knows that there is a studentProxy that does.
 * The studentProxy binds the student key (or some other key) to a specific method of some repository, making it a very specific coupling, enhancing
 * the predictability and maintainability.
 * 
 * 
 * @param <K>
 *            The key/id used on 'some method' of 'some class' to calculate/retrieve the value.
 *            If multiple params are needed create a wrapper class
 * @param <V>
 *            The actual value type that this proxy returns
 */
public abstract class LazyLoaderProxy<K, V> {

    /**
     * The Key of the object the proxy is for.
     */
    private final K key;

    /**
     * @param key
     */
    public LazyLoaderProxy(final K key) {
        this.key = key;
        state.assertNotNull(this.key, "GenericProxy.key");
    }

    /**
     * @return the key provided at construction
     */
    public K getKey() {
        return key;
    }

    /**
     * implementations of this should return a value or null, don;t throw exceptions if 
     * things can not be found/calculated, only for real errors.
     * The GetElement wraps this with a non-null check to raise exceptions if this is necessary
     * 
     * @return the proxied value or null if it does not exist / could not be calculated
     */
    public abstract V findElement();
    
    /**
     * @return the proxied value OR throws an exception if it cannot be found/calculated
     */
    public V getElement() {
        V element = findElement();
        state.assertTrue(element != null, "object not found for key: " + key);
        return element;
    }

    /**
     * @return true if the proxied value is available / can be calculated
     */
    public boolean exists() {
        return findElement() != null;
    }
    
    
    @Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((key == null) ? 0 : key.hashCode());
		return result;
	}
    
    @Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		@SuppressWarnings("rawtypes")
		LazyLoaderProxy other = (LazyLoaderProxy) obj;
		if (key == null) {
			if (other.key != null) {
				return false;
			}
		} else if (!key.equals(other.key)) {
			return false;
		}
		return true;
	}

	@Override
    public String toString() {
        String str = key.toString();
        if (!exists()) {
            str += "(unavaillable)";
        }
        return str;
    }
    
	/**
	 * return the object that are available Functional.Filter
	 */
	public static class Loaded<T extends LazyLoaderProxy<?,?>> implements Filter<T>{
		@Override
		public boolean applicable(final T val) {
			return val.exists();
		}
	};
	
	/**
	 * return the object that are unavailable Functional.Filter
	 */
	public static class NotLoaded<T extends LazyLoaderProxy<?,?>> implements Filter<T>{
		@Override
		public boolean applicable(final T val) {
			return !val.exists();
		}
	};
	
	/**
	 * function object for extracting an Value from a LazyLoaderProxy, useful in combination with Functional.map to turn a collection of LazyLoaderProxies into a collection of their values
	 * is based on findElement
	 */
	public static class ValueExtracter<T extends LazyLoaderProxy<?, V>, V> implements Operation<T,V>{
		@Override
		public  V apply(final T val) {			
			return val.findElement();
		}
	};	
	
}
