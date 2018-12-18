package com.thoughtworks.route;

import com.thoughtworks.route.trains.Digraph;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class ComputePath {

    public static void main(String[] args) {

        try (InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream("route.txt")) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            String input = reader.readLine();
            if (input == null || input.length() == 0) {
                System.out.println("Input routes are not standardized.for example;AB5,BC4,CD8,DC8,DE6,AD5,CE2,EB3,AE7");
            }

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

            System.out.println("#1: " + (v1 == -1 ? "NO SUCH ROUTE" : v1));
            System.out.println("#2: " + (v2 == -1 ? "NO SUCH ROUTE" : v2));
            System.out.println("#3: " + (v3 == -1 ? "NO SUCH ROUTE" : v3));
            System.out.println("#4: " + (v4 == -1 ? "NO SUCH ROUTE" : v4));
            System.out.println("#5: " + (v5 == -1 ? "NO SUCH ROUTE" : v5));
            System.out.println("#6: " + v6);
            System.out.println("#7: " + v7);
            System.out.println("#8: " + v8);
            System.out.println("#9: " + v9);
            System.out.println("#10: " + v10);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}