/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package socket_server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class Socket_server {
    
public static void main(String[] args) {
    while(true){
        try{
            System.out.println("Creando Socket servidor");
            ServerSocket serv = new ServerSocket();

            System.out.println("Asignando direccionn y puerto");
            InetSocketAddress direcServidor = new InetSocketAddress("localhost", 55555);
            serv.bind(direcServidor);

            System.out.println("Aceptando conexiones");
            Socket SockCliente = serv.accept();

            System.out.println("Conexión aceptada");

            InputStream entrada = SockCliente.getInputStream();
            OutputStream salida = SockCliente.getOutputStream();

            byte[] buffer = new byte[1];
            String mensaje = "";
            String caracter = "";

            do {
                //va leyendo caracter a caracter
                entrada.read(buffer);
                caracter = new String(buffer);
                //concatena
                mensaje = mensaje + caracter;

                //mientres no detecte el caracter de fin sigue leyendo
            } while (!caracter.equals("@"));
            //Elimina el delimitador
            mensaje = mensaje.replace("@", "");
            
            System.out.println("Mensaje recibido: " + mensaje);
            
            responde(mensaje, salida);

            System.out.println("Cerrando nuevo Socket del cliente");

            SockCliente.close();

            System.out.println("cerrando Socket servidor");
            serv.close();

            System.out.println("FIN");
            System.out.println("___________________-------------------------____________________");
        } catch (IOException e) {
                e.printStackTrace();
        }
       }
}
    public static void responde (String cadena, OutputStream salida) throws IOException
    {
        String respuesta;
        String delimitador = "@";
        String modo="";
        String mensaje="";
        
        String[] parts = cadena.split("-");
        modo=parts[0];
        mensaje=parts[1];

        switch (modo)
        {
            case "cifrar":
                    respuesta = cifradoCesar(mensaje);                    
                   break;
            case "descifrar":
                    respuesta = descifradoCesar(mensaje);

                   break;
            default:
                respuesta = "Opcion no valida";

                   break;
        }
        respuesta+=delimitador;
        salida.write(respuesta.getBytes());
        System.out.println("respuesta enviada: "+ respuesta.replace("@", ""));
        System.out.println("Fin de la respuesta");
    }

    
    public static String cifradoCesar(String texto) {
        String cifrado = "";
        
        //recorremos la string
        for (int i = 0; i < texto.length(); i++) {
            
            //comprobamos si la letra de la posicion esta entre a y z
            if (texto.charAt(i) >= 'a' && texto.charAt(i) <= 'z') {
                
                //si la posicion de la letra es > que z (26) 
                if ((texto.charAt(i) + 3) > 'z') {
                    
                    cifrado+=((char) (texto.charAt(i) + 3 - 26));
                    
                } else {
                    
                    cifrado+=((char) (texto.charAt(i) + 3));
                    
                }
            } 
        }
        return cifrado;
    }
    
    public static String descifradoCesar(String texto) {
        String cifrado = "";
        
        //recorremos la string
        for (int i = 0; i < texto.length(); i++) {
            
            //comprobamos si la letra de la posicion esta entre a y z
            if (texto.charAt(i) >= 'a' && texto.charAt(i) <= 'z') {
                
                //si la posicion de la letra es < que a (1) 
                if ((texto.charAt(i) - 3) < 'a') {
                    
                    cifrado+=((char) (texto.charAt(i) - 3 + 26));
                    
                } else {
                    
                    cifrado+=((char) (texto.charAt(i) -3 ));
                    
                }
            } 
        }
        return cifrado;
    }
}
