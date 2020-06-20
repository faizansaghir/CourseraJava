package java_Programming_Week9;

import processing.core.PApplet;
import processing.core.PImage;

public class Introduction_To_PApplet extends PApplet{
	/**
	 * 
	 */
	private static final long serialVersionUID = -2269303194903037876L;
	private PImage backImg;
	public void setup() {
		size(400,400);
		String imgURL="https://c.tadst.com/gfx/600x337/barcelona-morning-sky.jpg";
		backImg=loadImage(imgURL,"jpg");
	}
	public void draw() {
		backImg.resize(0, height);
		image(backImg,0,0);
		
		//Draw Face
		int[] faceColor=selectColor(second());
		fill(faceColor[0],faceColor[1],faceColor[2]);
		ellipse(width/2,height/2,width,height);
		
		//Draw Eyes
		fill(0,0,0);
		ellipse(width/3,height/3,width/10,height/10);
		ellipse((width-width/3),height/3,width/10,height/10);
		
		//Draw mouth
		//fill(255,255,255);
		//noFill();
		fill(0,0,0);
		arc(width/2,(height-height/3),(width/2.5f),height/3,0,PI);
	}
	public int[] selectColor(float seconds) {
		int[] rgb=new int[3];
		float diffFrom30=Math.abs(30-seconds);
		float ratio=diffFrom30/30;
		rgb[0]=(int)(255*ratio);
		rgb[1]=(int)(255*ratio);
		rgb[2]=0;
		return  rgb;
	}
}
