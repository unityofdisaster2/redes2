package UDP;

import java.net.*;
import java.io.*;

/**
 * CEcoUDPB
 */
public class CEcoUDPB {
    public static void main(String[] args) {
        try {
            DatagramSocket cl = new DatagramSocket(4000);
            System.out.println("Cliente iniciado...");
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            String mensaje = br.readLine();
            byte[] b = mensaje.getBytes();
            String dst = "127.0.0.1";
            int pto = 2000;
            DatagramPacket p = new DatagramPacket(b,b.length,InetAddress.getByName(dst),pto);
            cl.send(p);

            DatagramPacket r = new DatagramPacket(new byte[10000],10000);
            cl.receive(r);
            String respuesta = new String(r.getData(),0,r.getLength());
            System.out.println("respuesta: "+respuesta);
            cl.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}