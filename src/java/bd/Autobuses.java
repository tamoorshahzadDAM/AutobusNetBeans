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
public class Autobuses {

    /**
     * CREATE TABLE Autobuses( MATRICULA VARCHAR(10) PRIMARY KEY, PASS
     * VARCHAR(50));
     */

    //Variables
    private String matricula;
    private String pass;

    /**
     * Constructor de Autobuses
     * @param matricula
     * @param pass 
     */
    public Autobuses(String matricula, String pass) {
        this.matricula = matricula;
        this.pass = pass;
    }

    /**
     * Metodo para obtener matricula de autobus.
     * @return 
     */
    public String getMatricula() {
        return matricula;
    }

    /**
     * Metodo para obtener contraseña.
     * @return 
     */
    public String getPass() {
        return pass;
    }

}
