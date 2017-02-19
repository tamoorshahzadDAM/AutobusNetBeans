/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bd;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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

    /**
     * Es el constructor de conexion que nos asegura la conectividad entre
     * servidor con el usuario y contraseña indicado.
     */
    public Conexion() {
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            try {
                //Conectar desde clase.
                connection = DriverManager.getConnection("jdbc:oracle:thin:@192.168.180.10:1521:INSLAFERRERI", "TAMOOR", "1234");
            } catch (SQLException ex) {
                try {
                    //conectar desde casa.            
                    connection = DriverManager.getConnection("jdbc:oracle:thin:@ieslaferreria.xtec.cat:8081:INSLAFERRERI", "TAMOOR", "1234");

                } catch (SQLException e) {
                    System.out.println(e);
                }
            }

            //En el caso de fallo salta exception.
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     * Metodo para obtener conexion.
     *
     * @return connection
     */
    public Connection getConnection() {
        return connection;
    }

    /**
     * Metodo que finaliza el conexion.
     *
     * @throws SQLException
     */
    public void finalizarConexion() throws SQLException {
        connection.close();
    }

    /**
     * Metodo para insertar localizacion, que por parametros le llega un objeto
     * localizacion. Abre una conexion, inserta datos y finaliza el conexion.
     *
     * @param loc
     * @return
     * @throws SQLException
     */
    public boolean insertarLocalizacion(Localizacion loc) throws SQLException, ParseException {
        //Sentencia sql.
        String sql = "INSERT INTO Localizacion (ID_LOC, LATITUD, LONGITUD, FECHA, MATRICULA) VALUES (?, ?, ?, ?);";
        //Hace una conexion, y por parametro le pasamos sentencia sql.
        PreparedStatement stmt = connection.prepareStatement(sql);
        

        //Interto localizacion por el orden que toca a cada columna.
        stmt.setInt(1, loc.getIdloc());
        stmt.setDouble(2, loc.getLatitud());
        stmt.setDouble(3, loc.getLongitud());
        
        /**
        String fecha = loc.getFecha();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyy-MM-dd HH:mm:ss");
        //Fecha en formato
        Date fechaEnFormato = (Date) dateFormat.parse(fecha);
        //java.sql.Date fechaFinal = new java.sql.Date(fechaEnFormato.getTime());
        stmt.setDate(4, fechaEnFormato);
        */
            
        stmt.setString(4, loc.getFecha());
        
        stmt.setString(5, loc.getMatricula());
        //se actualiza y guarda el resultado en variable res.
        int res = stmt.executeUpdate();
        //finaliza la conexion.
        finalizarConexion();

        //Devuelve el res igual a 1.
        return (res == 1);
    }

    /**
     * Metodo que devuelve una lista de localizaciones.
     *
     * @return lista
     * @throws SQLException
     */
    public List<Localizacion> obtenerLocalizaciones() throws SQLException {
        ResultSet rset;
        //Crea lista de localizaciones.
        List<Localizacion> lista = new ArrayList();
        //Sentencia sql.
        String sql = "SELECT ID_LOC, LATITUD, LONGITUD, FECHA, MATRICULA FROM LOCALIZACION";
        //Se conecta y le llega sentencia sql por parametros.
        PreparedStatement stmt = getConnection().prepareStatement(sql);
        //Se ejecuta.
        rset = stmt.executeQuery();
        //Bucle que va ejecutando mientras hay valores.
        while (rset.next()) {
            //se añade en la lista
            lista.add(new Localizacion(rset.getInt("ID_LOC"), rset.getDouble("LATITUD"),
                    rset.getDouble("LONGITUD"), rset.getString("FECHA"), rset.getString("MATRICULA")));

        }
        //Se finaliza el conexion.
        finalizarConexion();
        //devuelve lista.
        return lista;
    }

    /**
     * Metodo que devuelve una localizacion.
     *
     * @param id
     * @return loc
     * @throws SQLException
     */
    public Localizacion obtenerLocalizacion(int id) throws SQLException {
        Localizacion loc = null;

        ResultSet rset;

        // Sentencia sql
        String sql = "SELECT ID_LOC, LATITUD, LONGITUD, FECHA, MATRICULA FROM Localizacion WHERE ID_LOC = ?";
        //Crea conexion y le pasamos sentencia sql por parametros.
        PreparedStatement stmt = getConnection().prepareStatement(sql);
        //le pongo primer columna de id.
        stmt.setInt(1, id);
        //Se ejecuta query
        rset = stmt.executeQuery();
        //Bucle mientras hay valores en la tabla.
        while (rset.next()) {
            //lo busca y lo guarda en loc
            loc = new Localizacion(rset.getInt("ID_LOC"), rset.getDouble("LATITUD"),
                    rset.getDouble("LONGITUD"), rset.getString("FECHA"), rset.getString("MATRICULA"));

        }
        //finaliza la conexion.
        finalizarConexion();
        //devuelve loc.
        return loc;

    }

    /**
     * Metodo que devuele localizacion actualizada.
     *
     * @param loc
     * @return
     * @throws SQLException
     */
    public boolean actualizarLocalizacion(Localizacion loc) throws SQLException, ParseException {
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

    /**
     * Metodo para eliminar localizacion.
     *
     * @param id
     * @return
     * @throws SQLException
     */
    public boolean eliminarLocalizacion(int id) throws SQLException {
        boolean result;
        //Sentencia sql
        String sql = "DELETE FROM Localizacion WHERE ID_LOC = ?";
        //Se conecta y le ponemos sentencia sql por parametros.
        PreparedStatement stmt = connection.prepareStatement(sql);
        //Le pongo id recogido de parametros.
        stmt.setInt(1, id);

        //Se ejecuta actualizacion.
        int res = stmt.executeUpdate();

        //Revuelve true si ya esta borrado.
        return (res == 1);
    }

    /**
     * Metodo para insertar autobus, le entra objecto autobus por parametros.
     *
     * @param auto
     * @return
     * @throws SQLException
     */
    public boolean insertarAutobus(Autobuses auto) throws SQLException {
        //Sentencia sql, que inserta en table de autobuses, matricula y pass.
        String sql = "INSERT INTO AUTOBUSES (MATRICULA, PASS) VALUES (?, ?)";
        //Hace conexion, y le pasamos sentencia sql por parametros.
        PreparedStatement stmt = connection.prepareStatement(sql);

        //Le añado columnas
        stmt.setString(1, auto.getMatricula());
        stmt.setString(2, auto.getPass());
        //Se ejecuta actualizacion y lo guarda en variable.
        int res = stmt.executeUpdate();
        //Finaliza el conexion.
        finalizarConexion();

        //devuelve res igual a 1.
        return (res == 1);
    }

    /**
     * Metodo para obtener una lista de todos los autobuses.
     *
     * @return lista
     * @throws SQLException
     */
    public List<Autobuses> obtenerAutobuses() throws SQLException {
        /**
         * CREATE TABLE Autobuses( MATRICULA VARCHAR(10) PRIMARY KEY, PASS
         * VARCHAR(50));
         */
        ResultSet rset;
        //se crea la lista
        List<Autobuses> lista = new ArrayList();
        //Seleciona todos los autobuses.
        String sql = "SELECT MATRICULA, PASS FROM AUTOBUSES";
        //Hace conexion, y le pasamos sentencia sql por parametros.
        PreparedStatement stmt = getConnection().prepareStatement(sql);
        //se ejecuta el query
        rset = stmt.executeQuery();
        //Mientras hay valores, se va ejecutando y va guardando la matricul y pas
        //en la lista.
        while (rset.next()) {
            lista.add(new Autobuses(rset.getString("MATRICULA"), rset.getString("PASS")));
        }
        //Finaliza el conexion.
        finalizarConexion();
        //Devuelve la lista.
        return lista;
    }

    /**
     * Metodo para obtener un autobus, para buscar lo le pasaremos la matricula
     * por parametros.
     *
     * @param matricula
     * @return autobus
     * @throws SQLException
     */
    public Autobuses obtenerAutobus(String matricula) throws SQLException {
        Autobuses autobus = null;
        ResultSet rset;
        //Se seleciona todos los campos donde el campo de matricula sea igual de
        //la que hemos introducido por parametros.
        String sql = "SELECT * FROM AUTOBUSES WHERE MATRICULA LIKE ?";

        //Hace conexion, y le pasamos sentencia sql por parametros.
        PreparedStatement stmt = getConnection().prepareStatement(sql);
        //se pone la matricula
        stmt.setString(1, matricula);
        //Ejecuta el query y el resultado se guarda en rset.
        rset = stmt.executeQuery();
        //Mientras hay valores, va buscando la matricla, cuando se encuenta se 
        //guarda la matricula y pass en variable autobus.
        while (rset.next()) {

            autobus = new Autobuses(rset.getString("MATRICULA"), rset.getString("PASS"));
        }
        //Finaliza el conexion.
        finalizarConexion();
        //devuelve autobus.
        return autobus;
    }

    /**
     * Metodo para buscar ultima posicion de todos los autobuses.
     * @return lista
     * @throws SQLException
     */
    public List<Localizacion> ultimaPosAutoBuses() throws SQLException {
        ResultSet rset;
        //Crea la lista
        List<Localizacion> lista = new ArrayList();
        //Senetencia sql que nos busca los campos de la table de localizacion, donde
        //metricula y la fecha esta de la fecha maxima en ese tabla y lo  agrupa por matricula.
        //Asi tendremos ultima posicuin de cada bus.
        String sql = "SELECT ID_LOC, LATITUD, LONGITUD, FECHA, MATRICULA FROM LOCALIZACION WHERE (MATRICULA, FECHA) IN"
                + "(SELECT MATRICULA, MAX(FECHA) FROM LOCALIZACION GROUP BY MATRICULA)";

        //Hace conexion, y le pasamos sentencia sql por parametros.
        PreparedStatement stmt = getConnection().prepareStatement(sql);
        //Se ejecuta el query
        rset = stmt.executeQuery();
        //Mientras hay filas, se va buscando y lo va añadiendo en la lista
        while (rset.next()) {
            lista.add(new Localizacion(rset.getInt("ID_LOC"), rset.getDouble("LATITUD"),
                    rset.getDouble("LONGITUD"), rset.getString("FECHA"), rset.getString("MATRICULA")));

        }
        //Finaliza el conexion.
        finalizarConexion();
        //devuelve la lista.
        return lista;
    }

    /**
     * Metodo para buscar una localizacion de un bus, pasando su matricula por parametros.
     * @param matricula
     * @return
     * @throws SQLException 
     */
     public List<Localizacion> obtenerLocDeBus(String matricula) throws SQLException {
        List<Localizacion> loc = new ArrayList<>();
        ResultSet rset;
        //Sentencia sql
        String sql = "SELECT ID_LOC, LATITUD, LONGITUD, FECHA, MATRICULA  FROM "
                + "(SELECT ID_LOC, LATITUD, LONGITUD, FECHA, MATRICULA  FROM LOCALIZACION "
                + "WHERE MATRICULA LIKE ? ORDER BY FECHA DESC)";

        //Crea conexion y le pasamos sentencia por prametros.
        PreparedStatement stmt = getConnection().prepareStatement(sql);
        stmt.setString(1, matricula);
        //Ejecuta el query.
        rset = stmt.executeQuery();
        //Mientras rset no esta vacio.
        while (rset.next()) {
            loc.add(new Localizacion(rset.getInt("ID_LOC"), rset.getDouble("LATITUD"),
                    rset.getDouble("LONGITUD"), rset.getString("FECHA"), rset.getString("MATRICULA")));
        }
        //Finaliza la conexion.
        finalizarConexion();
        
        return loc;
    }
    
    
}
