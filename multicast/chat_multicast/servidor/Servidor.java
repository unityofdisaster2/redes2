/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servidor;

import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.HashMap;
import java.util.LinkedList;

/**
 *
 * @author sandu
 */
public class Servidor {

    public static void main(String[] args) {
        InetAddress gpo = null;

        /*
         * se crea diccionario con una cantidad moderada de posibles emojis
         */
        HashMap<String, String> listaEmojis = new HashMap<>();
        listaEmojis = new HashMap<>();
        listaEmojis.put(":D", "&#x1F600");
        listaEmojis.put("XD", "&#x1F606");
        listaEmojis.put("':\\)", "&#x1F605");
        listaEmojis.put("x'D", "&#x1F923");
        listaEmojis.put(":\\)", "&#x1F642");
        listaEmojis.put("\\(:", "&#x1F643");
        listaEmojis.put(";\\)", "&#x1F609");
        listaEmojis.put(":$", "&#x1F60A");
        listaEmojis.put("0:\\)", "&#x1F607");
        listaEmojis.put("8-D", "&#x1F60D");
        listaEmojis.put(":\\*", "&#x1F618");
        listaEmojis.put(":3", "&#x1F617");
        listaEmojis.put(":9", "&#x1F60B");
        listaEmojis.put(";p", "&#x1F61C");
        listaEmojis.put(":\\(", "&#x1F641");
        listaEmojis.put(":o", "&#x1F62E");
        listaEmojis.put(":'\\(", "&#x1F622");
        try {
            MulticastSocket s = new MulticastSocket(4000);
            // se hace la direccion reutilizable para que pueda ser utilizada por multiples
            // sockets
            s.setReuseAddress(true);
            s.setTimeToLive(1);
            gpo = InetAddress.getByName("230.1.1.1");
            s.joinGroup(gpo);
            String msj = "";
            DatagramPacket recibidos, enviados;
            String peticion;
            // nombre,puerto
            String user, aux;
            LinkedList<String> listaUsuarios = new LinkedList<>();
            for (;;) {
                // se espera del cliente un datagrama que contiene una peticion
                recibidos = new DatagramPacket(new byte[2000], 2000);
                s.receive(recibidos);
                peticion = new String(recibidos.getData(), 0, recibidos.getLength());
                // peticion para agregar usuario
                if (peticion.equals("1")) {
                    System.out.println("llamada a peticion 1");
                    recibidos = new DatagramPacket(new byte[2000], 2000);
                    s.receive(recibidos);
                    // se recibe el nombre del usuario
                    String nom = new String(recibidos.getData(), 0, recibidos.getLength());
                    listaUsuarios.add(nom);
                    // se agrega usuario a la lista de usuarios conectados
                    for (String est : listaUsuarios) {
                        System.out.println("usuario " + est);
                    }

                }
                // peticion para recibir mensaje de cliente y enviarlo a la sala
                if (peticion.equals("2")) {
                    System.out.println("llamada a peticion 2");
                    // primero se recibe nombre de usuario
                    recibidos = new DatagramPacket(new byte[2000], 2000);
                    s.receive(recibidos);
                    user = new String(recibidos.getData(), 0, recibidos.getLength());

                    // despues se recibe mensaje
                    recibidos = new DatagramPacket(new byte[2000], 2000);
                    s.receive(recibidos);
                    msj = new String(recibidos.getData(), 0, recibidos.getLength());

                    if (msj.contains("unido") || msj.contains("salido")) {
                        aux = msj;
                    }
                    // se verifica si el mensaje tiene algun emoji, en caso de encontrar alguno
                    // se sustituye por su codigo exadecimal para poder desplegar el grafico
                    // correspondiente
                    else {
                        for (String faces : listaEmojis.keySet()) {
                            msj = msj.replaceAll(faces, listaEmojis.get(faces));
                        }
                        // se encierra mensaje entre simbolos <user>msj
                        aux = "&lt;" + user + "&gt;" + msj;

                    }
                    enviados = new DatagramPacket(aux.getBytes(), aux.getBytes().length, gpo, 9999);

                    s.send(enviados);
                }
                // remover usuario de la lista
                if (peticion.equals("3")) {
                    System.out.println("llamada a peticion 3");
                    recibidos = new DatagramPacket(new byte[2000], 2000);
                    s.receive(recibidos);
                    // se recibe nombre de usuario a eliminar
                    String usAux = new String(recibidos.getData(), 0, recibidos.getLength());
                    listaUsuarios.remove(listaUsuarios.indexOf(usAux));
                    // se envia mensaje de confirmacion de que el usuario ha sido removido del canal
                    aux = usAux + " ha salido de la sala";
                    enviados = new DatagramPacket(aux.getBytes(), aux.getBytes().length, gpo, 9999);
                    s.send(enviados);

                }
                // peticion para mensajes privados
                if (peticion.equals("4")) {
                    System.out.println("llamada a peticion 4");
                    // orden de mensajes emisor, receptor, mensaje
                    recibidos = new DatagramPacket(new byte[2000], 2000);
                    s.receive(recibidos);
                    // nombre del emisor
                    String emisor = new String(recibidos.getData(), 0, recibidos.getLength());

                    recibidos = new DatagramPacket(new byte[2000], 2000);
                    s.receive(recibidos);
                    // nombre del receptor
                    String receptor = new String(recibidos.getData(), 0, recibidos.getLength());

                    // mensaje del emisor
                    recibidos = new DatagramPacket(new byte[2000], 2000);
                    s.receive(recibidos);
                    String mensaje = new String(recibidos.getData(), 0, recibidos.getLength());
                    // se verifica si hay emojis
                    for (String faces : listaEmojis.keySet()) {
                        mensaje = mensaje.replaceAll(faces, listaEmojis.get(faces));
                    }
                    // se encierra el mensaje de la siguiente forma:
                    // <privado><emisor><receptor>mensaje
                    aux = "&lt;privado&gt;" + "&lt;" + emisor + "&gt;" + "&lt;" + receptor + "&gt;" + mensaje;
                    byte[] b = aux.getBytes();
                    enviados = new DatagramPacket(b, b.length, gpo, 9999);
                    s.send(enviados);
                }
                // peticion para enviar lista de usuarios al cliente
                if (peticion.equals("5")) {
                    System.out.println("llamada a peticion 5");
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    ObjectOutputStream oos = new ObjectOutputStream(baos);
                    // se convierte la lista de usuarios en un arreglo de bytes y se envia al
                    // cliente
                    oos.writeObject(listaUsuarios);
                    byte[] lst = baos.toByteArray();
                    enviados = new DatagramPacket(lst, lst.length, gpo, 9999);
                    s.send(enviados);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
