/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bd;

/**
 *
 * @author Tamoor
 */
public class Localizacion {

    /**
     *
     * CREATE TABLE Localizacion( ID_LOC NUMBER(10,0) , LATITUD NUMBER(10,0),
     * LONGITUD NUMBER(10,0), FECHA DATE, MATRICULA VARCHAR(50), CONSTRAINT
     * pk_localizacion PRIMARY KEY (MATRICULA, ID_LOC) );
     */

    //Atributos.
    private int idloc;
    private double latitud;
    private double longitud;
    private String fecha;
    private String matricula;

    /**
     * Constructor de Localizacion, y inicializa cada variable.
     * @param idloc
     * @param latitud
     * @param longitud
     * @param fecha
     * @param matricula 
     */
    public Localizacion(int idloc, double latitud, double longitud, String fecha, String matricula) {
        this.idloc = idloc;
        this.latitud = latitud;
        this.longitud = longitud;
        this.fecha = fecha;
        this.matricula = matricula;
    }

    /**
     * Metodo para obtener localizacion de autobus.
     * @return 
     */
    public int getIdloc() {
        return idloc;
    }

    /**
     * MEtodo para obtener latitud.
     * @return latitud
     */
    public double getLatitud() {
        return latitud;
    }

    /**
     * Metodo para obtener lagitud.
     * @return longitud
     */
    public double getLongitud() {
        return longitud;
    }

    /**
     * Metodo para obtener fecha.
     * @return fecha
     */
    public String getFecha() {
        return fecha;
    }

    /**
     * Metodo para obtener Matricula.
     * @return matricula
     */
    public String getMatricula() {
        return matricula;
    }

    /**
     * Methodo para poner id de localizacion.
     * @param idloc 
     */
    public void setIdloc(int idloc) {
        this.idloc = idloc;
    }

    /**
     * Metodo para insertar latitud.
     * @param latitud 
     */
    public void setLatitud(double latitud) {
        this.latitud = latitud;
    }

    /**
     * Metodo para insertar longitud
     * @param longitud 
     */
    public void setLongitud(double longitud) {
        this.longitud = longitud;
    }

    /**
     * Metodo para insertar fecha.
     * @param fecha 
     */
    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    /**
     * Metodo para insertar matricula.
     * @param matricula 
     */
    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

}
