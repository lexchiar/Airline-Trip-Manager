package com.tco.requests;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import java.lang.Math.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestDistancesRequest {

        DistancesRequest request;
        Places places;
        Distances distances;

        static final long bigRadius = 1000000000000L;
        static final long bigPi = Math.round(Math.PI * bigRadius);
        static final long bigPiHalf = Math.round(Math.PI / 2.0 * bigRadius);

        @BeforeEach
        public void beforeEach(){
            places = new Places();
            request = new DistancesRequest(bigRadius, places);
        }

        @Test
        @DisplayName("erivens: empty places")
        public void testEmptyPlaces(){
            // add places, then build a response
            request.buildResponse();
            // check distances
            distances = request.distances();
            assertEquals(0, distances.size());
            assertEquals(0L, distances.total());
            // check original values
            assertEquals(0, request.places().size());
            assertEquals(bigRadius, request.earthRadius);
        }

        // @Test
        // @DisplayName("erivens: one places")
        // public void testOnePlaces(){
        //     places.add(new Place("0", "0"));
        //     request.buildResponse();
        //     // check distances
        //     distances = request.distances();
        //     assertEquals(1, distances.size());
        //     assertEquals(0L, distances.total());
        //     assertEquals(0L, distances.get(0));
        //     //check original values
        //     assertEquals(1, request.places().size());
        //     assertEquals(bigRadius, request.earthRadius());
        // }
        @Test
        @DisplayName("lexchiar: three places")
        public void testThreePlaces(){
            places.add(new Place("0", "0"));
            places.add(new Place("90", "0"));
            places.add(new Place("0", "90"));
            request.buildResponse();
            
            distances = request.distances();
            //TODO: add distances to test between three
            assertEquals(3, request.places().size());
            assertEquals(bigRadius, request.earthRadius);
        }
}