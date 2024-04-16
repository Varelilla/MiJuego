import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.border.EmptyBorder;


public class JuegoOscar extends JFrame {

	private static final long serialVersionUID = 1L;
	public static final int MAINMENU = 0;
	public static final int CONTROLS = 1;
	public static final int BOSQUE = 2;
	public static final int LABORATORIO = 3;
	public static final int MANSION = 4;
	public static final int CIUDAD = 5;
	
	private AreaJuego areaJuego;
	private JButton btnBosque;
	private JButton btnLaboratorio;
	private JButton btnMansion;
	private JButton btnCiudad;
	private JButton btnControles;
	private JButton back;
	private JButton back1;
	private JButton back2;
	private JButton back3;
	private JButton back4;
	private PanelMenu pnlMansion;
	private PanelMenu pnlCiudad;
	private PanelMenu pnlBosque;
	private PanelMenu pnlLaboratorio;
	private PanelMenu pnlControles;
	private PanelMenu mainmenu;
	private EventosJuegoOscar eventos;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					JuegoOscar frame = new JuegoOscar();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 * @param state 
	 */
	public JuegoOscar() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//setExtendedState(JFrame.MAXIMIZED_BOTH);
		//setUndecorated(true);
		setBounds(0, 0, 1900, 980);

		setMinimumSize(new Dimension(950, 490));

		

		pnlCiudad = new PanelMenu(CIUDAD,this);
		pnlMansion = new PanelMenu(MANSION,this);
		pnlBosque = new PanelMenu(BOSQUE,this);
		pnlControles = new PanelMenu(CONTROLS,this);
		pnlLaboratorio= new PanelMenu(LABORATORIO,this);
		cargarDatos("datos.txt");
		areaJuego = new AreaJuego(0,this,pnlBosque);
		areaJuego.setBorder(new EmptyBorder(5, 5, 5, 5));
		mainmenu = new PanelMenu(MAINMENU,this);
			    
