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
	
	public Enemigo(int x, int y, int w, int h, int velX, int velY, int numMov, int tipo) {
		super(x, y, w, h);
		this.velX = -velX;
		this.velY = velY;
		mov = 0;
		this.numMov = numMov;
		this.tipo = tipo;
		cargarImagenes();
		estadoActual = 0;
		hitBox = new Rectangle(getX() + (int)(getAncho()*0.7),getY() + (int)(getAlto() * 0.7),getAncho(),getAlto());

	}
	public void dibujar(Graphics g){
		//g.fillOval(getX(), getY(), getAncho(), getAlto());
		frames++;
		if (frames == 4) {
			frames = 0;
			estadoActual=(estadoActual + 1) % 7;
		}

		g.drawImage(sprites.get(0).get(estadoActual%7),getX(),getY(),null);
	}
	
	public void mover() {
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
		this.hitBox = new Rectangle(getX() + (int)(getAncho()*1.4),getY() + (int)(getAlto() * 1.35),getAncho(),getAlto());
	}

}
