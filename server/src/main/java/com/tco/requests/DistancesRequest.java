package com.tco.requests;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import static com.tco.misc.DistanceCalculator.calculate;

 public class DistancesRequest extends Request {
    private static final transient Logger log = LoggerFactory.getLogger(DistancesRequest.class);

    public Double earthRadius;
    private Places places;
    private Distances distances;

    @Override 
    public void buildResponse(){
        distances = buildDistanceList();
        log.trace("buildResponse -> {}", this);
    }

    private Distances buildDistanceList(){
        Distances distances = new Distances();
        if(places.size() == 1){
			distances.add(0L);
		}
		else if(places.size() >= 2){
			for(int i = 0; i < places.size()-1; ++i){
				distances.add(calculate(places.get(i), places.get(i+1), earthRadius));
			}
			distances.add( calculate(places.get(places.size()-1), places.get(0), earthRadius));
		}
        return distances;
    }

    // Test Methods only
    public DistancesRequest(double earthRadius, Places places){
        super();
        this.requestType = "distances";
        this.earthRadius = earthRadius;
        this.places = places;
    }

    public double earthRadius() {return this.earthRadius;}
    public Places places() {return this.places;}
    public Distances distances() {return this.distances;}
 }