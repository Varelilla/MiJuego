import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.Timer;

public class EventosAreaJuego {
	private final int IZQUIERDA = 0;
	private final int DERECHA = 1;
	private final int ARRIBA = 2;
	private final int DASH = 3;
	private final int RESET = 4;
	private AreaJuego areaJuego;
	private Personaje personaje;
	private Timer reloj;
	
	public EventosAreaJuego(AreaJuego areaJuego) {
		this.areaJuego = areaJuego;
		personaje = areaJuego.getPersonaje();
		registrarEventos();
	    reloj.start();
	}
	
	public void registrarEventos() {
		reloj = new Timer(20, new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				personaje.mover();
				for (Enemigo ene: areaJuego.getEnemigos()) {
					ene.mover();
				}
				areaJuego.repaint();
			}
		});
		
		areaJuego.addKeyListener(new KeyListener() {
			
			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub
				if (e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_A) {
					
					personaje.getKeys()[IZQUIERDA] = true;
				}
				if (e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_D) {
					personaje.getKeys()[DERECHA] = true;
				}
				if (e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_W || e.getKeyCode() == KeyEvent.VK_SPACE) {
					personaje.getKeys()[ARRIBA] = true;
				}
				if (e.getKeyCode() == KeyEvent.VK_R) {
					personaje.getKeys()[RESET] = true;
				}
			}
			
			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				if (e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_A) {
					personaje.getKeys()[IZQUIERDA] = false;
				}
				if (e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_D) {
					personaje.getKeys()[DERECHA] = false;
				}
				if (e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_W || e.getKeyCode() == KeyEvent.VK_SPACE) {
					personaje.getKeys()[ARRIBA] = false;
				}
				if (e.getKeyCode() == KeyEvent.VK_R) {
					personaje.getKeys()[RESET] = false;
				}
			}
			
			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub
				if (e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_A) {
					personaje.getKeys()[IZQUIERDA] = true;
				}
				if (e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_D) {
					personaje.getKeys()[DERECHA] = true;
				}
				if (e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_W || e.getKeyCode() == KeyEvent.VK_SPACE) {
					personaje.getKeys()[ARRIBA] = true;
				}
				if (e.getKeyCode() == KeyEvent.VK_R) {
					personaje.getKeys()[RESET] = true;
				}
			}
		});
	}
}
