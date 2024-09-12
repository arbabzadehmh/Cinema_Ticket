package com.example.cinema_test.controller.api;

import com.example.cinema_test.controller.exception.ExceptionWrapper;
import com.example.cinema_test.model.entity.Cinema;
import com.example.cinema_test.model.entity.ShowTime;
import com.example.cinema_test.model.service.CinemaService;
import com.example.cinema_test.model.service.ShowTimeService;
import com.example.cinema_test.model.service.TicketService;
import jakarta.inject.Inject;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.core.Response;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Path("/show-time")
public class ShowTimeApi {

    @Inject
    private TicketService ticketService;

    @Inject
    private ShowTimeService showTimeService;

    @Inject
    private CinemaService cinemaService;

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id) {
        try {

            if (!ticketService.findSoldSeatsByShowId(id).isEmpty()) {
                log.error("Some tickets of this showtime has been sold, can not remove showtime-id : " + id);
                return Response.status(Response.Status.NOT_ACCEPTABLE)
                        .entity("Some tickets of this showtime has been sold !!!")
                        .build();
            } else {
                ShowTime showTimeToRemove = showTimeService.findById(id);
                Cinema cinema = showTimeToRemove.getCinema();
                showTimeService.remove(id);
                cinema.getShowTimeList().removeIf(showTime -> showTime.getId().equals(showTimeToRemove.getId()));
                cinemaService.edit(cinema);

                log.info("ShowTime removed successfully-ID : " + id);
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
