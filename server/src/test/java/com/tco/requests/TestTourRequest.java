package com.tco.requests;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import java.lang.Math.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestTourRequest {
    TourRequest tour;

    @BeforeEach
    public void createConfigurationForTestCases() {
        Places places = new Places();
        Place place1 = new Place("51.75","48.63330078125" );
        Place place2 = new Place("35.0442008972168", "-85.25830078125");
        Place place3 = new Place("54.604698181152344","18.443099975585938");
        Place place4 = new Place("35.84980011","-97.41560364");
        places.add(place1);
        places.add(place2);
        places.add(place3);
        places.add(place4);
        tour = new TourRequest();
        tour.setEarthRadius(3958.8);
        tour.setResponse(1.0);
        tour.setPlaces(places);
        tour.buildResponse();
    }

    @Test
    @DisplayName("lexchiar: Request type is \"tour\"")
    public void testType() {
        String type = tour.getRequestType();
        assertEquals("tour", type);
    }

    @Test
    @DisplayName("erivens: Test getEarthRadius()")
    public void testEarthRadius() {
        double radius = tour.getEarthRadius();
        assertEquals(3958.8, radius);
    }

    @DisplayName("erivens: Test getResponse()")
    public void testGetResponse() {
        double response = tour.getResponse();
        assertEquals(1.0, response);
    }

    @DisplayName("erivens: Test getPlaces() size")
    public void testGetPlaces() {
        int placesLength = tour.getPlaces().size();
        assertEquals(4, placesLength);
    }

    @Test
    @DisplayName("erivens: Test setEarthRadius()")
    public void testSetEarthRadius() {
        tour.setEarthRadius(12.12);
        double radius = tour.getEarthRadius();
        assertEquals(12.12, radius);
    }

    @Test
    @DisplayName("erivens: Test setResponse()")
    public void testSetResponse() {
        tour.setResponse(4.0);
        double response = tour.getResponse();
        assertEquals(4.0, response);
    }

    @Test
    @DisplayName("erivens: Test setPlaces()")
    public void testSetPlaces() {
        Place place = new Place("0.0","0.0");
        Places testPlaces = new Places();
        testPlaces.add(place);
        tour.setPlaces(testPlaces);
        int placesLength = tour.getPlaces().size();
        assertEquals(1, placesLength);
    }
}