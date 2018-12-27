package org.guy.rpg.dwg.maps;

import java.util.List;

import org.guy.rpg.dwg.csv.CSVManager;
import org.guy.rpg.dwg.models.Landmark;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Helper for OpenLayers 3 map.
 * 
 * @author Guy
 */
@Component
public class MapManager {

	private static final String CSV_FILE_NAME = "/properties/map/landmarks.csv";

	@Autowired
	CSVManager csvManager;
	
	/**
	 * Reads CSV of landmarks and returns a list of Landmark POJOs.
	 */
	public List<Landmark> getLandmarks() {
		return csvManager.getCSVAsListOfPOJOs(Landmark.class, CSV_FILE_NAME, "name", "description", "longitude", "latitude");
	}

}
