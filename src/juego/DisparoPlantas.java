package juego;
import java.awt.Image;
import entorno.Entorno;
import entorno.Herramientas;

public class DisparoPlantas
{
	private double x,y,diametro,velocidad,direccionX,direccionY,angulo;
	private Image imagen;
	
	public DisparoPlantas(double x, double y,double diametro, double direccionX, double direccionY)
	{
		this.x = x;
		this.y = y;
		this.diametro = diametro;
		this.velocidad = 3.5;
		this.direccionX = direccionX;
		this.direccionY = direccionY;
		this.angulo= 0;
		this.imagen = Herramientas.cargarImagen("assets/bolaFuego.png");
	}
	
	public void dibujarDisparoPlantas(Entorno e)
	{ 
		if(direccionX>0)
		{
			angulo=-Math.PI/2;
		}
		else if(direccionY>0)
		{
			angulo=Math.PI*2;
		}
		e.dibujarImagen(imagen, x, y, angulo, 0.01);
		//e.dibujarCirculo(x, y, diametro, Color.black);
	}
	
	public void moverDisparoPlantas(Entorno e)
	{
		x = x + velocidad * direccionX;
        y = y + velocidad * direccionY;
	}
	
	public static void agregarDisparoPlantas(DisparoPlantas[] disparoPlantas, DisparoPlantas DP) {	
				for(int i = 0; i<disparoPlantas.length; i++) {
					if(disparoPlantas[i]==null) {
						disparoPlantas[i] = DP;
						return;
					} 
				}
			}
    public boolean fueraDePantalla(Entorno entorno) {
        return (x < 0 || x > entorno.ancho() || y < 0 || y > entorno.alto());
    }
    	// Imagen 4
    
    
    // si choca con el auto lo destruye
    public boolean chocaConAuto(Auto a) {
        if (a != null) {
            double distancia = Math.sqrt(Math.pow(x - a.getX(), 2) + Math.pow(y - a.getY(), 2));
            return distancia < (a.getAncho() + diametro);
        }
        return false;
    } // Imagen 4
    
	// si la distancia calculada es menor del disparo es menor a la de layka, true 
	public boolean destruirLayka(Layka layka)
	{
		if(layka!=null)
		{
			double distance = Math.sqrt(Math.pow(x - layka.getX(), 2) + Math.pow(layka.getY() - y, 2));  
			return distance < (layka.getAncho() + diametro);
		}
		else
		{
			return false;
		}
	}
	
	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public double getDiametro() {
		return diametro;
	}

}
