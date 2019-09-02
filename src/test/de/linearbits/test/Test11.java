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

import java.io.IOException;
import java.util.Arrays;

import de.linearbits.suda2.SUDA2;
import de.linearbits.suda2.SUDA2Item;
import de.linearbits.suda2.SUDA2StatisticsKeys;
import de.linearbits.suda2.Timeable;

/**
 * Test based on survey data data
 * 
 * @author Fabian Prasser
 */
public class Test11 extends AbstractTest{

    /**
     * Main entry point
     * @param args
     * @throws IOException 
     */
    public static void main(String[] args) throws IOException {
        
        SUDA2Item.HPPC = true;
       
        // As array
        String[][] jobs = new String[][] {
//            new String[] {"data/adult_sparse_int.csv","5"},
//            new String[] {"data/fars_sparse_int.csv","5"},
//            new String[] {"data/atus_subset_sparse_int.csv","5"},
//            new String[] {"data/ihis_subset_sparse_int.csv","5"},
//            new String[] {"data/ss13acs_sparse_int.csv","5"},
            new String[] {"data/test.csv","0"},    // Adult
            new String[] {"data/test2.csv","0"},   // ATUS
            new String[] {"data/test4.csv","0"},   // FARS
//            new String[] {"data/test7.csv","0"},   // CUP
            new String[] {"data/test5.csv","0"},   // IHIS
            new String[] {"data/test6.csv","0"},    // SS13ACS
        };
        
        for (String[] job : jobs) {
            
            String file = job[0];
            int maxKeyLength = Integer.valueOf(job[1]);
            
            int[][] dataset = getData(file);
            System.out.println("Dataset: " + file + " length: " + dataset.length + " maximal key length: " + maxKeyLength);
            // Process
            int REPETITIONS = 1;
            long time = System.currentTimeMillis();
            for (int i=0; i<REPETITIONS; i++) {
                long time2 = System.currentTimeMillis();
//                Timeable.reset();
//                SUDA2StatisticsScores stats = new SUDA2(dataset).getStatisticsScores(maxKeyLength, true);
//                double[] sudaDISScores = stats.getDISScores(0.01d);
                SUDA2StatisticsKeys stats = new SUDA2(dataset).getStatisticsKeys(maxKeyLength);
                double[] results = stats.getKeySizeDistribution();
                System.out.println(" Run: " + (System.currentTimeMillis() - time2) + " MSUs: " + stats.getNumKeys() + " results: " + Arrays.toString(results));
//                Timeable.printOverview();
            }
            time = (long)((System.currentTimeMillis() - time) / (double)REPETITIONS);
            System.out.println(" - Average time: " + time);
            // Timeable.printOverview();
        }
    }
}
