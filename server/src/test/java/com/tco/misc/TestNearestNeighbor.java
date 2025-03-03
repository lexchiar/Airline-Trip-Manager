package com.tco.misc;

import org.junit.jupiter.api.Test;
import com.tco.requests.Place;
import com.tco.requests.Places;
import com.tco.misc.NearestNeighbor;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.DisplayName;

public class TestNearestNeighbor {
    private Places places1;
    private NearestNeighbor nearestNeighbor1;
    private double earthRadius = 3958.8;
    private double response = 0.0;
    public void addPlacesToPlaces1(){
        places1 = new Places();
        places1.add(new Place("40.7128", "74.0060" ));
        places1.add(new Place("34.0522", "118.2437" ));
        places1.add(new Place(" 45.5152", "122.6784" ));
        places1.add(new Place( "25.7617", "80.1918" ));
        nearestNeighbor1 = new NearestNeighbor(places1, earthRadius);
    }

    @Test
    @DisplayName("erivens: test buildDistanceMatrix")
    public void testBuildDistanceMatrix(){
        addPlacesToPlaces1();
        assert(nearestNeighbor1.distanceMatrix != null);
        assert(nearestNeighbor1.distanceMatrix.length != 0);
        assert(nearestNeighbor1.distanceMatrix.length == 4);
    }
    
    @Test
    @DisplayName("jameszsi:test bowtie nearestneighbor")
    public void testPlaces1(){
        if (nearestNeighbor1 == null) {
            addPlacesToPlaces1();
        }
         Places place1result = nearestNeighbor1.findBestNearestNeighborTour();
        if (place1result == null) {
        fail("place1result is null");
        }
        assertEquals(places1.get(0), place1result.get(0));
        assertEquals(places1.get(3), place1result.get(1));
        assertEquals(places1.get(1), place1result.get(2));
        assertEquals(places1.get(2), place1result.get(3));
    }

    @Test
    @DisplayName("lexchiar: test with pentagon shape")
    public void testPentagon(){

        Places places = new Places();
        places.add(new Place("58.68","34.98"));
        places.add(new Place("56.32","47.90"));
        places.add(new Place("55.88","36.74"));
        places.add(new Place("59.00","43.51"));
        places.add(new Place("54.32","42.63"));

        NearestNeighbor nn = new NearestNeighbor(places, earthRadius);

        Places optimizedPlaces = nn.findBestNearestNeighborTour();

        assertEquals(places.get(0), optimizedPlaces.get(0));
        assertEquals(places.get(2), optimizedPlaces.get(1));
        assertEquals(places.get(4), optimizedPlaces.get(2));
        assertEquals(places.get(1), optimizedPlaces.get(3));
        assertEquals(places.get(3), optimizedPlaces.get(4));
    }

    @Test
    @DisplayName("jameszsi:test Star nearestneighbor")
    public void testStars(){
        Places stars = new Places();
        stars.add(new Place("65.23", "98.61" ));
        stars.add(new Place("56.19", "90.0" ));
        stars.add(new Place("56.0", "104.59" ));
        stars.add(new Place( "61.95", "104.24" ));
        stars.add(new Place( "61.87", "89.30" ));
        NearestNeighbor nn = new NearestNeighbor(stars, earthRadius);
        Places starResult = nn.findBestNearestNeighborTour();
        assertEquals(stars.get(0), starResult.get(0));
        assertEquals(stars.get(3), starResult.get(1));
        assertEquals(stars.get(2), starResult.get(2));
        assertEquals(stars.get(1), starResult.get(3));
        assertEquals(stars.get(4), starResult.get(4));
    }
    
}
