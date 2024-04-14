import java.awt.BorderLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class EventosPanelMenu {
	private PanelMenu pnlMenu;

	
	public EventosPanelMenu(PanelMenu pnlMenu) {
		this.pnlMenu = pnlMenu;
		registrarEventos();
	}
	
	public void registrarEventos() {
		pnlMenu.addKeyListener(new KeyListener() {
			
			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void keyReleased(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_A) {
					if (pnlMenu.getContador()>0 && pnlMenu.isMundo()) {
						pnlMenu.setContador(pnlMenu.getContador() - 1);
						pnlMenu.repaint();
					}
				}
				if (e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_D) {
					if (pnlMenu.getContador()<5 && pnlMenu.isMundo()) {
						pnlMenu.setContador(pnlMenu.getContador() + 1);
						pnlMenu.repaint();
					}
				}
				if (e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_W) {
					if (pnlMenu.getContador()>2 && pnlMenu.isMundo()) {
						pnlMenu.setContador(pnlMenu.getContador() - 3);
						pnlMenu.repaint();
					}
					if (pnlMenu.getContador() > -1 && !pnlMenu.isMundo()) {
						if (pnlMenu.getContador() == 0)
							pnlMenu.setContador(5);
						else
						pnlMenu.setContador((pnlMenu.getContador() - 1)%6);
						pnlMenu.repaint();
					}
					
				}
				if (e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_S) {
					if (pnlMenu.getContador()<3 && pnlMenu.isMundo()) pnlMenu.setContador(pnlMenu.getContador() + 3);
					if (pnlMenu.getContador() > -1 && !pnlMenu.isMundo()) pnlMenu.setContador((pnlMenu.getContador() + 1)%6);
					pnlMenu.repaint();
				}
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					if (pnlMenu.isMundo()) {
						if (pnlMenu.getContador()<5) {
							if (pnlMenu.getContador()== 0) {
								pnlMenu.getJuego().getContentPane().removeAll();
								AreaJuego areaJuego = new AreaJuego(pnlMenu.getContador(), pnlMenu.getJuego());
								areaJuego.setPnlMenu(pnlMenu);
								if (pnlMenu.getCompletados()[pnlMenu.getContador()]) {
									areaJuego.setRecord(pnlMenu.getTiempos()[pnlMenu.getContador()]);
									areaJuego.setRutarRecord(pnlMenu.getPuntuaciones()[pnlMenu.getContador()]);
								} else {
									areaJuego.setRecord("");
									areaJuego.setRutarRecord("");
								}
								pnlMenu.getJuego().setAreaJuego(areaJuego);
								pnlMenu.getJuego().getContentPane().add(pnlMenu.getJuego().getAreaJuego(), BorderLayout.CENTER);
								pnlMenu.getJuego().getAreaJuego().requestFocus();
								pnlMenu.getJuego().revalidate();
								pnlMenu.getJuego().repaint();
							} else {
								if (pnlMenu.getCompletados()[pnlMenu.getContador() -1]) {
									pnlMenu.getJuego().getContentPane().removeAll();
									AreaJuego areaJuego = new AreaJuego(pnlMenu.getContador(), pnlMenu.getJuego());
									areaJuego.setPnlMenu(pnlMenu);
									if (pnlMenu.getPuntuaciones()[pnlMenu.getContador()].length() > 0) {
										areaJuego.setRecord(pnlMenu.getTiempos()[pnlMenu.getContador()]);
										areaJuego.setRutarRecord(pnlMenu.getPuntuaciones()[pnlMenu.getContador()]);
									} else {
										areaJuego.setRecord("");
										areaJuego.setRutarRecord("");
									}
									pnlMenu.getJuego().setAreaJuego(areaJuego);
									pnlMenu.getJuego().getContentPane().add(pnlMenu.getJuego().getAreaJuego(), BorderLayout.CENTER);
									pnlMenu.getJuego().getAreaJuego().requestFocus();
									pnlMenu.getJuego().revalidate();
									pnlMenu.getJuego().repaint();
								}
							}
						} else {
							pnlMenu.getJuego().getContentPane().removeAll();
							pnlMenu.getJuego().getContentPane().add(pnlMenu.getJuego().getMainmenu(), BorderLayout.CENTER);
							pnlMenu.getJuego().getMainmenu().requestFocus();
							pnlMenu.getJuego().revalidate();
							pnlMenu.getJuego().repaint();
						}
					} else if (pnlMenu.getContador() == -1) {
					} else {
						pnlMenu.getJuego().getContentPane().removeAll();
						switch (pnlMenu.getContador()) {
						case 0:
							pnlMenu.getJuego().getContentPane().add(pnlMenu.getJuego().getPnlBosque(), BorderLayout.CENTER);
							pnlMenu.getJuego().getPnlBosque().requestFocus();
							break;
						case 1:
							pnlMenu.getJuego().getContentPane().add(pnlMenu.getJuego().getPnlLaboratorio(),
									BorderLayout.CENTER);
							pnlMenu.getJuego().getPnlLaboratorio().requestFocus();
							break;
						case 2:
							pnlMenu.getJuego().getContentPane().add(pnlMenu.getJuego().getPnlMansion(), BorderLayout.CENTER);
							pnlMenu.getJuego().getPnlMansion().requestFocus();
							break;
						case 3:
							pnlMenu.getJuego().getContentPane().add(pnlMenu.getJuego().getPnlCiudad(), BorderLayout.CENTER);
							pnlMenu.getJuego().getPnlCiudad().requestFocus();
							break;
						case 4:
							pnlMenu.getJuego().getContentPane().add(pnlMenu.getJuego().getPnlControles(),
									BorderLayout.CENTER);
							pnlMenu.getJuego().getPnlControles().requestFocus();
							break;
						case 5:
							System.exit(0);
							break;
						}

						pnlMenu.getJuego().revalidate();
						pnlMenu.getJuego().repaint();
					}
				}
				if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
					if (pnlMenu.isMundo() || pnlMenu.getContador() == -1) {
						pnlMenu.getJuego().getContentPane().removeAll();
						pnlMenu.getJuego().getContentPane().add(pnlMenu.getJuego().getMainmenu(), BorderLayout.CENTER);
						pnlMenu.getJuego().getMainmenu().requestFocus();
						pnlMenu.getJuego().revalidate();
						pnlMenu.getJuego().repaint();
					} else {
						System.exit(0);
					}
					
				}
				
			}
			
			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
	}
}
