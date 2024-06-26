package juego;
import javax.swing.*;

import entorno.Entorno;
import entorno.Herramientas;

import java.awt.Color;
import java.awt.Image;

public class Layka {
    //variables de instancia del objeto LAYKA
    private double x,y,alto,ancho,angulo,direccionDisparoLaykaX,direccionDisparoLaykaY,xInicial,yInicial;
    private Color color;
    private Image Layka,LaykaIzquierda;
    private int vidas;
    private boolean conVida;

    
    
    //CONSTRUCTOR DE LAYKA
    public Layka(double x, double y, double alto, double ancho) {
        this.x = x;
        this.y = y;
        this.xInicial = x;
        this.yInicial = y;
        this.ancho = ancho;
        this.alto = alto;
        this.color = Color.red;
        this.Layka = Herramientas.cargarImagen("assets/imagenLayka.png");
        this.LaykaIzquierda = Herramientas.cargarImagen("assets/imagenLaykaIzquierda.png");
        this.direccionDisparoLaykaX = 0;
        this.direccionDisparoLaykaY = 0;
        this.vidas = 5;
        this.conVida = true;
    }

    //    METODO QUE MUESTRA A LAYKA
    public void dibujarLayka(Entorno e) {   
        if (e.estaPresionada(e.TECLA_IZQUIERDA))
        {
        e.dibujarImagen(LaykaIzquierda, x, y, angulo, 0.09);
        }
        else
        {
        e.dibujarImagen(Layka, x, y, angulo, 0.09);
        }
      	//e.dibujarRectangulo(x, y, ancho, alto, 0, color);
    }
    public void gameOver(String mensaje) {
        JOptionPane.showMessageDialog(null, mensaje, "game over", JOptionPane.ERROR_MESSAGE);
    }

    public boolean estaVivo() {
	    return conVida;
	}
    
    public void restarVida() {
        if (vidas > 0) {
            vidas--;
        }
    }
    public void aumentarVidas()
	{
		vidas = vidas + 2;
	}

    public boolean haPerdido() {
        return vidas <= 0;
    }
    
    public void resetearPosicionInicial() {
        x = xInicial;
        y = yInicial;
    }
    
	public boolean chocaConAuto(Auto a) {
		return(x<a.getX()+ancho && x + a.getAncho() > a.getX() && y < a.getY()+alto && y + a.getAlto() > a.getY() );
	}
	
	public boolean chocaConPlantas(Planta plant) {
		return (x<plant.getX()+ancho && x + plant.getAncho() > plant.getX() && y < plant.getY()+alto && y + plant.getAlto() > plant.getY() );
		
	}
	
	public boolean chocaConHueso(Hueso hueso) {
		return (x<hueso.getX()+ancho && x + hueso.getAncho() > hueso.getX() && y < hueso.getY()+alto && y + hueso.getAlto() > hueso.getY() );
		
	}

	public boolean intersectaManzana(Manzana[] manzanas) {
	    for (int i =0; i< manzanas.length ;i++ ) {
	        if (manzanas[i] != null) {
	            if (x + ancho / 2 > manzanas[i].getX() - manzanas[i].getAncho() / 2 &&
	                x - ancho / 2 < manzanas[i].getX() + manzanas[i].getAncho() / 2 &&
	                y - alto / 2 < manzanas[i].getY() + manzanas[i].getAlto() / 2 &&
	                y + alto / 2 > manzanas[i].getY() - manzanas[i].getAlto() / 2) {
	                return true;
	            }
	        }else {
	        	
	        }
	    }
	    return false;
	}

	
    //MUEVE A LAYKA
    public void moverDerecha(Entorno entorno,Manzana[] manzanas) {
        if (x+ancho/2 <= entorno.ancho()) {
            x = x + 1;
            direccionDisparoLaykaX = 1;
            direccionDisparoLaykaY = 0;
        }
        if(intersectaManzana(manzanas)) {
        	x=x-1;
        }
    }

    public void moverIzquierda(Entorno entorno,Manzana[] manzanas) {
        if (x+ancho/2 >= 0) {
            this.x = x - 1;
            direccionDisparoLaykaX = -1;
            direccionDisparoLaykaY = 0;
        }
        if(intersectaManzana(manzanas)) {
        	x=x+1;
        }
    }

    public void moverArriba(Entorno entorno,Manzana[] manzanas) {
        if (y+alto/2 >= 0) {
            this.y = y - 1;
            direccionDisparoLaykaX = 0;
            direccionDisparoLaykaY = -1;
        }
        if(intersectaManzana(manzanas)) {
        	y=y+1;
        }
    }

	public void moverAbajo(Entorno entorno,Manzana[] manzanas) {
        if (y+alto/2 <= entorno.alto()) {
            this.y = y + 1;
            direccionDisparoLaykaX = 0;
            direccionDisparoLaykaY = 1;
        }
        if(intersectaManzana(manzanas)) {
        	y=y-1;
        }
    }
	
	public DisparoLayka disparar(Entorno entorno) {
		DisparoLayka DL = new DisparoLayka(x,y, 6, direccionDisparoLaykaX, direccionDisparoLaykaY);
		return DL;
	}
	
	
	
    public double getAlto() {
		return alto;
	}

	public double getAncho() {
		return ancho;
	}
	

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public void setConVida(boolean conVida) {
		this.conVida = conVida;
	}

	public Color getColor() {
        return color;
    }

    public double getAngulo() {
        return angulo;
    }

    public Image getLayka() {
        return Layka;
    }

	public int getVidas() {
		return vidas;
	}

	
}
