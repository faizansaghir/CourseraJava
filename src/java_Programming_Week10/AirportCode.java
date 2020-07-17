package java_Programming_Week10;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collections;

public class AirportCode {
	public void getData(Airport[] airports) {
		//code to store data in given array
	}
	
	public void selectionSortData(Airport[] airports) {
		for(int i=0;i<airports.length-1;i++) {
			String small=null;
			int smallIndex=i;
			for(int j=i;j<airports.length;j++) {
				if(small==null) {
					small=airports[j].getCity();
					smallIndex=j;
				}
				else if(small.compareTo(airports[j].getCity())>0) {
					small=airports[j].getCity();
					smallIndex=j;
				}
			}
			Airport temp=airports[i];
			airports[i]=airports[smallIndex];
			airports[smallIndex]=temp;
		}
	}
	
	public void insertionSortData(Airport[] airports) {
		for(int pos=1;pos<airports.length;pos++) {
			int currPos=pos;
			while(currPos>0 && airports[currPos].getCity().compareTo(airports[currPos-1].getCity())<0) {
				Airport temp=airports[currPos];
				airports[currPos]=airports[currPos-1];
				airports[currPos-1]=temp;
				currPos--;
			}
		}
	}
	
	public String searchInListLinear(Airport[] airports,String searchCity) {
		String searchResult="Not Found";
		for(Airport currPort:airports) {
			String currCity=currPort.getCity();
			if(currCity.equals(searchCity)) {
				searchResult=currPort.getCode();
				return(searchResult);
			}
		}
		return searchResult;
	}
	
	public String searchInListBinary(Airport[] airports,String searchCity) {
		String searchResult="Not Found";
		int low=0,high=airports.length-1;
		while(low<=high) {
			int mid=low+(high-low)/2;
			int compare=searchCity.compareTo(airports[mid].getCity());
			if(compare==0) {
				return(airports[mid].getCode());
			}
			else if(compare>0){
				low=mid+1;
			}
			else {
				high=mid-1;
			}
		}
		return searchResult;
	}
	
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
		Airport[] airports = null;
		AirportCode obj=new AirportCode();
		obj.getData(airports);
		
		obj.selectionSortData(airports);
		obj.insertionSortData(airports);
		//Collections.sort(airportsList);// Merge sort used. It is faster than selection and insertion sort in most cases
		
		System.out.println("Enter city to be searched:");
		String searchCity=br.readLine();
		searchCity=Character.toUpperCase(searchCity.charAt(0))+searchCity.substring(1);
		System.out.println("Searching for "+searchCity);
		
		String codeLinear=obj.searchInListLinear(airports,searchCity);
		if(codeLinear.equals("Not Found")) {
			System.out.println("No airport found in "+searchCity);
		}
		else {
			System.out.println("Airport code in "+searchCity+" is "+codeLinear);
		}
		
		String codeBinary=obj.searchInListBinary(airports,searchCity);
		if(codeBinary.equals("Not Found")) {
			System.out.println("No airport found in "+searchCity);
		}
		else {
			System.out.println("Airport code in "+searchCity+" is "+codeBinary);
		}
	}

}
