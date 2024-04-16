import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
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
	private AffineTransform image2;
	private BufferedImage transformedImage;
	private BufferedImage bimage;
	private ArrayList<Image> imagenes;
	private double relX, relY;
	private int pared;
	private boolean movil;
	private int movimiento;
	private int velX;
	private int velY;
	private int numMov;
	private int mov;
	private int estado;
	
	
	public Plataforma(int x, int y, int w, int h, int estado){
		xscroll= 0;
		yscroll= 0;
		xOrigen = x;
		yOrigen = y;
		this.estado = estado;
		relX = 1;
		relY = 1;
		this.x = x;
		this.y = y;
		ancho = w;
		movimiento = 10;
		alto = h;
		pared = 0;
		hitBox = new Rectangle(x,y,ancho,alto);
		movil = false;
		cargarImagenes();
	}
	
	public Plataforma(int x, int y, int w, int h,int pared, int estado){
		xscroll= 0;
		yscroll= 0;
		xOrigen = x;
		yOrigen = y;
		this.estado = estado;
		relX = 1;
		relY = 1;
		this.x = x;
		this.y = y;
		ancho = w;
		movimiento = 10;
		alto = h;
		this.pared = pared;
		hitBox = new Rectangle(x,y,ancho,alto);
		movil = false;
		cargarImagenes();
	}
	
	public void cambiarHitBox(double relX, double relY) {
        hitBox = new Rectangle((int)(x *relX),(int)(y *relY),(int)(ancho*relX),(int)(alto*relY));
        
    }
	


	
	public void cargarImagenes() {
		String ruta = "";
		switch (estado) {
		case JuegoOscar.BOSQUE:
			ruta = "bosque";
			break;
		case JuegoOscar.LABORATORIO:
			ruta = "lab";
			break;
		case JuegoOscar.CIUDAD:
			ruta = "ciudad";
			break;
		case JuegoOscar.MANSION:
			ruta = "mansion";
			break;
		}

		imagenes = new ArrayList<Image>();
		image = new ImageIcon(getClass().getResource("" + ruta+ "/1.png")).getImage();
		imagenes.add(image);
		image = new ImageIcon(getClass().getResource(ruta+ "/2.png")).getImage();
		imagenes.add(image);
		image = new ImageIcon(getClass().getResource(ruta+ "/3.png")).getImage();
		imagenes.add(image);
		image = new ImageIcon(getClass().getResource(ruta+ "/4.png")).getImage();
		imagenes.add(image);
		image = new ImageIcon(getClass().getResource(ruta+ "/5.png")).getImage();
		imagenes.add(image);
		image = new ImageIcon(getClass().getResource(ruta + "/plataforma.png")).getImage();
		imagenes.add(image);
		image = new ImageIcon(getClass().getResource(ruta+ "/8.png")).getImage();
		imagenes.add(image);
	}


	public void dibujar(Graphics g){
		int contador = 0;
		int maxAncho = ancho/100;
		int maxAlto = alto/100;
		if(pared == 3) {
			g.drawImage(imagenes.get(3), x,y, 100, 100, null);
		} else if(pared == 4) {
			g.drawImage(imagenes.get(4), x,y, 100, 100, null);
		} else if(pared == 12) {
			g.drawImage(imagenes.get(6), x,y, 100, 100, null);
		} else {
			for (int i = 0;i<maxAncho;i++) {
				if (maxAncho == 1) {
					g.drawImage(imagenes.get(5), x + (100*i),y, 100, 100, null);
				} else if (i == 0) {
					g.drawImage(imagenes.get(0), x + (100*i), y, 100, 100, null);
				} else if (i == (maxAncho -1)) {
					g.drawImage(imagenes.get(2), x + (100*i), y, 100, 100, null);
				} else {
					g.drawImage(imagenes.get(1), x + (100*i), y , 100, 100, null);
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
		y = yOrigen - yscroll;
		hitBox = new Rectangle(x, y, ancho, alto);
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

	public boolean isMovil() {
		return movil;
	}

	public void setMovil(boolean movil) {
		this.movil = movil;
	}

	public int getVelX() {
		return velX;
	}

	public void setVelX(int velX) {
		this.velX = velX;
	}

	public int getVelY() {
		return velY;
	}

	public void setVelY(int velY) {
		this.velY = velY;
	}

	public int getNumMov() {
		return numMov;
	}

	public void setNumMov(int numMov) {
		this.numMov = numMov;
	}

	public int getMov() {
		return mov;
	}

	public void setMov(int mov) {
		this.mov = mov;
	}
}
