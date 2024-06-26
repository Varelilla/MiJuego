import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class AreaJuego extends JPanel {
	private Personaje personaje;
	private EventosAreaJuego eventos;
	private ArrayList<Plataforma> plataformas = new ArrayList<Plataforma>();
	private ArrayList<Polvo> polvos = new ArrayList<Polvo>();
	private ArrayList<Enemigo> enemigos = new ArrayList<Enemigo>();
	private ArrayList<Obstaculo> obstaculos = new ArrayList<Obstaculo>();
	private int xOriginal = 1900, yOriginal = 980;
	private double relX = 1,relY = 1;
	private Image image;
	private JuegoOscar juegoOscar;
	private int minutos;
    private int segundos;
    private int decimasSegundo;
    private int objetosRecogidos;
    private int totalObjetos =  10;
    private long tiempoInicio = System.currentTimeMillis();
    private Font font;
    private Polvo finalPolvo;
    private boolean finalizado = false;
    private boolean esperandoConfirmacion = false;
    private int botonSeleccionado = 0;
    private PanelMenu pnlMenu;
    private String record;
    private String rutarRecord;
    private int nivelPanelAnterior;
    private String[] niveles = {"/niveles/nivel1.tmx", "/niveles/nivel2.tmx", "/niveles/nivel3.tmx", "/niveles/nivel4.tmx", "/niveles/nivel5.tmx"
    		, "/niveles/nivel6.tmx", "/niveles/nivel7.tmx", "/niveles/nivel8.tmx", "/niveles/nivel9.tmx", "/niveles/nivel10.tmx"
    		, "/niveles/nivel11.tmx", "/niveles/nivel12.tmx", "/niveles/nivel13.tmx", "/niveles/nivel14.tmx", "/niveles/nivel15.tmx"
    		, "/niveles/nivel16.tmx", "/niveles/nivel17.tmx", "/niveles/nivel18.tmx", "/niveles/nivel19.tmx", "/niveles/nivel20.tmx"};
    private long tiempos[] = {30, 30, 30, 30, 20, 25, 15, 25, 25, 0, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40};
    long tiempoFinal = 40;
    private int[] iniciosX = {0,0,0,0,1200,0,0,2200,-200,1200,300,300,300,300,300,300,300,300,300,300};
    private int[] iniciosY = {800,-3000,-1000,800,800,300,-3000,-1100,800,800,600,600,600,600,600,600,600,600,600,600};
    private boolean nivelFinal = false;
    private String rutaMundo;
    

	/**
	 * Create the panel.
	 */
	public AreaJuego(int contador,JuegoOscar juegoOscar, PanelMenu pnlMenu) {
		nivelPanelAnterior = contador;
		this.juegoOscar = juegoOscar;
		setBounds(0, 0, 300, 100);
		setBackground(Color.gray);
		setFocusable(true);
		this.pnlMenu = pnlMenu;
		setRequestFocusEnabled(true);
		//crearPlataformas();
		switch (pnlMenu.getEstado()) {
		case JuegoOscar.BOSQUE:
			rutaMundo = "bosque";
			break;
		case JuegoOscar.LABORATORIO:
			rutaMundo = "lab";
			break;
		case JuegoOscar.MANSION:
			rutaMundo = "mansion";
			break;
		case JuegoOscar.CIUDAD:
			rutaMundo = "ciudad";
			break;
		}
		cargarNivel(niveles[contador]);
		tiempoFinal = tiempos[contador];
		personaje = new Personaje(this, 300, 600);
		personaje.setxScroll(iniciosX[contador]);
		personaje.setyScroll(iniciosY[contador]);
		personaje.setXScrollOriginal(iniciosX[contador]);
		personaje.setYScrollOriginal(iniciosY[contador]);
		try {
            ClassLoader classLoader = PanelMenu.class.getClassLoader();
            InputStream is = classLoader.getResourceAsStream("PressStart2P-Regular.ttf");
            font = Font.createFont(Font.TRUETYPE_FONT, is);
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
        }
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

		// Dibujar polvos
		for (Polvo p : polvos) {
			if (!p.isPisado())
				p.dibujar(g2d); // Utilizar g2d en lugar de g
		}

		finalPolvo.dibujar(g2d);
		// Dibujar obstaculo

		// Dibujar plataformas
		for (Plataforma p : plataformas) {
			p.dibujar(g2d); // Utilizar g2d en lugar de g
		}

		// Dibujar enemigos
		for (Enemigo e : enemigos) {
			e.dibujar(g2d); // Utilizar g2d en lugar de g
		}

		// Dibujar obstaculos
		for (Obstaculo o : obstaculos) {
			o.dibujar(g2d); // Utilizar g2d en lugar de g
		}

		// Dibujar personaje
		personaje.dibujar(g2d); // Utilizar g2d en lugar de g


		// Dibuja los elementos del juego

		// Dibuja la información del nivel
		g2d.setColor(new Color(0, 0, 0, 128));
		g2d.fillRect(0, 0, 3000, 50);
		
		// Dibujamos el record y su puntuacion si este nivel ya ha sido jugado
		if (!record.equals("")) {
			g2d.setColor(Color.WHITE);
			g2d.setFont(font.deriveFont(Font.PLAIN, 30));
			g2d.drawString("Record:" + record, 10, 42);
			g2d.drawImage(new ImageIcon(getClass().getResource(rutarRecord)).getImage(), 470, 5, 40, 40, null);
		}

		// Establece la fuente y el tamaño del texto
		g2d.setFont(font.deriveFont(Font.PLAIN, 30)); 
		g2d.setColor(Color.WHITE);
		g2d.drawString(String.format("%02d:%02d:%02d", minutos, segundos, decimasSegundo),1630, 42);
		g2d.drawString(objetosRecogidos + "/" + totalObjetos, 1430, 42);
		if (finalizado) {
			eventos.getReloj().stop();
			mostrarAnimacionFinal(g);
			if (!esperandoConfirmacion) {
				long tiempoActual = System.currentTimeMillis();
				long tiempoTranscurrido = tiempoActual - tiempoInicio;
				eventos.setTiempoActual(System.currentTimeMillis());
				eventos.setTiempoRestante(tiempoTranscurrido);
				eventos.getRelojAnimacion().setDelay((int) (2000/eventos.getTiempoRestante()));
				eventos.getRelojAnimacion().start();
				esperandoConfirmacion = true;
			}
		} 

		g2d.dispose();

	}
	
	public void comprobarRecord(String puntuacion, long tiempoFinal) {
		// Esta funcion comprueba si la puntuacion recibida es mayor que la que teniamos, y en ese caso cambia los valores tanto del propia areaJuego, como del panelMenu
		int minutos = (int) (tiempoFinal / 60000);
		int segundos = (int) ((tiempoFinal / 1000) % 60);
		int decimas = (int) ((tiempoFinal / 10) % 100);
		int aux = 0;
		switch (pnlMenu.getEstado()) {
		case JuegoOscar.BOSQUE:
			aux = 0;
			break;
		case JuegoOscar.LABORATORIO:
			aux = 5;
			break;
		case JuegoOscar.MANSION:
			aux = 10;
			break;
		case JuegoOscar.CIUDAD:
			aux = 15;
			break;
		}

		String tiempoFormateado = String.format("%02d:%02d:%02d", minutos, segundos, decimas);
		if (record.equals("")) {
			record = tiempoFormateado;
			rutarRecord = puntuacion;
			pnlMenu.getTiempos()[nivelPanelAnterior-aux] = tiempoFormateado;
			pnlMenu.getPuntuaciones()[nivelPanelAnterior-aux] = puntuacion;
		} else {
			if (sacarValorPuntuacion(puntuacion) > sacarValorPuntuacion(rutarRecord)) {
				record = tiempoFormateado;
				rutarRecord = puntuacion;
				pnlMenu.getTiempos()[nivelPanelAnterior-aux] = tiempoFormateado;
				pnlMenu.getPuntuaciones()[nivelPanelAnterior-aux] = puntuacion;
			} else if (sacarValorPuntuacion(puntuacion) == sacarValorPuntuacion(rutarRecord) && tiempoFinal < eventos.getTiempoFinal()) {
				record = tiempoFormateado;
				rutarRecord = puntuacion;
				pnlMenu.getTiempos()[nivelPanelAnterior-aux] = tiempoFormateado;
				pnlMenu.getPuntuaciones()[nivelPanelAnterior-aux] = puntuacion;
			}
		}
		if (sacarValorPuntuacion(puntuacion) > 2) {
			pnlMenu.getCompletados()[nivelPanelAnterior-aux] = true;
		}
		pnlMenu.getJuego().guardarDatos("datos.txt");
	}
	
	public int sacarValorPuntuacion(String puntuacion) {
		// Funcion para darle valor numerico a las puntuaciones para compararlas
		switch (puntuacion) {
		case "puntuaciones/SSS.png":
			return 6;
		case "puntuaciones/SS.png":
			return 5;
		case "puntuaciones/S.png":
			return 4;
		case "puntuaciones/A.png":
			return 3;
		case "puntuaciones/B.png":
			return 2;
		case "puntuaciones/C.png":
			return 1;
		default:
			return 0;
		}
	}

	public void actualizarCronometro() {
        long tiempoActual = System.currentTimeMillis();
        long tiempoTranscurrido = tiempoActual - tiempoInicio;

        // Calcula minutos, segundos y décimas de segundo
        minutos = (int) (tiempoTranscurrido / 60000);
        segundos = (int) ((tiempoTranscurrido / 1000) % 60);
        decimasSegundo = (int) ((tiempoTranscurrido / 10) % 100);
    }
	
	public void cargarNivel(String nombreArchivo) {
		try {
		    File file = new File(getClass().getResource(nombreArchivo).getFile());
		    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		    DocumentBuilder builder = factory.newDocumentBuilder();
		    Document doc = builder.parse(file);
		    doc.getDocumentElement().normalize();

		    NodeList layerList = doc.getElementsByTagName("layer");

		    for (int l = 0; l < layerList.getLength(); l++) {
		        Node layerNode = layerList.item(l);
		        if (layerNode.getNodeType() == Node.ELEMENT_NODE) {
		            Element layerElement = (Element) layerNode;
		            NodeList dataNodes = layerElement.getElementsByTagName("data");
		            if (dataNodes.getLength() > 0) {
		                Element dataElement = (Element) dataNodes.item(0);
		                String encoding = dataElement.getAttribute("encoding");
		                String data = dataElement.getTextContent().trim();

		                if (encoding.equals("csv")) {
		                    String[] rows = data.split("\n");
		                    for (int y = 0; y < rows.length; y++) {
		                        String[] tiles_data = rows[y].split(",");
		                        for (int x = 0; x < tiles_data.length; x++) {
		                            int tileValue = Integer.parseInt(tiles_data[x].trim());
		                            int real_x = x;
		                            int real_y = y;

		                            // Check if the current tile is part of a group
		                            if (tileValue == 1 || tileValue == 2 || tileValue == 13) {
		                                // Calculate the width of the platform or obstacle
		                                int width = 1;
		                                while (x + width < tiles_data.length && Integer.parseInt(tiles_data[x + width].trim()) == tileValue) {
		                                    width++;
		                                }

		                                // Adjust the width of the current tile group
		                                x += width - 1;

		                                // Create a platform or obstacle based on the tile value and width
		                                if (tileValue == 1 || tileValue == 13) {
		                                    plataformas.add(new Plataforma(real_x * 100, real_y * 100 - 3000, width * 100, 100, pnlMenu.getEstado()));
		                                    // Generate a platform at position (real_x, real_y) with extended width
		                                } else if (tileValue == 2) {
		                                    obstaculos.add(new Obstaculo(real_x * 100, (real_y * 100) - 3000, width * 100, 100, pnlMenu.getEstado()));
		                                    // Generate an obstacle at position (real_x, real_y) with extended width
		                                } 
		                            } else if (tileValue == 3 || tileValue == 4) {
                                        plataformas.add(new Plataforma(real_x * 100, real_y * 100 - 3000, 100, 100, tileValue,pnlMenu.getEstado()));
	                                } else if (tileValue == 8) {
	                                	finalPolvo = new Polvo((real_x * 100) + 25, (real_y * 100 - 3000) + 25, 75, 75, true, pnlMenu.getEstado());
	                                } else if (tileValue == 9) {
	                                	polvos.add(new Polvo((real_x * 100) + 25, (real_y * 100 - 3000) + 25, 50, 50, pnlMenu.getEstado()));
									} else if (tileValue == 5 ||  tileValue == 6 || tileValue == 7) {
										obstaculos.add(new Obstaculo(real_x * 100, real_y * 100 - 3000, 100, 100, tileValue-4,pnlMenu.getEstado()));
									} else if (tileValue == 12) {
										plataformas.add(new Plataforma(real_x * 100, real_y * 100 - 3000, 100, 100, tileValue,pnlMenu.getEstado()));
									} else if (tileValue == 11) {
										enemigos.add(new Enemigo(real_x * 100, real_y * 100 - 3000, 100, 100, 0, -7, 0, pnlMenu.getEstado()));
										enemigos.get(enemigos.size()-1).setTipoEnemigo(pnlMenu.getEstado());
									} else if (tileValue == 10) {
										enemigos.add(new Enemigo(real_x * 100, real_y * 100 - 3000, 100, 100, -7, 0, 0, pnlMenu.getEstado()));
										enemigos.get(enemigos.size()-1).setTipoEnemigo(pnlMenu.getEstado());
									}
		                        }
		                    }
		                }
		            }
		        }
		    }
		} catch (Exception e) {
		    e.printStackTrace();
		}
    }
	
	public void terminar() {
		finalizado = true;
	}
	
	public String calcularPuntuacion(long tiempoActual, int objetosRecogidos) {
		String puntuacion = "";
		
		tiempoFinal+=objetosRecogidos*3;
		if (tiempoActual <= tiempoFinal) {
            puntuacion = "puntuaciones/SSS.png";
        } else if (tiempoActual <= tiempoFinal + 10) {
            puntuacion = "puntuaciones/SS.png";
        } else if (tiempoActual <= tiempoFinal + 20) {
            puntuacion = "puntuaciones/S.png";
        } else if (tiempoActual <= tiempoFinal + 30) {
            puntuacion = "puntuaciones/A.png";
        } else if (tiempoActual <= tiempoFinal + 40) {
            puntuacion = "puntuaciones/B.png";
        } else {
            puntuacion = "puntuaciones/C.png";
        }
		return puntuacion;
	}

	public void mostrarAnimacionFinal(Graphics g) {
	    // Dibuja la animación de finalización del juego
	    // Puedes usar g.drawString() para mostrar la puntuación
		g.setColor(new Color(0, 0, 0, 128));
        g.fillRect(700, 300, 500, 300);

        g.setColor(Color.WHITE);
        g.setFont(font.deriveFont(Font.PLAIN, 30));

        // Mostrar el tiempo restante
        long minutos = eventos.getTiempoRestante() / 60;
        long segundos = eventos.getTiempoRestante() % 60;
        g.drawString("Tiempo: " + minutos + "m " + segundos + "s", 750, 350);

        // Mostrar el conteo de objetos recogidos
		for (int i = 0; i < eventos.getObjetosContados(); i++) {
			g.drawImage(new ImageIcon(getClass().getResource("objetos/escoba.png")).getImage(), 710 + (i * 50), 370, 40,
					40, null);
		}

        // Mostrar la puntuación sancando la imagen de la carpeta de recursos
		//ruta = "puntuaciones/SSS.png";
		Image puntuacion = new ImageIcon(getClass().getResource(eventos.getPuntuacion())).getImage();
		g.drawImage(puntuacion, 850, 430, 200, 100, null);
        
        g.setColor(Color.BLUE);
        g.fillRect(775, 550, 150, 40); // Botón para volver al menú
        g.fillRect(975, 550, 150, 40); // Botón para repetir el nivel

        // Cambiar el color del botón seleccionado
        g.setColor(Color.RED);
        if (botonSeleccionado == 0) {
            g.drawRect(775, 550, 150, 40);
        } else {
            g.drawRect(975, 550, 150, 40);
        }

        // Escribir el texto en los botones
        g.setColor(Color.WHITE);
        g.setFont(font.deriveFont(Font.BOLD, 14));
        g.drawString("Repetir", 800, 580);
        g.drawString("Continuar", 990, 580);
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

	public ArrayList<Obstaculo> getObstaculos() {
		return obstaculos;
	}

	public void setObstaculos(ArrayList<Obstaculo> obstaculos) {
		this.obstaculos = obstaculos;
	}

	public int getxOriginal() {
		return xOriginal;
	}

	public void setxOriginal(int xOriginal) {
		this.xOriginal = xOriginal;
	}

	public int getyOriginal() {
		return yOriginal;
	}

	public void setyOriginal(int yOriginal) {
		this.yOriginal = yOriginal;
	}

	public double getRelX() {
		return relX;
	}

	public void setRelX(double relX) {
		this.relX = relX;
	}

	public double getRelY() {
		return relY;
	}

	public void setRelY(double relY) {
		this.relY = relY;
	}

	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}

	public int getMinutos() {
		return minutos;
	}

	public void setMinutos(int minutos) {
		this.minutos = minutos;
	}

	public int getSegundos() {
		return segundos;
	}

	public void setSegundos(int segundos) {
		this.segundos = segundos;
	}

	public int getDecimasSegundo() {
		return decimasSegundo;
	}

	public void setDecimasSegundo(int decimasSegundo) {
		this.decimasSegundo = decimasSegundo;
	}

	public int getObjetosRecogidos() {
		return objetosRecogidos;
	}

	public void setObjetosRecogidos(int objetosRecogidos) {
		this.objetosRecogidos = objetosRecogidos;
	}

	public int getTotalObjetos() {
		return totalObjetos;
	}

	public void setTotalObjetos(int totalObjetos) {
		this.totalObjetos = totalObjetos;
	}

	public long getTiempoInicio() {
		return tiempoInicio;
	}

	public void setTiempoInicio(long tiempoInicio) {
		this.tiempoInicio = tiempoInicio;
	}

	public Font getFont() {
		return font;
	}

	public void setFont(Font font) {
		this.font = font;
	}

	public Polvo getFinalPolvo() {
		return finalPolvo;
	}

	public void setFinalPolvo(Polvo finalPolvo) {
		this.finalPolvo = finalPolvo;
	}

	public boolean isFinalizado() {
		return finalizado;
	}

	public void setFinalizado(boolean finalizado) {
		this.finalizado = finalizado;
	}

	public boolean isEsperandoConfirmacion() {
		return esperandoConfirmacion;
	}

	public void setEsperandoConfirmacion(boolean esperandoConfirmacion) {
		this.esperandoConfirmacion = esperandoConfirmacion;
	}

	public int getBotonSeleccionado() {
		return botonSeleccionado;
	}

	public void setBotonSeleccionado(int botonSeleccionado) {
		this.botonSeleccionado = botonSeleccionado;
	}

	public PanelMenu getPnlMenu() {
		return pnlMenu;
	}

	public void setPnlMenu(PanelMenu pnlMenu) {
		this.pnlMenu = pnlMenu;
	}

	public String getRecord() {
		return record;
	}

	public void setRecord(String record) {
		this.record = record;
	}

	public String getRutarRecord() {
		return rutarRecord;
	}

	public void setRutarRecord(String rutarRecord) {
		this.rutarRecord = rutarRecord;
	}

	public boolean isNivelFinal() {
		return nivelFinal;
	}

	public void setNivelFinal(boolean nivelFinal) {
		this.nivelFinal = nivelFinal;
	}

	public String getRutaMundo() {
		return rutaMundo;
	}

	public void setRutaMundo(String rutaMundo) {
		this.rutaMundo = rutaMundo;
	}

}
