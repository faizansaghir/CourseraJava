package java_Programming_Week9;

import processing.core.PApplet;
import processing.event.MouseEvent;

public class Introduction_To_EventDrivenProgramming extends PApplet{
	/**
	 * 
	 */
	int[] back= {0,0,0};
	int selected=0;
	private static final long serialVersionUID = -2893520483185037133L;
	public void setup() {
		size(400,400);
	}
	public void draw() {
		background(back[0],back[1],back[2]);
		fill(255,0,0);
		rect(50,50,25,25);
		fill(0,255,0);
		rect(100,50,25,25);
		fill(0,0,255);
		rect(150,50,25,25);
	}
	
	public void mouseReleased() {
		if(mouseX>=50 && mouseY>=50 && mouseX<=75 && mouseY<=75) {
			selected=0;
		}
		else if(mouseX>=100 && mouseY>=50 && mouseX<=125 && mouseY<=75) {
			selected=1;
		}
		else if(mouseX>=150 && mouseY>=50 && mouseX<=175 && mouseY<=75) {
			selected=2;
		}
	}
	public void mouseWheel(MouseEvent event) {
		int num=event.getCount();//returns +1 for scroll-down and -1 for scroll-up
		if(num>0 && back[selected]<255 || num<0 && back[selected]>0) {
			back[selected]+=num*5;
		}
	}
}
