package UDP;

import java.net.*;
import java.util.Arrays;
import java.io.*;

/**
 * CEcoUDPB
 */
public class CEcoUDPB { 
    public static void main(String[] args) {
        try {
            //se realiza conexion
            DatagramSocket cl = new DatagramSocket(4000);
            System.out.println("Cliente iniciado...");
            
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            //obtencion de mensaje desde consola
            String mensaje = br.readLine();
            byte[] b = mensaje.getBytes();
            String dst = "127.0.0.1";
            int pto = 2000;

            //se obtiene el numero de paquetes de tamano 20 en que se fragmentara el mensaje
            Integer tam = (int)(java.lang.Math.ceil(b.length/20f));
            
            byte [] bt = tam.toString().getBytes(); 

            //se crea y envia un datagrama con el tamano para que el servidor sepa cuantos paquetes recibira
            DatagramPacket p_tam = new DatagramPacket(bt,bt.length,InetAddress.getByName(dst),pto);
            cl.send(p_tam);
            

            //se crean variables para recorrer limite inferior y limite superior de arreglo de bytes
            int c_l = 0, c_m=20;
            byte [] aux = new byte[20];
            DatagramPacket p;


            for(int i=0;i<tam;i++){
                //condiciones para manejar el arreglo cuandolos tamanos difieren con los limites sup e inferior
                if(b.length<20){
                    aux = Arrays.copyOfRange(b, 0, b.length);
                }
                else if(c_m < b.length){
                    aux = Arrays.copyOfRange(b, c_l, c_m);
                }
                else if(c_m > b.length){
                    aux = Arrays.copyOfRange(b, c_l, b.length);
                }
                
                p  = new DatagramPacket(aux,aux.length,InetAddress.getByName(dst),pto);
                cl.send(p);
                c_l +=20;
                c_m +=20;
            }
            //------------------------------termina zona de envio------------------------

            //------------------------------inicia zona de recepcion---------------------

            p_tam = new DatagramPacket(new byte[20],20);
            cl.receive(p_tam);
            tam = Integer.parseInt(new String(p_tam.getData(),0,p_tam.getLength()));
            String respuesta = "";
            p = new DatagramPacket(new byte[20],20);
            for(int i =0;i<tam;i++){
                cl.receive(p);
                respuesta +=new String(p.getData(),0,p.getLength());
            }
            System.out.println(respuesta);

            cl.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}