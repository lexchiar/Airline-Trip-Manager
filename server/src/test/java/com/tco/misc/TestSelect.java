package com.tco.misc;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.DisplayName;

public class TestSelect {
    
    @Test
    @DisplayName("dswan: check select table")
    public void testSelectTable() {
        assertEquals("world", Select.TABLE);
    }

    @Test
    @DisplayName("dswan: check select columns")
    public void testSelectColumns() {
        assertEquals("world.id, world.name, world.municipality, region.name as region, country.name as country, continent.name as continent, world.latitude, world.longitude",
                        Select.COLUMNS);
    }

    @Test
    @DisplayName("dswan: check match method")
    public void testMatch() {
        assertEquals("SELECT " + Select.COLUMNS
                    + " FROM " + Select.TABLE
                    + " INNER JOIN continent ON world.continent = continent.id"
                    + " INNER JOIN country ON world.iso_country = country.id"
                    + " INNER JOIN region ON world.iso_region = region.id"
                    + " WHERE (region.name LIKE \"%match%\""
                    + " OR world.municipality LIKE \"%match%\""
                    + " OR country.name LIKE \"%match%\""
                    + " OR continent.name LIKE \"%match%\""
                    + " OR world.name LIKE \"%match%\""
                    + " OR world.id LIKE \"%match%\")"
                    + " AND (true) AND (true)"
                    + " LIMIT 5;",
                        Select.match("match", 5, new String[0], new String[0]));
    }

    @Test
    @DisplayName("dswan: check found method")
    public void testFound() {
        assertEquals("SELECT COUNT(*) AS count"
                    + " FROM " + Select.TABLE
                    + " INNER JOIN continent ON world.continent = continent.id"
                    + " INNER JOIN country ON world.iso_country = country.id"
                    + " INNER JOIN region ON world.iso_region = region.id"
                    + " WHERE (region.name LIKE \"%match%\""
                    + " OR world.municipality LIKE \"%match%\""
                    + " OR country.name LIKE \"%match%\""
                    + " OR continent.name LIKE \"%match%\""
                    + " OR world.name LIKE \"%match%\""
                    + " OR world.id LIKE \"%match%\")"
                    + " AND (true) AND (true);",
                        Select.found("match", new String[0], new String[0]));
    }

    @Test
    @DisplayName("erivens: check select statment with empty arguments")
    public void testStatementEmptyArguments() {
        assertEquals("SELECT "
                    + " FROM " + Select.TABLE
                    + " INNER JOIN continent ON world.continent = continent.id"
                    + " INNER JOIN country ON world.iso_country = country.id"
                    + " INNER JOIN region ON world.iso_region = region.id"
                    + " WHERE (true) AND (true)"
                    + " ORDER BY RAND()"    ,
                    Select.statement("", "", new String[0], new String[0]));
    }

    @Test
    @DisplayName("erivens: check match method with 0 as limit")
    public void testZeroMatch() {
        assertEquals("SELECT " + Select.COLUMNS
                    + " FROM " + Select.TABLE
                    + " INNER JOIN continent ON world.continent = continent.id"
                    + " INNER JOIN country ON world.iso_country = country.id"
                    + " INNER JOIN region ON world.iso_region = region.id"
                    + " WHERE (region.name LIKE \"%match%\""
                    + " OR world.municipality LIKE \"%match%\""
                    + " OR country.name LIKE \"%match%\""
                    + " OR continent.name LIKE \"%match%\""
                    + " OR world.name LIKE \"%match%\""
                    + " OR world.id LIKE \"%match%\")"
                    + " AND (true) AND (true)"
                    + " LIMIT 100;",
                        Select.match("match", 0, new String[0], new String[0]));
    }

