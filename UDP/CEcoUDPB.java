package UDP;

import java.net.*;
import java.io.*;

/**
 * CEcoUDPB
 */
public class CEcoUDPB {
    public static void main(String[] args) {
        try {
            DatagramSocket cl = new DatagramSocket();
            System.out.println("Cliente iniciado...");
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            String mensaje = br.readLine();
            byte[] b = mensaje.getBytes();
            String dst = "127.0.0.1";
            int pto = 2000;
            DatagramPacket p = new DatagramPacket(b,b.length,InetAddress.getByName(dst),pto);
            cl.send(p);
            cl.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}