package com.thoughtworks.route;

import org.junit.Test;
import com.thoughtworks.route.trains.Digraph;

import static org.junit.Assert.assertTrue;

/**
 * Created by wang on 2018/12/18.
 */
public class ComputePathTest {
    @Test
    public void test(){

            String input = "AB5,BC4,CD8,DC8,DE6,AD5,CE2,EB3,AE7";
            Digraph digraph = Digraph.create(input);

            int v1 = digraph.getDistance("A-B-C");
            int v2 = digraph.getDistance("A-D");
            int v3 = digraph.getDistance("A-D-C");
            int v4 = digraph.getDistance("A-E-B-C-D");
            int v5 = digraph.getDistance("A-E-D");

            int v6 = digraph.getTripsNum('C', 'C', 2) + digraph.getTripsNum('C', 'C', 3);
            int v7 = digraph.getTripsNum('A', 'C', 4);

            int v8 = digraph.getShortestPath('A', 'C');
            int v9 = digraph.getShortestPath('B', 'B');
            int v10 = digraph.getDifferentRoutes('C', 'C', 30);

            assertTrue(v1 == 9);
            assertTrue(v2 == 5);
            assertTrue(v3 == 13);
            assertTrue(v4 == 22);
            assertTrue(v5 == -1);
            assertTrue(v6 == 2);
    }
}