    @Test
    @DisplayName("erivens: test matchStatement('eric') method")
    public void testMatchStatementWithString() {
        assertEquals(" WHERE (region.name LIKE \"%eric%\""
                    + " OR world.municipality LIKE \"%eric%\""
                    + " OR country.name LIKE \"%eric%\""
                    + " OR continent.name LIKE \"%eric%\""
                    + " OR world.name LIKE \"%eric%\""
                    + " OR world.id LIKE \"%eric%\")"
                    + " AND (true) AND (true)",
                        Select.matchStatement("eric", new String[0], new String[0]));  
    }

    @Test
    @DisplayName("erivens: test empty matchStatement() method")
    public void testEmptyMatchStatement() {
        assertEquals(" WHERE (true) AND (true) ORDER BY RAND()", Select.matchStatement("", new String[0], new String[0]));
    }

    @Test
    @DisplayName("dswan: check select statement with some valid arguments")
    public void testStatementValidArguments() {
        assertEquals("SELECT data"
                    + " FROM " + Select.TABLE
                    + " INNER JOIN continent ON world.continent = continent.id"
                    + " INNER JOIN country ON world.iso_country = country.id"
                    + " INNER JOIN region ON world.iso_region = region.id"
                    + " WHERE (region.name LIKE \"%match%\""
                    + " OR world.municipality LIKE \"%match%\""
                    + " OR country.name LIKE \"%match%\""
                    + " OR continent.name LIKE \"%match%\""
                    + " OR world.name LIKE \"%match%\""
                    + " OR world.id LIKE \"%match%\")"
                    + " AND (true) AND (true)",
                        Select.statement("match", "data", new String[0], new String[0]));
    }

    @Test
    @DisplayName("dswan: test where request with 1 item in where array")
    public void testWhereWith1Where() {
        assertEquals("SELECT " + Select.COLUMNS
                    + " FROM " + Select.TABLE
                    + " INNER JOIN continent ON world.continent = continent.id"
                    + " INNER JOIN country ON world.iso_country = country.id"
                    + " INNER JOIN region ON world.iso_region = region.id"
                    + " WHERE (region.name LIKE \"%match%\""
                    + " OR world.municipality LIKE \"%match%\""
                    + " OR country.name LIKE \"%match%\""
                    + " OR continent.name LIKE \"%match%\""
                    + " OR world.name LIKE \"%match%\""
                    + " OR world.id LIKE \"%match%\""
                    + ") AND (region.name LIKE \"%where%\""
                    + " OR world.municipality LIKE \"%where%\""
                    + " OR country.name LIKE \"%where%\""
                    + " OR continent.name LIKE \"%where%\")"
                    + " AND (true)"
                    + " LIMIT 5;",
                        Select.match("match", 5, new String[]{"where"}, new String[0]));
    }

    @Test
    @DisplayName("dswan: test where request with 2 items in where array")
    public void testWhereWith2Wheres() {
        assertEquals("SELECT " + Select.COLUMNS
                    + " FROM " + Select.TABLE
                    + " INNER JOIN continent ON world.continent = continent.id"
                    + " INNER JOIN country ON world.iso_country = country.id"
                    + " INNER JOIN region ON world.iso_region = region.id"
                    + " WHERE (region.name LIKE \"%match%\""
                    + " OR world.municipality LIKE \"%match%\""
                    + " OR country.name LIKE \"%match%\""
                    + " OR continent.name LIKE \"%match%\""
                    + " OR world.name LIKE \"%match%\""
                    + " OR world.id LIKE \"%match%\""
                    + ") AND (region.name LIKE \"%where1%\""
                    + " OR world.municipality LIKE \"%where1%\""
                    + " OR country.name LIKE \"%where1%\""
                    + " OR continent.name LIKE \"%where1%\""
                    + " OR region.name LIKE \"%where2%\""
                    + " OR world.municipality LIKE \"%where2%\""
                    + " OR country.name LIKE \"%where2%\""
                    + " OR continent.name LIKE \"%where2%\")"
                    + " AND (true)"
                    + " LIMIT 5;",
                        Select.match("match", 5, new String[]{"where1", "where2"}, new String[0]));
    }

