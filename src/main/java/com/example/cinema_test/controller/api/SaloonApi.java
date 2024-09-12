package com.example.cinema_test.controller.api;

import com.example.cinema_test.controller.exception.ExceptionWrapper;
import com.example.cinema_test.model.entity.Cinema;
import com.example.cinema_test.model.entity.Saloon;
import com.example.cinema_test.model.service.CinemaService;
import com.example.cinema_test.model.service.SaloonService;
import com.example.cinema_test.model.service.ShowTimeService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Path("/saloon")
public class SaloonApi {

    @Inject
    private SaloonService saloonService;

    @Inject
    private ShowTimeService showTimeService;

    @Inject
    private CinemaService cinemaService;

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id) {
        try {

            if (!showTimeService.findActiveShowsBySaloonId(id).isEmpty()) {
                log.error("Show is playing on this saloon, can not remove this saloon-id : " +id);
                return Response.status(Response.Status.NOT_ACCEPTABLE)
                        .entity("Show is playing on this saloon !!!")
                        .build();
            } else {
                Saloon saloonToRemove = saloonService.findById(id);

                Cinema cinema = cinemaService.findCinemaBySaloonId(id);

                saloonService.remove(id);

                cinema.getSaloonList().removeIf(saloon -> saloon.getId().equals(saloonToRemove.getId()));
                cinemaService.edit(cinema);

                log.info("Saloon removed successfully-ID : " + id);
                return Response.accepted().build();
            }
        }catch (Exception e) {
            log.error(ExceptionWrapper.getMessage(e).toString());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("An error occurred: " + e.getMessage())
                    .build();
        }
    }



}
