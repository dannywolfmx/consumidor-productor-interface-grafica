package main.scala

import scala.concurrent
import scala.concurrent.{Await, Future, Promise}
import scala.collection.mutable.ArrayBuffer
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.Duration
//import main.java.CustomOutputStream
import scala.swing._
import java.io.PrintStream
import scala.swing.event._
import main.scala.modelo.Contenedor

import main.scala.vista.Vista
import main.scala.controlador.Controlador



object Main extends App{
    val vista = new Vista
    val controlador = new Controlador(vista);
  

    val listaProductos = Seq.range(1,31)//[1...30]

    val contenedor = Contenedor.instancia;


    def loopProductor(){
      for(a <- listaProductos){
    		controlador.agregaProducto(contenedor,a.toString); 
    	}
      loopProductor
    }

    val productores = Future{
        loopProductor
    }
    
    val consumidores = Future{   
      while(true){
        controlador.tomarProducto(contenedor);
      }   
    	
    }

    Await.ready(productores,Duration.Inf);
    Await.ready(consumidores,Duration.Inf);


}