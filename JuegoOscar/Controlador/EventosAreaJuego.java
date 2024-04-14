import java.awt.BorderLayout;
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
	private Timer relojAnimacion;
	private int objetosContados = 0;
    private long tiempoRestante;
    private String puntuacion = "puntuaciones/SSS.png";
    private long tiempoFinal;
    private long tiempoActual;
    private long tiempoPasado;
    private int contador;
	
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
				areaJuego.actualizarCronometro();
				areaJuego.repaint();
			}
		});
		
		relojAnimacion = new Timer(10, new ActionListener() {

			
			@Override
			public void actionPerformed(ActionEvent e) {
				if (tiempoRestante <= 0) {
					tiempoRestante = 0;
					areaJuego.comprobarRecord(puntuacion, tiempoFinal);
				} else {
					// Animación: contar los objetos recogidos
					tiempoRestante--;

					// Calcular la puntuación basada en el tiempo restante
					puntuacion = areaJuego.calcularPuntuacion((tiempoFinal/1000)-tiempoRestante,objetosContados);

					// Incrementar la cantidad de objetos contados gradualmente
					contador ++;
					if (contador >= 2000/relojAnimacion.getDelay()/10) {
						contador = 0;
						if (objetosContados < areaJuego.getObjetosRecogidos()) {
							objetosContados++;
						}
					}
				}

                areaJuego.repaint(); // Volver a dibujar el JPanel
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
					if (areaJuego.isEsperandoConfirmacion()) {
						areaJuego.setBotonSeleccionado((areaJuego.getBotonSeleccionado() - 1)%2);
					}
					personaje.getKeys()[IZQUIERDA] = false;
				}
				if (e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_D) {
					if (areaJuego.isEsperandoConfirmacion()) {
						areaJuego.setBotonSeleccionado((areaJuego.getBotonSeleccionado() + 1)%2);
					}
					personaje.getKeys()[DERECHA] = false;
				}
				if (e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_W || e.getKeyCode() == KeyEvent.VK_SPACE) {
					personaje.getKeys()[ARRIBA] = false;
				}
				if (e.getKeyCode() == KeyEvent.VK_R) {
					personaje.getKeys()[RESET] = false;
				}
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					if (areaJuego.isEsperandoConfirmacion()) {
						if (areaJuego.getBotonSeleccionado() == 0) {
							areaJuego.setEsperandoConfirmacion(false);
							areaJuego.setFinalizado(false);
							contador = 0;
							objetosContados = 0;
							relojAnimacion.stop();
							reloj.start();
							areaJuego.getPersonaje().restart();
						} else {
							areaJuego.setEsperandoConfirmacion(false);
							areaJuego.getJuegoOscar().getContentPane().removeAll();
							areaJuego.getJuegoOscar().getContentPane().add(areaJuego.getPnlMenu(), BorderLayout.CENTER);
							areaJuego.getPnlMenu().requestFocus();
							areaJuego.getJuegoOscar().revalidate();
							areaJuego.getJuegoOscar().repaint();
							// Por ultimo eliminamos areaJuego para no seguir consumiendo recursos
							relojAnimacion.stop();
							reloj.stop();
							areaJuego = null;
						}
					}
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

	public Timer getRelojAnimacion() {
		return relojAnimacion;
	}

	public void setRelojAnimacion(Timer relojAnimacion) {
		this.relojAnimacion = relojAnimacion;
	}

	public int getObjetosContados() {
		return objetosContados;
	}

	public void setObjetosContados(int objetosContados) {
		this.objetosContados = objetosContados;
	}

	public String getPuntuacion() {
		return puntuacion;
	}

	public void setPuntuacion(String puntuacion) {
		this.puntuacion = puntuacion;
	}

	public long getTiempoRestante() {
		return tiempoRestante;
	}

	public void setTiempoRestante(long tiempoRestante) {
		this.tiempoRestante = tiempoRestante/1000;
		this.tiempoFinal = tiempoRestante;
	}

	public long getTiempoFinal() {
		return tiempoFinal;
	}

	public void setTiempoFinal(long tiempoFinal) {
		this.tiempoFinal = tiempoFinal;
	}

	public long getTiempoActual() {
		return tiempoActual;
	}

	public void setTiempoActual(long tiempoActual) {
		this.tiempoActual = tiempoActual;
	}

	public long getTiempoPasado() {
		return tiempoPasado;
	}

	public void setTiempoPasado(long tiempoPasado) {
		this.tiempoPasado = tiempoPasado;
	}

	public Timer getReloj() {
		return reloj;
	}

	public void setReloj(Timer reloj) {
		this.reloj = reloj;
	}
}
