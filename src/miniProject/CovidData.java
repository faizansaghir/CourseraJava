package miniProject;

public class CovidData {
	private int day;
	private	int month;
	private int year;
	private int cases;
	private int deaths;
	private String country;
	private String geoId;
	public int getDay() {
		return day;
	}
	public void setDay(int day) {
		this.day = day;
	}
	public int getMonth() {
		return month;
	}
	public void setMonth(int month) {
		this.month = month;
	}
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	public int getCases() {
		return cases;
	}
	public void setCases(int cases) {
		this.cases = cases;
	}
	public int getDeaths() {
		return deaths;
	}
	public void setDeaths(int deaths) {
		this.deaths = deaths;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getGeoId() {
		return geoId;
	}
	public void setGeoId(String geoId) {
		this.geoId = geoId;
	}
	public String toString() {
		String ret="Date:"+getYear()+"-"+getMonth()+"-"+getDay()+"\nCases\\Deaths:"+getCases()+"\\"+getDeaths();
		return(ret);
	}
	
}
