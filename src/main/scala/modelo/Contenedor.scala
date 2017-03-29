package main.scala.modelo

import scala.collection.mutable.ArrayBuffer
import java.util.concurrent.Semaphore;



class Contenedor(maxProductos:Int){
    
    val semaforo = new Semaphore(1,true);
    var ultimoInsert = 0;

    var cantProductos = 0;
    var cantUltmimo = 0;
    var ultimoEliminar = 0;
    type Producto = String

    var maxElementos = 30
    private var lista = ArrayBuffer[Producto]();

    def llenarLista = {
      if(lista.size == 0){

      for(a <- 1 until maxElementos+1) {
        lista += "____"
      }
      }
    }

    
    
    //Colocar un elemento al final de la lista
    def colocar(producto:Producto) = {
        //0       
        lista(ultimoInsert) = producto;
        cantUltmimo = cantUltmimo + 1;
        
        if(ultimoInsert == maxElementos-1){
          ultimoInsert = 0
        }else{
          ultimoInsert = ultimoInsert + 1
        }
    	  Thread.sleep(500);
    }
    
    //Removemos el primer elemento y devolverlo
    def tomar =  {
        lista(ultimoEliminar) = "____";

        if(cantUltmimo != 0){          
          if(ultimoEliminar == maxElementos-1){
            ultimoEliminar = 0
          }else{
            ultimoEliminar = ultimoEliminar + 1
          }
          cantUltmimo = cantUltmimo - 1
        }
        Thread.sleep(500);
    }
    
    //Determinar si la lista a superado el maximo de elementos permitidos
    def estaLleno = false //lista.size > maxProductos -1
    
    //Determinar si la lista esta vacia
    def estaVacio = cantUltmimo == 0;
    
    def tamMax = maxProductos

    def dameSeq:Seq[String] = synchronized{
      lista
    }
    def entrar:Boolean = semaforo.tryAcquire()
    def salir = semaforo.release();
}

object Contenedor{
	private lazy val _instancia = defineContenedor(tam);
	var tam = 30
	
	
	def instancia:Contenedor =  {
	    
		  _instancia;
	}
  
  private def defineContenedor(tam:Int):Contenedor = {
    return new Contenedor(tam);
  }
}


