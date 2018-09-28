package com.bridgelabz.userservice;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Utility {
	public static String asJsonString(final Object obj) {
		try {
			final ObjectMapper mapper = new ObjectMapper();
			return mapper.writeValueAsString(obj);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * This function takes the file name with json and convert it to a Map
	 * 
	 * @param fileName with json
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, String> convertJSONToMap(String fileName) {
		try {
			// converting json to Map
			byte[] jsonData = Files.readAllBytes(Paths.get(fileName));
			ObjectMapper objectMapper = new ObjectMapper();
			// read JSON like DOM Parser
			JsonNode jsonNode = objectMapper.readTree(jsonData);

			// convert json string to object
			TestModels testModals = objectMapper.readValue(jsonData, TestModels.class);

			Map<String, String> myMap = new HashMap<String, String>();
			myMap = objectMapper.readValue(jsonData, HashMap.class);
			System.out.println("Map is: " + myMap);
			return myMap;
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new RuntimeException(ex.getMessage());
		}
	}

	/**
	 * This function takes the file name with json and convert it to a Object
	 * 
	 * @param <T> Actial POJO Class to be constructed
	 * @param fileName with json
	 * @return Object
	 */
	public static <T> T convertJSONToObject(String fileName, Class<T> objClass) {
		try {
			// converting json to Map
			byte[] jsonData = Files.readAllBytes(Paths.get(fileName));
			ObjectMapper objectMapper = new ObjectMapper();

			// convert json string to object
			return objectMapper.readValue(jsonData, objClass);
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new RuntimeException(ex.getMessage());
		}
	}
}
