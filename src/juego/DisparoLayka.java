package juego;

import java.awt.*;

import entorno.*;

public class DisparoLayka
{
	private double x,y,diametro,velocidad,direccionX,direccionY;
	private Image imagen;
	

	public DisparoLayka(double x, double y, double diametro, double direccionX, double direccionY)
	{
		this.imagen = Herramientas.cargarImagen("assets/rayoLaser.png");
		this.x = x;
		this.y = y;
		this.diametro = diametro;
		this.velocidad = 5;
		this.direccionX = direccionX;
		this.direccionY = direccionY;
	}
	
	public void dibujarDisparoLayka(Entorno e)
	{
		
		if(direccionX == 0)
		{
			e.dibujarImagen(imagen, x, y, 0, 0.01);
		}
		e.dibujarImagen(imagen, x, y, 0, 0.01);
		//e.dibujarCirculo(x, y, diametro, Color.black);
		}
	
	public void moverDisparoLayka(Entorno e)
	{
		x = x + velocidad * direccionX;
        y = y + velocidad * direccionY;
	}
	
	public static void agregarDisparoLayka(DisparoLayka[] disparoLayka, DisparoLayka DL) {
		for(int i = 0; i<disparoLayka.length; i++) {
			if(disparoLayka[i]==null) {
				disparoLayka[i] = DL;
				return;
			}
		}
	}	
	
	public boolean destruirPlanta(Planta plant)
	{
		if(plant!=null)
		{
			double distance = Math.sqrt(Math.pow(x - plant.getX(), 2) + Math.pow(plant.getY() - y, 2));  
			return distance < (plant.getAncho() + diametro);
		}
		else
		{
			return false;
		}
	}
		
	public boolean chocaConAuto(Auto a) {
		return((x - diametro / 2 < a.getX() + a.getAncho() / 2
		        && x + diametro / 2 > a.getX() - a.getAncho() / 2
		        && y - diametro / 2 < a.getY() + a.getAncho() / 2
		        && y + diametro / 2 > a.getY() - a.getAncho() / 2) ); 	
	}
	
	public boolean chocaConManzanas(Manzana[] manzanas) {
		for (int i =0; i< manzanas.length ;i++ ) {
	        if (manzanas[i] != null) {
	            if (x + diametro / 2 > manzanas[i].getX() - manzanas[i].getAncho() / 2 &&
	                x - diametro / 2 < manzanas[i].getX() + manzanas[i].getAncho() / 2 &&
	                y - diametro / 2 < manzanas[i].getY() + manzanas[i].getAlto() / 2 &&
	                y + diametro / 2 > manzanas[i].getY() - manzanas[i].getAlto() / 2) {
	                return true;
	            }
	        }else {
	        	
	        }
	    }
	    return false;
}
	
	public boolean chocaConDisparoPlanta(DisparoPlantas disp) {
			return ((x - diametro / 2 < disp.getX() + disp.getDiametro() / 2
			        && x + diametro / 2 > disp.getX() - disp.getDiametro() / 2
			        && y - diametro / 2 < disp.getY() + disp.getDiametro() / 2
			        && y + diametro / 2 > disp.getY() - disp.getDiametro() / 2) );							
	}
	
    public boolean fueraDePantalla(Entorno entorno) {
        return (x < 0 || x > entorno.ancho() || y < 0 || y > entorno.alto());
    }
	

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}		

}
