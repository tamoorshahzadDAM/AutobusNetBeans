/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bd;

/**
 *
 * @author ALUMNEDAM
 */
public class Localizacion {
    /**
     * 
CREATE TABLE Localizacion(
ID_LOC NUMBER(10,0) ,
LATITUD NUMBER(10,0),
LONGITUD NUMBER(10,0),
FECHA DATE,
MATRICULA VARCHAR(50),
CONSTRAINT pk_localizacion PRIMARY KEY (MATRICULA, ID_LOC)
);
     */
    
    private int idloc;
    private double latitud;
    private double longitud;
    private String fecha;
    private String matricula;

    public Localizacion(int idloc, double latitud, double longitud, String fecha, String matricula) {
        this.idloc = idloc;
        this.latitud = latitud;
        this.longitud = longitud;
        this.fecha = fecha;
        this.matricula = matricula;
    }

    public int getIdloc() {
        return idloc;
    }

    public double getLatitud() {
        return latitud;
    }

    public double getLongitud() {
        return longitud;
    }

    public String getFecha() {
        return fecha;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setIdloc(int idloc) {
        this.idloc = idloc;
    }

    public void setLatitud(double latitud) {
        this.latitud = latitud;
    }

    public void setLongitud(double longitud) {
        this.longitud = longitud;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }
    
    
    
    
    
    
    
    
    
    
    
}
