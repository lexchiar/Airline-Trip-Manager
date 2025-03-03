import { useState, useEffect } from 'react';
import { LOG } from '../utils/constants';
import { sendAPIRequest } from '../utils/restfulAPI';
import { Place } from '../models/place.model';


export function useTour(earthRadius, places, {setPlaces}, response, serverURL){
    const[optimizedTour, setOptimizedTour] = useState([]);
    const[originalPlaces, setOriginalPlaces] = useState([]);

    const tour = {
        originalPlaces:originalPlaces,
        optimizedTour:optimizedTour
    }

    const tourActions = {
        setOptimizedTour: setOptimizedTour,
        setOriginalPlaces: setOriginalPlaces,
        optimize: async () => makeTourRequest(earthRadius,response,places,{setPlaces},serverURL)
        
      }

      useEffect(() => {makeTourRequest(earthRadius, response, places, {setPlaces}, serverURL);},
    [])

    return {tour,tourActions};

}

async function makeTourRequest(earthRadius, response, places, {setPlaces}, serverURL){

    //const {setOptimizedTour} = setPlaces;

    const requestBody = {requestType: "tour", earthRadius: earthRadius, response: response, places: places};
    const tourResponse = await sendAPIRequest(requestBody, serverURL);

    if(tourResponse){
        let tempPlaces = []
        tourResponse.places.forEach( place =>{
            const nextPlace = new Place(place);
            tempPlaces.push(nextPlace);
    })
    setPlaces(tempPlaces);
    }else{
        LOG.error(`Tour request to ${serverURL} failed. Check the log for more details.`, "error");
    }

}