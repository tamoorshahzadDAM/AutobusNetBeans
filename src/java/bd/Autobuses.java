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
public class Autobuses {
    /**
     * CREATE TABLE Autobuses(
MATRICULA VARCHAR(10) PRIMARY KEY,
PASS VARCHAR(50));
     */
    
    private String matricula;
    private String pass;

    public Autobuses(String matricula, String pass) {
        this.matricula = matricula;
        this.pass = pass;
    }

    public String getMatricula() {
        return matricula;
    }

    public String getPass() {
        return pass;
    }
    
    
    
    
}
