import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;


public class Personaje {
	
	private final int ALTO = 76;
	private final int ANCHO = 79;
	private final int VEL_MAXIMA = 10 ;
	private final int VEL_MAXIMA_CAIDA = 18;
	private Rectangle hitbox;
	private Rectangle leftHitBox;
	private Rectangle rightHitBox;
	private Rectangle botHitBox;
	private boolean vivo;
	private boolean enAire;
	private int xPos;
	private int yPos;
	private int initXPos;
	private int initYPos;
	private double xVel;
	private double yVel;
	private double xAceleracion;
	private boolean tocandoIzquierda;
	private boolean tocandoDerecha;
	private boolean tocandoSuelo;
	private boolean saltando;
	private AreaJuego level;
	private boolean[] keys = new boolean[5];
	private final int IZQUIERDA = 0;
	private final int DERECHA = 1;
	private final int ARRIBA = 2;
	private final int DASH = 3;
	private final int RESET = 4;
	private int estadoActual = 0;
	private int plataformaIzquierda;
	private int plataformaDerecha;
	private int mirando = 0;
	private int posicion = 2;
	private Image imgPared;
	private Image imgParedInversa;
    private double gravedad;
    private ArrayList<Plataforma> plataformas;
    private ArrayList<Polvo> polvos;
    private ArrayList<Enemigo> enemigos;
    private ArrayList<ArrayList<Image>> sprites;
    private int frame = 0;
    private boolean dobleSalto = true;
    private boolean enPared = true;
    private int xScroll;
    private int yScroll;
   
	public Personaje(AreaJuego level,int x, int y) {
		enPared = false;
		tocandoIzquierda = false;
		tocandoDerecha = false;
		tocandoSuelo = false;
		xScroll = 0;
		yScroll = 0;
		vivo=true;
		enAire=true;
		initXPos=xPos=x;
		initYPos=yPos=y;
		//this.frame=frame;
		gravedad =1.1;
		xAceleracion = 2;
		this.level=level;
		plataformas = level.getPlataformas();
		polvos = level.getPolvos();
		enemigos = level.getEnemigos();
		cargarImagenes();
		hitbox = new Rectangle(xPos,yPos,ANCHO,ALTO);
		leftHitBox = new Rectangle(xPos-4,yPos+4,8,ALTO-4);
		rightHitBox = new Rectangle(xPos+ANCHO -4,yPos+4,8,ALTO-4);
		botHitBox = new Rectangle(xPos,yPos+ALTO-4,20,4);
		
	}

