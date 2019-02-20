package multicast;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

/**
 * SMulticastB
 */
public class SMulticastB {

    public static void main(String[] args) {
        InetAddress gpo = null;
        try {
            MulticastSocket s = new MulticastSocket(9876);
            s.setReuseAddress(true);
            s.setTimeToLive(1);
            String msj = "hola";
            byte[] b = msj.getBytes();
            gpo = InetAddress.getByName("228.1.1.1");
            s.joinGroup(gpo);
            for(;;){
                DatagramPacket p = new DatagramPacket(b,b.length, gpo,9999);
                s.send(p);
                System.out.println("Mensaje :"+msj+"con TTL= "+s.getTimeToLive());
                try {
                    Thread.sleep(3000);//espera 3 segundos para volver a enviar el paquete
                } catch (InterruptedException io) {
                    io.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}