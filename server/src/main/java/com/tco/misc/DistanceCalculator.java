
package com.tco.misc;

import com.tco.misc.GeographicCoordinate;
import java.lang.Math;

public final class DistanceCalculator {

    private DistanceCalculator() {}

    public static double absVal(double radian1, double radian2) { 
        return Math.abs(radian1-radian2); 
    }

    public static long arctan(double radius, double centralAngle){
         return Math.round(centralAngle * radius);
    }

    public static long calculate(GeographicCoordinate P1, GeographicCoordinate P2, double earthRadius){

        double absValLon = absVal(P1.lonRadians(), P2.lonRadians());
        
        double numeratorPart1 = Math.pow((Math.cos(P2.latRadians()) * Math.sin(absValLon)), 2);
        double numeratorPart2 = Math.cos(P1.latRadians()) * Math.sin(P2.latRadians());
        double numeratorPart3 = Math.sin(P1.latRadians()) * Math.cos(P2.latRadians()) * Math.cos(absValLon);  
        double denominatorPart0 = Math.sin(P1.latRadians()) * Math.sin(P2.latRadians());
        double denominatorPart1 = Math.cos(P1.latRadians()) * Math.cos(P2.latRadians()) * Math.cos(absValLon);
        double numeratorDifference = numeratorPart2 - numeratorPart3;
        double numeratorFinal = Math.sqrt(numeratorPart1 + Math.pow(numeratorDifference, 2));
        double denominatorFinal = denominatorPart0 + denominatorPart1;
  
        double centralAngle = Math.atan2(numeratorFinal, denominatorFinal);
  
        double distanceAsDouble = arctan(earthRadius, centralAngle);
        Long distance = (long)distanceAsDouble;

        return distance;
    }
}
