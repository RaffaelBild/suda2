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
import de.linearbits.suda2.SUDA2Result;
import de.linearbits.suda2.SUDA2StatisticsColumns;
import de.linearbits.suda2.SUDA2StatisticsKeys;
import de.linearbits.suda2.SUDA2StatisticsScores;

/**
 * Test based on survey data data
 * 
 * @author Fabian Prasser
 */
public class Test7 extends AbstractTest{

    /**
     * Main entry point
     * @param args
     * @throws IOException 
     */
    public static void main(String[] args) throws IOException {
        
        System.out.println("Test");
        
        // As array
        int[][] data = getData("data/test2.csv");
        SUDA2StatisticsKeys result = new SUDA2(data).getStatisticsKeys(0);
        System.out.println(result.toString());
        
        
        
//        // Process
//        int REPETITIONS = 6;
//        long time = System.currentTimeMillis();
//        double[] result1 = null;
//        for (int i=0; i<REPETITIONS; i++) {
//            result1 = new SUDA2(data).getStatisticsScores(0, false).getDISScores(0.01d);
//        }
//        time = (long)((System.currentTimeMillis() - time) / (double)REPETITIONS);
//        System.out.println("Time: " + time);
//        System.out.println(Arrays.toString(result1));
//        
//        // SDCM
//        // - Number of MSUs: 321406
//        // - Column key contributions
//        // - 34.16, 74.76, 21.95, 27.10 33.59, 25.29, 67.00, 52.42, 29.62
    }
}
