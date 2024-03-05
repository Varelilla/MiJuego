import java.awt.Rectangle;

public class Polvo extends Plataforma{
	private boolean pisado;
	public Polvo(int x, int y, int w, int h) {
		super(x, y, w, h);
		pisado = false;
	}
	public boolean isPisado() {
		return pisado;
	}
	public void setPisado(boolean pisado) {
		this.pisado = pisado;
	}
	

}
