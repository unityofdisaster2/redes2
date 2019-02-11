import java.net.*;
import java.io.*;

public class SEcoTCPB extends Object {
    public static void main(String[] args) {
        try {
            // se crea el socket
            ServerSocket s = new ServerSocket(1234);
            System.out.println("Esperando Cliente: ");
            // Se crea ciclo infinito
            for (;;) {
                // bloqueo
                Socket cl = s.accept();
                System.out.println("Conexion establecida desde: " + cl.getInetAddress() + ":" + cl.getPort());
                String mensaje = "Hola Mundo";

                BufferedReader br2 = new BufferedReader(
                    new InputStreamReader(cl.getInputStream()));
                String mensajeCliente = br2.readLine();
                System.out.println("mensaje recibido: "+mensajeCliente);
                //br2.close();


                PrintWriter pw = new PrintWriter(new OutputStreamWriter(cl.getOutputStream()));
                // se envia el mensaje
                pw.println(mensaje);
                // se limpia el flujo;
                pw.flush();
                pw.close();
                cl.close();
            } // for
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

/*
. Crea ServerSocket 
. Ciclo infinito
  . Espera conexion
  . Envia, recibe
  . Cierra conexion
*/