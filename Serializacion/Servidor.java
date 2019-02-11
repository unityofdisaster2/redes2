import java.net.*;
import java.io.*;

public class Servidor {
    public static void main(String args[]) {
        ObjectOutputStream oos = null;
        ObjectInputStream ois = null;
        try {
            ServerSocket s = new ServerSocket(9999);
            System.out.println("Servidor iniciado...");
            for (;;) {
                Socket cl = s.accept();
                System.out.println("cliente conectado desde " + cl.getInetAddress() + ":" + cl.getPort());
                oos = new ObjectOutputStream(cl.getOutputStream());
                ois = new ObjectInputStream(cl.getInputStream());
                Usuario u = (Usuario) ois.readObject();// **
                System.out.println("Objeto recibido... extrayendo informacion");
                System.out.println("Nombre " + u.getNombre());
                System.out.println("A paterno " + u.getPaterno());
                System.out.println("A materno " + u.getMaterno());
                System.out.println("Password " + u.getPWD());
                System.out.println("Edad " + u.getEdad());
                System.out.println("Devolviendo objeto...");
                oos.writeObject(u);
                oos.flush();

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}