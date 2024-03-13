import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;

import javax.swing.ImageIcon;

public class Obstaculo extends Plataforma{
	private Image image;
	
	public Obstaculo(int x, int y, int w, int h) {
		super(x, y, w, h);
		image = new ImageIcon(getClass().getResource("bosque/17.png")).getImage();
	}
	
	public void dibujar(Graphics g) {
		g.drawImage(image, getX() , getY() , getAncho() , getAlto() ,null);
	}
	
	
}
