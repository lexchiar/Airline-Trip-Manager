package com.tco.requests;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import java.lang.Math.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
public class TestFindRequest {

    FindRequest find;

    @BeforeEach
    public void createConfigurationForTestCases() {
        find = new FindRequest();
        find.setLimit(13);
        find.setMatch("heliport");
        find.buildResponse();
    }

    @Test
    @DisplayName("erivens: Request type is \"find\"")
    public void testType() {
        String type = find.getRequestType();
        assertEquals("find", type);
    }

    @Test
    @DisplayName("erivens: Verify match is correct")
    public void testMatch() {
        String match = find.getMatch();
        assertEquals("heliport", match);
    }

    @Test
    @DisplayName("erivens: Verify limit is correct")
    public void testLimit() {
        int limit = find.getLimit();
        assertEquals(13, limit);
    }

}

