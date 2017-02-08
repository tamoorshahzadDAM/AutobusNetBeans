/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bd;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Tamoor
 */
public class Conexion {

    Connection connection;

    public Conexion() {
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            connection = DriverManager.getConnection("jdbc:oracle:thin:@192.168.180.10:1521:INSLAFERRERI", "TAMOOR", "1234");
            // connection = DriverManager.getConnection("jdbc:oracle:thin:@ieslaferreria.xtec.cat:8081:INSLAFERRERI", "TAMOOR","1234");
        } catch (SQLException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public Connection getConnection() {
        return connection;
    }

    public void finalizarConexion() throws SQLException {
        connection.close();
    }

    public boolean insertarLocalizacion(Localizacion loc) throws SQLException {
        String sql = "INSERT INTO Localizacion (ID_LOC, LATITUD, LONGITUD, FECHA, MATRICULA) VALUES (?, ?, ?, ?, ?)";
        PreparedStatement stmt = connection.prepareStatement(sql);
        
        stmt.setInt(1, loc.getIdloc());
        stmt.setDouble(2, loc.getLatitud());
        stmt.setDouble(3, loc.getLongitud());
        stmt.setString(4, loc.getFecha());
        stmt.setString(5, loc.getMatricula());
        int res = stmt.executeUpdate();
        finalizarConexion();

        return (res == 1);
    }

    public List<Localizacion> obtenerLocalizacion() throws SQLException {
        ResultSet rset;
        List<Localizacion> lista = new ArrayList();
        String sql = "SELECT ID_LOC, LATITUD, LONGITUD, FECHA, MATRICULA FROM Localizacion";
        PreparedStatement stmt = getConnection().prepareStatement(sql);
        rset = stmt.executeQuery();
        while (rset.next()) {
            lista.add(new Localizacion(rset.getInt("ID_LOC"), rset.getDouble("LATITUD"),
                    rset.getDouble("LONGITUD"), rset.getString("FECHA"), rset.getString("MATRICULA")));

        }
        finalizarConexion();
        return lista;
    }

    public Localizacion obtenerLocalizacion(int id) throws SQLException {
        Localizacion loc = null;

        ResultSet rset;

        String sql = "SELECT ID_LOC, LATITUD, LONGITUD, FECHA, MATRICULA FROM Localizacion";
        PreparedStatement stmt = getConnection().prepareStatement(sql);
        stmt.setInt(1, id);
        rset = stmt.executeQuery();
        while (rset.next()) {
            loc = new Localizacion(rset.getInt("ID_LOC"), rset.getDouble("LATITUD"), 
                    rset.getDouble("LONGITUD"), rset.getString("FECHA"), rset.getString("MATRICULA"));

        }
        finalizarConexion();
        return loc;

    }

    public boolean actualizarLocalizacion(Localizacion loc) throws SQLException {
        boolean result;
        String sql = "UPDATE Localizacion SET LATITUD = ?, LONGITUD = ?, FECHA = ? WHERE ID_LOC = ? and MATRICULA = ?";
        PreparedStatement stmt = connection.prepareStatement(sql);
        
        stmt.setDouble(1, loc.getLatitud());
        stmt.setDouble(2, loc.getLongitud());
        stmt.setString(3, loc.getFecha());
        stmt.setInt(4, loc.getIdloc());
        stmt.setString(5, loc.getMatricula());

        int res = stmt.executeUpdate();
        if (res == 0) {
            result = insertarLocalizacion(loc);
        } else {
            result = true;
        }

        return (result);
    }

    public boolean eliminarLocalizacion(int id) throws SQLException {
        boolean result;
        String sql = "DELETE FROM Localizacion WHERE ID_LOC = ?";
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setInt(1, id);

        int res = stmt.executeUpdate();

        return (res == 1);
    }

}
