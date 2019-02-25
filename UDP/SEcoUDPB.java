package UDP;

import java.net.*;
import java.util.Arrays;
import java.io.*;

public class SEcoUDPB{
    public static void main(String[] args){
        try{
            DatagramSocket s = new DatagramSocket(2000);
            System.out.println("Servidor iniciado");
            for(;;){
                
                //-------------------Inicia zona de recepcion---------------------------------
                //variable que recibe el numero de paquetes de tamano 20 que seran enviados
                DatagramPacket p_tam = new DatagramPacket(new byte[20],20);
                //variable principal que estara recibiendo los multiples fragmentos
                DatagramPacket p = new DatagramPacket(new byte[20],20);

                s.receive(p_tam);
                
                String mensaje = "";
                int tamano = Integer.parseInt(new String(p_tam.getData(),0,p_tam.getLength()));
                //se realiza un ciclo para recibir los paquetes de tamano 20
                for(int i = 0;i<tamano;i++){
                    s.receive(p); //bloqueo
                    mensaje+= new String(p.getData(),0,p.getLength());
                }
                
                //se muestra en pantalla resultado
                System.out.println("Con el mensaje: "+mensaje);
                System.out.println("Datagrama recibido desde "+p.getAddress()+":"+p.getPort());
                //-------------------Termina zona de recepcion---------------------------------
                InetAddress direc = p.getAddress();
                int port = p.getPort();
                //------------------------Inicia zona de eco-----------------------------------
                mensaje = "servidor responde: "+mensaje;
                byte[] respuesta = mensaje.getBytes();
                byte[] aux = new byte[20];
                tamano = (int)java.lang.Math.ceil(respuesta.length/20f);

                byte [] bt = Integer.toString(tamano).getBytes();

                p_tam = new DatagramPacket(bt, bt.length,direc,port);
                s.send(p_tam);

                int inferior=0,superior=20;
                
                for(int i = 0;i<tamano;i++){
                    if(respuesta.length<20){
                        aux = Arrays.copyOfRange(respuesta, 0, respuesta.length);
                    }
                    else if(superior < respuesta.length){
                        aux = Arrays.copyOfRange(respuesta, inferior, superior);
                    }
                    else if(superior > respuesta.length){
                        aux = Arrays.copyOfRange(respuesta, inferior, respuesta.length);
                    }
                    
                    p  = new DatagramPacket(aux,aux.length,direc,port);
                    s.send(p);
                    inferior +=20;
                    superior +=20;                    
                }

                //------------------termina zona de eco--------------------------------
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}