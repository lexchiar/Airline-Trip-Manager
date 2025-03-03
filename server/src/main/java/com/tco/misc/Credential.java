package com.tco.misc;

public class Credential {
    final static String USER = "cs314-db";
    final static String PASSWORD = "eiK5liet1uej";
    static String useTunnel = System.getenv("CS314_USE_DATABASE_TUNNEL");

    public static String url() {
        if(useTunnel != null && useTunnel.equals("true")) {
            return "jdbc:mariadb://127.0.0.1:56247/cs314";
        } 
        else {
            return "jdbc:mariadb://faure.cs.colostate.edu/cs314";
        }
    }
}