		setTitle("DustForce");
		this.getContentPane().add(mainmenu);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//setContentPane(areaJuego);
		mainmenu.setFocusable(true);
		mainmenu.requestFocus();
		eventos = new EventosJuegoOscar(this);
	}
	
	public void guardarDatos(String nombreArchivo) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(nombreArchivo))) {
            for (int i = 0; i < 4; i++) {
                writer.write("Mundo " + i + ":\n");
                PanelMenu aux = null;
				switch (i) {
				case 0:
					aux = pnlBosque;
					break;
				case 1:
					aux = pnlLaboratorio;
					break;
				case 2:
					aux = pnlMansion;
					break;
				case 3:
					aux = pnlCiudad;
					break;
				}
                // Escribir tiempos
                for (String tiempo : aux.getTiempos()) {
                    writer.write(tiempo + " ,");
                }
                writer.newLine();

                // Escribir puntuaciones
                for (String puntuacion : aux.getPuntuaciones()) {
                    writer.write(puntuacion + " ,");
                }
                writer.newLine();

                // Escribir completados
                for (boolean completado : aux.getCompletados()) {
                    writer.write(completado + " ,");
                }
                aux = null;
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error al guardar los datos: " + e.getMessage());
        }
    }

    // Función para cargar los datos de todos los mundos desde un archivo
    public void cargarDatos(String nombreArchivo) {
        try (BufferedReader reader = new BufferedReader(new FileReader(nombreArchivo))) {
            String line;
            int mundo = 0;
            while ((line = reader.readLine()) != null && mundo < 4) {
                if (line.startsWith("Mundo")) {
                    // Saltar la línea de identificación del mundo
                	PanelMenu aux = null;
                	switch (mundo) {
                	case 0:
                		aux = pnlBosque;
                		break;
                	case 1:
                		aux = pnlLaboratorio;
                		break;
                	case 2:
                		aux = pnlMansion;
                		break;
                	case 3:
                		aux = pnlCiudad;
                		break;
                	}
                    line = reader.readLine();
                    String[] datos = line.split(",");
					for (int i = 0; i < datos.length; i++) {
						datos[i] = datos[i].trim();
					}
                    aux.setTiempos(datos);

                    line = reader.readLine();
                    datos = line.split(",");
					for (int i = 0; i < datos.length; i++) {
						datos[i] = datos[i].trim();
					}
                    aux.setPuntuaciones(datos);

                    line = reader.readLine();
                    datos = line.split(",");
                    boolean[] completados = new boolean[datos.length];
                    for (int j = 0; j < datos.length; j++) {
                        completados[j] = Boolean.parseBoolean(datos[j].trim());
                    }
                    aux.setCompletados(completados);
                    mundo++;
                }
            }
        } catch (IOException e) {
            System.err.println("Error al cargar los datos: " + e.getMessage());
        }
    }


	
	public void cambiarTamaño(int x, int y) {
		mainmenu.cambiarTamaño(x,y);
		pnlControles.cambiarTamaño(x,y);
		pnlBosque.cambiarTamaño(x, y);
		pnlLaboratorio.cambiarTamaño(x, y);
		pnlMansion.cambiarTamaño(x, y);
		pnlCiudad.cambiarTamaño(x, y);
		areaJuego.cambiarTamaño(x, y);

	}

	public JButton getBtnBosque() {
		return btnBosque;
	}

	public void setBtnBosque(JButton btnBosque) {
		this.btnBosque = btnBosque;
	}

	public JButton getBtnLaboratorio() {
		return btnLaboratorio;
	}

	public void setBtnLaboratorio(JButton btnLaboratorio) {
		this.btnLaboratorio = btnLaboratorio;
	}

	public JButton getBtnMansion() {
		return btnMansion;
	}

	public void setBtnMansion(JButton btnMansion) {
		this.btnMansion = btnMansion;
	}

	public JButton getBtnCiudad() {
		return btnCiudad;
	}

	public void setBtnCiudad(JButton btnCiudad) {
		this.btnCiudad = btnCiudad;
	}

	public JButton getBtnControles() {
		return btnControles;
	}

	public void setBtnControles(JButton btnControles) {
		this.btnControles = btnControles;
	}

	public JButton getBack() {
		return back;
	}

	public void setBack(JButton back) {
		this.back = back;
	}

	public JButton getBack1() {
		return back1;
	}

	public void setBack1(JButton back1) {
		this.back1 = back1;
	}

	public JButton getBack2() {
		return back2;
	}

	public void setBack2(JButton back2) {
		this.back2 = back2;
	}

	public JButton getBack3() {
		return back3;
	}

	public void setBack3(JButton back3) {
		this.back3 = back3;
	}

	public PanelMenu getPnlBosque() {
		return pnlBosque;
	}

	public void setPnlBosque(PanelMenu pnlBosque) {
		this.pnlBosque = pnlBosque;
	}

	public PanelMenu getPnlLaboratorio() {
		return pnlLaboratorio;
	}

	public void setPnlLaboratorio(PanelMenu pnlLaboratorio) {
		this.pnlLaboratorio = pnlLaboratorio;
	}

	public JPanel getPnlControles() {
		return pnlControles;
	}

	public void setPnlControles(PanelMenu pnlControles) {
		this.pnlControles = pnlControles;
	}

	public PanelMenu getMainmenu() {
		return mainmenu;
	}

	public void setMainmenu(PanelMenu mainmenu) {
		this.mainmenu = mainmenu;
	}

	public PanelMenu getPnlMansion() {
		return pnlMansion;
	}

	public void setPnlMansion(PanelMenu pnlMansion) {
		this.pnlMansion = pnlMansion;
	}

	public PanelMenu getPnlCiudad() {
		return pnlCiudad;
	}

	public void setPnlCiudad(PanelMenu pnlCiudad) {
		this.pnlCiudad = pnlCiudad;
	}

	public JButton getBack4() {
		return back4;
	}

	public void setBack4(JButton back4) {
		this.back4 = back4;
	}

	public AreaJuego getAreaJuego() {
		return areaJuego;
	}

	public void setAreaJuego(AreaJuego areaJuego) {
		this.areaJuego = areaJuego;
	}

}
