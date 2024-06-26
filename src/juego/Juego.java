package juego;

import java.awt.Color;
//import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import javax.sound.sampled.*;


import entorno.Entorno;
import entorno.Herramientas;
import entorno.InterfaceJuego;
public class Juego extends InterfaceJuego {

    private Entorno entorno;
    private Image fondo,fondoGameOver,fondoGanador,fondoInicio;
    
    private 
    Clip sonidoFondo,
    sonidoColisionDisparos,
    sonidoLaser,
    sonidoChoqueAuto,
    sonidoChoqueLayka,
    sonidoChoquePlantas,
    sonidoHuesoRecogido,
    sonidoAparicionHueso,
    sonidoAutoDisparoPlantas;
    
    private Layka layka;
    private Hueso hueso;
    private Planta[] plantas;
    private Auto[] autos;
    private Manzana[] manzanas;
    private DisparoLayka[] disparoLayka;
    private DisparoPlantas[] disparoPlantas;
    private boolean plantaEnMovimiento,huesoRecogido,pantallaInicio,ganador,enJuego;
    private long tiempoUltimoDisparoPlantas = 0; // var de instancia para buscar el tiempo del último disparo de las plantas
    private Timer timerDisparoPlantas = new Timer();
    private Timer timerGenerarPlantas = new Timer();
    private Timer timerGenerarAutos = new Timer();
    private Timer temporizador;
    private int cantidadPlantasEliminadas, tiempoRestante = 90; // 90 segundos
    

    

    public Juego() { 	
    	this.entorno = new Entorno(this, "Plantas Invasoras - Grupo N°1 - Inti - Perez - Vanhooreenbeck - Final", 800, 600);
    	this.fondo = Herramientas.cargarImagen("assets/fondoJuego.jpg");
    	this.fondoGameOver = Herramientas.cargarImagen("assets/fondoGameOver.png");
    	this.fondoGanador = Herramientas.cargarImagen("assets/fondoGanador.png");
    	this.fondoInicio = Herramientas.cargarImagen("assets/fondoInicio.jpg");
    	this.cantidadPlantasEliminadas =0;
    	this.sonidoFondo = Herramientas.cargarSonido("assets/sonidoFondo.wav");
    	this.sonidoColisionDisparos = Herramientas.cargarSonido("assets/sonidoColisionDisparos.wav");
    	this.sonidoChoqueAuto = Herramientas.cargarSonido("assets/sonidoChoqueAuto.wav");
    	this.sonidoChoqueLayka = Herramientas.cargarSonido("assets/sonidoChoqueLayka.wav");
    	this.sonidoChoquePlantas = Herramientas.cargarSonido("assets/sonidoChoquePlantas.wav");
    	this.sonidoHuesoRecogido = Herramientas.cargarSonido("assets/sonidoHuesoRecogido.wav");
    	this.sonidoAparicionHueso = Herramientas.cargarSonido("assets/sonidoHueso.wav");
    	this.sonidoAutoDisparoPlantas = Herramientas.cargarSonido("assets/sonidoAutoDisparoPlantas.wav"); 
    	this.sonidoLaser = Herramientas.cargarSonido("assets/sonidoLaser.wav");
    	
    	enJuego = false;
        pantallaInicio = true;
        ganador = false;
        
        huesoRecogido = false;
        plantaEnMovimiento=false;
        
        layka = new Layka(525, entorno.alto() - 10, 20, 20);
        hueso = new Hueso(500,123,20,20);
        
        disparoPlantas = new DisparoPlantas[6];
        disparoLayka = new DisparoLayka[6];
        
        manzanas = Manzana.crearManzanas();
        plantas = Planta.crearPlantas();
        autos = Auto.crearAutos();
        
        this.entorno.iniciar(); 
        
        sonidoFondo.start();      
        temporizador = new Timer();
        TimerTask tareaTemporizador = new TimerTask() {
    		// Ejecuta la tarea cada segundo (1000 ms)
    	    public void run() {
    	        if (tiempoRestante > 0) {
    	            tiempoRestante--;
    	        } else {
    	            // Cuando el tiempo se agota, detén el temporizador
    	            cancel(); 
    	        }
    	    }
    	}; temporizador.schedule(tareaTemporizador, 0, 1000);      
//        GENERAR DISPAROS CADA 6 SEGS SE CREA CON EL TimerTask ( tiempo de tareas)
        TimerTask generarPlantasTiempo = new TimerTask() {
        	public void run() {
        		for (int i = 0; i < plantas.length; i++) {
        			if (plantas[i] == null) {
        				plantas[i] = Planta.crearPlantas()[i];
        			}
        		}
        	}
        };     
        TimerTask tareaDisp = new TimerTask() {
            public void run() {
                for (int j = 0; j < plantas.length; j++) {
                    if (plantas[j] != null && enJuego) {
                        DisparoPlantas.agregarDisparoPlantas(disparoPlantas, plantas[j].disparar(entorno));
                    }
                }
                //es para que no se muestre 1 disparo en la pantalla si ya hay uno en movimiento 
                tiempoUltimoDisparoPlantas = System.currentTimeMillis(); // Actualizar el tiempo del último disparo
            }
        };
        timerDisparoPlantas.schedule(tareaDisp, 3000, 6000); // Disparar cada 6 segundos y empieza dsps de 3 segs      
        timerGenerarPlantas.schedule(generarPlantasTiempo, 10000, 15000); // genera plantas cada 15 segundos y empieza dsps de 10 segs     
        TimerTask generarAutosTiempo = new TimerTask() {
            public void run() {
                for (int i = 0; i < autos.length; i++) {
                	if (autos[i] == null) {
                		autos[i] = Auto.crearAutos()[i];
                		}
                }
            }
        };
        timerGenerarAutos.schedule(generarAutosTiempo, 10000, 15000); // genera autos cada 15 segundos y empieza dsps de 10 segs    
    }
    
