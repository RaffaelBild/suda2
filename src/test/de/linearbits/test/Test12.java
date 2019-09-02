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

/**
 * Example from KAP
 * 
 * @author Fabian Prasser
 */
public class Test12 extends AbstractTest{

    /**
     * Main entry point
     * @param args
     * @throws IOException 
     */
    public static void main(String[] args) throws IOException {
       
        int[][] data = new int[][] {
            new int[] { 2, 0, 0,  3, 1 }, // 0 
            new int[] { 1, 3, 6, 16, 1 }, // 1 
            new int[] { 1, 0, 0,  5, 1 }  // 2
        };
        
        // Process
        double[] result1 = new SUDA2(data).getStatisticsColumns(0, false).getColumnKeyContributions();
        System.out.println(Arrays.toString(result1));
        
        // Run sdcMicro:
        // require(sdcMicro)
        // a <- c(2, 1, 1)
        // b <- c(0, 3, 0)
        // c <- c(0, 6, 0)
        // d <- c(3, 16, 5)
        // e <- c(1, 1, 1)
        // data <- data.frame(a,b,c,d,e)
        // su <- suda2(data)
        // su
        
        // SDCM:
        // 23.07692 19.23077 15.38462 46.15385 0.00000
        //
        // ARX:
        // 0.23076923076923078, 0.19230769230769232, 0.19230769230769232, 0.46153846153846156, 0.0
        // ARX intermediateScores:
        // 24, 6, 2, 1, 1
        // ARX totalScore:
        // 6*24 + 2*6 = 156
        // ARX contributions (not normalized):
        // 36 30 30 72 0

    }
}
