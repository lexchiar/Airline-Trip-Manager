package com.tco.misc;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.DisplayName;
public class TestDatabase {

    @Test
    @DisplayName("lexchiar: tests table value")
    public void testTable(){
        assertEquals("world", Database.TABLE);
    }

    @Test
    @DisplayName("andrewb3: tests columns value")
    public void testColumns(){
        assertEquals("id,name,municipality,region,country,continent,latitude,longitude", Database.COLUMNS);
    }
    

}
