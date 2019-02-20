package UDP;

import java.net.*;
import java.io.*;

public class SEcoUDPB{
    public static void main(String[] args){
        try{
            DatagramSocket s = new DatagramSocket(2000);
            System.out.println("Servidor iniciado");
            for(;;){
                //se crea variable para realizar lectura de datagrama
                DatagramPacket p = new DatagramPacket(new byte[10000],10000);

                s.receive(p); //bloqueo
                System.out.println("Datagrama recibido desde "+p.getAddress()+":"+p.getPort());
                String msj = new String(p.getData(),0,p.getLength());
                System.out.println("Con el mensaje: "+msj);
                System.out.println("se envia mensaje de vuelta...");
                msj = "servidor: "+msj;
                byte[] b = msj.getBytes();
                //se crea variable para realizar envio de datagrama 
                DatagramPacket w = new DatagramPacket(b,b.length,p.getAddress(),4000);
                s.send(w);                

            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}