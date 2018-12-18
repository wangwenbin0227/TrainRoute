package com.thoughtworks.route.trains;

import java.util.Set;
import java.util.TreeSet;

/**
 * directed graph
 * Based on adjacency-matrix of assembly process storage
 * Created by wangWenBin on 2018/12/08.
 */
public class Digraph {
    /**
    * vertex number
    */
    private int vertexNum;
    /**
     * edge number
     */
    private int edgeNum;
    /**
     * vertex array
     */
    private char[] vertexes;
    /**
     * weighted adjacency matrix
     */
    private Matrix adjacencyWeightMatrix;
    /**
     * weightless adjacency matrix
     */
    private Matrix adjacencyMatrix;

    /**
     * constructing Directed Graphs Based on Input Strings
     * @param input eg: AB5, BC4, CD8, DC8, DE6, AD5, CE2, EB3, AE7
     * @return
     */
    public static Digraph create(String input) {
        if (input == null || input.length() == 0) {
            throw new IllegalArgumentException();
        }

        try {
            String[] routes = input.split(",");
            Set<Character> set = new TreeSet<>();
            for (int i = 0; i<routes.length; i++) {
                set.add(routes[i].charAt(0));
                set.add(routes[i].charAt(1));
            }

            // vertex number
            int size = set.size();

            char[] vertexes = new char[size];
            int[][] weightArray = new int[size][size];
            int[][] array = new int[size][size];
            Digraph digraph = new Digraph();
            digraph.vertexNum = size;
            digraph.edgeNum = routes.length;

            //vertex array assignment
            int index = 0;
            for (Character character : set) {
                vertexes[index++] = character;
            }
            digraph.vertexes = vertexes;

            //initialization of adjacency matrix
            for (int i = 0; i<weightArray.length; i++) {
                for (int j = 0; j<weightArray[i].length; j++) {
                    weightArray[i][j] = Integer.MAX_VALUE;
                    array[i][j] = 0;
                }
            }

            //adjacency matrix assignment
            for (int k = 0; k<routes.length; k++) {
                int i = digraph.indexOf(routes[k].charAt(0));
                int j = digraph.indexOf(routes[k].charAt(1));
                weightArray[i][j] = Integer.parseInt(String.valueOf(routes[k].charAt(2)));
                array[i][j] = 1;
            }
            digraph.adjacencyWeightMatrix = Matrix.of(weightArray);
            digraph.adjacencyMatrix = Matrix.of(array);
            return digraph;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * calculate the total number of directed paths with length n from start to end
     * @param from starting point
     * @param to end point
     * @param n length
     * @return
     */
    public int getTripsNum(char from, char to, int n) {
        int fromIndex = this.indexOf(from);
        int toIndex = this.indexOf(to);
        if (fromIndex < 0 || toIndex < 0) {
            // path does not exist
            return -1;
        }
        Matrix matrix = this.getAdjacencyMatrix();
        int[][] array = matrix.power(n).getArray();
        return array[fromIndex][toIndex];
    }

    /**
     * calculate the distance of the route
     * @param route eg: A-B-C
     * @return
     */
    public int getDistance(String route) {
        if (route == null || route.length() == 0) {
            throw new IllegalArgumentException();
        }
        int distance = 0;
        char[] address = route.replaceAll("-", "").toCharArray();
        for (int k = 0; k<address.length-1; k++) {
            int i = indexOf(address[k]);
            int j = indexOf(address[k+1]);
            int[][] array = this.adjacencyWeightMatrix.getArray();
            if (array[i][j] < Integer.MAX_VALUE) {
                distance += array[i][j];
            } else {
                //path does not exist
                return -1;
            }
        }
        return distance;
    }

    //preserving the calculation results of Floyd algorithm
    private int[][] floyd;

    /**
     * calculating the Shortest Path from the Designated Starting Point to the End Point Based on Floyd Algorithms
     * @param from starting point
     * @param to end point
     * @return
     */
    public int getShortestPath(char from, char to) {
        int fromIndex = this.indexOf(from);
        int toIndex = this.indexOf(to);
        if (fromIndex < 0 || toIndex < 0) {
            // path does not exist
            return -1;
        }

        // initialization of Floyd array
        this.floyd = new int[this.vertexNum][this.vertexNum];
        int[][] weightArray = this.adjacencyWeightMatrix.getArray();
        for (int i = 0; i<weightArray.length; i++) {
            for (int j = 0; j<weightArray[i].length; j++) {
                this.floyd[i][j] = weightArray[i][j];
            }
        }

        // freud algorithm
        for (int index = 0; index<this.vertexNum; index++) {
            for (int i = 0; i<this.floyd.length; i++) {
                for (int j = 0; j<this.floyd[i].length; j++) {
                    if (this.floyd[i][index] < Integer.MAX_VALUE && this.floyd[index][j] < Integer.MAX_VALUE && this.floyd[i][j] > this.floyd[i][index] + this.floyd[index][j]) {
                        this.floyd[i][j] = this.floyd[i][index] + this.floyd[index][j];
                    }
                }
            }
        }
        return this.floyd[fromIndex][toIndex];
    }

    /**
     * depth first search
     * @param fromIndex starting point
     * @param toIndex end point
     * @param lessThan maximum distance limit from start to finish
     * @param distance current path distance
     */
    private void dfs(int fromIndex, int toIndex, int lessThan, int distance) {
        if (distance >= lessThan) {
            return;
        }
        if (fromIndex == toIndex && distance > 0) {
            differentRoutesNum++;
        }
        int[][] weightArray = this.adjacencyWeightMatrix.getArray();
        for (int i = 0; i<weightArray[fromIndex].length; i++) {
            if (weightArray[fromIndex][i] < Integer.MAX_VALUE) {
                dfs(i, toIndex, lessThan, weightArray[fromIndex][i] + distance);
            }
        }
    }

    public int getDifferentRoutes(char from, char to, int lessThan) {
        int fromIndex = indexOf(from);
        int toIndex = indexOf(to);
        if (fromIndex < 0 || toIndex < 0) {
            return -1;
        }
        int distance = 0;
        this.differentRoutesNum = 0;
        dfs(fromIndex, toIndex, lessThan, distance);
        return this.differentRoutesNum;
    }

    // the number of different routes used to mark distances from a to b less than a fixed constant
    private int differentRoutesNum = 0;

    /**
     * compute vertex subscript
     * @param vertex
     * @return
     */
    private int indexOf(char vertex) {
        for (int i = 0; i<this.vertexes.length; i++) {
            if (this.vertexes[i] == vertex) {
                return i;
            }
        }
        return -1;
    }

    public int getVertexNum() {
        return vertexNum;
    }

    public int getEdgeNum() {
        return edgeNum;
    }

    public char[] getVertexes() {
        return vertexes;
    }

    public Matrix getAdjacencyWeightMatrix() {
        return adjacencyWeightMatrix;
    }

    public Matrix getAdjacencyMatrix() {
        return adjacencyMatrix;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Vertex table：");
        sb.append("[");
        if (this.vertexes != null && this.vertexes.length > 0) {
            for (int i = 0; i<this.vertexes.length; i++) {
                if (i > 0) {
                    sb.append(",");
                }
                sb.append(this.vertexes[i]);
            }
        }
        sb.append("]\n");
        sb.append("Weighted adjacency matrix：\n");
        sb.append(this.adjacencyWeightMatrix);
        sb.append("Weightless adjacency matrix：\n");
        sb.append(this.adjacencyMatrix);
        return sb.toString();
    }
}
