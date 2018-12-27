package org.guy.rpg.dwg.csv;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.guy.rpg.dwg.models.KeyValuePair;
import org.guy.rpg.dwg.models.Tip;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.bean.ColumnPositionMappingStrategy;
import au.com.bytecode.opencsv.bean.CsvToBean;

/**
 * A helper for reading CSVs.
 * Used primarily for API.AI integration.
 * 
 * @author Guy
 */
@Component
public class CSVManager {

	private static final Logger LOGGER = LoggerFactory.getLogger(CSVManager.class);
	
	/**
	 * Reads a CSV with columns corresponding to POJO attributes.
	 * Returns a list of POJOs with attributes populated by CSV.
	 */
	public <T> List<T> getCSVAsListOfPOJOs(Class<T> resultClass, String fileName, String ... colNames) {
		List<T> resultList = new ArrayList<T>();
		CSVReader csvReader = null;

		try {
			InputStream is = CSVManager.class.getResourceAsStream(fileName); 
			csvReader = new CSVReader(new InputStreamReader(is), '|', '"', 0);

			ColumnPositionMappingStrategy<T> mappingStrategy = new ColumnPositionMappingStrategy<T>();
			mappingStrategy.setType(resultClass);

			mappingStrategy.setColumnMapping(colNames);

			CsvToBean<T> ctb = new CsvToBean<T>();
			resultList = ctb.parse(mappingStrategy, csvReader);
		} catch (Exception ex) {
			LOGGER.error(ex.getMessage());
			ex.printStackTrace();
		} finally {
			try {
				csvReader.close();
			} catch (Exception ex) {
				LOGGER.error(ex.getMessage());
			}
		}

		return resultList;
	}
	
	/**
	 * Reads a two-column CSV (key / value) and returns a populated map.
	 */
	public Map<String, String> getKeyValuePairsFromCSVAsMap(String fileName) {
		List<KeyValuePair> list = getCSVAsListOfPOJOs(KeyValuePair.class, fileName, "key", "value");
		return list.stream().collect(Collectors.toMap( x -> x.getKey(), x -> x.getValue()));
	}
	
	public List<Tip> getTipsByKey(String key, String fileName) {
		List<Tip> tips = getCSVAsListOfPOJOs(Tip.class, fileName, "key", "traitName", "traitDescription");
		Stream<Tip> stream = tips.stream().filter(tip -> tip.getKey().toLowerCase().equals(key.toLowerCase()));
		return stream.collect(Collectors.toList());
	}
		
}
