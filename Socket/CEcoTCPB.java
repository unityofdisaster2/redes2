package Socket;
import java.net.*;
import java.io.*;

public class CEcoTCPB {
    public static void main(String [] args){
        try{
            BufferedReader br1 = new BufferedReader(
                                new InputStreamReader(System.in));
            System.out.printf("Escribe la direccion del servidor:");
            String host = br1.readLine();
            System.out.printf("\n\nEscriba el puerto:");
            int pto = Integer.parseInt(br1.readLine());
            //creamos el socket y nos conectamos
            Socket cl = new Socket(host,pto);


            PrintWriter pw = new PrintWriter(new OutputStreamWriter(cl.getOutputStream()));
            // se envia el mensaje
            pw.println("este es un mensaje enviado desde el cliente");
            pw.flush();



            BufferedReader br2 = new BufferedReader(
                                new InputStreamReader(cl.getInputStream()));
            String mensaje = br2.readLine();
            System.out.println("Recibimos un mensaje desde el servidor");
            System.out.println("Mensaje: " +  mensaje);
            //cerramos flujo y socket
            br1.close();
            br2.close();
            cl.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}

/*
. Crea el socket
. Se conecta.
. Recibe el mensaje
. Cierra el socket
*/