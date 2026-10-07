package org.sebsy.compta;
import org.sebsy.compta.infra.Db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class Main {
    public static void main(String[] args) {//        DataSource ds = Db.getDataSource();
//        Connection connection;
//        try {
//            Connection connection = ds.getConnection();
//            System.out.println(connection);
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        } finally { // finally = ce que ca fait que ca passe dans le try ou dans le catch
//            if (null != connection) {
//                try {
//                    connection.close();
//                } catch (SQLException e) {
//                    throw new RuntimeException(e);
//                }
//            }
//
//        }

        // même chose mais beaucoup plus propre
        try(Connection cnx = Db.getDataSource().getConnection();
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery("SELECT id, nom FROM fournisseur")) {
//            System.out.println(cnx);
            while (rs.next()) {
                System.out.println("ligne fournisseur " +
                        rs.getInt("id") + " - " +
                        rs.getString("nom"));
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
}