import java.net.*;

import sun.security.pkcs11.wrapper.CK_INFO;

import java.io.*;

public class Cliente{
    public static void main(String [] args){
        ObjectOutputStream oos = null;
        ObjectInputStream ois = null ;
        String host = "127.0.0.1";
        int port = 9999;
        try{
            Socket cl = new Socket(host,port);
            System.out.println("conexion establecida...");
            oos = new ObjectOutputStream(cl.getOutputStream());
            ois = new ObjectInputStream(cl.getInputStream());
            Usuario u = new Usuario("Pepito", "Perez", "Juarez", "12345", 20);
            System.out.println("Enviando objeto...");
            oos.writeObject(u);
            oos.flush();
            System.out.println("Preparado para recibir respuesta");
            Usuario u2 = (Usuario)ois.readObject();
            System.out.println("Extrayendo datos...");
            System.out.println("Nombre: "+u2.getNombre()+
                                "\nA paterno: "+u2.getPaterno()+
                                "\nA materno "+u2.getMaterno()+
                                "\nPassword: "+u2.getPWD()+
                                "\nEdad: "+u2.getEdad());
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}