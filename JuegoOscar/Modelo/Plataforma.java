import java.awt.Graphics;
import java.awt.Rectangle;

public class Plataforma {
	private int x;
	private int y;
	private int xOrigen;
	private int yOrigen;
	private int ancho;
	private int alto;
	private Rectangle hitBox;
	private int xscroll;
	private int yscroll;
	
	public Plataforma(int x, int y, int w, int h){
		xscroll=0;
		yscroll=0;
		xOrigen = x;
		yOrigen = y;
		this.x = x;
		this.y = y;
		ancho = w;
		alto = h;
		hitBox = new Rectangle(x,y,ancho,alto);

	}
	
	public void dibujar(Graphics g){
		g.fillRect(x, y, ancho, alto);
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getAncho() {
		return ancho;
	}

	public void setAncho(int ancho) {
		this.ancho = ancho;
	}

	public int getAlto() {
		return alto;
	}

	public void setAlto(int alto) {
		this.alto = alto;
	}

	public Rectangle getHitBox() {
		return hitBox;
	}

	public void setHitBox(Rectangle hitBox) {
		this.hitBox = hitBox;
	}

	public int getXscroll() {
		return xscroll;
	}

	public void setXscroll(int xscroll) {
		this.xscroll = xscroll;
		x = xOrigen - xscroll;
		hitBox = new Rectangle(x,y,ancho,alto);
	}

	public int getYscroll() {
		return yscroll;
	}

	public void setYscroll(int yscroll) {
		this.yscroll = yscroll;
	}
	
	public int getTop(){
		return hitBox.y;
	}

	public int getLeft(){
		return hitBox.x;
	}

	public int getRight(){
		return hitBox.x+ancho;
	}

	public int getBottom(){
		return hitBox.y+alto;
	}

	public int getxOrigen() {
		return xOrigen;
	}

	public void setxOrigen(int xOrigen) {
		this.xOrigen = xOrigen;
	}

	public int getyOrigen() {
		return yOrigen;
	}

	public void setyOrigen(int yOrigen) {
		this.yOrigen = yOrigen;
	}
}
