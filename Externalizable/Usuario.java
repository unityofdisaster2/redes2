package Externalizable;

import java.util.*;
import java.io.*;


class Usuario implements Externalizable{
    private String usuario;
    private String password;
    public Usuario(){
        System.out.println("Creando usuario");
    }
    Usuario(String u, String p){
        System.out.println("Creando usuario("+u+","+p+")");
        usuario = u;
        password = p;
    }
    public void writeExternal(ObjectOutput out) throws IOException{
            //Explicitamente indicamos cuales atrubutos se van a enviar
            out.writeObject(usuario);
    }
    public void readExternal(ObjectInput in) throws IOException,ClassNotFoundException{
            System.out.println("Usuario readExternal");
            //explicitamente indicamos cuales atributos se van a recuperar
            usuario = (String)in.readObject();
    }
    public void muestraUsuario(){
        String cad = "Usuario: "+usuario+"Password: ";
        if(password ==null){
            cad = cad+"no disponible";
        }
        else{
            cad = cad+password;
            System.out.println(cad);
        }
    }

}