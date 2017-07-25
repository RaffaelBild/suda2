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

import de.linearbits.suda2.SUDA2;
import de.linearbits.suda2.SUDA2Result;

/**
 * Test based on census data
 * 
 * @author Fabian Prasser
 */
public class Test5 extends AbstractTest{

    /**
     * Main entry point
     * @param args
     * @throws IOException 
     */
    public static void main(String[] args) throws IOException {
       
        // As array
        int[][] data = getData("data/test.csv");
        
        // Process
        long time = System.currentTimeMillis();
        SUDA2Result result1 = null;
        for (int i=0; i<100; i++) {
            result1 = new SUDA2(data).getKeyStatistics(0, true);
        }
        time = (long)((System.currentTimeMillis() - time) / 100d);
        System.out.println("Time: " + time);
        System.out.println(result1);
    }
}