    @Test
    @DisplayName("dswan: test random where request")
    public void testRandomWhere() {
        assertEquals("SELECT " + Select.COLUMNS
                    + " FROM " + Select.TABLE
                    + " INNER JOIN continent ON world.continent = continent.id"
                    + " INNER JOIN country ON world.iso_country = country.id"
                    + " INNER JOIN region ON world.iso_region = region.id"
                    + " WHERE (region.name LIKE \"%where%\""
                    + " OR world.municipality LIKE \"%where%\""
                    + " OR country.name LIKE \"%where%\""
                    + " OR continent.name LIKE \"%where%\")"
                    + " AND (true)"
                    + " ORDER BY RAND()"
                    + " LIMIT 5;",
                        Select.match("", 5, new String[]{"where"}, new String[0]));
    }

    @Test
    @DisplayName("dswan: test type request with 1 item in type array")
    public void testTypeWith1Type() {
        assertEquals("SELECT " + Select.COLUMNS
                    + " FROM " + Select.TABLE
                    + " INNER JOIN continent ON world.continent = continent.id"
                    + " INNER JOIN country ON world.iso_country = country.id"
                    + " INNER JOIN region ON world.iso_region = region.id"
                    + " WHERE (region.name LIKE \"%match%\""
                    + " OR world.municipality LIKE \"%match%\""
                    + " OR country.name LIKE \"%match%\""
                    + " OR continent.name LIKE \"%match%\""
                    + " OR world.name LIKE \"%match%\""
                    + " OR world.id LIKE \"%match%\""
                    + ") AND (true)"
                    + " AND (world.type LIKE \"%type%\")"
                    + " LIMIT 5;",
                        Select.match("match", 5, new String[0], new String[]{"type"}));
    }
    
    @Test
    @DisplayName("dswan: test type request with 2 items in type array")
    public void testTypeWith2Types() {
        assertEquals("SELECT " + Select.COLUMNS
                    + " FROM " + Select.TABLE
                    + " INNER JOIN continent ON world.continent = continent.id"
                    + " INNER JOIN country ON world.iso_country = country.id"
                    + " INNER JOIN region ON world.iso_region = region.id"
                    + " WHERE (region.name LIKE \"%match%\""
                    + " OR world.municipality LIKE \"%match%\""
                    + " OR country.name LIKE \"%match%\""
                    + " OR continent.name LIKE \"%match%\""
                    + " OR world.name LIKE \"%match%\""
                    + " OR world.id LIKE \"%match%\""
                    + ") AND (true)"
                    + " AND (world.type LIKE \"%type1%\" OR world.type LIKE \"%type2%\")"
                    + " LIMIT 5;",
                        Select.match("match", 5, new String[0], new String[]{"type1", "type2"}));
    }

    @Test
    @DisplayName("dswan: test random type request")
    public void testRandomType() {
        assertEquals("SELECT " + Select.COLUMNS
                    + " FROM " + Select.TABLE
                    + " INNER JOIN continent ON world.continent = continent.id"
                    + " INNER JOIN country ON world.iso_country = country.id"
                    + " INNER JOIN region ON world.iso_region = region.id"
                    + " WHERE (true)"
                    + " AND (world.type LIKE \"%type%\")"
                    + " ORDER BY RAND()"
                    + " LIMIT 5;",
                        Select.match("", 5, new String[0], new String[]{"type"}));
    }

    @Test
    @DisplayName("dswan: test random other type request")
    public void testRandomOtherType() {
        assertEquals("SELECT " + Select.COLUMNS
                    + " FROM " + Select.TABLE
                    + " INNER JOIN continent ON world.continent = continent.id"
                    + " INNER JOIN country ON world.iso_country = country.id"
                    + " INNER JOIN region ON world.iso_region = region.id"
                    + " WHERE (true)"
                    + " AND (world.type LIKE \"%seaplane_base%\" OR world.type LIKE \"%closed%\")"
                    + " ORDER BY RAND()"
                    + " LIMIT 5;",
                        Select.match("", 5, new String[0], new String[]{"other"}));
    }

