package multicast;

import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.UnknownHostException;

/**
 * CMulticastB
 */
public class CMulticastB {

    public static void main(String[] args) {
        InetAddress gpo = null;
        try {
            MulticastSocket cl = new MulticastSocket(9999);
            System.out.println("Cliente escuchando puerto: " + cl.getLocalPort());
            //para poder usar varios clientes debemos hacer que la direccion sea reutilizable
            cl.setReuseAddress(true);
            try {
                //nombre o numero del gripo
                gpo = InetAddress.getByName("228.1.1.1");
            } catch (UnknownHostException u) {
                System.err.println("Direccion erronea");
            }
            //se une cliente al grupo definido anteriormente
            cl.joinGroup(gpo);
            System.out.println("Unido al grupo");
            for (;;) {
                //en el ciclo se estaran recibiendo mensajes del grupo
                DatagramPacket p = new DatagramPacket(new byte[10], 10);
                cl.receive(p);
                String msj = new String(p.getData());
                System.out.println("Datagrama recibido: " + msj);
                System.out.println("Servidor descubierto: " + p.getAddress() + ":" + p.getPort());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}