import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class EventosJuegoOscar {
	private JuegoOscar juego;
	
	public EventosJuegoOscar(JuegoOscar juegoOscar) {
		// TODO Auto-generated constructor stub
		this.juego = juegoOscar;
		registrarEventos();
	}

	public void registrarEventos() {
		juego.getBtnBosque().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				juego.getContentPane().removeAll();
				juego.getContentPane().add(juego.getPnlBosque(), BorderLayout.CENTER);
				juego.getPnlBosque().requestFocus();
				juego.revalidate();
				juego.repaint();
			}
		});

		juego.getBtnLaboratorio().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				juego.getContentPane().removeAll();
				juego.getContentPane().add(juego.getPnlLaboratorio(), BorderLayout.CENTER);
				juego.getPnlLaboratorio().requestFocus();
				juego.revalidate();
				juego.repaint();
			}
		});
		
		juego.getBtnMansion().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				juego.getContentPane().removeAll();
				juego.getContentPane().add(juego.getPnlMansion(), BorderLayout.CENTER);
				juego.getPnlMansion().requestFocus();
				juego.revalidate();
				juego.repaint();
			}
		});
		
		juego.getBtnCiudad().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				juego.getContentPane().removeAll();
				juego.getContentPane().add(juego.getPnlCiudad(), BorderLayout.CENTER);
				juego.getPnlCiudad().requestFocus();
				juego.revalidate();
				juego.repaint();
			}
		});
		
		juego.getBtnControles().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				juego.getContentPane().removeAll();
				juego.getContentPane().add(juego.getPnlControles(), BorderLayout.CENTER);
				juego.getPnlControles().requestFocus();
				juego.revalidate();
				juego.repaint();
			}
		});
		
	}


}
