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
package de.linearbits.test;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class FileConverter {

    public static void main(String[] args) throws IOException {
        
        String name = "ss13acs_sparse";
        File file = new File("data/" + name + ".csv");
        BufferedReader reader = new BufferedReader(new FileReader(file));
        reader.readLine(); // skip header
        
        List<String[]> lines = new ArrayList<String[]>();
        String line = reader.readLine();
        while (line != null) {
            lines.add(line.split(String.valueOf(";"), -1));
            line = reader.readLine();
        }
        reader.close();

        write("data/" + name + "_int.csv", lines.iterator());
    }
    
    private static void write(String file, Iterator<String[]> iterator) throws IOException {
        Map<Integer, Map<String, Integer>> maps = new HashMap<>();
        BufferedWriter writer = new BufferedWriter(new FileWriter(new File(file)));
        while(iterator.hasNext()) {
            int[] row = encode(maps, iterator.next());
            for (int i=0; i<row.length; i++) {
                writer.write(String.valueOf(row[i]));
                if (i != row.length-1) {
                    writer.write(";");
                } else {
                    writer.write("\n");
                }
            }
        }
        writer.close();
        
        int distinct = 0;
        for (Map<String, Integer> map : maps.values()) {
            distinct+=map.size();
        }
        System.out.println("Distinct values: " + distinct);
    }

    private static int[] encode(Map<Integer, Map<String, Integer>> maps, String[] next) {
        int[] result = new int[next.length];
        for (int column = 0; column < next.length; column++) {
            if (!maps.containsKey(column)) {
                maps.put(column, new HashMap<String, Integer>());
            }
            Integer code = maps.get(column).get(next[column]);
            if (code == null) {
                code = maps.get(column).size();
                maps.get(column).put(next[column], code);
            }
            result[column] = code;
        }
        return result;
    }
}
