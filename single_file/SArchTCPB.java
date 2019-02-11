/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author sandu
 */
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class SArchTCPB {
    public static void main(String[] args) {
        try {
            // creamos el socket
            ServerSocket s = new ServerSocket(7010);
            // iniciamos el ciclo infinito
            for (;;) {
                // esperamos una conexion
                System.out.println("esperando conexion");
                Socket cl = s.accept();
                System.out.println("conexion establecida desde: " + cl.getInetAddress() + ":" + cl.getPort());
                DataInputStream dis = new DataInputStream(cl.getInputStream());
                byte[] b = new byte[1024];

                String nombre = dis.readUTF();
                long tam = dis.readLong();
                System.out.println("recibimos el archivo: " + nombre);

                DataOutputStream dos = new DataOutputStream(new FileOutputStream(nombre));
                // seccion para recibir el archivo
                Long recibidos = 0l;
                int n, porcentaje;
                while (recibidos < tam) {
                    n = dis.read(b);
                    dos.write(b, 0, n);
                    dos.flush();
                    recibidos = recibidos + n;
                    porcentaje = (int) (recibidos * 100 / tam);
                    System.out.print("Recibido: " + porcentaje + "%\n");
                }
                System.out.print("\n\narchivo recibido\n");
                dos.close();
                dis.close();
                cl.close();
            }

        } catch (Exception e) {
            e.printStackTrace();
        } // try-catch
    }// main
}// class