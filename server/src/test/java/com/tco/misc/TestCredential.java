package com.tco.misc;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.DisplayName;

public class TestCredential {
    
    public void resetUseTunnelVariable() {
        Credential.useTunnel = System.getenv("CS314_USE_DATABASE_TUNNEL");
    }

    @Test
    @DisplayName("dswan: check correct database username")
    public void testUsername() {
        assertEquals("cs314-db", Credential.USER);
    }

    @Test
    @DisplayName("dswan: check correct database password")
    public void testPassword() {
        assertEquals("eiK5liet1uej", Credential.PASSWORD);
    }

    @Test
    @DisplayName("dswan: check tunnel database url")
    public void testTunnelUrl() {
        Credential.useTunnel = "true";
        assertEquals("jdbc:mariadb://127.0.0.1:56247/cs314", Credential.url());
        resetUseTunnelVariable();
    }

    @Test
    @DisplayName("dswan: check database url when useTunnel is false")
    public void testFalseTunnelUrl() {
        Credential.useTunnel = "false";
        assertEquals("jdbc:mariadb://faure.cs.colostate.edu/cs314", Credential.url());
        resetUseTunnelVariable();
    }

    @Test
    @DisplayName("dswan: check databse url when useTunnel is null")
    public void testNullTunnelUrl() {
        Credential.useTunnel = null;
        assertEquals("jdbc:mariadb://faure.cs.colostate.edu/cs314", Credential.url());
        resetUseTunnelVariable();
    }
}