	public void mover(){
		enPared = false;
		if(!vivo){
			restart();
		}
		if(keys[RESET]){
			restart();
		}
		frame++;
		if (frame > 4) {
			frame = 0;
			estadoActual++;
		}
		if(!enAire){
			dobleSalto = true;
			if(keys[ARRIBA]){
				yVel=-14;
				estadoActual = 0;
				enAire=true;
				saltando=true;
			}
			if(keys[ARRIBA]&&keys[DERECHA]){
				yVel=-14;
				mirando = 0;
				saltando=true;
			}
			if(keys[DERECHA]){
				posicion = 0;
				mirando = 0;
				if (tocandoDerecha)
					xVel=0;
				else if(xVel<VEL_MAXIMA*1.5&&xVel>=0)
					xVel+=xAceleracion;
				else if(xVel<VEL_MAXIMA)
					xVel +=xAceleracion;
				else
					xVel=VEL_MAXIMA;
			}
			else if(keys[IZQUIERDA]){
				posicion = 0;
				mirando = 1;
				if(tocandoIzquierda)
					xVel = 0;
				else if(xVel>-VEL_MAXIMA*1.5&&xVel<=0)
					xVel-=xAceleracion;
				else if(xVel>-VEL_MAXIMA)
					xVel-=xAceleracion;
				else 
					xVel=-VEL_MAXIMA;
			}
			else {
				posicion = 2;
				xVel=0;	
			}
		}
		else{
			if(keys[DERECHA]){
				posicion = 0;
				mirando = 0;
				if (tocandoDerecha) {
					if (yVel > 0)
						yVel -= 0.85;
					xVel = 0;
					posicion = 2;
					enPared = true;
				}
				else if(xVel<VEL_MAXIMA*1.5&&xVel>=0)
					xVel+=xAceleracion;
				else if(xVel<VEL_MAXIMA)
					xVel +=xAceleracion;
				else
					xVel=VEL_MAXIMA;
			}
			else if(keys[IZQUIERDA]){
				posicion = 0;
				mirando = 1;
				if(tocandoIzquierda) {
					enPared = true;
					if (yVel > 0)
						yVel -= 0.85;
					xVel = 0;
					posicion = 2;
				}
				else if(xVel>-VEL_MAXIMA*1.5&&xVel<=0)
					xVel-=xAceleracion;
				else if(xVel>-VEL_MAXIMA){
					xVel-=xAceleracion;
				}
				else 
					xVel=-VEL_MAXIMA;
			}
			else if(xVel>2) {
				xVel-=xAceleracion;
			}
			else if(xVel<-2) {
				xVel+=xAceleracion;
				}
			else {
				xVel=0;
			}	
			if(!keys[ARRIBA]){
				saltando=false;
			}
			if(!saltando&&keys[ARRIBA])
			{
				saltando=true;
				if(tocandoDerecha)
				{
						xVel = -12;
						yVel = -14;
				}
				else if(tocandoIzquierda)
				{
						xVel = 12;
						yVel = -14;
				}
				else if(dobleSalto) {
					dobleSalto = false;
					yVel = -14;
				}
			}
			if(yVel<=VEL_MAXIMA_CAIDA)
				yVel+=gravedad;
		}
		if ((xPos+xVel)>= 1400) sumarScrollX();
		else xPos+=xVel;
		yPos+=yVel;
		tocandoDerecha = false;
		tocandoIzquierda = false;
		tocandoSuelo=false;
		hitbox = new Rectangle(xPos,yPos,ANCHO,ALTO );
		leftHitBox = new Rectangle(xPos-4,yPos+4,8,ALTO-4);
		rightHitBox = new Rectangle(xPos+ANCHO -4,yPos+4,8,ALTO-4);
		botHitBox = new Rectangle(xPos+4,yPos+ALTO-4,ANCHO-4,4);
		
		if(yPos>level.getHeight()){
			restart();
		}

		if(xPos<0){
			xPos=0;
			xVel=0;
		}
		if(xPos>level.getWidth()-ANCHO){
			xPos=level.getWidth()-ANCHO;
			xVel=0;
		}
		if(yPos<0){
			yPos=0; 
			yVel=0;
		}
		checkCollisions();
		
	}
	
	public void sumarScrollX() {
		xScroll+=xVel;
		plataformaIzquierda-=xVel;
		plataformaDerecha-=xVel;
	}

	public void checkCollisions(){
		vivo=true;
		ArrayList<Plataforma> tmplist = new ArrayList<Plataforma>();
		tmplist.addAll(plataformas);
		for(int i=0;i<tmplist.size();i++){
			Plataforma temp = tmplist.get(i);	
			temp.setXscroll(xScroll);
			if(hitbox.intersects(temp.getHitBox())){
				
				if (Math.abs(xPos+ANCHO-temp.getLeft())<=xVel+5 && yPos>temp.getTop()-ALTO && yPos<temp.getBottom() && rightHitBox.intersects(temp.getHitBox()))
				{
					xPos = temp.getLeft()-ANCHO;
					tocandoDerecha = true;
				}
				else if (Math.abs(xPos-temp.getRight())<=Math.abs(xVel-5) && yPos>temp.getTop()-ALTO && yPos<temp.getBottom() && leftHitBox.intersects(temp.getHitBox()))
				{
					xPos = temp.getRight();
					tocandoIzquierda = true;
				}
				else if (yVel>=0 && yPos+ALTO<temp.getBottom())
				{
						yVel = 0;
						plataformaIzquierda = temp.getLeft();
						plataformaDerecha = temp.getRight();
						yPos = temp.getTop()-ALTO;
						enAire=false;
				}
				else
				{
					
					tocandoSuelo = true;
					yPos = temp.getBottom();
					yVel = 0;
				}
			}	
			else{
				if(!enAire && (xPos+ANCHO<plataformaIzquierda+5 || xPos>plataformaDerecha-5))
				{	
					enAire = true;
					estadoActual = 0;
				}
				if(enAire && leftHitBox.intersects(temp.getHitBox()) && !tocandoSuelo)
				{
					tocandoIzquierda= true;
				}
				else if (enAire && rightHitBox.intersects(temp.getHitBox()) && !tocandoSuelo)
				{
					tocandoDerecha = true;
				}
			}
		}
		for (Polvo p : polvos) {
			p.setXscroll(xScroll);
			Rectangle rect = new Rectangle(p.getX(),p.getY()-5,p.getAncho(),p.getAlto());
			if(hitbox.intersects(rect) || leftHitBox.intersects(rect) || rightHitBox.intersects(rect) || botHitBox.intersects(rect)){
				p.setPisado(true);
			}
			
		}
		for (Enemigo p : enemigos) {
			p.setXscroll(xScroll);
			p.hitBoxEnemigo();
			Rectangle rect = p.getHitBox();
			if(hitbox.intersects(rect) || leftHitBox.intersects(rect) || rightHitBox.intersects(rect) || botHitBox.intersects(rect)){
				restart();
			}
			
			
		}

	}

