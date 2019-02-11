/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


/**
 *
 * @author sandu
 */
import java.net.*;
import java.io.*;

public class SArchTCPB{
    public static void main(String[] args){
        try{
            //creamos el socket
            ServerSocket s = new ServerSocket(7010);
            //iniciamos el ciclo infinito
            for(;;){
                //esperamos una conexion
                System.out.println("esperando conexion");
                Socket cl = s.accept();
                System.out.println("conexion establecida desde: " + cl.getInetAddress()+":"+cl.getPort());
                DataInputStream dis = new DataInputStream(cl.getInputStream());
                byte[] b = new byte[1024];

                int numArchivos = dis.readInt();

                System.out.println("numero de archivos: "+numArchivos);


                int iter;
                String[] nombres = new String[numArchivos];
                Long[] tams = new Long[numArchivos];
                DataOutputStream dos = new DataOutputStream(cl.getOutputStream());
                for(iter = 0; iter < numArchivos; iter++){
                    nombres[iter] = dis.readUTF();
                    System.out.println("recibimos el archivo: "+nombres[iter]);
                    tams[iter] = dis.readLong();
                } 
                //DataOutputStream dos = new DataOutputStream(new FileOutputStream(nombres[0]));
                //seccion para recibir el archivo
                Long recibidos;
                int n=0,porcentaje;
                iter = 0;
                for(; iter < numArchivos; iter++){
                    recibidos = 0l;
                    dos = new DataOutputStream(new FileOutputStream(nombres[iter]));
                    while(recibidos < tams[iter]){
                        if(tams[iter] - recibidos < 1024){
                            n = dis.read(b,0,(int)(tams[iter]-recibidos));
                        }
                        else{
                            n = dis.read(b);
                        }
                        dos.write(b,0,n);
                                
                        
                        dos.flush();
                        //System.out.println("--------------\nbytes: "+n+" "+nombres[iter]+"\n-----------------");
                        
                        recibidos = recibidos+n;
                        porcentaje = (int)(recibidos*100/tams[iter]);
                        System.out.print("Recibido: " + porcentaje +"%\n");
                    }
                    dos.close();
                }
                System.out.print("\n\narchivo recibido\n");
                dos.close();
                dis.close();
                cl.close();
            }
        }catch(Exception e){
            e.printStackTrace();
        }//try-catch
    }//main
}//class*