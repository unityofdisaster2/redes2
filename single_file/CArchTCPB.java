/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author sandu
 */
package single_file;
import javax.swing.JFileChooser;
import java.net.*;
import java.io.*;

public final class CArchTCPB {
    public static void main(String[] args) {
        try {

            // ingreso de datos de servidor
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            System.out.printf("Escriba la direccion del servidor:");
            String host = br.readLine();
            System.out.printf("\n\nEscriba el puerto:");
            int pto = Integer.parseInt(br.readLine());
            Socket cl = new Socket(host, pto);
            JFileChooser jf = new JFileChooser();
            jf.setMultiSelectionEnabled(true);

            int r = jf.showOpenDialog(null);
            if (r == JFileChooser.APPROVE_OPTION) {
                // File[] f = jf.getSelectedFile();
                // se obtiene arreglo con las referencias de los archivo seleccionados
                File f = jf.getSelectedFile();
                String archivo = f.getAbsolutePath();
                String nombre = f.getName();
                long tam = f.length();

                // obtencion de flujos de datos
                DataOutputStream dos = new DataOutputStream(cl.getOutputStream());

                DataInputStream dis = new DataInputStream(cl.getInputStream());

                dis = new DataInputStream(new FileInputStream(archivo));
                dos.writeUTF(nombre);
                dos.flush();
                dos.writeLong(tam);
                dos.flush();

                byte[] b = new byte[1024];
                long enviados = 0;
                int porcentaje, n=0;

                while (enviados < tam) {
                    n = dis.read(b);
                    dos.write(b, 0, n);
                    dos.flush();
                    enviados = enviados + n;
                    System.out.print("--------------------------\ntamanio:  " + dos.size() + "\n-------------------\n");
                    porcentaje = (int) (enviados * 100 / tam);
                    System.out.print("Enviado " + porcentaje + "%\n");
                }
                System.out.println("eneeeeeeeeeeeeeeeeee"+n);
                System.out.print("\n\nArchivo Enviado");
                dos.close();
                dis.close();
                cl.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}