import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;

import javax.swing.ImageIcon;

public class Polvo extends Plataforma{
	private boolean pisado;
	private Image image;
	
	public Polvo(int x, int y, int w, int h) {
		super(x, y, w, h);
		pisado = false;
		image = new ImageIcon(getClass().getResource("Tiles/Tile_01.png")).getImage();
	}
	
	public void dibujar(Graphics g) {
		g.drawImage(image, getX() , getY() , getAncho() , getAlto() ,null);
	}
	
	public boolean isPisado() {
		return pisado;
	}
	public void setPisado(boolean pisado) {
		this.pisado = pisado;
	}
	

}
