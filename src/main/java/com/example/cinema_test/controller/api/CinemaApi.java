package com.example.cinema_test.controller.api;

import com.example.cinema_test.controller.exception.ExceptionWrapper;
import com.example.cinema_test.model.entity.Cinema;
import com.example.cinema_test.model.entity.CinemaVO;
import com.example.cinema_test.model.entity.Manager;
import com.example.cinema_test.model.service.CinemaService;
import com.example.cinema_test.model.service.ManagerService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Path("/cinema")
public class CinemaApi {

    @Inject
    private CinemaService cinemaService;

    @Inject
    private ManagerService managerService;

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id) {
        try {
                cinemaService.remove(id);
                Manager manager = managerService.findManagerByCinemaId(id);
                manager.setCinema(null);
                managerService.edit(manager);
                log.info("Cinema removed successfully-ID : " + id);
                return Response.accepted().build();
        }catch (Exception e) {
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

            Cinema cinema = cinemaService.findByName(name);

            if (cinema != null) {
                CinemaVO cinemaVO = new CinemaVO(cinema);
                log.info("Cinema found successfully-ID : " + cinema.getId());
                return Response.ok(cinemaVO).build();
            } else {
                log.error("Cinema not found");
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("No records found for name: " + name)
                        .build();
            }
        }catch (Exception e) {
            log.error(ExceptionWrapper.getMessage(e).toString());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("An error occurred: " + e.getMessage())
                    .build();
        }
    }


}
