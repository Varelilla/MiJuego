import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class PanelMenu extends JPanel{
	private Image img;
	private Image [] niveles;
	private Image marcador;
	private JuegoOscar juego;
	private int contador;
	private boolean mundo;
	private int x;
	private int y;
	private int xOriginal = 400;
	private int yOriginal = 300;
	private String[] nombres;
	private String[] tiempos = {"", "", "", "", ""}; 
	private String[] puntuaciones = {"", "", "", "", ""};
	private boolean[] completados = {false, false, false, false, false};
	private EventosPanelMenu eventos;
	private Font font;
	private double relX, relY;
	
	public PanelMenu(int estado, JuegoOscar juegoOscar) {
		// TODO Auto-generated constructor stub
		super();
		this.juego = juegoOscar;
		contador = 0;
		niveles = new Image[6];
		font = null;
		x = 400;
		y = 340;
		relX = 1;
		relY = 1;
		try {
            ClassLoader classLoader = PanelMenu.class.getClassLoader();
            InputStream is = classLoader.getResourceAsStream("PressStart2P-Regular.ttf");
            font = Font.createFont(Font.TRUETYPE_FONT, is);
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
        }
		marcador = new ImageIcon(getClass().getResource("menu/flecha.png")).getImage();
		switch (estado) {
		case JuegoOscar.MAINMENU:
			img = new ImageIcon(getClass().getResource("menu/mainMenu.png")).getImage();
			mundo = false;
			nombres = new String[]{"Bosque", "Laboratorio", "Mansion", "Ciudad", "Controles", "Salir"};
			x = juego.getWidth()/2 - 150;
			y = juego.getHeight()/2 - 200;
			xOriginal = x;
			yOriginal = y;
			break;
		case JuegoOscar.CONTROLS:
			img = new ImageIcon(getClass().getResource("menu/mainMenu.png")).getImage();
			mundo = false;
			contador = -1;
			x = juego.getWidth()/2 - 300;
			y = juego.getHeight()/2 - 200;
			xOriginal = x;
			yOriginal = y;
			nombres = new String[]{"Izq, A: Moverse hacia la izquierda.", 
					"Drch, D: Moverse hacia la derecha.", 
					"Arriba, Esp, W: Salto y doble salto.", 
					"R: Reiniciar nivel.",
					"Esc: Pausar el Juego.",
					"El personaje dispone de doble salto que  solo se resetea si pisas el suelo.    "
					+ "El personaje puede posarse en los muros  para reducir la velocidad de caida  y realizar otro salto.    "
					+ "El objetivo es llegar al final de cada  nivel lo mas rapido posible y limpiar  la mayor cantidad de polvo posible."};
			break;
		case JuegoOscar.BOSQUE:
			img = new ImageIcon(getClass().getResource("menu/bosque.png")).getImage();
			for (int i = 0; i < niveles.length; i++) {
                niveles[i] = new ImageIcon(getClass().getResource("menu/bosque/" + (i+1) + ".png")).getImage();
            }
			mundo = true;
			break;
		case JuegoOscar.LABORATORIO:
			img = new ImageIcon(getClass().getResource("menu/laboratorio.png")).getImage();
			for (int i = 0; i < niveles.length; i++) {
                niveles[i] = new ImageIcon(getClass().getResource("menu/laboratorio/" + (i+1) + ".png")).getImage();
            }
			mundo = true;
			break;
		case JuegoOscar.MANSION:
			img = new ImageIcon(getClass().getResource("menu/mansion.png")).getImage();
			for (int i = 0; i < niveles.length; i++) {
                niveles[i] = new ImageIcon(getClass().getResource("menu/mansion/" + (i+1) + ".png")).getImage();
            }
			mundo = true;
			break;
		case JuegoOscar.CIUDAD:
			img = new ImageIcon(getClass().getResource("menu/ciudad.png")).getImage();
			for (int i = 0; i < niveles.length; i++) {
                niveles[i] = new ImageIcon(getClass().getResource("menu/ciudad/" + (i+1) + ".png")).getImage();
            }
			mundo = true;
			break;
		}
		eventos = new EventosPanelMenu(this);
		
	}
	
	public void cambiarTamaño(int x, int y) {
		relX = (double) x/1900;
		relY = (double) y/980;
		this.x = (int)(xOriginal * relX);
		this.y = (int)(yOriginal * relY);
		this.repaint();
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(img, 0, 0,juego.getWidth(),juego.getHeight(), null);
		if(mundo){
			g.drawImage(marcador,x + (int)(140*relX) + ((contador% 3) * (int)(400*relX)),y - (int)(70*relY) + ((contador / 3)* (int)(250*relY)),null);
			Graphics2D g2d = (Graphics2D) g;
			g2d.setStroke(new BasicStroke(3));
			g.setColor(Color.WHITE);
			g.drawRect(x-2 + ((contador% 3) * (int)(400)),y-2 + ((contador / 3)* (int)(250*relY)),(int)(303*relX),(int)(153*relY));
			for (int i = 0; i < niveles.length; i++) {
				if (i == 0 || i == niveles.length - 1) {
					g.drawImage(niveles[i], x + ((i % 3) * (int)(400*relX)), y + ((i / 3) * (int)(250*relY)),(int)(300*relX),(int)(150*relY), null);	
				} else {
					if (completados[i - 1]) {
						g.drawImage(niveles[i], x + ((i % 3) * (int)(400*relX)), y + ((i / 3) * (int)(250*relY)),(int)(300*relX),(int)(150*relY), null);	
					} else {
						//dibujamos imagen en gris
						BufferedImage imagenGris = new BufferedImage(400, 250, BufferedImage.TYPE_BYTE_GRAY);
						Graphics2D prueba = imagenGris.createGraphics();
						prueba.drawImage(niveles[i], 0, 0, null);
						prueba.dispose();
	
						// Dibujar la imagen en tonos de gris
						g.drawImage(imagenGris, x + ((i % 3) * (int)(400*relX)), y + ((i / 3) * (int)(250*relY)), (int)(300*relX), (int)(150*relY), null);
					}
				}
				g2d.setColor(new Color(0, 0, 0, 100));
                g2d.fillRect(x + 10 + ((i % 3) * (int)(400*relX)),y + (int)(100*relY) + ((i / 3) * (int)(250*relY)), (int)(160*relX), (int)(40*relY));
                g.setColor(Color.WHITE);
                g.setFont(font.deriveFont(Font.BOLD,(int)(20*relX)));
                if(i==5) g.drawString("Volver", x + (int)(15*relX) + ((i % 3) * (int)(400*relX)), y + (int)(130*relY) + ((i / 3) * (int)(250*relY)));
                else g.drawString("Nivel " + i, x + (int)(15*relX) + ((i % 3) * (int)(400*relX)), y + (int)(130*relY) + ((i / 3) * (int)(250*relY)));
                if (i<5) {
                	if (puntuaciones[i].length() > 0) {
                		// Si tiene puntuacion dibujamos la imagen a la izquierda del drawString del nivel
                		Image puntuacion = new ImageIcon(getClass().getResource(puntuaciones[i])).getImage();
                		g.drawImage(puntuacion, x + (int)(230*relX) + ((i % 3) * (int)(400*relX)), y + (int)(80*relY) + ((i / 3) * (int)(250*relY)), (int)(70*relX), (int)(70*relY), null);
                	}
                }
			}

		} else if (contador > -1) {
			Graphics2D g2d = (Graphics2D) g;
			g2d.setStroke(new BasicStroke(3));
			g.setColor(Color.WHITE);
			g.drawRect(x-2,y-2 + (int)((contador* 85) * relY),(int)(318 * relX),(int)(83 * relY));
			for (int i = 0; i < 6; i++) {
				g2d.setColor(new Color(0, 0, 0, 100));
				g2d.fillRect(x, y + (int)((i * 85) * relY), (int)(315 * relX),(int) (80 * relY));
				g.setColor(Color.WHITE);
				g.setFont(font.deriveFont(Font.BOLD, (int)(25 * relX)));
				FontMetrics metrics = g.getFontMetrics();
				int x = (getWidth() - metrics.stringWidth(nombres[i])) / 2; // Centra horizontalmente
				g.drawString(nombres[i], x+15, y + (int)(55*relY) + (int)((i * 85) *relY));
			}
		} else {
			Graphics2D g2d = (Graphics2D) g;
			g2d.setColor(new Color(0, 0, 0, 200));
			g2d.fillRect(x, y, (int)(600*relX), (int)(540*relY));
			String[] instrucciones = nombres[5].split("  ");
			for (int i = 0; i < 6; i++) {
				g.setColor(Color.WHITE);
				g.setFont(font.deriveFont(Font.PLAIN, (int)(13*relX)));
				if (i == 5) {
					for (int j = 0; j < instrucciones.length; j++) {
						g.drawString(instrucciones[j], x + (int)(20*relX), y + (int)(100*relY) + (int)((i * 45)*relY)+(int)((j*20)*relY));
					}
				} else {
					g.drawString(nombres[i], x + (int)(20*relY), y + (int)(50*relY) + (int)((i * 45)*relY));
				}

			}
		}
	}

	public int getContador() {
		return contador;
	}

	public void setContador(int contador) {
		this.contador = contador;
	}

	public JuegoOscar getJuego() {
		return juego;
	}

	public void setJuego(JuegoOscar juego) {
		this.juego = juego;
	}

	public boolean isMundo() {
		return mundo;
	}

	public void setMundo(boolean mundo) {
		this.mundo = mundo;
	}

	public String[] getTiempos() {
		return tiempos;
	}

	public void setTiempos(String[] tiempos) {
		this.tiempos = tiempos;
	}

	public String[] getPuntuaciones() {
		return puntuaciones;
	}

	public void setPuntuaciones(String[] puntuaciones) {
		this.puntuaciones = puntuaciones;
	}

	public boolean[] getCompletados() {
		return completados;
	}

	public void setCompletados(boolean[] completados) {
		this.completados = completados;
	}
}
