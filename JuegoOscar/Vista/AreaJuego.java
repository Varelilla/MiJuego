import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.JPanel;

public class AreaJuego extends JPanel {
	private Personaje personaje;
	private EventosAreaJuego eventos;
	private ArrayList<Plataforma> plataformas;

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
		plataformas.add(new Plataforma(0, 900, 1000, 1000));
		plataformas.add(new Plataforma(200, 800, 800, 800));
		plataformas.add(new Plataforma(1200, 800, 800, 800));
		plataformas.add(new Plataforma(1400, 700, 100, 100));
		plataformas.add(new Plataforma(1700, 600, 100, 100));
		plataformas.add(new Plataforma(1400, 500, 100, 100));
		plataformas.add(new Plataforma(1, 1, 10, 2000));
	}

	public void paint(Graphics g) {
		super.paint(g);
		for (Plataforma p : plataformas) {
			p.dibujar(g);
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

}
