package com.tco.misc;

import org.junit.jupiter.api.Test;
import static java.lang.Math.toRadians;
import static com.tco.misc.DistanceCalculator.calculate;
import static java.lang.Math.toRadians;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.DisplayName;

public class TestDistanceCalculator {

    static class Geo implements GeographicCoordinate {
        private Double degreesLatitude;
        private Double degreesLongitude;

        public Geo(Double lat, Double lon) {
            this.degreesLatitude = lat;
            this.degreesLongitude = lon;
        }

        public Double latRadians() {
            return toRadians(degreesLatitude);
        }

        public Double lonRadians() {
            return toRadians(degreesLongitude);
        }
    }

    final Geo origin = new Geo(0.,0.);
    final Geo e10 = new Geo(0.,10.);
    final Geo w10 = new Geo(0.,-10.);
    final Geo neCorner = new Geo(90., 180.);
    final Geo seCorner = new Geo(-90., 180.);
    final Geo nwCorner = new Geo(90., -180.);
    final Geo swCorner = new Geo(-90., -180.);
    final Geo farEast = new Geo(0., 180.);
    final Geo farWest = new Geo(0., -180.);

    final static long small = 1L;
    final static long piSmall = Math.round(Math.PI * small);
    final static long piHalfSmall = Math.round(Math.PI/2.0 * small);
    final static long big = Long.MAX_VALUE;
    final static long piBig = Math.round(Math.PI * big);
    final static long piHalfBig = Math.round(Math.PI / 2.0 * big);

    @Test
    @DisplayName("dswan: distance to two different places with small radius")
    public void testDistanceSmallRadius() {
        assertEquals(2L /* Should be 2*/, calculate(origin, neCorner, small));
    }

    @Test
    @DisplayName("dswan: distance between close points with small radius")
    public void testDistanceClosePointsSmallRadius() {
        assertEquals(0L /* Should be 0*/, calculate(e10, origin, small));
    }
    
    @Test
    @DisplayName("dswan: distance across globe with small radius")
    public void testDistanceMaxSmallRadius() {
        assertEquals(3L /* Should be 3*/, calculate(neCorner, seCorner, small));
    }

    @Test
    @DisplayName("jameszsi: distance at the same point with small radius")
    public void testDistanceSamePointSmallRadius() {
        assertEquals(0L /* Should be 0*/, calculate(e10, e10, small));
    }

    @Test
    @DisplayName("jameszsi: distance at the same point with large radius")
    public void testDistanceSamePointLargeRadius() {
        assertEquals(0L /* Should be 0*/, calculate(e10, e10, big));
    }


    @Test
    @DisplayName("erivens: distance to extremes of Longitude")
    public void testDistanceMaxLongitudes() {
        assertEquals(0L /* Should be 0*/, calculate(farWest, farEast, small));
        //assertEquals(0L /* Should be 0*/, calculate(farEast, farWest, big));
    }
    
}