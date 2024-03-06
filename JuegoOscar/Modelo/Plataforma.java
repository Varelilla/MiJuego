import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class Plataforma extends JPanel {
	private int x;
	private int y;
	private int xOrigen;
	private int yOrigen;
	private int ancho;
	private int alto;
	private Rectangle hitBox;
	private int xscroll;
	private int yscroll;
	private Image image;
	private ArrayList<Image> imagenes;
	
	public Plataforma(int x, int y, int w, int h){
		xscroll=0;
		yscroll=0;
		xOrigen = x;
		yOrigen = y;
		this.x = x;
		this.y = y;
		ancho = w;
		alto = h;
		hitBox = new Rectangle(x,y,ancho,alto);
		
		cargarImagenes();

	}
	public void cargarImagenes() {
		imagenes = new ArrayList<Image>();
		image = new ImageIcon(getClass().getResource("Tiles/Tile_01.png")).getImage();
		imagenes.add(image);
		image = new ImageIcon(getClass().getResource("Tiles/Tile_03.png")).getImage();
		imagenes.add(image);
		image = new ImageIcon(getClass().getResource("Tiles/Tile_02.png")).getImage();
		imagenes.add(image);
		image = new ImageIcon(getClass().getResource("Tiles/Tile_05.png")).getImage();
		imagenes.add(image);
		image = new ImageIcon(getClass().getResource("Tiles/Tile_21.png")).getImage();
		imagenes.add(image);
		image = new ImageIcon(getClass().getResource("Tiles/Tile_23.png")).getImage();
		imagenes.add(image);
		image = new ImageIcon(getClass().getResource("Tiles/Tile_22.png")).getImage();
		imagenes.add(image);
	}
	
	
	public void dibujar(Graphics g){
		int contador = 0;
		int maxAncho = ancho/100;
		int maxAlto = alto/100;
		for (int i = 0;i<maxAncho;i++) {
			for (int j = 0;j<maxAlto;j++) {
				if (j == 0) {
					if (j == maxAncho - 1) {
						g.drawImage(imagenes.get(3), x + (100*i), y + (100*j), 100, 100, null);
					} else if (i == 0) {
						g.drawImage(imagenes.get(0), x + (100*i), y + (100*j), 100, 100, null);
					} else if (i == (maxAncho -1)) {
						g.drawImage(imagenes.get(1), x + (100*i), y + (100*j), 100, 100, null);
					} else {
						g.drawImage(imagenes.get(2), x + (100*i), y + (100*j), 100, 100, null);
					}
				} else {
					
					if (i == 0) {
						g.drawImage(imagenes.get(3), x + (100*i), y + (100*j), 100, 100, null);
					} else if (i == (maxAncho -1)) {
						g.drawImage(imagenes.get(4), x + (100*i), y + (100*j), 100, 100, null);
					} else {
						g.drawImage(imagenes.get(5), x + (100*i), y + (100*j), 100, 100, null);
					}
				}
				contador++;
			}
		}
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getAncho() {
		return ancho;
	}

	public void setAncho(int ancho) {
		this.ancho = ancho;
	}

	public int getAlto() {
		return alto;
	}

	public void setAlto(int alto) {
		this.alto = alto;
	}

	public Rectangle getHitBox() {
		return hitBox;
	}

	public void setHitBox(Rectangle hitBox) {
		this.hitBox = hitBox;
	}

	public int getXscroll() {
		return xscroll;
	}

	public void setXscroll(int xscroll) {
		this.xscroll = xscroll;
		x = xOrigen - xscroll;
		hitBox = new Rectangle(x,y,ancho,alto);
	}

	public int getYscroll() {
		return yscroll;
	}

	public void setYscroll(int yscroll) {
		this.yscroll = yscroll;
	}
	
	public int getTop(){
		return hitBox.y;
	}

	public int getLeft(){
		return hitBox.x;
	}

	public int getRight(){
		return hitBox.x+ancho;
	}

	public int getBottom(){
		return hitBox.y+alto;
	}

	public int getxOrigen() {
		return xOrigen;
	}

	public void setxOrigen(int xOrigen) {
		this.xOrigen = xOrigen;
	}

	public int getyOrigen() {
		return yOrigen;
	}

	public void setyOrigen(int yOrigen) {
		this.yOrigen = yOrigen;
	}

}
