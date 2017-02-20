/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest;

import bd.Autobuses;
import bd.Localizacion;
import bd.Conexion;
import com.google.gson.Gson;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;

/**
 * REST Web Service
 *
 * @author Tamoor
 */
@Path("generic")
public class GenericResource {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of GenericResource
     */
    public GenericResource() {
    }

    /**
     * Metodo get que lista todas las localizaciones.
     * @return an instance of java.lang.String
     */
    @GET
    @Path("localizacion")
    @Produces(MediaType.APPLICATION_JSON)
    public String listarLocalizacion() {

        //Crea conexion
        Conexion conexion = new Conexion();
        //Crea lista
        List<Localizacion> lista = null;
        try {
            //llama a metodo para obtener localizaciones, y guarda en la lista
            lista = conexion.obtenerLocalizaciones();

        } catch (SQLException ex) {
            Logger.getLogger(GenericResource.class.getName()).log(Level.SEVERE, null, ex);
        }
        Gson gson = new Gson();

        //Devuelve la lista de gson
        return gson.toJson(lista);
    }

    /**
     * Metodo put para insertar localizacion. 
     * @param loc
     * @return
     */
    @POST
    @Path("insertLocalizacion")
    @Consumes(MediaType.APPLICATION_JSON)
    public boolean insertarLocalizacion(String loc) throws ParseException {
        
        
        boolean result = true;
        //Se crea conexion
        Conexion conexion = new Conexion();
        Gson gson = new Gson();
        Localizacion localizacion = gson.fromJson(loc, Localizacion.class);
        try {

            //llamo a metodo de insertar localizacion y le paso localizacion por parametros.
            conexion.insertarLocalizacion(localizacion);
        } catch (SQLException ex) {
            Logger.getLogger(GenericResource.class.getName()).log(Level.SEVERE, null, ex);
            //EN el caso de error devuelve un false
            result = false;

        }
        return result;

    }

    /**
     * Metodo que mostra localizacion, de la id que introducimos por parametros.
     * @param id
     * @return 
     */
    @GET
    @Path("idLoc/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public String mostrarLocalizacion(@PathParam("id") int id) {
        Localizacion loc = null;
        Conexion conexion = new Conexion();
        try {
            //se llama a metodo y el resultado se guarda en loc
            loc = conexion.obtenerLocalizacion(id);
        } catch (SQLException ex) {
            //En caso de fallo
            Logger.getLogger(GenericResource.class.getName()).log(Level.SEVERE, null, ex);
        }
        Gson gson = new Gson();

        // devuelve json de loc.
        return gson.toJson(loc);
    }

    /**
     * Metodo para actualizar localizacion.
     * @param loc
     * @return 
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public boolean actualizarLocalizacion(String loc) throws ParseException {
        boolean result = true;
        //Crea conexion
        Conexion conexion = new Conexion();
        //Instancia gson
        Gson gson = new Gson();
        Localizacion localizacion;
        //se recoje loc de objecto gson de la classe localizacion y lo guarda en localizacion.
        localizacion = gson.fromJson(loc, Localizacion.class);
        try {

            //Llama a metodo de actualizar localizacion
            conexion.actualizarLocalizacion(localizacion);
        } catch (SQLException ex) {
            Logger.getLogger(GenericResource.class.getName()).log(Level.SEVERE, null, ex);
            result = false;
        }
        return result;
    }

    /**
     * Metodo para poder eliminar localizacion. En caso necesario
     * @param id 
     */
    @DELETE
    @Path("/delete/{id}")
    public void eliminarLocalizacion(@PathParam("id") int id) {
        Conexion conexion = new Conexion();

        try {

            conexion.eliminarLocalizacion(id);
        } catch (SQLException ex) {
            Logger.getLogger(GenericResource.class.getName()).log(Level.SEVERE, null, ex);

        }
    }
    
    
    
    /**
     * Metodo para mostrar todos los auto buses
     * @return 
     */
    
    @GET
    @Path("autobus")
    @Produces(MediaType.APPLICATION_JSON)
    public String mostrarAutobuses() {
        //Crea conexion
        Conexion conexion = new Conexion();
        //Crea una lista
        List<Autobuses> auto = null;
        try {
            //llama a metodo de obtener autobuses y resultado lo guarda en auto
            auto = conexion.obtenerAutobuses();

        } catch (SQLException ex) {
            Logger.getLogger(GenericResource.class.getName()).log(Level.SEVERE, null, ex);
        }
        Gson gson = new Gson();
        return auto.isEmpty() ? gson.toJson(false) : gson.toJson(auto);
    }
    
    /**
     * Metodo que muestra un bus, el de la matricula que le pasamos por parametros.
     * @param matricula
     * @return 
     */
    @GET
    @Path("{matricula}")
    @Produces(MediaType.APPLICATION_JSON)
    public String mostrarUnBus(@PathParam("matricula") String matricula) {
        Autobuses auto = null;
        Conexion conexion = new Conexion();
        try {
            auto = conexion.obtenerAutobus(matricula);
        } catch (SQLException ex) {
            Logger.getLogger(GenericResource.class.getName()).log(Level.SEVERE, null, ex);
        }
        Gson gson = new Gson();

        return auto == null ? gson.toJson(false) : gson.toJson(auto);
    }
    
    
    /**
     * Metodo que muestra ultima localizacion de un autobus pasado su matricula 
     * por parametros.
     * @param matricula
     * @return 
     */
    @GET
    @Path("ultimaPos/{matricula}")
    @Produces(MediaType.APPLICATION_JSON)
    public String mostrarLocDeUnBus(@PathParam("matricula") String matricula) {
        //Crea lista
        List<Localizacion> loc = new ArrayList<>();
        //Crea conexion
        Conexion conexion = new Conexion();
        try {
            //Ejecuta el metodo
            loc = conexion.obtenerLocDeBus(matricula);
        } catch (SQLException ex) {
            Logger.getLogger(GenericResource.class.getName()).log(Level.SEVERE, null, ex);
        }
        Gson gson = new Gson();
        return loc.isEmpty() ? gson.toJson(false) : gson.toJson(loc);
    }
    
    
    
    
    
    
    
    
    
    /**
     * Metodo que muestra ultima posicion de bus.
     * @return 
     */
    @GET
    @Path("autobus/ultimapos")
    @Produces(MediaType.APPLICATION_JSON)
    public String ultimaPosAutobuses() {
        List<Localizacion> auto = null;
        Conexion conexion = new Conexion();
        try {
            auto = conexion.ultimaPosAutoBuses();

        } catch (SQLException ex) {
            Logger.getLogger(GenericResource.class.getName()).log(Level.SEVERE, null, ex);
        }
        Gson gson = new Gson();
        return auto.isEmpty() ? gson.toJson(false) : gson.toJson(auto);
    }
     
    /**
     * Metodo para insertar un bus.
     * @param matricula
     * @return 
     */
    @POST
    @Path("insertautobus")
    @Consumes(MediaType.APPLICATION_JSON)
    public boolean insertarAutobus(String matricula) {
        boolean result = true;
        Conexion conexion = new Conexion();
        Gson gson = new Gson();
        Autobuses autobuses;
        autobuses = gson.fromJson(matricula, Autobuses.class);
        try {

            conexion.insertarAutobus(autobuses);
        } catch (SQLException ex) {
            Logger.getLogger(GenericResource.class.getName()).log(Level.SEVERE, null, ex);
            result = false;

        }
        return result;

    }
    
    
    
}
