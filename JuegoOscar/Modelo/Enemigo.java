import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.util.ArrayList;

import javax.swing.ImageIcon;

public class Enemigo extends Plataforma {
	private int velX;
	private int velY;
	private int numMov;
	private int mov;
	private int tipo;
	private ArrayList<ArrayList<Image>> sprites;
	private int estadoActual;
	private int frames=0;
	private Rectangle hitBox;
	private int tipoEnemigo;
	private int xInicial, yInicial;
	
	public Enemigo(int x, int y, int w, int h, int velX, int velY, int tipo, int estado) {
		super(x+15, y+10, w-30, h-20, estado);
		this.velX = -velX;
		this.velY = velY;
		this.xInicial = x;
		this.yInicial = y;
		mov = 0;
		this.numMov = 25;
		this.tipo = tipo;
		cargarImagenes();
		estadoActual = 0;
		hitBox = new Rectangle(x+15,y+15,w-30,h-30);
	}
	
	public void dibujar(Graphics g){
		//g.fillOval(getX(), getY(), getAncho(), getAlto());
		frames++;
		if (frames == 4) {
			frames = 0;
			estadoActual=(estadoActual + 1) % 7;
		}

		g.drawImage(sprites.get(0).get(estadoActual%7),getX(),getY(),null);
		if (tipoEnemigo == JuegoOscar.LABORATORIO) {
			if (velX < 0) {
				g.drawImage(new ImageIcon(getClass().getResource("objetos/barrilIzquierda.png")).getImage(),xInicial - getXscroll(),yInicial - getYscroll(),100,100,null);
			} else if  (velX > 0) {
				g.drawImage(new ImageIcon(getClass().getResource("objetos/barrilDerecha.png")).getImage(),
					xInicial - getXscroll(), yInicial - getYscroll(), 100, 100, null);
			} else if (velY > 0) {
				g.drawImage(new ImageIcon(getClass().getResource("objetos/barrilAbajo.png")).getImage(),
					xInicial - getXscroll(), yInicial - getYscroll(), 100, 100, null);
			}
			else{
				g.drawImage(new ImageIcon(getClass().getResource("objetos/barrilArriba.png")).getImage(),xInicial - getXscroll(),yInicial - getYscroll(),100,100,null);
			}
		}
	}
	
	public void mover() {
		if (tipoEnemigo == JuegoOscar.BOSQUE) {
			mov++;
			if (mov >= numMov) {
				velX = 0-velX;
				velY = 0-velY;
				mov = -numMov;
			} else if (mov <= -numMov) {
				velX = 0-velX;
				velY = 0-velY;
				mov = -numMov;
			}
	
			setX(getX()+velX);
			setxOrigen(getxOrigen()+velX);
	
			setY(getY()+velY);
			setyOrigen(getyOrigen()+velY);
		} else if (tipoEnemigo == JuegoOscar.LABORATORIO) {
			mov++;
			setX(getX()+velX);
			setxOrigen(getxOrigen()+velX);
			setY(getY()+velY);
			setyOrigen(getyOrigen()+velY);
			if (mov > numMov) {
				mov = 0;
				setX(xInicial);
				setxOrigen(xInicial);
				setY(yInicial);
				setyOrigen(yInicial);
			}
		}
	}
	
	public void cargarImagenes() {
		sprites = new ArrayList<ArrayList<Image>>();
		ArrayList<Image> tmp = new ArrayList<Image>();
		for (int i = 0; i < 7; i++) {
			Image image = new ImageIcon(getClass().getResource("enemigos/slimeball/airidle000" + i + ".png")).getImage();
			tmp.add(image);
		}
		sprites.add(tmp);
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
	public Rectangle getHitBox() {
		return hitBox;
	}
	public void hitBoxEnemigo() {
		this.hitBox = new Rectangle(getX()+15,getY()+15,getAncho()-30,getAlto()-30);
	}

	public int getTipoEnemigo() {
		return tipoEnemigo;
	}

	public void setTipoEnemigo(int tipoEnemigo) {
		this.tipoEnemigo = tipoEnemigo;
		switch (tipoEnemigo) {
		case JuegoOscar.BOSQUE:
			numMov = 25;
			break;
		case JuegoOscar.LABORATORIO:
			numMov = 100;
			velX = velX*2;
			velY = velY*2;
			break;
		case JuegoOscar.MANSION:
			numMov = 25;
			break;
		case JuegoOscar.CIUDAD:
			numMov = 25;
			break;
		}
	}

}
