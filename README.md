GadgetON
========

GadgetON

GadgetON es una tienda online creada para el proyecto integrado del ciclo formativo de Grado Superior "Desarrollo de Aplicaciones Multiplataforma"

Se puede descargar desde Google Play: https://play.google.com/store/apps/details?id=com.dmontielfdez.gadgeton&hl=es_419

El proyecto lo forma varias partes:

* GadgetON: Aplicacion movil basada en Android para uso de clientes. Se pueden ver productos, añadir al carrito, comprar y tener un historial de nuestras compras.Todas las consultas se realizan a traves de un API RESTFUL. Esta adaptada a todas las versiones Android gracias a la libreria ActionBarSherlock. Además, se adapta a cualquier resolucion de pantalla o tablet. Dispone de una libreria interna llamada Card.IO (https://www.card.io/) que integra un lector de tarjetas de credito

* GadgetONAdmin: Aplicacion movil basada en Android para uso de empleados de la tienda. Se pueden realizar operaciones con productos y categorias, gestionar pedidos, escanear productos y gestionar clientes. Esta orientada para trabajar con tablets ya que hace uso de Fragments aunque dispone de resolucion para moviles.

* GadgetON API: Servidor escrito en PHP, concretamente Laravel, un framework diseñado para hacer APIs RESTFUL.

* Promotion Server: Servidor escrito es JAVA que haciendo uso de Sockets es capaz de enviar ofertas de productos desde la aplicacion de administrador a los clientes.

* ActionBarSherlock: Libreria que facilita la adaptacion del ActionBar a todas las versiones de Android. http://actionbarsherlock.com/

* Google play services lib: Libreria para los servicios de Google. En este caso para hacer uso de mapas.

* zXing: Libreria creada por los autores de Barcode Scanner. Haciendo uso de la camara del telefono escanea codigos QR.
