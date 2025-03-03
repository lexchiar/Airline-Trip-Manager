
package com.tco.requests;
import java.util.HashMap;
import java.lang.Math;
import com.tco.misc.GeographicCoordinate;

public class Place extends HashMap<String,String> implements GeographicCoordinate {
    
    //for testing purposes

    public Place(String lat, String lon){
        this.put("latitude", lat);
        this.put("longitude", lon);
    }

    public Place() {}

    public Double latRadians(){
        return Math.toRadians(Double.parseDouble(this.get("latitude")));
    }

    public Double lonRadians(){
        return Math.toRadians(Double.parseDouble(this.get("longitude")));
    }

    public String id(){
        return this.get("id");
    }

}

