package visualizer;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import tile.Tile;

public class VPanel extends JPanel{
	private static final long serialVersionUID = 1064812704658251024L;
	int scale = 25;
	Tile[][] array;
	BufferedImage img;
	public VPanel(Tile[][] arr) {
		array = arr;
		img = new BufferedImage(arr.length *scale, arr[0].length*scale, BufferedImage.TYPE_INT_ARGB);
		addMouseWheelListener(new MouseWheelListener() {
			@Override
			public void mouseWheelMoved(MouseWheelEvent e) {
				scale += e.getWheelRotation();
				repaint();
			}
		});
	}
	@Override
	public void paintComponent(Graphics g) {
		drawImage();
		g.drawImage(img, 0, 0, null);
	}
	
	public void Export(File f) throws IOException {
		drawImage();
		ImageIO.write(img, "png", f);
	}
	
	private void drawImage() {
		Graphics2D g2d = img.createGraphics();
		for(int i = 0; i < array.length; i++) {
			for(int j = 0; j < array[i].length; j++) {
				int temp = array[i][j].elevation;
				if(temp >=240)
					g2d.setColor(new Color(255,255,255));
				else if(temp >=200 && temp < 240)
					g2d.setColor(new Color(68, 44, 0));
				else if(temp >=150 && temp < 200)
					g2d.setColor(new Color(0, 112, 3));
				else if(temp >=100 && temp < 150)
					g2d.setColor(new Color(0, 221, 20));
				else if(temp >=20 && temp < 100)
					g2d.setColor(new Color(0, 255, 6));
				else if(temp >=1 && temp < 20)
					g2d.setColor(new Color(255, 250, 0));
				else if(temp == 0)
					g2d.setColor(new Color(0, 242, 255));
				else if(temp <=-1 && temp > -20)
					g2d.setColor(new Color(0, 199, 255));
				else if(temp <=-20 && temp > -100)
					g2d.setColor(new Color(0, 148, 255));
				else if(temp <=-100 && temp > -150)
					g2d.setColor(new Color(0, 108, 186));
				else if(temp <=-150 && temp > -200)
					g2d.setColor(new Color(0, 62, 183));
				else if(temp <=-200 && temp > -240)
					g2d.setColor(new Color(0, 5, 119));
				else if(temp <=-240)
					g2d.setColor(new Color(0, 0, 0));
				g2d.fillRect(i*scale, j*scale, scale, scale);
				g2d.setColor(new Color(0,0,0,100));
				//g2d.drawString(array[i][j].elevation+"", i*scale, j*scale);
			}
		}
	}
}
