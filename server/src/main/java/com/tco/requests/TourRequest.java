package com.tco.requests;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.ArrayList;
import java.util.Arrays;
import static com.tco.misc.DistanceCalculator.calculate;
import com.tco.requests.Distances;  
import com.tco.misc.NearestNeighbor;

public class TourRequest extends Request {

    private static final transient Logger log = LoggerFactory.getLogger(TourRequest.class);
    private Double earthRadius;
    private double response = 1.0; //placeholder
    private Places places;

    @Override
    public void buildResponse() {
        if (this.response == 0.0 || this.places.size() < 4) {
            return;
        }
        double predictedTimeInSeconds = predictTime(this.places.size());
        if (predictedTimeInSeconds > this.response) {
            return;
        }else{
            NearestNeighbor tour = new NearestNeighbor(this.places, this.earthRadius);
            this.places = tour.findBestNearestNeighborTour();
        }
        log.trace("buildResponse -> {}", this);
    }

    
     /* Methods for testing purposes only. */
    public TourRequest() {
        this.requestType = "tour";
    }

    public void setEarthRadius(Double earthRadius){
        this.earthRadius = earthRadius;
    }

    public Double getEarthRadius(){
        return this.earthRadius;
    }

    public void setResponse(double response){
        this.response = response;
    }

    public double getResponse(){
        return this.response;
    }

    public void setPlaces(Places places){
        this.places = places;
    }

    public Places getPlaces(){
        return this.places;
    }

    public double predictTime(int numOfPlaces) {
        // 0.003x^2 - 0.5x + 100
        double predictedMilliseconds = (0.003*numOfPlaces*numOfPlaces) - (0.5*numOfPlaces) + 100;
        return predictedMilliseconds / 1000.0;
    }

}




