package juego;

import java.awt.Image;


import entorno.*;

public class Planta
{
	private double x,y,alto,ancho,velocidadX,velocidadY,direccion,direccionDisparoPlantaX,direccionDisparoPlantaY;
//	private Color color;
	private Image imagen;

	
	public Planta(double x, double y, double direccion, double velocidadX, double velocidadY)
	{
		this.x = x;
		this.y = y;
		this.alto = 15;
		this.ancho = 15;
		this.direccion = direccion;
		this.imagen = Herramientas.cargarImagen("assets/plantaMutante.png");
		this.velocidadX = velocidadX;
        this.velocidadY = velocidadY;
        this.direccionDisparoPlantaX = 0;
        this.direccionDisparoPlantaY = 0;
	}
	
	public static Planta[] crearPlantas() {
		Planta[] plant = new Planta[6];
			plant[0] = new Planta(236, 35, 0, 1, 0);
			plant[1] = new Planta(236, 235, 0, 1, 0);
			plant[2] = new Planta(236, 435, 0, 1, 0);
			plant[3] = new Planta(136, 236, 0, 0, 1);
			plant[4] = new Planta(336, 430, 0, 0, 1);
			plant[5] = new Planta(536, 430, 0, 0, 1);
			return plant;
	}
	public void dibujarPlantas(Entorno e) {
		e.dibujarImagen(imagen, x, y, direccion, 1);
		//e.dibujarRectangulo(x, y, ancho, alto, 0, color);
	}
	
	
	public void moverPlantas(Entorno e)
	{       
	       x = x + velocidadX; // Mover en el eje X
	       y = y + velocidadY; // Mover en el eje Y
	       if(x==e.ancho())
	       {
	    	   x = 0;
	       }
	       if(y==e.alto())
	       {
	    	   y = 0;
	       }

	       if(velocidadY == 0)
	       {
	    	   direccionDisparoPlantaX = 1;
	       }
	       if(velocidadX == 0)
	       {
	    	   direccionDisparoPlantaY = 1;
	       }
	       
	}


	public DisparoPlantas disparar(Entorno entorno) {
		DisparoPlantas DP = new DisparoPlantas(x,y,5, 
				direccionDisparoPlantaX, direccionDisparoPlantaY);
		return DP;
	} // Imagen 3
	
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
