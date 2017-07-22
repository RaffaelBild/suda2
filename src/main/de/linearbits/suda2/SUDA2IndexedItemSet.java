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

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import com.carrotsearch.hppc.LongObjectOpenHashMap;
import com.carrotsearch.hppc.cursors.ObjectCursor;

/**
 * A set of items
 * 
 * @author Fabian Prasser
 */
public class SUDA2IndexedItemSet {

    /** The underlying map */
    private final LongObjectOpenHashMap<SUDA2Item> items = new LongObjectOpenHashMap<SUDA2Item>();
    
    /**
     * Adds an item
     * @param item
     * @return
     */
    public void addItem(SUDA2Item item) {
        items.put(item.getId(), item);
    }

    /**
     * Returns item for the given id
     * @param id
     * @return
     */
    public SUDA2Item getItem(long id) {
        return items.get(id);
    }
    
    /**
     * Returns a list containing items sorted by rank
     * @param items
     * @return
     */
    public SUDA2ItemList getItemList() {

        // Create list and sort by support
        List<SUDA2Item> list = new ArrayList<SUDA2Item>();
        Iterator<ObjectCursor<SUDA2Item>> iter = items.values().iterator();
        while (iter.hasNext()) {
            list.add(iter.next().value);
        }
        Collections.sort(list, new Comparator<SUDA2Item>() {
            @Override
            public int compare(SUDA2Item o1, SUDA2Item o2) {
                return o1.getSupport() < o2.getSupport() ? -1 :
                       o1.getSupport() > o2.getSupport() ? +1 : 0;
            }
        });
        
        // Return
        return new SUDA2ItemList(list);
    }
    
    /**
     * Either returns an existing entry or creates a new one
     * @param column
     * @param value
     * @return
     */
    public SUDA2Item getOrCreateItem(int column, int value) {
        long id = SUDA2Item.getId(column, value);
        SUDA2Item item;
        if (items.containsKey(id)) {
            item = items.lget(); 
        } else {
            item = new SUDA2Item(column, value, id);
            items.put(id, item);
        }
        return item;
    }
}
