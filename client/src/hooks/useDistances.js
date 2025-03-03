import { useState, useEffect } from 'react';
import { LOG } from '../utils/constants';
import { sendAPIRequest } from '../utils/restfulAPI';

export function useDistances(places, earthRadius, serverURL) {
    const [leg, setLeg] = useState([]);
    const [cumulative, setCumulative] = useState([]);
    const [total, setTotal] = useState(0);
    const[radius,setRadius] = useState(3958.8);
    
    const distances = {
      leg: leg,
      cumulative: cumulative,
      total: total,
      radius: radius
    }
    
    const distanceActions = {
      setLeg: setLeg,
      setCumulative: setCumulative,
      setTotal: setTotal,
      setRadius:setRadius,
    }
    
    useEffect(() => {makeDistancesRequest(places, earthRadius, serverURL, distanceActions);},
              [places,earthRadius])
  
    return {distances, distanceActions};
}

async function makeDistancesRequest(places, earthRadius, serverURL, distanceActions) {
  
    const {setLeg, setCumulative, setTotal,setRadius} = distanceActions;
  
    const requestBody = { requestType: "distances", places: places, earthRadius: earthRadius };
    const distancesResponse = await sendAPIRequest(requestBody, serverURL);

    if (distancesResponse) {
        setLeg(distancesResponse.distances);
        setCumulative(calcCumulative(distancesResponse.distances))
        setTotal(calcTotal(distancesResponse.distances))
        setRadius(distanceResponse.distances);
    }
    else {
      LOG.error(`Distance request to ${serverURL} failed. Check the log for more details.`, "error");
    }
}

function calcCumulative(distances){
  let cumDistances = [];
  let sum = 0;
  let i;
  for (i = 0; i < distances.length; i++){
    sum += distances[i];
    cumDistances.push(sum);
  }
  return cumDistances;
}

function calcTotal(distances){
  let sum = 0;
  let i;
  for (i = 0; i < distances.length; i++){
    sum += distances[i];
  }
  return sum;
}