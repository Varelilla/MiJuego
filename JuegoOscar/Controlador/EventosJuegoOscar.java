import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;


public class EventosJuegoOscar {
	private static final double ASPECT_RATIO = 1900 / 980;
	private JuegoOscar juego;
	private int xAnterior, yAnterior;
	
	public EventosJuegoOscar(JuegoOscar juegoOscar) {
		// TODO Auto-generated constructor stub
		this.juego = juegoOscar;
		registrarEventos();
	}

	public void registrarEventos() {

		juego.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                Dimension newSize = e.getComponent().getSize();

                // Calcula las nuevas dimensiones x e y manteniendo la proporción
                int newX = (int) newSize.getWidth();
                int newY = (int) newSize.getHeight();

                // Actualiza el tamaño de la ventana con las nuevas dimensiones
                juego.cambiarTamaño(newX, newY);

                // Agrega cualquier lógica adicional aquí que quieras ejecutar cuando la ventana se redimensione
            }
        });
		
		
		
	}


}
