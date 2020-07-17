package java_Programming_Week10;

public class Airport implements Comparable<Airport>{
	private String city;
	private String country;
	private String code;
	public String getCity() {
		return city;
	}
	public String getCountry() {
		return country;
	}
	public String getCode() {
		return code;
	}
	@Override
	public int compareTo(Airport o) {
		// TODO Auto-generated method stub
		return(this.getCity().compareTo(o.getCity()));
	}
	
}
