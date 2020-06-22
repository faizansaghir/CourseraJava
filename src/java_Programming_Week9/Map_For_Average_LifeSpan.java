package java_Programming_Week9;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import de.fhpotsdam.unfolding.UnfoldingMap;
import de.fhpotsdam.unfolding.data.Feature;
import de.fhpotsdam.unfolding.data.GeoJSONReader;
import de.fhpotsdam.unfolding.marker.Marker;
import de.fhpotsdam.unfolding.providers.Google;
import de.fhpotsdam.unfolding.utils.MapUtils;
import edu.duke.FileResource;
import processing.core.PApplet;

public class Map_For_Average_LifeSpan extends PApplet{
	UnfoldingMap map;
	Map<String,Float> countryLifeSpan;
	List<Marker> countryMarker;
	List<Feature> countryFeature;
	public void setup() {
		size(800,600);
		map=new UnfoldingMap(this,50,50,700,500,new Google.GoogleMapProvider());
		MapUtils.createDefaultEventDispatcher(this, map);
		countryLifeSpan=populateMap();
		countryFeature=GeoJSONReader.loadData(this,"C:\\Users\\FAIZAN\\CourseEraJava\\data\\countries.geo.json");
		countryMarker=MapUtils.createSimpleMarkers(countryFeature);
		map.addMarkers(countryMarker);
		shadeCountries();
	}
	public void draw() {
		map.draw();
	}
	private Map<String,Float> populateMap() {
		File f=new File("C:\\Users\\FAIZAN\\CourseEraJava\\data\\LifeExpectancyWorldBank.csv");
		Map<String,Float> build=new HashMap<String,Float>();
		try {
			BufferedReader br=new BufferedReader(new FileReader(f));
			CSVParser parser;
			parser = new CSVParser(br,CSVFormat.DEFAULT);
			for(CSVRecord record:parser) {
				String country =record.get(3);
				float avgLifeSpan;
				if(record.get(5).equals("..")) {
					avgLifeSpan=65;
				}
				else {
					avgLifeSpan=Float.parseFloat(record.get(5));
				}
				build.put(country, avgLifeSpan);
			}
		} catch (IOException e) {
			System.out.println(e);
		}
		return build;
	}
	private void shadeCountries() {
		for(Marker mark:countryMarker) {
			String countryID=mark.getId();
			if(countryLifeSpan.containsKey(countryID)) {
				float avgLifeSpan=countryLifeSpan.get(countryID);
				int colorLevel=(int)map(avgLifeSpan,40,90,10,255);
				mark.setColor(color(255-colorLevel,100,colorLevel));
			}
			else {
				mark.setColor(color(150,150,150));
			}
		}
	}
}