	public void cargarImagenes() {
		sprites = new ArrayList<ArrayList<Image>>();
		ArrayList<Image> tmp = new ArrayList<Image>();
		ArrayList<Image> tmp2 = new ArrayList<Image>();
		for (int i = 0; i < 8; i++) {
			Image image = new ImageIcon(getClass().getResource("sprint/sprint000" + i + ".png")).getImage();
			tmp.add(image);
			BufferedImage bimage = new BufferedImage(image.getWidth(null), image.getHeight(null), BufferedImage.TYPE_INT_ARGB);
			BufferedImage mirrorImage = new BufferedImage(bimage.getWidth(), bimage.getHeight(), bimage.getType());

		    // Crear un objeto Graphics2D para dibujar en la nueva imagen
		    Graphics2D g2 = mirrorImage.createGraphics();

		    // Crear una transformación que invierte la imagen horizontalmente
		    AffineTransform at = AffineTransform.getScaleInstance(-1, 1);
		    at.translate(-bimage.getWidth(), 0);

		    // Aplicar la transformación y dibujar la imagen original en la nueva imagen
		    g2.transform(at);
		    g2.drawImage(image, 0, 0, null);

		    // Limpiar los recursos de Graphics2D
		    g2.dispose();
		    tmp2.add(mirrorImage);
		}
		
		sprites.add(tmp);
		sprites.add(tmp2);
		tmp = new ArrayList<Image>();
		tmp2 = new ArrayList<Image>();
		for (int i = 0; i < 10; i++) {
			Image image = new ImageIcon(getClass().getResource("still/idle000" + i + ".png")).getImage();
			tmp.add(image);
			BufferedImage bimage = new BufferedImage(image.getWidth(null), image.getHeight(null), BufferedImage.TYPE_INT_ARGB);
			BufferedImage mirrorImage = new BufferedImage(bimage.getWidth(), bimage.getHeight(), bimage.getType());

		    Graphics2D g2 = mirrorImage.createGraphics();

		    AffineTransform at = AffineTransform.getScaleInstance(-1, 1);
		    at.translate(-bimage.getWidth(), 0);

		    g2.transform(at);
		    g2.drawImage(image, 0, 0, null);

		    g2.dispose();
		    tmp2.add(mirrorImage);
		}	
		sprites.add(tmp);
		sprites.add(tmp2);
		
		tmp = new ArrayList<Image>();
		tmp2 = new ArrayList<Image>();
		for (int i = 0; i < 9; i++) {
			Image image = new ImageIcon(getClass().getResource("jump/jumpf000" + i + ".png")).getImage();
			tmp.add(image);
			Image image2 = new ImageIcon(getClass().getResource("jump/jumpb000" + i + ".png")).getImage();
			tmp2.add(image2);
		}	
		sprites.add(tmp);
		sprites.add(tmp2);
		
		tmp = new ArrayList<Image>();
		tmp2 = new ArrayList<Image>();
		for (int i = 0; i < 11; i++) {
			Image image = new ImageIcon(getClass().getResource("jump/dbljumpf000" + i + ".png")).getImage();
			tmp.add(image);
			image = new ImageIcon(getClass().getResource("jump/dbljumpb000" + i + ".png")).getImage();
			tmp2.add(image);
		}	
		sprites.add(tmp);
		sprites.add(tmp2);
		
		tmp = new ArrayList<Image>();
		tmp2 = new ArrayList<Image>();
		for (int i = 0; i < 9; i++) {
			Image image = new ImageIcon(getClass().getResource("jump/jump000" + i + ".png")).getImage();
			tmp.add(image);
			BufferedImage bimage = new BufferedImage(image.getWidth(null), image.getHeight(null), BufferedImage.TYPE_INT_ARGB);
			BufferedImage mirrorImage = new BufferedImage(bimage.getWidth(), bimage.getHeight(), bimage.getType());

		    Graphics2D g2 = mirrorImage.createGraphics();

		    AffineTransform at = AffineTransform.getScaleInstance(-1, 1);
		    at.translate(-bimage.getWidth(), 0);

		    g2.transform(at);
		    g2.drawImage(image, 0, 0, null);

		    g2.dispose();
		    tmp2.add(mirrorImage);
		}	
		sprites.add(tmp);
		sprites.add(tmp2);

		tmp = new ArrayList<Image>();
		tmp2 = new ArrayList<Image>();
		for (int i = 0; i < 11; i++) {
			Image image = new ImageIcon(getClass().getResource("jump/dbljump000" + i + ".png")).getImage();
			tmp.add(image);
			BufferedImage bimage = new BufferedImage(image.getWidth(null), image.getHeight(null), BufferedImage.TYPE_INT_ARGB);
			BufferedImage mirrorImage = new BufferedImage(bimage.getWidth(), bimage.getHeight(), bimage.getType());

		    Graphics2D g2 = mirrorImage.createGraphics();

		    AffineTransform at = AffineTransform.getScaleInstance(-1, 1);
		    at.translate(-bimage.getWidth(), 0);

		    g2.transform(at);
		    g2.drawImage(image, 0, 0, null);

		    g2.dispose();
		    tmp2.add(mirrorImage);
		}	
		sprites.add(tmp);
		sprites.add(tmp2);

		imgPared = new ImageIcon(getClass().getResource("wallgrab/wallgrabairidle0001.png")).getImage();
		BufferedImage bimage = new BufferedImage(imgPared.getWidth(null), imgPared.getHeight(null), BufferedImage.TYPE_INT_ARGB);
		BufferedImage mirrorImage = new BufferedImage(bimage.getWidth(), bimage.getHeight(), bimage.getType());

	    Graphics2D g2 = mirrorImage.createGraphics();

	    AffineTransform at = AffineTransform.getScaleInstance(-1, 1);
	    at.translate(-bimage.getWidth(), 0);

	    g2.transform(at);
	    g2.drawImage(imgPared, 0, 0, null);

	    g2.dispose();
	    imgParedInversa = mirrorImage;
	}

