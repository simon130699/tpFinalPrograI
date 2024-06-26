package juego;

public class Manzana {
	private double x,y,alto,ancho;

	
	public Manzana(double x, double y)
	{
		this.x = x;
		this.y = y;
		this.alto = 150;
		this.ancho = 150;
	}
		
	public static Manzana[] crearManzanas() {
		Manzana[] man = new Manzana[15];
			man[0] = new Manzana(25,125);	
			man[1] = new Manzana(25,325);
			man[2] = new Manzana(25,525);
			man[3] = new Manzana(225,125);
			man[4] = new Manzana(225,325);
			man[5] = new Manzana(225,525);
			man[6] = new Manzana(425,125);
			man[7] = new Manzana(425,325);
			man[8] = new Manzana(425,525);
			man[9] = new Manzana(626,125);
			man[10] = new Manzana(626,325);
			man[11] = new Manzana(626,525);
			man[12] = new Manzana(825,125);
			man[13] = new Manzana(825,325);
			man[14] = new Manzana(825,525);
			return man;
	}
	public double getX() {
		return x;
	}


	public double getY() {
		return y;
	}


	public double getAlto() {
		return alto;
	}


	public double getAncho() {
		return ancho;
	}
}
