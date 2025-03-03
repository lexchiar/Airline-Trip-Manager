package com.tco.requests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class TestDistances {
    @Test
    @DisplayName("lexchiar:")//credit: Dave
    public void testNoDistances(){
        Distances distances = new Distances();
        assertEquals(0, distances.size());
        assertEquals(0L, distances.total());
    }

    @Test
    @DisplayName("lexchiar:")//credit Dave
    public void testOneDistances(){
        Distances distances = new Distances();
        distances.add(12345L);
        assertEquals(1, distances.size());
        assertEquals(12345L, distances.total());
    }

    @Test
    @DisplayName("lexchiar:")//credit Dave
    public void testDistances(){
        Distances distances = new Distances();
        distances.add(5L);
        distances.add(40L);
        distances.add(300L);
        distances.add(2000L);
        distances.add(10000L);
        assertEquals(5, distances.size());
        assertEquals(12345L, distances.total());
    }
    @Test
    @DisplayName("jameszsi:")
    public void testthreeDistances(){
        Distances distances = new Distances();
        distances.add(1L);
        distances.add(10L);
        distances.add(100L);
        assertEquals(3, distances.size());
        assertEquals(111L, distances.total());
    }
    @Test
    @DisplayName("lexchiar:")
    public void testZeroDistance(){
        Distances distances = new Distances();
        distances.add(0L);
        assertEquals(1, distances.size());
        assertEquals(0L, distances.total());
    }
}
