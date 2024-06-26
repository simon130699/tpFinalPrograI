package juego;

import java.awt.*;

import entorno.Entorno;
import entorno.Herramientas;

public class Auto
{
	private double x,y,alto,ancho,direccion,velocidadX,velocidadY;
	private Image imagen;

	
	public Auto(double x, double y, double direccion, double velocidadX, double velocidadY)
	{

		this.x = x;
		this.y = y;
		this.alto = 20;
		this.ancho = 20;
		this.direccion = direccion;
		this.imagen = Herramientas.cargarImagen("assets/autoRojo.png");
		this.velocidadX = velocidadX;
        this.velocidadY = velocidadY;
	}
	
	public static Auto[] crearAutos() {
		Auto[] aut = new Auto[7];
			aut[0] = new Auto(0, 36, 0, 2, 0); // X, Y, ALTO, ANCHO, DIRECCION, VELOCIDADX, VELOCIDADY
			aut[1] = new Auto(0, 236, 0, 2, 0);
			aut[2] = new Auto(0, 436, 0, 2, 0);
			aut[3] = new Auto(135, 50, Math.PI/2, 0, 2);
			aut[4] = new Auto(314, 550, -Math.PI/2, 0, -2);
			aut[5] = new Auto(535, 50, Math.PI/2, 0, 2);
			aut[6] = new Auto(714, 550, -Math.PI/2, 0, -2);
			return aut;
	}
	
	public void dibujarAutos(Entorno e) {
		e.dibujarImagen(imagen, x, y, direccion, 0.08);
		//e.dibujarRectangulo(x, y, ancho, alto, 1, Color.black);
	}
	
	
	public void moverAutos(Entorno entorno) {
        x = x + velocidadX; // Mover en el eje X
        y = y + velocidadY; // Mover en el eje Y

        if(x == entorno.ancho())
        {
        	velocidadX = velocidadX* -1;
        	direccion = Math.PI;
        	y = y-25;
        } // Imagen 2
        if(x == 0)
        {
        	velocidadX = velocidadX* -1;
        	direccion = 0;
        	y=y+25;
        }
        if(y == entorno.alto())
        {
        	velocidadY = velocidadY* -1;
        	direccion=-Math.PI/2;
        	x = x-25;
     
        }
        if(y == 0)
        {
        	velocidadY = velocidadY*-1;
        	direccion= Math.PI/2;
        	x = x+25;
        }
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
