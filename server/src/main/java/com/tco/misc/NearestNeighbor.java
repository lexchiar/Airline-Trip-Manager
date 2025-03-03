package com.tco.misc;

import com.tco.requests.Place;
import com.tco.requests.Places;

public class NearestNeighbor {

    private Places places;
    private int tourSize;
    private double earthRadius;
    private int[] currTour;
    private int[] bestTour;
    private boolean[] visited;
    public long[][] distanceMatrix;
    private int[] originalTour;
    private long originalDistance;
    private long currTourDistance;
    private long bestTourDistance;

    public NearestNeighbor(Places places, double earthRadius) {
        this.places = places;
        this.tourSize = places.size();
        this.earthRadius = earthRadius;
        this.currTour = new int[this.tourSize];
        this.bestTour = new int[this.tourSize];
        this.visited = new boolean[this.tourSize];
        this.currTourDistance = 0;
        buildDistancesMatrix();
        buildOriginalTour();
        this.bestTourDistance = this.originalDistance;
    }

    /**************************
    Builds distance matrix
    ***************************/
    private void buildDistancesMatrix(){
        distanceMatrix = new long[tourSize][tourSize];
        for (int i = 0; i < tourSize; i++) {
            for (int j = i; j < tourSize; j++) {
                if (i == j) {
                    distanceMatrix[i][j] = 0;
                }
                else{
                    long distanceFound = DistanceCalculator.calculate(places.get(i), places.get(j), earthRadius);
                    distanceMatrix[i][j] = distanceFound;
                    distanceMatrix[j][i] = distanceFound;
                    if (j == (i + 1)) {
                        originalDistance += distanceFound;
                    }
                }             
            }                   
        }
        originalDistance += DistanceCalculator.calculate(places.get(0), places.get(tourSize - 1), earthRadius);
    }

    /***********************************************
    For all nearest neighbor tours, finds the best
    ***********************************************/
    public Places findBestNearestNeighborTour() {
        for (int i = 0; i < tourSize; ++i) {
            createNearestNeighborTour(i);
            if (currTourDistance < bestTourDistance) {
                updateBestTour();
            }
            resetTour();
        }
        shiftBestTourIndices(); 
        return buildBestPlaces();
    }

    /******************************** 
    Creates nearest neighbor tour
    *********************************/
    private void createNearestNeighborTour(int startCityIndex) {
        /* Add starting city to tour and mark it visited */
        currTour[0] = originalTour[startCityIndex];
        visited[startCityIndex] = true;
        int currCityIndex = startCityIndex;
        
        /* Loops through unvisited places to find nearest neighbor */
        for (int i = 1; i < tourSize; ++i) {
            int nearestNeighborIndex = tourSize;
            long nearestNeighborDistance = Integer.MAX_VALUE;

            /* Finds nearest neighbor */
            for (int j = 0; j < tourSize; ++j) {
                if (visited[j]) {
                    continue;
                }
                else {
                    /* Get the distance between current city and the next city in the array */
                    long currDistance = distanceMatrix[currCityIndex][j];
                    if ((currDistance < nearestNeighborDistance) && (currCityIndex != j)) {
                        nearestNeighborDistance = currDistance;
                        nearestNeighborIndex = j;
                    }
                }
            }
            /* Add nearest neighbor to tour and update visited */
            currTour[i] = nearestNeighborIndex;
            currTourDistance += nearestNeighborDistance;
            visited[nearestNeighborIndex] = true;
            currCityIndex = nearestNeighborIndex;
        }
        /* Add distance of return leg */
        int lastCity = currTour[tourSize - 1];
        currTourDistance += distanceMatrix[startCityIndex][lastCity];
    }

    /*************************************************************************** 
    Updates the best tour array to keep track of the current shortest tour found
    *****************************************************************************/
    private void updateBestTour() {
        bestTourDistance = currTourDistance;
        for (int i = 0; i < tourSize; ++i) {
            bestTour[i] = currTour[i];
        }
    }

    /********************************************************************************** 
    Rotates the indices in the best tour array to preserve the original start place
    ***********************************************************************************/
    private void shiftBestTourIndices() {
        while (bestTour[0] != 0) {
            int temp = bestTour[0];
            for (int i = 0; i < tourSize - 1; ++i) {
                bestTour[i] = bestTour[i + 1];
            }
            bestTour[tourSize - 1] = temp;
        }
    }

    /***************************************************************  
    Builds a new Places object with the updated tour and returns it
    *****************************************************************/
    private Places buildBestPlaces() {
        Places newPlaces = new Places();
        for (int i = 0; i < tourSize; ++i) {
            newPlaces.add(places.get(bestTour[i]));
        }
        return newPlaces;
    }

    /***********************************************
    Used to initialize data member arrays
    ***********************************************/
    private void buildOriginalTour() {
        originalTour = new int[tourSize];
        for (int i = 0; i < tourSize; ++i) {
            originalTour[i] = i;
            bestTour[i] = i;
        }
    }

    /************************************
    Resets variables for the next tour
    ************************************/
    private void resetTour() {
        for (int i = 0; i < tourSize; ++i) {
            visited[i] = false;
        }
        currTourDistance = 0;
    }
}
