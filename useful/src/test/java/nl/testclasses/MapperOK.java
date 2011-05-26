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

import java.util.HashSet;
import java.util.Set;

public class MapperOK {
    public Integer mapTo(final Object o) {
        if (o == null) {
            return null;
        } else {
            return 1;
        }
    }

    public void mapInto(final Object o, final Object b) {
    }

    public void mapInto(final Object o, final Object b, final Object third) {// should not be called
        throw new RuntimeException();
    }

    public Set<Integer> mapToOSet(final Set<Object> o) {
        if (o == null) {
            return null;
        } else {
            return new HashSet<Integer>();
        }
    }

}