    public void tick() {
    	if (pantallaInicio)
    	{
    		if (entorno.sePresiono(entorno.TECLA_ENTER)) {
    			enJuego = true;
    			pantallaInicio = false; // Comienza el juego cuando se presiona la tecla de espacio
    		} 
    		else
    		{			// Muestra la pantalla de inicio
    			entorno.dibujarImagen(fondoInicio, entorno.ancho() / 2, entorno.alto() / 2, 0, 1);
    		}
    	} 
    	else 
    	{
    		if (enJuego)
    		{	
    			if (!layka.haPerdido() || tiempoRestante>1) {
    				entorno.dibujarImagen(fondo, entorno.ancho() / 2, entorno.alto() / 2, 0, 1);
    				entorno.cambiarFont("sunday", 20, Color.BLACK);
    				entorno.escribirTexto("Plantas eliminadas: "+ cantidadPlantasEliminadas,25,35);
    				entorno.escribirTexto("Vida restantes:" + layka.getVidas(), 25, 65);
    				entorno.escribirTexto("Tiempo: " + tiempoRestante, 25, 95);



    				//codigo para mostrar hueso e interaccion con layka al agarrarlo
    				if (tiempoRestante <= 45) { // Cambia 45 por 55 para dibujar el hueso después de 55 segundos
    					if (!huesoRecogido) {
    						hueso.dibujarHueso(entorno);
    						sonidoAparicionHueso.start();
    					}
    				}

    				if(layka.chocaConHueso(hueso))
    				{
    					if (!huesoRecogido && layka.chocaConHueso(hueso)) {
    						huesoRecogido = true; // Marcar que el hueso ha sido recogido
    						Random rand = new Random();
    						int opcionHueso = rand.nextInt(3);
    						if (opcionHueso == 0) {
    							tiempoRestante = tiempoRestante +20;

    							//layka.aumentarVidas();
    						} else if (opcionHueso == 1){
    							tiempoRestante = tiempoRestante +20;
    							//layka.aumentarVidas();


    						}
    						sonidoHuesoRecogido.start();
    					}

    				}

    				//Codigo de movimiento de Layka e interseccion con Manzanas.
    				boolean enMovimiento = false;
    				if ((entorno.estaPresionada(entorno.TECLA_DERECHA) && !enMovimiento) ) {
    					layka.moverDerecha(entorno,manzanas);
    					enMovimiento = true;        		            
    				}

    				if (entorno.estaPresionada(entorno.TECLA_IZQUIERDA) && !enMovimiento  ) {
    					layka.moverIzquierda(entorno,manzanas);
    					enMovimiento = true;

    				}
    				if (entorno.estaPresionada(entorno.TECLA_ARRIBA) && !enMovimiento  ) {
    					layka.moverArriba(entorno,manzanas);
    					enMovimiento = true;
    				}


    				if (entorno.estaPresionada(entorno.TECLA_ABAJO) && !enMovimiento ) {
    					layka.moverAbajo(entorno,manzanas);
    					enMovimiento = true;
    				}

    				//Codigo de agregar un disparo de Layka.
    				if (entorno.sePresiono(entorno.TECLA_ESPACIO)) {
    					sonidoLaser.setFramePosition(0);
    					sonidoLaser.start();
    					DisparoLayka.agregarDisparoLayka(disparoLayka, layka.disparar(entorno));
    				}

    				//Codigo de disparo de Layka con interseccion con plantas y con manzanas
    				for (int i = 0; i < disparoLayka.length; i++) {
    					if (disparoLayka[i] != null) {
    						disparoLayka[i].dibujarDisparoLayka(entorno);
    						disparoLayka[i].moverDisparoLayka(entorno);                            
    						if (disparoLayka[i].fueraDePantalla(entorno) || disparoLayka[i].chocaConManzanas(manzanas)) {
    							disparoLayka[i] = null;
    						}

    						for (int j = 0; j < disparoPlantas.length; j++) {
    							if((disparoLayka[i]!=null && disparoPlantas[j]!=null) && disparoLayka[i].chocaConDisparoPlanta(disparoPlantas[j])) {
    								disparoLayka[i]=null;
    								disparoPlantas[j]=null;
    								sonidoColisionDisparos.setFramePosition(0);
    								sonidoColisionDisparos.start(); // Reproducir el sonido de colisión
    							}
    						}

    						for (int j = 0; j < autos.length; j++) {
    							if (autos[j] != null && disparoLayka[i] != null && disparoLayka[i].chocaConAuto(autos[j])) {
    								disparoLayka[i] = null; // Elimina el disparo que impactó en el auto
    								sonidoChoqueAuto.setFramePosition(0);
    								sonidoChoqueAuto.start();
    							}
    						}

    						for (int j = 0; j < plantas.length; j++) {
    							if (plantas[j] != null &&  disparoLayka[i] != null && disparoLayka[i].destruirPlanta(plantas[j])) {
    								plantas[j] = null; //Borra la planta que fue alcanzada por el disparo de Layka
    								this.cantidadPlantasEliminadas++;
    								disparoLayka[i] = null; //Borra el disparo que alcanzo a la planta
    								sonidoChoquePlantas.setFramePosition(0);
    								sonidoChoquePlantas.start();
    							}
    						}
    					}
    				}   


    				// Código para verificar que no hayan mas de 1 disparo en pantalla
    				if (layka.estaVivo() && plantaEnMovimiento==true ) {
    					long tiempoActual = System.currentTimeMillis(); // Obtener el tiempo actual en milisegundos
    					//solo se va a mostar si no hay un disparo en movimiento en la pantalla
    					if (tiempoActual - tiempoUltimoDisparoPlantas >= 6000) {
    						plantaEnMovimiento=true;
    						for (int j = 0; j < plantas.length; j++) {
    							if (plantas[j] != null) {
    								DisparoPlantas.agregarDisparoPlantas(disparoPlantas, plantas[j].disparar(entorno));
    							}
    						}
    						tiempoUltimoDisparoPlantas = tiempoActual; // Actualizar el tiempo del último disparo
    					}
    				}


    				//codigo de disparo de plantas mostrandolo en pantalla, restando vida a layka, despareciendo los autos
    				for (int k = 0; k < disparoPlantas.length; k++) {
    					if (disparoPlantas[k] != null) {
    						disparoPlantas[k].dibujarDisparoPlantas(entorno);
    						disparoPlantas[k].moverDisparoPlantas(entorno);

    						if (disparoPlantas[k].fueraDePantalla(entorno)) {
    							disparoPlantas[k] = null;
    						}

    						if (disparoPlantas[k]!= null && layka.estaVivo() && disparoPlantas[k].destruirLayka(layka)) {
    							layka.restarVida();

    							if (layka.estaVivo()) {
    								layka.resetearPosicionInicial();
    								sonidoChoqueLayka.setFramePosition(0);
    								sonidoChoqueLayka.start();
    							}
    							disparoPlantas[k] = null; // Elimina el disparo que impactó en Layka
    						}

    						for (int j = 0; j < plantas.length; j++) {
    							if (autos[j] != null &&  disparoPlantas[k] != null && disparoPlantas[k].chocaConAuto(autos[j])) {
    								autos[j] = null; //Borra el auto que fue alcanzado por el disparo de la planta
    								sonidoAutoDisparoPlantas.setFramePosition(0);
    								sonidoAutoDisparoPlantas.start();
    							} // Imagen 5
    						}
    					}
    				}





    				//Codigo para mostar los autos
    				for (int i = 0; i < autos.length; i++)
    				{
    					if (autos[i] != null)
    					{
    						autos[i].dibujarAutos(entorno);
    						autos[i].moverAutos(entorno);
    						if (layka.estaVivo() && layka.chocaConAuto(autos[i]))
    						{
    							layka.restarVida();

    							if (layka.estaVivo()) {                       
    								layka.resetearPosicionInicial(); 
    								sonidoChoqueLayka.setFramePosition(0);
    								sonidoChoqueLayka.start();	                    
    							}
    						}
    					}	   
    				}

    				//Codigo para mostrar las plantas
    				for (int i = 0; i < plantas.length; i++) {
    					if (plantas[i] != null) {
    						plantas[i].dibujarPlantas(entorno);
    						plantas[i].moverPlantas(entorno);
    					}
    					if (plantas[i] != null && layka.estaVivo() && layka.chocaConPlantas(plantas[i]))
    					{

    						layka.restarVida();
    						if (layka.estaVivo()) {
    							layka.resetearPosicionInicial(); 
    							sonidoChoqueLayka.setFramePosition(0);
    							sonidoChoqueLayka.start();
    						}
    					}

    				}

    				//Codigo de vidas de Layka 
    				if (layka.estaVivo()) {
    					layka.dibujarLayka(entorno);
    				}

    			if (layka.haPerdido())
    			{
    				ganador = false;
    				enJuego = false;
    			}
    			else if (tiempoRestante == 0 || cantidadPlantasEliminadas == 25)
    			{
    				ganador = true;
    				enJuego = false;
    			}
    		}
    	} //Hasta aca el codigo se ejecuta si enJuego es true
    		
    	else if(!enJuego)
    	{
    		if(ganador)
    		{
    			sonidoFondo.stop();
    			entorno.dibujarImagen(fondoGanador, entorno.ancho() / 2, entorno.alto() / 2, 0, 1);
				entorno.cambiarFont("sunday", 30, Color.BLACK);
				entorno.escribirTexto("Plantas eliminadas: "+ cantidadPlantasEliminadas,170,140);
				if(entorno.sePresiono(entorno.TECLA_ESPACIO))
				{
					Juego nuevoJuego = new Juego();	
				}	
    		}
    		else
    		{
    			sonidoFondo.stop();
    			entorno.cambiarFont("sunday", 30, Color.WHITE);
    			entorno.dibujarImagen(fondoGameOver, entorno.ancho() / 2, entorno.alto() / 2, 0, 1);
    			entorno.escribirTexto("Plantas eliminadas: "+cantidadPlantasEliminadas, 170, 140); 
    			if(entorno.sePresiono(entorno.TECLA_ESPACIO)) {
    				Juego nuevoJuego = new Juego();           
    				}   
    		}
    	}
    }
  }
    public static void main(String[] args) {
        Juego juego = new Juego();
    }
}