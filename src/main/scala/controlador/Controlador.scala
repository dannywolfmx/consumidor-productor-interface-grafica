package main.scala.controlador

import main.scala.vista._
import main.scala.modelo._
import java.awt.event.ActionListener

import scala.collection.mutable.ArrayBuffer

class Controlador(vista:Vista){

    
   type Producto = String;
   val maxRandom:Int = 100
   var bufferDeProductos = ArrayBuffer[Producto]();
   val contenedor = Contenedor.instancia;
   contenedor.llenarLista
  

   
   def agregaProducto(contenedor:Contenedor,producto:Producto){
       vista.productorTrabajando()
       
       if( contenedor.estaLleno ){
          vista.productorDurmiendo()
       }else{
            if( contenedor.entrar ){
              
              vista.actualizaListaDeProductos( contenedor.dameSeq );          
              vista.productorContenedor()
              contenedor.colocar(producto)
              contenedor.salir
            }else{
              vista.productorDurmiendo()
              dormir()
              agregaProducto( contenedor, producto );
            }

            vista.productorTrabajando();
            trabajar()
       }
   }

   def tomarProducto(contenedor:Contenedor){

       vista.consumidorTrabajando()
       
       if( contenedor.estaVacio ){
          vista.consumidorDurmiendo()
          dormir()
          tomarProducto( contenedor )
       }else{
          if( contenedor.entrar ){            	    

            vista.actualizaListaDeProductos( contenedor.dameSeq );
            vista.consumidorContenedor()
            contenedor.tomar
            contenedor.salir
          }else{
            vista.consumidorDurmiendo()
            dormir();
            tomarProducto( contenedor )
          }
          vista.consumidorTrabajando()
          trabajar();
       }
   }
   

    protected def dormir(): Unit = {
        val numeroAleatorio = scala.util.Random.nextInt(maxRandom);
        Thread.sleep ( numeroAleatorio );
    }
   

  protected def trabajar() : Unit = {
       val numeroAleatorio = scala.util.Random.nextInt(maxRandom);
       Thread.sleep ( numeroAleatorio );
  }   
   
}