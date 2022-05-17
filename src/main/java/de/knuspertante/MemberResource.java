package de.knuspertante;

import java.io.BufferedReader;
import java.io.EOFException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.jboss.logging.Logger;

import gdv.xport.Datenpaket;
import gdv.xport.io.ImportException;

@Path("/hello")
public class MemberResource {

    @Inject
    Logger logger;

    private List<Datenpaket> datenpakete = new ArrayList<>();

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String hello() throws Exception {

        /**
         * do some shit here ;-)
         */

    try (BufferedReader inputReader = Files.newBufferedReader(Paths.get("path"))) {
        while (inputReader.ready()) {
            Datenpaket paket = new Datenpaket();
            try {
                paket.importFrom(inputReader);
                datenpakete.add(paket);
            } catch (EOFException ex) {
                logger.info("EOF nach " + datenpakete.size() + " Datenpaketen erreicht.");
                break;
            } catch (ImportException ex) {
                throw new Exception(
                        "Could not import File caused of wrong Order Mark: " + ex.getMessage());
            }
        }

        return "Hello from RESTEasy Reactive";

    }
}
}