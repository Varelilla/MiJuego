import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class AreaJuego extends JPanel {
	private Personaje personaje;
	private EventosAreaJuego eventos;
	private ArrayList<Plataforma> plataformas;
	private ArrayList<Polvo> polvos;
	private ArrayList<Enemigo> enemigos;
	private ArrayList<Obstaculo> obstaculos;
	private int xOriginal = 1900, yOriginal = 980;
	private double relX = 1,relY = 1;
	private Image image;
	private JuegoOscar juegoOscar;

	/**
	 * Create the panel.
	 */
	public AreaJuego(JuegoOscar juegoOscar) {
		this.juegoOscar = juegoOscar;
		setBounds(0, 0, 300, 100);
		setBackground(Color.gray);
		setFocusable(true);
		setRequestFocusEnabled(true);
		crearPlataformas();
		personaje = new Personaje(this, 300, 600);
		eventos = new EventosAreaJuego(this);
	}
	
	public void cambiarTamaño(int x, int y) {
		relX = (double) x / (double) xOriginal;
		relY = (double) y / (double) yOriginal;
		for (Polvo p : polvos) {
	        if (!p.isPisado())
	            p.cambiarHitBox(relX,relY); // Utilizar g2d en lugar de g
	    }

	    // Dibujar plataformas
	    for (Plataforma p : plataformas) {
	    	p.cambiarHitBox(relX,relY); // Utilizar g2d en lugar de g
	    }

	    // Dibujar enemigos
	    for (Enemigo e : enemigos) {
	    	e.cambiarHitBox(relX,relY); // Utilizar g2d en lugar de g
	    }

	    // Dibujar personaje
	    personaje.cambiarHitBox(relX,relY);
	}
	
	
	public void crearPlataformas() {
		plataformas = new ArrayList<Plataforma>();
		plataformas.add(new Plataforma(0, 900, 200, 100));
		plataformas.add(new Plataforma(200, 800, 800, 100));
		plataformas.add(new Plataforma(1200, 800, 800, 100));
		plataformas.add(new Plataforma(1400, 700, 100, 100));
		plataformas.add(new Plataforma(1700, 600, 100, 100));
		plataformas.add(new Plataforma(2000, 700, 800, 100));
		plataformas.add(new Plataforma(1400, 500, 100, 100));
		plataformas.add(new Plataforma(-50, 0, 100, 900,true));
		polvos = new ArrayList<Polvo>();
		polvos.add(new Polvo(45,1,10,20));
		polvos.add(new Polvo(45,20,10,20));
		polvos.add(new Polvo(45,40,10,20));
		polvos.add(new Polvo(45,60,10,20));
		polvos.add(new Polvo(45,80,10,20));
		polvos.add(new Polvo(45,100,10,20));
		polvos.add(new Polvo(45,120,10,20));
		polvos.add(new Polvo(45,140,10,20));
		polvos.add(new Polvo(1400,495,20,10));
		polvos.add(new Polvo(1420,495,20,10));
		polvos.add(new Polvo(1440,495,20,10));
		polvos.add(new Polvo(1460,495,20,10));
		polvos.add(new Polvo(1480,495,20,10));
		polvos.add(new Polvo(1480,495,20,10));
		polvos.add(new Polvo(1480,495,20,10));
		polvos.add(new Polvo(2400,695,20,10));
		enemigos = new ArrayList<Enemigo>();
		enemigos.add(new Enemigo(1010,700,50,50,0,5,25,1));
		enemigos.add(new Enemigo(150,100,50,50,-5,5,25,1));
	}
	

	/*public void paint(Graphics g) {
		
		super.paint(g);
		image = new ImageIcon(getClass().getResource("Background.png")).getImage();
		g.drawImage(image, 0, 0 , 1500 , this.getHeight() ,null);
		image = new ImageIcon(getClass().getResource("Tiles/fuente.png")).getImage();
		g.drawImage(image, 500 - personaje.getxScroll(), 656 , 144 , 144 ,null);
		for (Polvo p : polvos) {
			if (!p.isPisado())
			p.dibujar(g);
		}
		for (Plataforma p : plataformas) {
			p.dibujar(g);
		}
		for (Enemigo e : enemigos) {
			e.dibujar(g);
		}
		personaje.dibujar(g);
		
	}*/
	
	public void paint(Graphics g) {

	    Graphics2D g2d = (Graphics2D) g.create();
	    super.paint(g2d);
	    // Aplicar transformación de escala
	    g2d.scale(relX, relY);

	    // Dibujar el fondo
	    Image image = new ImageIcon(getClass().getResource("Background.png")).getImage();
	    for (int i = 0; i < 3; i ++) {
	    	g2d.drawImage(image, 0 + (i * 1500), 0 , 1500 , 980 ,null);
	    }

	    // Dibujar fuente
	    image = new ImageIcon(getClass().getResource("Tiles/fuente.png")).getImage();
	    g2d.drawImage(image, (500 - personaje.getxScroll()), 656 , 144 , 144 ,null);

	    // Dibujar polvos
	    for (Polvo p : polvos) {
	        if (!p.isPisado())
	            p.dibujar(g2d); // Utilizar g2d en lugar de g
	    }

	    // Dibujar plataformas
	    for (Plataforma p : plataformas) {
	        p.dibujar(g2d); // Utilizar g2d en lugar de g
	    }

	    // Dibujar enemigos
	    for (Enemigo e : enemigos) {
	        e.dibujar(g2d); // Utilizar g2d en lugar de g
	    }

	    // Dibujar personaje
	    personaje.dibujar(g2d); // Utilizar g2d en lugar de g

        g2d.dispose();
        
	}

	public Personaje getPersonaje() {
		return personaje;
	}

	public void setPersonaje(Personaje personaje) {
		this.personaje = personaje;
	}

	public EventosAreaJuego getEventos() {
		return eventos;
	}

	public void setEventos(EventosAreaJuego eventos) {
		this.eventos = eventos;
	}

	public ArrayList<Plataforma> getPlataformas() {
		return plataformas;
	}

	public void setPlataformas(ArrayList<Plataforma> plataformas) {
		this.plataformas = plataformas;
	}

	public ArrayList<Polvo> getPolvos() {
		return polvos;
	}

	public void setPolvos(ArrayList<Polvo> polvos) {
		this.polvos = polvos;
	}

	public ArrayList<Enemigo> getEnemigos() {
		return enemigos;
	}

	public void setEnemigos(ArrayList<Enemigo> enemigos) {
		this.enemigos = enemigos;
	}

	public JuegoOscar getJuegoOscar() {
		return juegoOscar;
	}

	public void setJuegoOscar(JuegoOscar juegoOscar) {
		this.juegoOscar = juegoOscar;
	}

}
