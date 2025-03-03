package com.tco.misc;

public class Select {
    final static String TABLE = "world";
    final static String COLUMNS = "world.id, world.name, world.municipality, region.name as region, country.name as country, continent.name as continent, world.latitude, world.longitude";
    final static String[] otherTypes = {"seaplane_base", "closed"};
    
    static String match(String match, int limit, String[] where, String[] type) {
        if(limit == 0){
            limit = 100;
        }
        String limitString = " LIMIT " + limit + ";";
        return statement(match, COLUMNS, where, type) + limitString;
    }

    static String found(String match, String[] where, String[] type) {
        return statement(match, "COUNT(*) AS count", where, type) + ";";
    }

    static String statement(String match, String data, String[] where, String[] type) {
        return "SELECT " + data
                + " FROM " + TABLE
                + " INNER JOIN continent ON world.continent = continent.id"
                + " INNER JOIN country ON world.iso_country = country.id"
                + " INNER JOIN region ON world.iso_region = region.id"
                + matchStatement(match, where, type);
    }

    static String matchStatement(String match, String[] where, String[] type) {
        String statement = " WHERE (";
        if (match.equals("")) {
            statement += getFullWhereTypeStatement(where, type);
            statement += " ORDER BY RAND()";
        } else {
            statement += getIndividualStatement(match, "where")
                + " OR world.name LIKE \"%" + match + "%\""
                + " OR world.id LIKE \"%" + match + "%\")";
            statement += " AND (" + getFullWhereTypeStatement(where, type);
        }
        
        return statement;
    }

    static String getFullWhereTypeStatement(String[] where, String[] type) {
        String statement = getFullStatement(where, "where");
        statement += ") AND (" + getFullStatement(type, "type") + ")";
        return statement;
    }

    static String getFullStatement(String[] strings, String typeOfStatement) {
        if (strings != null && strings.length != 0) {
            return iterateArray(strings, typeOfStatement);
        }
        else {
            return "true";
        }
    }

    static String iterateArray(String[] strings, String typeOfStatement) {
        String statement = "";
        for (int i = 0; i < strings.length; i ++) {
            if (strings[i].equals("other")) {
                statement += iterateArray(otherTypes, "type");
            } else {
                statement += getIndividualStatement(strings[i], typeOfStatement);
            }
            if (i != strings.length-1) {
                statement += " OR ";
            }
        }
        return statement;
    }

    static String getIndividualStatement(String string, String typeOfStatement) {
        if (typeOfStatement.equals("where")) {
            return "region.name LIKE \"%" + string + "%\""
                + " OR world.municipality LIKE \"%" + string + "%\""
                + " OR country.name LIKE \"%" + string + "%\""
                + " OR continent.name LIKE \"%" + string + "%\"";
        }
        else if (typeOfStatement.equals("type")) {
            return "world.type LIKE \"%" + string + "%\"";
        }
        else {
            return "";
        }
    }
}
