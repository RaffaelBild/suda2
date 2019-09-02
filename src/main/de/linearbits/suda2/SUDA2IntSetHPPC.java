/*
 * SUDA2: An implementation of the SUDA2 algorithm for Java
 * Copyright 2017 Fabian Prasser
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package de.linearbits.suda2;

import com.carrotsearch.hppc.IntOpenHashSet;

/**
 * An integer set using an implementation provided by the HPPC library.
 * 
 * @author Raffael Bild
 */
public class SUDA2IntSetHPPC extends SUDA2IntSet {

    /** Default */
    private static final float DEFAULT_LOAD_FACTOR      = 0.75f;

    /** Default */
    private static final int   DEFAULT_INITIAL_CAPACITY = 8;

    /** Min */
    private int                min                      = Integer.MAX_VALUE;

    /** Max */
    private int                max                      = Integer.MIN_VALUE;

    /** The set */
    private IntOpenHashSet     set;

    /**
     * Creates a new instance
     */
    public SUDA2IntSetHPPC() {
        this.set = new IntOpenHashSet(DEFAULT_INITIAL_CAPACITY, DEFAULT_LOAD_FACTOR);
        instance(TYPE_INT_SET_HPPC);
    }
    
    @Override
    public void add(int value) {
        min = Math.min(value, min);
        max = Math.max(value, max);
        set.add(value);
    }

    @Override
    public boolean contains(final int value) {
        return set.contains(value);
    }
    
    @Override
    public boolean containsSpecialRow(SUDA2Item[] items, SUDA2Item referenceItem, int[][] data) {
        
        // ----------------------------------------------------- //
        startTiming();
        // ----------------------------------------------------- //
        
        final int numItems = items.length;
        
        final int [] keys = set.keys;
        final boolean [] allocated = set.allocated;
        outer: for (int i = 0; i < allocated.length; i++) {
            if (allocated[i]) {
                int[] row = data[keys[i] - 1];
                for (int j = 0; j < numItems; j++) {
                    if (!items[j].isContained(row)) {
                        continue outer;
                    }
                }
                if (referenceItem.isContained(row)) {
                    continue;
                }
                // ----------------------------------------------------- //
                endTiming(TYPE_INT_SET_HPPC, TYPE_METHOD_SPECIALROW, set.size());
                // ----------------------------------------------------- //
                return true;
            }
        }
        // ----------------------------------------------------- //
        endTiming(TYPE_INT_SET_HPPC, TYPE_METHOD_SPECIALROW, set.size());
        // ----------------------------------------------------- //
        return false;
    }
    
    @Override
    public SUDA2IntSet intersectWith(SUDA2IntSet other) {

        // No output, empty set
        if (set.size() == 0 || this.max < other.min() || other.max() < this.min) {
            return new SUDA2IntSetHPPC();
        }

        // ----------------------------------------------------- //
        startTiming();
        // ----------------------------------------------------- //
        
        // Prepare
        SUDA2IntSet result = new SUDA2IntSetHPPC();
        
        // Intersect ranges
        int min = Math.max(this.min,  other.min());
        int max = Math.min(this.max,  other.max());
        
        final int [] keys = set.keys;
        final boolean [] allocated = set.allocated;
        for (int i = 0; i < allocated.length; i++) {
            if (allocated[i]) {
                int row = keys[i];
                if (row != 0 && row >= min && row <= max && other.contains(row)) {
                    result.add(row);
                }
            }
        }

        // ----------------------------------------------------- //
        endTiming(TYPE_INT_SET_HPPC, TYPE_METHOD_INTERSECTION, set.size());
        // ----------------------------------------------------- //

        return result;
    }

    @Override
    public boolean isBitSet() {
        return false;
    }

    @Override
    public boolean isSupportRowPresent(SUDA2IntSet other) {

        // No intersection
        if (this.max < other.min() || other.max() < this.min) {
            return false;
        }

        // ----------------------------------------------------- //
        startTiming();
        // ----------------------------------------------------- //

        // Intersect ranges
        int min = Math.max(this.min,  other.min());
        int max = Math.min(this.max,  other.max());

        boolean supportRowFound = false;
        final int [] keys = set.keys;
        final boolean [] allocated = set.allocated;
        for (int i = 0; i < allocated.length; i++) {
            if (allocated[i]) {
                int row = keys[i];
                if (row != 0 && row >= min && row <= max && other.contains(row)) {
                    if (supportRowFound) {
                        // ----------------------------------------------------- //
                        endTiming(TYPE_INT_SET_HPPC, TYPE_METHOD_SUPPORTROW, set.size());
                        // ----------------------------------------------------- //
                        // More than one support row
                        return false;
                    } else {
                        supportRowFound = true;
                    }
                }
            }
        }
        // ----------------------------------------------------- //
        endTiming(TYPE_INT_SET_HPPC, TYPE_METHOD_SUPPORTROW, set.size());
        // ----------------------------------------------------- //
        return supportRowFound;
    }

    @Override
    public int max() {
        return max;
    }

    @Override
    public int min() {
        return min;
    }

    @Override
    public int size() {
        return set.size();
    }

    @Override
    public String toString() {
        return "Size=" + set.size() + " array=" + set.toString();
    }
}