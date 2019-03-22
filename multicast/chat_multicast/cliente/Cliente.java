/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cliente;

import app.SalaChat;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.io.*;
import java.util.LinkedList;

/**
 *
 * @author sandu
 */
public class Cliente {
    String usuario;
    InetAddress grupo;
    int puerto, puertoGpo = 4000;
    MulticastSocket cl;
    DatagramPacket enviados, recibidos;
    SalaChat chat;

    /**
     * Iniciar variables de cliente incluyendo puerto
     * 
     * @param usuario
     * @param grupo
     * @param puerto
     */
    public Cliente(String usuario, String grupo, int puerto) {

        this.usuario = usuario;
        try {
            this.grupo = InetAddress.getByName(grupo);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Funcion para actualizar lista de usuarios cada que se agrega o elimina uno
     * del chat
     * 
     * @return Se retorna una lista que contiene el nombre de los usuarios que estan
     *         actualmente conectados al chat
     */
    public LinkedList<String> updateLista() {
        try {
            String pet = "5";
            // se envia peticion al servidor
            enviados = new DatagramPacket(pet.getBytes(), pet.getBytes().length, grupo, 4000);
            cl.send(enviados);

            recibidos = new DatagramPacket(new byte[1024 * 4], 1024 * 4);
            cl.receive(recibidos);
            // se recibe un arreglo de bytes que corresponde a un objeto LinkedList<String>
            ByteArrayInputStream bais = new ByteArrayInputStream(recibidos.getData());
            ObjectInputStream ois = new ObjectInputStream(bais);
            // se hace lectura de objeto y se castea al tipo de lista requerido
            LinkedList<String> listaAux = (LinkedList<String>) ois.readObject();
            return listaAux;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // obtiene objeto de la sala por si es necesario actualizarlo desde el cliente
    // (no se utilizo)
    public void setSalaChat(SalaChat chat) {
        this.chat = chat;
    }

    /**
     * Iniciar variables de cliente sin puerto
     * 
     * @param usuario
     * @param grupo
     */
    public Cliente(String usuario, String grupo) {
        this.usuario = usuario;
        try {
            this.grupo = InetAddress.getByName(grupo);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Funcion para iniciar conexion con el chat principal
     * 
     * @return retorna una cadena donde se indica que el usuario se ha unido al chat
     *         o un mensaje de error si no fue posible
     */
    public String iniciarConexion() {
        try {
            cl = new MulticastSocket(9999);
            cl.setReuseAddress(true);
            cl.joinGroup(grupo);
            String pet = "1";
            enviados = new DatagramPacket(pet.getBytes(), pet.getBytes().length, grupo, 4000);
            cl.send(enviados);
            // se envia datagrama con nombre de usuario
            enviados = new DatagramPacket(this.usuario.getBytes(), this.usuario.getBytes().length, grupo, 4000);
            cl.send(enviados);

            return this.usuario + " se ha unido al chat";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "Error";
    }

    /**
     * funcion para salir del chat
     */
    public void salir() {
        try {
            String pet = "3";
            enviados = new DatagramPacket(pet.getBytes(), pet.getBytes().length, grupo, puertoGpo);
            cl.send(enviados);
            // se envia nombre de usuario para que sea eliminado de la lista
            enviados = new DatagramPacket(this.usuario.getBytes(), this.usuario.getBytes().length, grupo, puertoGpo);
            cl.send(enviados);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Funcion para enviar un mensaje a la sala principal del chat
     * 
     * @param mensaje se recibe como parametro el mensaje que se quiere enviar a la
     *                sala
     */
    public void enviarASala(String mensaje) {
        try {

            byte[] b = mensaje.getBytes();
            String pet = "2";
            enviados = new DatagramPacket(pet.getBytes(), pet.getBytes().length, grupo, 4000);
            cl.send(enviados);
            // se envia nombre de usuario que esta mandando el mensaje
            byte[] u = this.usuario.getBytes();
            enviados = new DatagramPacket(u, u.length, grupo, 4000);
            cl.send(enviados);

            // se envia mensaje
            enviados = new DatagramPacket(b, b.length, grupo, 4000);
            cl.send(enviados);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Funcion para enviar un mensaje privado a un usuario especifico
     * 
     * @param emisor   Nombre de usuario que esta enviando el mensaje
     * @param receptor Nombre de usuario que recibe
     * @param mensaje  Mensaje privado
     */
    public void enviarPrivado(String emisor, String receptor, String mensaje) {
        String pet = "4";
        byte[] e = emisor.getBytes();
        byte[] r = receptor.getBytes();
        byte[] m = mensaje.getBytes();
        try {
            // se envia peticion
            enviados = new DatagramPacket(pet.getBytes(), pet.getBytes().length, grupo, 4000);
            cl.send(enviados);

            enviados = new DatagramPacket(e, e.length, grupo, 4000);
            cl.send(enviados);

            enviados = new DatagramPacket(r, r.length, grupo, 4000);
            cl.send(enviados);

            enviados = new DatagramPacket(m, m.length, grupo, 4000);
            cl.send(enviados);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Funcion para recuperar mensajes del servidor
     * 
     * @return cadena con el mensaje mas reciente del servidor
     */
    public String recuperaMensaje() {
        try {
            recibidos = new DatagramPacket(new byte[2000], 2000);
            cl.receive(recibidos);
            String str = new String(recibidos.getData(), 0, recibidos.getLength());

            if (str.contains("privado")) {
                // si el mensaje contiene el nombre de usuario
                // significa que es participante del chat privado y el mensaje sera tomado en
                // cuenta
                if (str.contains(this.usuario)) {
                    return "<p>" + str + "</p>";
                } else {
                    return "";
                }
            } else {
                return "<p>" + str + "</p>";
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

}
