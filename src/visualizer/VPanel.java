package visualizer;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import tile.Tile;

public class VPanel extends JPanel{

	private static final long serialVersionUID = 1064812704658251024L;
	Tile[][] array;
	BufferedImage img;
	public VPanel(Tile[][] arr) {
		array = arr;
		img = new BufferedImage(arr.length *5, arr[0].length*5, BufferedImage.TYPE_INT_ARGB);
	}
	@Override
	public void paintComponent(Graphics g) {
		drawImage();
		g.drawImage(img, 0, 0, null);
	}
	private void drawImage() {
		Graphics2D g2d = img.createGraphics();
		for(int i = 0; i < array.length; i++) {
			for(int j = 0; j < array[i].length; j++) {
				int temp = array[i][j].elevation;
				g2d.drawRect(i*5, j*5, 5, 5);
				if(temp >=240)
					g2d.setColor(new Color(255,255,255));
				else if(temp >=200 && temp < 240)
					g2d.setColor(new Color(68 + (int)((temp - 200)* 4.675), 44 + (int)((temp - 200)* 5.275), 0 + (int)((temp - 200)* 6.375)));
				else if(temp >=150 && temp < 200)
					g2d.setColor(new Color(0 + (int)((temp - 150)* 1.36), 112 + (int)((temp - 150)* 1.36), 3 - (int)((temp - 150)* 0.06)));
				else if(temp >=100 && temp < 150)
					g2d.setColor(new Color(0 , 221 - (int)((temp - 100)* 2.18), 20 + (int)((temp - 100)* 0.34)));
				else if(temp >=20 && temp < 100)
					g2d.setColor(new Color(0, 255 - (int)((temp - 20)* 0.05), 6 + (int)((temp - 20)* 0.175)));
				else if(temp >=1 && temp < 20)
					g2d.setColor(new Color(255 - (int)((temp - 1)* 12.75), 250 + (int)((temp - 1)* 0.25), 0 + (int)((temp - 1)* 0.3)));
				else if(temp == 0)
					g2d.setColor(new Color(0, 242, 255));
				else if(temp <=-1 && temp > -20)
					g2d.setColor(new Color(0, 199 + (int)((temp + 1)* 2.55), 255));
				else if(temp <=-20 && temp > -100)
					g2d.setColor(new Color(0, 148 + (int)((temp + 20)* 0.5), 255 + (int)((temp + 20)* 1.38)));
				else if(temp <=-100 && temp > -150)
					g2d.setColor(new Color(0, 108 + (int)((temp + 100)* 0.92), 186 + (int)((temp + 100)*0.06)));
				else if(temp <=-150 && temp > -200)
					g2d.setColor(new Color(0, 62 + (int)((temp + 150)* 1.14), 183 + (int)((temp + 150)* 1.28)));
				else if(temp <=-200 && temp > -240)
					g2d.setColor(new Color(0, 5 + (int)((temp + 200)* 0.125), 119 + (int)((temp + 200)* 2.975)));
				else if(temp <=-240)
					g2d.setColor(new Color(0, 0, 0));
				g2d.fillRect(i*5, j*5, 5, 5);
			}
		}
	}
}
