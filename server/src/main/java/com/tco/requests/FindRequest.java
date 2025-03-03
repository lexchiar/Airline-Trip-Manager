package com.tco.requests;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import static com.tco.misc.Database.found;
import static com.tco.misc.Database.places;
import java.util.ArrayList;
import java.util.HashMap;

public class FindRequest extends Request{
    private static final transient Logger log = LoggerFactory.getLogger(FindRequest.class);
    
    private String match;
    private int limit;
    private String[] type;
    private String[] where;
    private Places places;
    private int found;

    public FindRequest(){
      this.requestType = "find";
    }

    @Override 
    public void buildResponse(){
        places = buildFoundList();
        log.trace("buildResponse -> {}", this);
    }

    private Places buildFoundList() {
        Places places = new Places();
        try {
            found = found(match, where, type);
            places = places(match, limit, where, type);
        } catch (Exception e) { }
        return places;
    }


    // TEST METHODS
    public String getMatch() {
        return this.match;
    }

    public void setMatch(String match) {
        this.match = match;
    }

    public int getLimit() {
        return this.limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }


 }
        
