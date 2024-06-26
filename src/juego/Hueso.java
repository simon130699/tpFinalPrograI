package juego;

import java.awt.*;
import java.util.Random;

import entorno.Entorno;
import entorno.Herramientas;

	public class Hueso {
		private double x,y,ancho,alto;
		private Image imagen;
		private int opcionCoordenada;
		private double[][] coordenadasHueso = {
	            {226, 412}, 
	            {420, 210},
	            {745, 520},
	            {47, 236},
	            {137, 537},
	    };
	
	public Hueso(double x, double y, double alto, double ancho)
	{
		this.x = x;
		this.y = y;
		this.ancho = ancho;
		this.alto = alto;
		ubicarHuesoAleatoriamente();
		this.imagen = Herramientas.cargarImagen("assets/imagenHueso.png");
	}
	
	  public void ubicarHuesoAleatoriamente() {
	        Random rand = new Random();
	        opcionCoordenada = rand.nextInt(coordenadasHueso.length);
	        double[] coordenadaAleatoria = coordenadasHueso[opcionCoordenada];
	        this.x = coordenadaAleatoria[0];
	        this.y = coordenadaAleatoria[1];
	    }
	
	public void dibujarHueso(Entorno e)
	{
		e.dibujarImagen(imagen, x, y, 0, 0.04);
	}
	
	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public double getAncho() {
		return ancho;
	}

	public double getAlto() {
		return alto;
	}
	
}
