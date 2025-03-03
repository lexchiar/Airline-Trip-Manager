package com.tco.requests;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.lang.Math;

import static java.lang.Math.PI;
import static org.junit.jupiter.api.Assertions.*;


public class TestPlace {
    
    @Test
    @DisplayName("andrewb3")   //credit goes to Dave for this test
    public void testLatLong(){
        Place place = new Place("0", "0");
        assertEquals(0.0, place.latRadians());
        assertEquals(0.0, place.lonRadians());
    }

    @Test
    @DisplayName("andrewb3")   
    public void testLatNegative(){
        Place place = new Place("0", "-180");
        assertEquals(0.0, place.latRadians());
        assertEquals(-PI, place.lonRadians());
    }

    @Test
    @DisplayName("andrewb3")   
    public void testLonNegative(){
        Place place = new Place("-90", "0");
        assertEquals(-PI/2.0, place.latRadians());
        assertEquals(0.0, place.lonRadians());
    }

    @Test
    @DisplayName("lexchiar")
    public void testLatPositive(){
        Place place = new Place("90","0");
        assertEquals(PI/2.0, place.latRadians());
        assertEquals(0.0, place.lonRadians());
    }
}