	public void restart(){
		enAire=false;
		//level.aumentarMuertes();
		xPos = initXPos;
		yPos = initYPos;
		xVel=0;
		yVel=0;
		estadoActual=0;
		enAire = true;
		xScroll = 0;
		for (Plataforma p : plataformas) p.setXscroll(0);
		for (Polvo p: polvos) {
			p.setPisado(false);
			p.setXscroll(0);
		}
	}

	public void dibujar(Graphics g){
		if (enPared) {
			if (mirando == 0) {
				g.drawImage(imgPared, xPos, yPos, null);
			} else {
				g.drawImage(imgParedInversa, xPos, yPos, null);
			}
		} else
		if (enAire) {
			if (xVel == 0) {
				if (dobleSalto)
					g.drawImage(sprites.get(10 + mirando).get(estadoActual%8), xPos, yPos, null);
				else
					g.drawImage(sprites.get(8 + mirando).get(estadoActual%8), xPos, yPos, null);
			} else {
				if (dobleSalto) {
					g.drawImage(sprites.get(4 + mirando).get(estadoActual%9), xPos, yPos, null);
				} else {
					g.drawImage(sprites.get(6 + mirando).get(estadoActual%9), xPos, yPos, null);
				}
			}
		} else {
			g.drawImage(sprites.get(posicion + mirando).get(estadoActual%8),xPos,yPos,null);
		}
	}
	public boolean[] getKeys() {
		return keys;
	}
	public void setKeys(boolean[] keys) {
		this.keys = keys;
	}

	public int getxScroll() {
		return xScroll;
	}

	public void setxScroll(int xScroll) {
		this.xScroll = xScroll;
	}

	public int getyScroll() {
		return yScroll;
	}

	public void setyScroll(int yScroll) {
		this.yScroll = yScroll;
	}
}
