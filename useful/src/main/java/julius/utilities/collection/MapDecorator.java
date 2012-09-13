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

package julius.utilities.collection;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

import org.jpatterns.gof.DecoratorPattern.Decorator;

/**
 * Class decorating a map
 */
@Decorator
public abstract class MapDecorator<K, V> implements Map<K,V>{

	protected final Map<K,V> decorated;

	/**
	 * @param map instance of the decorated map, allows injection of all kinds of maps
	 */
	protected MapDecorator(final Map<K,V> map){
		this.decorated = map;
	}
	
	@Override
	public int size() {
		return decorated.size();
	}

	@Override
	public boolean isEmpty() {
		return decorated.isEmpty();
	}

	@Override
	public boolean containsKey(final Object key) {
		return decorated.containsKey(key);
	}

	@Override
	public boolean containsValue(final Object value) {
		return decorated.containsValue(value);
	}

	@Override
	public V get(final Object key) {
		return decorated.get(key);
	}

	@Override
	public V put(final K key, final V value) {
		return decorated.put(key, value);
	}

	@Override
	public V remove(final Object key) {
		return decorated.remove(key);
	}

	@Override
	public void putAll(final Map<? extends K, ? extends V> m) {
		decorated.putAll(m);
	}

	@Override
	public void clear() {
		decorated.clear();
	}

	@Override
	public Set<K> keySet() {
		return decorated.keySet();
	}

	@Override
	public Collection<V> values() {
		return decorated.values();
	}

	@Override
	public Set<java.util.Map.Entry<K, V>> entrySet() {
		return decorated.entrySet();
	}

	@Override
	public boolean equals(final Object o) {
		return decorated.equals(o);
	}

	@Override
	public int hashCode() {
		return decorated.hashCode();
	}

}
