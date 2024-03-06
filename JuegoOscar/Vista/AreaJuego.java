import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.JPanel;

public class AreaJuego extends JPanel {
	private Personaje personaje;
	private EventosAreaJuego eventos;
	private ArrayList<Plataforma> plataformas;
	private ArrayList<Polvo> polvos;
	private ArrayList<Enemigo> enemigos;

	/**
	 * Create the panel.
	 */
	public AreaJuego() {
		setBounds(0, 0, 300, 100);
		setBackground(Color.gray);
		setFocusable(true);
		setRequestFocusEnabled(true);
		crearPlataformas();
		personaje = new Personaje(this, 300, 600);
		eventos = new EventosAreaJuego(this);
	}
	
	public void crearPlataformas() {
		plataformas = new ArrayList<Plataforma>();
		plataformas.add(new Plataforma(0, 900, 200, 1000));
		plataformas.add(new Plataforma(200, 800, 800, 100));
		plataformas.add(new Plataforma(1200, 800, 800, 100));
		plataformas.add(new Plataforma(1400, 700, 100, 100));
		plataformas.add(new Plataforma(1700, 600, 100, 100));
		plataformas.add(new Plataforma(2000, 700, 800, 100));
		plataformas.add(new Plataforma(1400, 500, 100, 100));
		plataformas.add(new Plataforma(-90, 0, 100, 2000));
		polvos = new ArrayList<Polvo>();
		polvos.add(new Polvo(1,1,10,20));
		polvos.add(new Polvo(1,20,10,20));
		polvos.add(new Polvo(1,40,10,20));
		polvos.add(new Polvo(1,60,10,20));
		polvos.add(new Polvo(1,80,10,20));
		polvos.add(new Polvo(1,100,10,20));
		polvos.add(new Polvo(1,120,10,20));
		polvos.add(new Polvo(1,140,10,20));
		polvos.add(new Polvo(1400,500,20,10));
		polvos.add(new Polvo(1420,500,20,10));
		polvos.add(new Polvo(1440,500,20,10));
		polvos.add(new Polvo(1460,500,20,10));
		polvos.add(new Polvo(1480,500,20,10));
		polvos.add(new Polvo(1480,500,20,10));
		polvos.add(new Polvo(1480,500,20,10));
		polvos.add(new Polvo(2400,700,20,10));
		enemigos = new ArrayList<Enemigo>();
		enemigos.add(new Enemigo(1010,700,50,50,0,5,25,1));
		enemigos.add(new Enemigo(100,100,50,50,-5,5,25,1));
		
	}

	public void paint(Graphics g) {
		super.paint(g);
		for (Plataforma p : plataformas) {
			p.dibujar(g);
		}
		g.setColor(Color.GREEN);
		for (Polvo p : polvos) {
			if (!p.isPisado())
			p.dibujar(g);
		}
		for (Enemigo e : enemigos) {
			e.dibujar(g);
		}
		personaje.dibujar(g);
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

}
