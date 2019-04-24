/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package conectionMySql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Marsal
 */
public class conection {

    private static Connection con;

    private static final String DB_URL = "jdbc:mysql://localhost:3306/banco";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "2016hmds";

    public conection() {
    }


    public static Connection getConnection() {
        if (con == null) {
            System.out.println("Conectando ao banco...");
            try {
                Class.forName("com.mysql.jdbc.Driver");
                con = DriverManager.getConnection("jdbc:mysql://localhost:3306/banco", DB_USER, DB_PASSWORD);
                System.out.println("Conectado.");
            } catch (ClassNotFoundException ex) {
                System.out.println("Classe não encontrada, adicione o driver nas bibliotecas.");
                Logger.getLogger(conection.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException e) {
                System.out.println(e);
                throw new RuntimeException(e);
            }
        }
        return con;
    }

    public static void desconectar() {
        try {
            con.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } catch (NullPointerException e) {
            System.out.println("method 'close()' error. Not conection opened");
        }
    }

}
