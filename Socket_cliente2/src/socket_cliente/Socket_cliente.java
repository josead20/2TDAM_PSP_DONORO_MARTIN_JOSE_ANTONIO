/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package socket_cliente;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Socket_cliente {
    

    public static void main(String[] args) {
        
        String modo;
        String cadena;
        String mensaje;

        Scanner entradaEscaner = new Scanner (System.in); //Creación de un objeto Scanner
        
        System.out.println("Quieres cifrar o descifrar?");
        modo = entradaEscaner.nextLine (); //Invocamos un método sobre un objeto Scanner
        System.out.println("Escribe el mensaje:");
        cadena = entradaEscaner.nextLine (); //Invocamos un método sobre un objeto Scanner
        
        mensaje=modo.trim()+"-"+cadena.trim()+"@";
        mensaje=mensaje.toLowerCase();
        
      try {
            System.out.println("Creando Socket cliente");
            Socket clien = new Socket();

            System.out.println("Estableciendo la conexión");
            InetSocketAddress direcServidor = new InetSocketAddress("localhost", 55555);
            clien.connect(direcServidor);

            OutputStream salida = clien.getOutputStream();
            InputStream entrada = clien.getInputStream();

            System.out.println("Enviando mensaje");
            String delimitador = "@";
            salida.write(mensaje.getBytes());
            System.out.println("Mensaje a "+modo+":"+ cadena.replace("@", "") );

            byte[] buffer = new byte[1];
            String respuesta = "";
            String caracter;
            do {
                //va leyendo caracter a caracter
                entrada.read(buffer);
                caracter = new String(buffer);
                //concatena
                respuesta = respuesta + caracter;

                //mientres no detecte el caracter de fin sigue leyendo
            } while (!caracter.equals("@"));
            
            //Elimina el delimitador
            respuesta= respuesta.replace("@", "");

            System.out.println("Respuesta recibida: " + respuesta);

            System.out.println("Cerrando Socket del cliente");
            clien.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
}
