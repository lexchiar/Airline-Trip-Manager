import { useState, useEffect } from 'react';
import { LOG } from '../utils/constants';
import { sendAPIRequest } from '../utils/restfulAPI';

export function useFind(match, limit, serverURL){
    //const[match, setMatch] = useState("");
    //const[limit, setLimit] = useState(5);
    const[places, setPlaces] = useState([]);

    const find = {
        places: places,
    }

    const findActions = {
        setPlaces: setPlaces,
        random: async () => makeFindRequest("",limit,serverURL,findActions),
    }

    useEffect(() => {makeRequest(match, limit, serverURL, findActions);},
    [match,limit])

    return {find,findActions};
}

function makeRequest(match, limit, serverURL, findActions) {
    if (match) {
        makeFindRequest(match, limit, serverURL, findActions);
    } else {
        const {setPlaces} = findActions;
        setPlaces([]);
    }
}

async function makeFindRequest(match, limit, serverURL, findActions){

    const {setPlaces} = findActions;


    const requestBody = {requestType: "find", match: match, limit: limit};
    const findResponse = await sendAPIRequest(requestBody, serverURL);

    if(findResponse){
        setPlaces(findResponse.places);
    }else{
        LOG.error(`Find request to ${serverURL} failed. Check the log for more details.`, "error");
    }
}