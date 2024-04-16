import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;

import javax.swing.ImageIcon;

public class Obstaculo extends Plataforma{
	private Image image;
	private Image pinchoDerecha;
	private Image pinchoIzquierda;
	private Image pinchoArriba;
	private int pincho;
	private int mov;
	
	public Obstaculo(int x, int y, int w, int h,int estado) {
		super(x, y, w, h,estado);
		image = new ImageIcon(getClass().getResource("bosque/7.png")).getImage();
		pinchoDerecha = new ImageIcon(getClass().getResource("bosque/pinchoDerecha.png")).getImage();
		pinchoIzquierda = new ImageIcon(getClass().getResource("bosque/pinchoIzquierda.png")).getImage();
		pinchoArriba = new ImageIcon(getClass().getResource("bosque/pinchoArriba.png")).getImage();
		pincho = 0;
		mov = 1;
	}
	
	public Obstaculo(int x, int y, int w, int h, int pincho,int estado) {
		super(x, y, w, h,estado);
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

		image = new ImageIcon(getClass().getResource(ruta +"/8.png")).getImage();
		pinchoDerecha = new ImageIcon(getClass().getResource(ruta +"/pinchoDerecha.png")).getImage();
		pinchoIzquierda = new ImageIcon(getClass().getResource(ruta +"/pinchoIzquierda.png")).getImage();
		pinchoArriba = new ImageIcon(getClass().getResource(ruta +"/pinchoArriba.png")).getImage();
		this.pincho = pincho;
		mov = 1;
	}
	
	
	public void dibujar(Graphics g) {
		
		if (pincho > 0) {
			g.drawImage(image, getX() , getY() , getAncho() , getAlto() ,null);
			for (int i = 0; i < 5; i++) {
				// Aqui dibujamos te pinchos en la cara de este obstaculo en el que necesitamos pinchos
				g.drawImage(image, getX() , getY() , getAncho() , getAlto() ,null);
				switch (pincho) {
				case 1:
					g.drawImage(pinchoDerecha, getX() + getAncho(), getY() + (i * (getAlto()/5)), getAncho()/5, getAlto()/5, null);
					break;
				case 3:
					g.drawImage(pinchoIzquierda, getX() - getAncho() / 5, getY() + (i * (getAlto() / 5)),
							getAncho() / 5, getAlto() / 5, null);
					break;
				case 2:
					g.drawImage(pinchoArriba, getX() + (i * (getAncho() / 5)), getY() - getAlto() / 5,
							getAncho() / 5, getAlto() / 5, null);
					break;
				}
			}
		}  else {
			Image image = new ImageIcon(getClass().getResource("bosque/agua" + ((mov/2) + 1) + ".png")).getImage();
			g.drawImage(image, getX() , getY() , getAncho() , getAlto() ,null);
		}
		mov++;
		if (mov == 34) {
			mov = 1;
		}
	}
	
	
}
