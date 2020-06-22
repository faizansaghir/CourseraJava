package java_Programming_Week9;

import de.fhpotsdam.unfolding.UnfoldingMap;
import de.fhpotsdam.unfolding.data.PointFeature;
import de.fhpotsdam.unfolding.geo.Location;
import de.fhpotsdam.unfolding.marker.Marker;
import de.fhpotsdam.unfolding.marker.SimplePointMarker;
import de.fhpotsdam.unfolding.providers.Google;
import de.fhpotsdam.unfolding.utils.MapUtils;
import processing.core.PApplet;

public class Introduction_To_UnfoldingMap extends PApplet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 454900536331664203L;
	private UnfoldingMap map;
	public void setup() {
		size(950,600);
		map=new UnfoldingMap(this,200,50,700,500,new Google.GoogleMapProvider());
		map.zoomToLevel(2);
		MapUtils.createDefaultEventDispatcher(this, map);
		Location location=new Location(23.4,84.7);
		PointFeature m1PF=new PointFeature(location);
		m1PF.addProperty("Title", "Ranchi");
		m1PF.addProperty("Date", "28-10-2020");
		m1PF.addProperty("Magnitude", "23.7");
		
		map.addMarker(new SimplePointMarker(m1PF.getLocation(),m1PF.getProperties()));
	}
	public void draw() {
		map.draw();
	}
}
