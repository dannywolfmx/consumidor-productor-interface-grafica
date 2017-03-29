package main.scala.vista
import scala.swing._
import scala.swing.event._
import javax.swing.ImageIcon



case object AgregarProducto extends Event

class Vista extends Publisher{
  
  val consola = new TextArea(rows = 30, columns = 10);
  val contenedorConsola = new ScrollPane(consola);
  val botonAgregar = new Button("Agregar")
  val textProductoAgregar = new TextArea(rows = 1, columns = 10);
  
  val listaDeProductosVista = new ListView[String]
  
  listaDeProductosVista.fixedCellHeight_=(15)
  listaDeProductosVista.fixedCellWidth_=(150)
  
  
  val panelAgregarProducto = new FlowPanel{
    contents.append(
      botonAgregar,
      textProductoAgregar
    )
  }
  
  val colorDurmiendo        = new Color( 52,152,219 )
  val colorTrabajando       = new Color( 155,89,182 ) 
  val colorDentroContenedor = new Color( 46,204,113 ) 

  

  val colorDefaultProductor   =   new Color( 52,73,94 )
  val colorDefaultConsumidor  =  new Color( 127,140,141 )
  
  val productor = new Label{
    preferredSize_=( new Dimension(100,50) )
    background_=( colorDefaultProductor )
    opaque_=( true )
    text_=( "Productor" )
  }
  
  val consumidor = new Label{
    preferredSize_=( new Dimension( 100,50 ) )
    background_=( colorDefaultConsumidor )
    opaque_=( true )
    text_=( "Consumidor" )
  }

  val contenedor = new Label{
    preferredSize_=( new Dimension( 100,50 ) )
    opaque_=( true )
    text_=( "Contenedor" )
  }
  
  
  locally{
    val ventanaPrincipal = new MainFrame{
        preferredSize_=( new Dimension( 500,500 ) )
        title = "Detesto swing"
        contents = new FlowPanel{
          preferredSize_=( new Dimension( 500,500 ) )
          contents.append(
              productor,
              consumidor,
              contenedor,
              listaDeProductosVista
              )        
            focusable=true
            listenTo(keys)
            reactions += {
                case KeyPressed(_, Key.Escape, _, _) =>{
                    println("Salir")
                    sys.exit(0);
                }
            }
        }
       
        centerOnScreen

        visible = true
    }
  }

  
  def dameNuevoProducto:String =  {
    val texto = textProductoAgregar.text
    textProductoAgregar.text = ""
    texto   
  }
  
  
  /////////
  //Actualiza lista de productos
  /////////
  
  def actualizaListaDeProductos( lista:Seq[ String ] )  {
    println(lista)
    listaDeProductosVista.listData_=( lista )
    listaDeProductosVista.repaint();

  }

  /////////
  //Actualiza lista de productos
  /////////

  def productorTrabajando() = productor.background_=(colorTrabajando)
  def productorDurmiendo() = productor.background_=(colorDurmiendo)
  def productorContenedor() = {
    consumidor.background_=(colorDefaultConsumidor)
    productor.background_=(colorDentroContenedor)
    contenedor.background_=(colorDefaultProductor)
  }
  
  def consumidorTrabajando() = consumidor.background_=(colorTrabajando)
  def consumidorDurmiendo() = consumidor.background_=(colorDurmiendo)
  def consumidorContenedor() = {
    productor.background_=(colorDefaultProductor)
    consumidor.background_=(colorDentroContenedor)
    contenedor.background_=(colorDefaultConsumidor)
  }

  //////////
  //EVENTOS
  /////////
  
  listenTo(botonAgregar)
  
  reactions += {
    case ButtonClicked(_) => publish(AgregarProducto)
  }
  
  
  
}

