package com.tco.misc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.ResultSet;
import com.tco.misc.Credential;
import com.tco.requests.Place;
import com.tco.requests.Places;
import java.util.ArrayList;
import java.util.HashMap;
import java.lang.Exception;

public class Database {

    public final static String TABLE = "world";
    final static String COLUMNS = "id,name,municipality,region,country,continent,latitude,longitude";

    public static Integer found(String match, String[] where, String[] type) throws Exception {
			String sql = Select.found(match, where, type);
			try (
				// connect to the database and query
				Connection conn = DriverManager.getConnection(Credential.url(), Credential.USER, Credential.PASSWORD);
				Statement query = conn.createStatement();
				ResultSet results = query.executeQuery(sql)
			) {
				return count(results);
			} catch (Exception e) {
				throw e;
			}
		}

		private static Integer count(ResultSet results) throws Exception {
			if (results.next()) {
				return results.getInt("count");
			}
			throw new Exception("No count results in found query.");
		}


		public static Places places(String match, Integer limit, String[] where, String[] type) throws Exception {
			String sql      = Select.match(match, limit, where, type);
			String url      = Credential.url();
			String user     = Credential.USER;
			String password = Credential.PASSWORD;
			try (
				// connect to the database and query
				Connection conn    = DriverManager.getConnection(url, user, password);
				Statement  query   = conn.createStatement();
				ResultSet  results = query.executeQuery(sql)
			) {
				return convertQueryResultsToPlaces(results, COLUMNS);
			} catch (Exception e) {
				throw e;
			}
		}


		private static Places convertQueryResultsToPlaces(ResultSet results, String columns) throws Exception {
			int count = 0;
			String[] cols = columns.split(",");
			Places places = new Places();
			while (results.next()) {
				Place place = new Place();
				for (String col: cols) {
					place.put(col, results.getString(col));
				}
				place.put("index", String.format("%d",++count));
				places.add(place);
			}
			return places;
		}


	}
