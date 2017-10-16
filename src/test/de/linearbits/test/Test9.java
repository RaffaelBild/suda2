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
import de.linearbits.suda2.Timeable;

/**
 * Test based on survey data data
 * 
 * @author Fabian Prasser
 */
public class Test9 extends AbstractTest{

    /**
     * Main entry point
     * @param args
     * @throws IOException 
     */
    public static void main(String[] args) throws IOException {
       
        // As array
        String[] files = new String[]{
//            "data/test.csv",    // Adult
//            "data/test2.csv",   // Whatever
//            "data/test3.csv",   // Whatever
//            "data/test4.csv",   // FARS
//            "data/test5.csv",   // IHIS
            "data/test6.csv",   // SS13ACS
//            "data/test7.csv"    // CUP
        };
        
        for (String file : files) {
            Timeable.reset();
            int[][] dataset = getData(file);
            System.out.println("Dataset: " + file + " length: " + dataset.length);
            // Process
            int REPETITIONS = dataset.equals("data/test6.csv") ? 1 : 20;
            long time = System.nanoTime();
            for (int i=0; i<REPETITIONS; i++) {
//                long time2 = System.currentTimeMillis();
                new SUDA2(dataset).getKeyStatistics(0, true);
//                System.out.println(" Run: " + (System.currentTimeMillis() - time2));
            }
//            time = (long)((System.nanoTime() - time) / (double)REPETITIONS);
            System.out.println(" - Average time: " + (float)((System.nanoTime() - time) / (double)REPETITIONS / 1e9d));
            System.out.println("   - Projection time: " + (float)((double)Timeable.methodCallTime[Timeable.METHOD_PROJECTION] / (double)REPETITIONS / 1e9d) + " count: " + (long)((double)Timeable.methodCallCount[Timeable.METHOD_PROJECTION] / (double)REPETITIONS));
            for (int type : new int[]{Timeable.TYPE_INT_SET_BITS, Timeable.TYPE_INT_SET_HASH, Timeable.TYPE_INT_SET_SMALL}) {
                System.out.println("");
                System.out.println("Type " + type + " instances: " + Timeable.instanceCount[type]);
                System.out.println(" - Intersection time: " + (float)((double)Timeable.typeMethodCallTime[type][Timeable.TYPE_METHOD_INTERSECTION] / (double)REPETITIONS / 1e9d) + " count: " + (long)((double)Timeable.typeMethodCallCount[type][Timeable.TYPE_METHOD_INTERSECTION] / (double)REPETITIONS));
                printDetails(" - Intersection", type, Timeable.TYPE_METHOD_INTERSECTION, REPETITIONS);
                System.out.println(" - Special row time: " + (float)((double)Timeable.typeMethodCallTime[type][Timeable.TYPE_METHOD_SPECIALROW] / (double)REPETITIONS / 1e9d) + " count: " + (long)((double)Timeable.typeMethodCallCount[type][Timeable.TYPE_METHOD_SPECIALROW] / (double)REPETITIONS));
                printDetails(" - Special row", type, Timeable.TYPE_METHOD_SPECIALROW, REPETITIONS);
                System.out.println(" - Support row time: " + (float)((double)Timeable.typeMethodCallTime[type][Timeable.TYPE_METHOD_SUPPORTROW] / (double)REPETITIONS / 1e9d) + " count: " + (long)((double)Timeable.typeMethodCallCount[type][Timeable.TYPE_METHOD_SUPPORTROW] / (double)REPETITIONS));
                printDetails(" - Support row", type, Timeable.TYPE_METHOD_SUPPORTROW, REPETITIONS);
            }
            System.out.println("");
            System.out.println("Totals:");
            printDetailTotals(" - Intersection", Timeable.TYPE_METHOD_INTERSECTION, REPETITIONS);
            printDetailTotals(" - Special row", Timeable.TYPE_METHOD_SPECIALROW, REPETITIONS);
            printDetailTotals(" - Support row", Timeable.TYPE_METHOD_SUPPORTROW, REPETITIONS);
        }
    }

    private static void printDetails(String name, int type, int typeMethod, int reps) {
        String line = name + " time buckets: ";
        for (int i=0; i<7; ++i) {
            line += (float)((double)Timeable.typeMethodSizeTimeBuckets[type][typeMethod][i] / (double)reps / 1e9d) + "    ";
        }
        System.out.println(line);
        
        line = name + " count buckets: ";
        for (int i=0; i<7; ++i) {
            line += (long)((double)Timeable.typeMethodSizeCountBuckets[type][typeMethod][i] / (double)reps) + "    ";
        }
        System.out.println(line);
    }
    
    private static void printDetailTotals(String name, int typeMethod, int reps) {
        String line = name + " times: ";
        long sum = 0;
        for (int type : new int[]{Timeable.TYPE_INT_SET_BITS, Timeable.TYPE_INT_SET_HASH, Timeable.TYPE_INT_SET_SMALL}) {
            sum += Timeable.typeMethodCallTime[type][typeMethod];
        }
        line += (float)((double)sum / (double)reps / 1e9d) + "    ";
        System.out.println(line);

        line = name + " counts: ";
        sum = 0;
        for (int type : new int[]{Timeable.TYPE_INT_SET_BITS, Timeable.TYPE_INT_SET_HASH, Timeable.TYPE_INT_SET_SMALL}) {
            sum += Timeable.typeMethodCallCount[type][typeMethod];
        }
        line += (long)((double)sum / (double)reps) + "    ";
        System.out.println(line);

        line = name + " - time buckets: ";
        for (int i=0; i<7; ++i) {
            sum = 0;
            for (int type : new int[]{Timeable.TYPE_INT_SET_BITS, Timeable.TYPE_INT_SET_HASH, Timeable.TYPE_INT_SET_SMALL}) {
                sum += Timeable.typeMethodSizeTimeBuckets[type][typeMethod][i];
            }
            line += (float)((double)sum / (double)reps / 1e9d) + "    ";
        }
        System.out.println(line);

        line = name + " - count buckets: ";
        for (int i=0; i<7; ++i) {
            sum = 0;
            for (int type : new int[]{Timeable.TYPE_INT_SET_BITS, Timeable.TYPE_INT_SET_HASH, Timeable.TYPE_INT_SET_SMALL}) {
                sum += Timeable.typeMethodSizeCountBuckets[type][typeMethod][i];
            }
            line += (long)((double)sum / (double)reps) + "    ";
        }
        System.out.println(line);
    }
}