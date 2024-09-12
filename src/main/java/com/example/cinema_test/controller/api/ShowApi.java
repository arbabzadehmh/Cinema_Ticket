package com.example.cinema_test.controller.api;

import com.example.cinema_test.controller.exception.ExceptionWrapper;
import com.example.cinema_test.model.entity.Cinema;
import com.example.cinema_test.model.entity.Show;
import com.example.cinema_test.model.service.CinemaService;
import com.example.cinema_test.model.service.ShowService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Path("/show")
public class ShowApi {

    @Inject
    private ShowService showService;

    @Inject
    private CinemaService cinemaService;

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id) {
        try {
            Show showToRemove = showService.findById(id);

            if (showToRemove.isAvailable()) {
                log.error("Show is active on a cinema, can not remove show-id : "+ id);
                return Response.status(Response.Status.NOT_ACCEPTABLE)
                        .entity("Show is active on a cinema !!!")
                        .build();
            } else {
                showService.remove(id);
                for (Cinema cinema : cinemaService.findAll()) {
                    cinema.getShowList().removeIf(show -> show.getId().equals(showToRemove.getId()));
                    cinemaService.edit(cinema);
                }
                log.info("Show removed successfully-ID : " + id);
                return Response.accepted().build();
            }
        } catch (Exception e) {
            log.error(ExceptionWrapper.getMessage(e).toString());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("An error occurred: " + e.getMessage())
                    .build();
        }
    }


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/findByName/{name}")
    public Response findByName(@PathParam(value = "name") String name) {
        try {

            Object result = showService.findByName(name);

            if (result != null) {
                log.info("Show found-name : " + name);
                return Response.ok(result).build();
            } else {
                log.error("Show not found-name : " + name);
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("No records found for name: " + name)
                        .build();
            }
        } catch (Exception e) {
            log.error(ExceptionWrapper.getMessage(e).toString());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("An error occurred: " + e.getMessage())
                    .build();
        }
    }


}
