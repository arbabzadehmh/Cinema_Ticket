package com.example.cinema_test.controller.api;

import com.example.cinema_test.controller.exception.ExceptionWrapper;
import com.example.cinema_test.model.service.PaymentService;
import jakarta.inject.Inject;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.core.Response;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Path("/payment")
public class PaymentApi {

    @Inject
    private PaymentService paymentService;

    @DELETE
    @Path("{id}")
    public Response delete(@PathParam("id") Long id) {
        try {
            paymentService.remove(id);
            log.info("Payment removed successfully-ID : " + id);
            return Response.accepted().build();
        } catch (Exception e) {
            log.error(ExceptionWrapper.getMessage(e).toString());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("An error occurred: " + e.getMessage())
                    .build();
        }
    }

}