    @Test
    @DisplayName("dswan: test random where and type request")
    public void testRandomWhereType() {
        assertEquals("SELECT " + Select.COLUMNS
                    + " FROM " + Select.TABLE
                    + " INNER JOIN continent ON world.continent = continent.id"
                    + " INNER JOIN country ON world.iso_country = country.id"
                    + " INNER JOIN region ON world.iso_region = region.id"
                    + " WHERE (region.name LIKE \"%where%\""
                    + " OR world.municipality LIKE \"%where%\""
                    + " OR country.name LIKE \"%where%\""
                    + " OR continent.name LIKE \"%where%\")"
                    + " AND (world.type LIKE \"%type%\")"
                    + " ORDER BY RAND()"
                    + " LIMIT 5;",
                        Select.match("", 5, new String[]{"where"}, new String[]{"type"}));
    }

    @Test
    @DisplayName("andrewb3: test get full statement with null value")
    public void testgetFullStatement() {
        assertEquals(Select.getFullStatement(null, "type"), "true");

    }

    @Test
    @DisplayName("andrewb3: test get full statement with empty array")
    public void testgetFullStatementTwo() {
        assertEquals(Select.getFullStatement(new String[0], "type"), "true");

    }
    
    @DisplayName("dswan: test iterateArray with single type")
    public void testIterateArraySingleType() {
        assertEquals("world.type LIKE \"%type%\"",
                        Select.iterateArray(new String[]{"type"}, "type"));
    }

    @Test
    @DisplayName("dswan: test iterateArray with two types")
    public void testIterateArrayTwoType() {
        assertEquals("world.type LIKE \"%type1%\" OR world.type LIKE \"%type2%\"", 
                        Select.iterateArray(new String[]{"type1", "type2"}, "type"));
    }

    @Test
    @DisplayName("dswan: test iterateArray with single where")
    public void testIterateArraySingleWhere() {
        assertEquals("region.name LIKE \"%where1%\" OR world.municipality LIKE \"%where1%\" OR country.name LIKE \"%where1%\" OR continent.name LIKE \"%where1%\"",
                        Select.iterateArray(new String[]{"where1"}, "where"));
    }

    @Test
    @DisplayName("dswan: test iterateArray with two wheres")
    public void testIterateArrayTwoWhere() {
        assertEquals("region.name LIKE \"%where1%\" OR world.municipality LIKE \"%where1%\" OR country.name LIKE \"%where1%\" OR continent.name LIKE \"%where1%\" OR region.name LIKE \"%where2%\""
                    + " OR world.municipality LIKE \"%where2%\" OR country.name LIKE \"%where2%\" OR continent.name LIKE \"%where2%\"",
                        Select.iterateArray(new String[]{"where1", "where2"}, "where"));
    }

    @Test
    @DisplayName("dswan: test iterateArray with invalid type")
    public void testIterateArrayInvalidType() {
        assertEquals("",
                        Select.iterateArray(new String[]{"test"}, "invalid"));
    }

    @Test
    @DisplayName("dswan: test getIndividualStatement with where")
    public void testGetIndividualStatementWhereType() {
        assertEquals("region.name LIKE \"%where1%\" OR world.municipality LIKE \"%where1%\" OR country.name LIKE \"%where1%\" OR continent.name LIKE \"%where1%\"",
                        Select.getIndividualStatement("where1", "where"));
    }

    @Test
    @DisplayName("dswan: test getIndividualStatement with type")
    public void testGetIndividualStatementTypeType() {
        assertEquals("world.type LIKE \"%type%\"", 
                        Select.getIndividualStatement("type", "type"));
    }

    @Test
    @DisplayName("dswan: test getIndividualStatement with invalid")
    public void testGetIndividualStatementInvalidType() {
        assertEquals("", Select.getIndividualStatement("test", "invalid"));
    }
}
