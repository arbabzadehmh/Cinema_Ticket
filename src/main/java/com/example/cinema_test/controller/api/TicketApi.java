package com.example.cinema_test.controller.api;

import com.example.cinema_test.controller.exception.ExceptionWrapper;
import com.example.cinema_test.model.entity.*;
import com.example.cinema_test.model.service.CustomerService;
import com.example.cinema_test.model.service.SeatService;
import com.example.cinema_test.model.service.TicketService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;


@Slf4j
@Path("/ticket")
public class TicketApi {


    @Inject
    private TicketService ticketService;

    @Inject
    private SeatService seatService;

    @Inject
    private CustomerService customerService;

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id) {
        try {
//            TODO : PAYMENT ??? BANK???? customer???

            Ticket ticket = ticketService.findById(id);
            ticket.getCustomer().getTicketList().removeIf(ticket1 -> ticket1.getId().equals(id));
            customerService.edit(ticket.getCustomer());

            ticketService.remove(id);

            log.info("Ticket removed successfully-ID : " + id);
            return Response.accepted().build();
        } catch (Exception e) {
            log.error(ExceptionWrapper.getMessage(e).toString());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("An error occurred: " + e.getMessage())
                    .build();
        }
    }


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/findById/{id}")
    public Response findById(@PathParam(value = "id") Long id) {
        try {

            Ticket ticket = ticketService.findById(id);

            if (ticket != null) {
                TicketVO ticketVO = new TicketVO(ticket);
                ticketVO.setSeatLabel(seatService.findById(ticket.getSeatId()).getLabel());
                return Response.ok(ticketVO).build();
            } else {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("No records found for ID: " + id)
                        .build();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("An error occurred: " + e.getMessage())
                    .build();
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/findByShowTimeId/{id}")
    public Response findByShowTimeId(@PathParam(value = "id") Long id) {
        try {

            List<Ticket> ticketList = ticketService.findByShowTimeId(id);
            List<TicketVO> ticketVOList = new ArrayList<>();
            for (Ticket ticket : ticketList) {
                TicketVO ticketVO = new TicketVO(ticket);
                ticketVO.setSeatLabel(seatService.findById(ticket.getSeatId()).getLabel());
                ticketVOList.add(ticketVO);
            }

            if (!ticketVOList.isEmpty()) {
                return Response.ok(ticketVOList).build();
            } else {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("No records found for showtime ID: " + id)
                        .build();
            }
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("An error occurred: " + e.getMessage())
                    .build();
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/findByPhone/{phoneNumber}")
    public Response findByCustomerPhoneNumber(@PathParam(value = "phoneNumber") String phoneNumber) {
        try {

            List<Ticket> ticketList = ticketService.findByCustomerPhoneNumber(phoneNumber);
            List<TicketVO> ticketVOList = new ArrayList<>();
            for (Ticket ticket : ticketList) {
                TicketVO ticketVO = new TicketVO(ticket);
                ticketVO.setSeatLabel(seatService.findById(ticket.getSeatId()).getLabel());
                ticketVOList.add(ticketVO);
            }

            if (!ticketVOList.isEmpty()) {
                return Response.ok(ticketVOList).build();
            } else {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("No records found for Phone Number: " + phoneNumber)
                        .build();
            }
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("An error occurred: " + e.getMessage())
                    .build();
        }
    }


}
