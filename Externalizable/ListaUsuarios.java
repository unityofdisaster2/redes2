import java.util.*;
import java.io.*;

class ListaUsuarios implements Serializable{
    private LinkedList lista = new LinkedList();
    int valor;
    ListaUsuarios(String[] usuarios,String[] passwords){
        for(int i = 0; i<usuarios.length;i++){
            lista.add(new Usuario(usuarios[i],passwords[i]));
        }
    }
    public void muestraUsuarios(){
        ListIterator li = lista.listIterator();
        Usuario u;
        while(li.hasNext()){
            u = (Usuario)li.next();
            u.muestraUsuario();
        }
    }
}