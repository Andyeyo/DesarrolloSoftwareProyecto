/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectofinaldes;

import java.sql.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author PC017
 */
public class Conexion {
    Connection conexion=null;
   public Connection conexion(){
     String driver = "org.postgresql.Driver"; // el nombre de nuestro driver Postgres.
String connectString = "jdbc:postgresql://localhost:5432/facturacion"; // llamamos nuestra bd
String user = "postgres"; // usuario postgres
String password = "123456"; // no tiene password nuestra bd.
       
    try {
            Class.forName(driver);
            //Hacemos la coneccion.
          conexion = DriverManager.getConnection(connectString, user, password);
            //Si la conexion fue realizada con exito, muestra el sgte mensaje.
//            System.out.println("Conexion a la base de datos Ejemplo realizada con exito! ");
                // TODO add your handling code here:
        } catch (Exception ex) {
            System.out.println("Error de conexion ");
        } 
    return conexion;
   }
   
public static void main(String[] args) {
        Conexion c = new Conexion();
        c.conexion();
    }
}
