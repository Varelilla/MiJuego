import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
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
	private int x = 400;
	private int y = 300;
	private String[] nombres;
	private EventosPanelMenu eventos;
	private Font font;
	
	public PanelMenu(int estado, JuegoOscar juegoOscar) {
		// TODO Auto-generated constructor stub
		super();
		this.juego = juegoOscar;
		contador = 0;
		niveles = new Image[6];
		font = null;
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
			break;
		case JuegoOscar.CONTROLS:
			img = new ImageIcon(getClass().getResource("menu/mainMenu.png")).getImage();
			mundo = false;
			contador = -1;
			x = juego.getWidth()/2 - 300;
			y = juego.getHeight()/2 - 200;
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
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(img, 0, 0,juego.getWidth(),juego.getHeight(), null);
		if(mundo){
			g.drawImage(marcador,x + 140 + ((contador% 3) * 400),y - 70 + ((contador / 3)* 250),null);
			Graphics2D g2d = (Graphics2D) g;
			g2d.setStroke(new BasicStroke(3));
			g.setColor(Color.WHITE);
			g.drawRect(x-2 + ((contador% 3) * 400),y-2 + ((contador / 3)* 250),303,153);
			for (int i = 0; i < niveles.length; i++) {
				g.drawImage(niveles[i], x + ((i % 3) * 400), y + ((i / 3) * 250),300,150, null);
				
				g2d.setColor(new Color(0, 0, 0, 100));
                g2d.fillRect(x + 10 + ((i % 3) * 400), y + 100 + ((i / 3) * 250), 160, 40);
                g.setColor(Color.WHITE);
                g.setFont(font.deriveFont(Font.BOLD,20));
                if(i==5) g.drawString("Volver", x + 15 + ((i % 3) * 400), y + 130 + ((i / 3) * 250));
                else g.drawString("Nivel " + i, x + 15 + ((i % 3) * 400), y + 130 + ((i / 3) * 250));
			}

		} else if (contador > -1) {
			Graphics2D g2d = (Graphics2D) g;
			g2d.setStroke(new BasicStroke(3));
			g.setColor(Color.WHITE);
			g.drawRect(x-2,y-2 + (contador* 85),318,83);
			for (int i = 0; i < 6; i++) {
				g2d.setColor(new Color(0, 0, 0, 100));
				g2d.fillRect(x, y + (i * 85), 315, 80);
				g.setColor(Color.WHITE);
				g.setFont(font.deriveFont(Font.BOLD, 25));
				FontMetrics metrics = g.getFontMetrics();
				int x = (getWidth() - metrics.stringWidth(nombres[i])) / 2; // Centra horizontalmente
				g.drawString(nombres[i], x, y + 55 + (i * 85));
			}
		} else {
			Graphics2D g2d = (Graphics2D) g;
			g2d.setColor(new Color(0, 0, 0, 200));
			g2d.fillRect(x, y, 600, 540);
			String[] instrucciones = nombres[5].split("  ");
			for (int i = 0; i < 6; i++) {
				g.setColor(Color.WHITE);
				g.setFont(font.deriveFont(Font.PLAIN, 13));
				if (i == 5) {
					for (int j = 0; j < instrucciones.length; j++) {
						g.drawString(instrucciones[j], x + 20, y + 100 + (i * 45)+(j*20));
					}
				} else {
					g.drawString(nombres[i], x + 20, y + 50 + (i * 45));
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
}
