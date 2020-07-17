package miniProject;

import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PFont;
import processing.core.PGraphics;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.commons.csv.*;
import org.json.*;

import de.fhpotsdam.unfolding.UnfoldingMap;
import de.fhpotsdam.unfolding.geo.Location;
import de.fhpotsdam.unfolding.marker.SimplePointMarker;
import de.fhpotsdam.unfolding.providers.Google;
import de.fhpotsdam.unfolding.providers.MBTilesMapProvider;
import de.fhpotsdam.unfolding.utils.MapUtils;

public class CovidCases extends PApplet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6186747229582491432L;

	private HashMap<String, SimplePointMarker> countries;
	private HashMap<String, String> country;
	private ArrayList<CovidData> covidData;
	private int uBtnX = 0;
	private int uBtnY = 0;
	private int uBtnW = 100;
	private int uBtnH = 40;
	private int totalCases=-1;
	private int totalDeaths=-1;
	private boolean uBtnActive = false;
	private boolean markerHovered = false;
	private boolean markerClicked = false;
	private int textColor;
	private String selectedCountry;
	private ArrayList<CovidData> countryDataArray;
	// must be false if working offline and true if working online
	private boolean isOnline = true;
	public String mbTilesString = "blankLight-1-3.mbtiles";
	private UnfoldingMap map;

	public void setup() {
		size(1200, 800);
		File dataFile = new File("covidData1.txt");
		if (isOnline) {
			map = new UnfoldingMap(this, 0, 0, 800, 800, new Google.GoogleMapProvider());
			if (!dataFile.exists()) {
				updateCovidDataFile();
			}
			textColor=color(255,255,255);
		} else {
			map = new UnfoldingMap(this, 0, 0, 800, 800, new MBTilesMapProvider(mbTilesString));
			map.setBackgroundColor(color(255, 255, 255));
			if (!dataFile.exists()) {
				System.out.println("Data file not found. Go online to download file");
				System.exit(0);
			}
			textColor=color(0,0,0);
		}
		getCovidDataFromFile();
		createCountryMarker();
		//MapUtils.createDefaultEventDispatcher(this, map);
	}

	public void draw() {
		map.draw();
		uBtnDraw();
		markerHovered();
		if(markerClicked) {
			afterClickDraw();
		}
	}

	private void afterClickDraw() {
		fill(textColor);
		int currPos=30;
		String infoStr="INFORMATION";
		text(infoStr,(float) (1050-infoStr.length()*2.5),0,300,currPos);
		if(countryDataArray==null) {
			countryDataArray=getCovidDataForCountry(14);
		}
		currPos+=20;
		text("Total Cases:"+totalCases,950,currPos);
		currPos+=20;
		text("Total Deaths:"+totalDeaths,950,currPos);
		currPos+=40;
		String dayString="Latest Cases/Deaths Records";
		text(dayString,(float) (1050-dayString.length()*2.5),currPos);
		currPos+=30;
		for(CovidData currData:countryDataArray) {
			text(currData.toString(),950,currPos);
			currPos+=40;
		}
	}
	
	private void markerHovered() {
		markerHovered = false;
		for (String geoId : countries.keySet()) {
			SimplePointMarker marker = countries.get(geoId);
			if (marker.isInside(map, mouseX, mouseY) && markerHovered == false && markerClicked == false) {
				CovidData currData = getCountryLatestCovidData(geoId);
				String displayString;
				String casesString;
				String countryString;
				String dateString;
				String countryName=country.get(geoId);
				if(countryName!=null) {
					countryString = "Country:" +countryName;
				}
				else {
					countryString="GeoId:"+geoId;
				}
				if (currData != null) {
					int currCases = currData.getCases();
					int currDeaths = currData.getDeaths();
					int currDay=currData.getDay();
					int currMonth=currData.getMonth();
					int currYear=currData.getYear();
					casesString = "Cases/Deaths:" + currCases + "/" + currDeaths;
					dateString=currYear+"-"+currMonth+"-"+currDay;
				} else {
					casesString = "Cases/Deaths:NA/NA";
					dateString="____-__-__";
				}
				displayString = countryString+"\nDate:"+dateString + "\n" + casesString;
				float rectW = (float) (displayString.length() * 5.5);
				fill(255, 255, 0);
				rect(mouseX, mouseY - 16, rectW, 50);
				fill(0, 0, 0);
				text(displayString, mouseX + 10, mouseY);
				markerHovered = true;
			}
			else if (marker.isInside(map, mouseX, mouseY) && markerHovered == false && markerClicked == true && geoId.contentEquals(selectedCountry)) {
				String displayString;
				String casesString;
				String countryString;
				String countryName=country.get(geoId);
				if(countryName!=null) {
					countryString = "Country:" +countryName;
				}
				else {
					countryString="GeoId:"+geoId;
				}
				if(totalCases==-1 && totalDeaths==-1) {
					getTotalCasesAndDeaths(selectedCountry);
				}
				casesString = "Total Cases/Deaths:" + totalCases + "/" + totalDeaths;
				displayString = countryString + "\n" + casesString;
				float rectW = (float) (displayString.length() * 5.5);
				fill(255, 255, 0);
				rect(mouseX, mouseY - 16, rectW, 40);
				fill(0, 0, 0);
				text(displayString, mouseX + 10, mouseY);
				markerHovered = true;
			}
		}
	}

	private void getTotalCasesAndDeaths(String geoId) {
		int casesSum=0;
		int deathsSum=0;
		for(CovidData currData:covidData) {
			if(currData.getGeoId().equals(geoId)) {
				casesSum+=currData.getCases();
				deathsSum+=currData.getDeaths();
			}
		}
			totalCases=casesSum;
			totalDeaths=deathsSum;
	}
	
	private void markerClicked() {
		if (markerClicked == false) {
			for (String geoId : countries.keySet()) {
				SimplePointMarker marker = countries.get(geoId);
				if (marker.isInside(map, mouseX, mouseY)) {
					markerClicked = true;
				} 
			}
			for (String geoId : countries.keySet()) {
				SimplePointMarker marker = countries.get(geoId);
				if (marker.isInside(map, mouseX, mouseY)) {
					marker.setHidden(false);
					selectedCountry=geoId;
				} 
				else if(markerClicked){
					marker.setHidden(true);
				}
			}
		}
		else {
			for (String geoId : countries.keySet()) {
				SimplePointMarker marker = countries.get(geoId);
				marker.setHidden(false);
				markerClicked = false;
				totalCases=-1;
				totalDeaths=-1;
				countryDataArray=null;
			}
		}
	}

	private CovidData getCountryLatestCovidData(String geoId) {
		for (CovidData data : covidData) {
			String currGeoId = data.getGeoId();
			if (currGeoId.equals(geoId)) {
				return data;
			}
		}
		return (null);
	}

	public void mouseClicked() {
		uBtnClicked();
		markerClicked();
	}

	// function to define how update button should be drawn
	private void uBtnDraw() {
		if (mouseX >= uBtnX && mouseY >= uBtnY && mouseX <= uBtnX + uBtnW / 10 && mouseY <= uBtnY + uBtnH) {
			fill(0, 0, 150);
			rect(uBtnX, uBtnY, uBtnW, uBtnH);
			fill(255, 255, 255);
			text("UPDATE DATA", uBtnX + uBtnW / 10, uBtnY + uBtnH / 2);
			uBtnActive = true;
		} else if (mouseX >= uBtnX && mouseY >= uBtnY && mouseX <= uBtnX + uBtnW && mouseY <= uBtnY + uBtnH
				&& uBtnActive) {
			fill(0, 0, 150);
			rect(uBtnX, uBtnY, uBtnW, uBtnH);
			fill(255, 255, 255);
			text("UPDATE DATA", uBtnX + uBtnW / 10, uBtnY + uBtnH / 2);
		} else {
			fill(150, 150, 150);
			rect(uBtnX, uBtnY, uBtnW / 10, uBtnH);
			uBtnActive = false;
		}
	}

	// function to define how update button behaves when clicked
	private void uBtnClicked() {
		if (mouseX >= uBtnX && mouseY >= uBtnY && mouseX <= uBtnX + uBtnW && mouseY <= uBtnY + uBtnH && uBtnActive) {
			if (isOnline) {
				updateCovidDataFile();
				getCovidDataFromFile();
			} else {
				System.out.println("You need internet connection to update data file.");
			}
		}
	}

	private void createCountryMarker() {
		countries = new HashMap<>();
		country=new HashMap<>();
		ArrayList<String> availableCountries=new ArrayList<>();
		for(CovidData data:covidData) {
			String geoId=data.getGeoId();
			if(!availableCountries.contains(geoId)) {
				availableCountries.add(geoId);
			}
		}
		FileReader locationFile;
		try {
			locationFile = new FileReader(new File("countriesLocation.csv"));
			CSVParser parser = new CSVParser(locationFile, CSVFormat.DEFAULT);
			int i = 0;
			for (CSVRecord countryData : parser) {
				// skip header/first row or rows that do not contain location info
				if (i == 0 || countryData.get(1).isEmpty() || countryData.get(2).isEmpty()) {
					i++;
					continue;
				}
				float lat = Float.parseFloat(countryData.get(1));
				float lon = Float.parseFloat(countryData.get(2));
				String id = countryData.get(0);
				String countryName=countryData.get(3);
				Location countryLocation = new Location(lat, lon);
				SimplePointMarker countryMarker = new SimplePointMarker(countryLocation);
				countryMarker.setRadius(5);
				countryMarker.setColor(color(255, 100, 100));
				if(availableCountries.contains(id)) {
					map.addMarker(countryMarker);
					countries.put(id, countryMarker);
					country.put(id,countryName);
				}
			}
			parser.close();
		} catch (FileNotFoundException e) {
			System.out.println("FileNotFound Error");
			System.exit(0);
		} catch (IOException e) {
			System.out.println("IO Error");
			System.exit(0);
		}
	}

	private void updateCovidDataFile() {
		StringBuilder data = new StringBuilder("");
		try {
			URL url = new URL("https://opendata.ecdc.europa.eu/covid19/casedistribution/json/");
			URLConnection connection = url.openConnection();
			InputStreamReader stream = new InputStreamReader(connection.getInputStream());
			BufferedReader br = new BufferedReader(stream);
			String line;
			System.out.println("Collecting data from URL...Plase wait");
			// read data from above URL to store it in a StringBuilder instance
			while ((line = br.readLine()) != null) {
				data.append(line);
			}
			System.out.println("Data collected successfully from URL");
		} catch (IOException e) {
			System.out.println("Connection Error");
			System.exit(0);
		}
		File covidFile = new File("covidData1.txt");
		// delete covidData.txt if it already exist in system
		if (covidFile.exists()) {
			covidFile.delete();
		}
		// create new covidData.txt and store all the data from URL into the file
		try {
			covidFile.createNewFile();
			FileOutputStream fos = new FileOutputStream(covidFile);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			System.out.println("Writing data to file...Please wait");
			oos.writeObject(data.toString());
			System.out.println("Data stored in file ");
			oos.close();
		} catch (IOException e) {
			System.out.println("IOException occured");
			System.exit(0);
		}
	}

	private void getCovidDataFromFile() {
		File covidFile = new File("covidData1.txt");
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(covidFile)));
			StringBuilder data = new StringBuilder("");
			String line;
			System.out.println("Collecting data from file...This may take a while");
			while ((line = br.readLine()) != null) {
				data.append(line);
			}
			System.out.println("Data collected successfully from file");
			br.close();
			// check from where real JSON data starts in file
			int s = data.toString().indexOf("{");
			// get exact JSON data
			String realData = data.substring(s);
			// parse the JSON data to create ArrayList of CovidData
			parseCovidData(realData);
		} catch (FileNotFoundException e) {
			System.out.println("FileNotFound Error");
			System.exit(0);
		} catch (IOException e) {
			System.out.println("IO Error");
			System.exit(0);
		}
	}

	private void parseCovidData(String data) {
		// initialize ArrayList(covidData) to refer to an object of type ArrayList
		covidData = new ArrayList<>();
		// convert whole data to JSON object
		JSONObject JSONobj = new JSONObject(data);
		// extract records and store it in a JSON array
		JSONArray records = JSONobj.getJSONArray("records");
		// iterate over all records to create instances of CovidData
		for (int i = 0; i < records.length(); i++) {
			CovidData currData = new CovidData();
			try {
				JSONObject record = (JSONObject) records.get(i);
				int currDay = record.getInt("day");
				int currMonth = record.getInt("month");
				int currYear = record.getInt("year");
				String currCountry = record.getString("countriesAndTerritories");
				String currGeoId = record.getString("geoId");
				int currCases = record.getInt("cases");
				int currDeaths = record.getInt("deaths");

				currData.setCases(currCases);
				currData.setCountry(currCountry);
				currData.setDay(currDay);
				currData.setDeaths(currDeaths);
				currData.setGeoId(currGeoId);
				currData.setMonth(currMonth);
				currData.setYear(currYear);
				// add newly created instance of CovidData to ArrayList(covidData)
				covidData.add(currData);
			} catch (JSONException e) {
				System.out.println("Unable to create JSON object from record");
				System.exit(0);
			}
		}
	}

	private ArrayList<CovidData> getCovidDataForCountry(int days){
		ArrayList<CovidData> returnData=new ArrayList<>();
		for(CovidData currData:covidData) {
			if(currData.getGeoId().equals(selectedCountry)) {
				returnData.add(currData);
				days--;	
			}
			if(days<=0) {
				break;
			}
		}
		return returnData;
	}

}
