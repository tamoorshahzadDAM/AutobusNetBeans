/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest;

import bd.Localizacion;
import bd.Conexion;
import com.google.gson.Gson;
import java.sql.SQLException;
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
import javax.ws.rs.PUT;
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
     * Retrieves representation of an instance of rest.GenericResource
     *
     * @return an instance of java.lang.String
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String listarLocalizacion() {

        Conexion conexion = new Conexion();
        List<Localizacion> lista = null;
        try {
            lista = conexion.obtenerLocalizacion();

        } catch (SQLException ex) {
            Logger.getLogger(GenericResource.class.getName()).log(Level.SEVERE, null, ex);
        }
        Gson gson = new Gson();

        return gson.toJson(lista);
    }

    /**
     * PUT method for updating or creating an instance of GenericResource
     *
     * @param loc
     * @return
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public boolean insertarLocalizacion(String loc) {
        boolean result = true;
        Conexion conexion = new Conexion();
        Gson gson = new Gson();
        Localizacion localizacion;
        localizacion = gson.fromJson(loc, Localizacion.class);
        try {

            conexion.insertarLocalizacion(localizacion);
        } catch (SQLException ex) {
            Logger.getLogger(GenericResource.class.getName()).log(Level.SEVERE, null, ex);
            result = false;

        }
        return result;

    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public String mostrarLocalizacion(@PathParam("id") int id) {
        Localizacion loc = null;
        Conexion conexion = new Conexion();
        try {
            loc = conexion.obtenerLocalizacion(id);
        } catch (SQLException ex) {
            Logger.getLogger(GenericResource.class.getName()).log(Level.SEVERE, null, ex);
        }
        Gson gson = new Gson();

        return gson.toJson(loc);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public boolean actualizarLocalizacion(String loc) {
        boolean result = true;
        Conexion conexion = new Conexion();
        Gson gson = new Gson();
        Localizacion localizacion;
        localizacion = gson.fromJson(loc, Localizacion.class);
        try {

            conexion.actualizarLocalizacion(localizacion);
        } catch (SQLException ex) {
            Logger.getLogger(GenericResource.class.getName()).log(Level.SEVERE, null, ex);
            result = false;
        }
        return result;
    }

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
}
