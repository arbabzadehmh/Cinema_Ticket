package com.example.cinema_test.controller.api;

import com.example.cinema_test.controller.exception.ExceptionWrapper;
import com.example.cinema_test.model.entity.Payment;
import com.example.cinema_test.model.entity.PaymentVO;
import com.example.cinema_test.model.service.PaymentService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Path("/payment")
public class PaymentApi {

    @Inject
    private PaymentService paymentService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/findById/{id}")
    public Response findById(@PathParam(value = "id") Long id) {
        try {

            Payment payment = paymentService.findById(id);

            if (payment != null) {
                PaymentVO paymentVO = new PaymentVO(payment);
                log.info("Payment found successfully-ID : " + id);
                return Response.ok(paymentVO).build();
            } else {
                log.info("Payment not found-id : " + id);
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("No records found for ID: " + id)
                        .build();
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
    @Path("/findByPaymentDate/{date}")
    public Response findByPaymentDate(@PathParam(value = "date") String dateString) {
        try {

            LocalDate date = LocalDate.parse(dateString);

            List<Payment> paymentList = paymentService.findByDate(date);
            List<PaymentVO> paymentVOList = new ArrayList<>();
            for (Payment payment : paymentList) {
                PaymentVO paymentVO = new PaymentVO(payment);
                paymentVOList.add(paymentVO);
            }

            if (!paymentVOList.isEmpty()) {
                log.info("Payment found successfully-payment date : " + date);
                return Response.ok(paymentVOList).build();
            } else {
                log.error("Payment not found-payment date : " + date);
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("No records found for date : " + date)
                        .build();
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
    @Path("/findByPhone/{phoneNumber}")
    public Response findByCustomerPhoneNumber(@PathParam(value = "phoneNumber") String phoneNumber) {
        try {

            List<Payment> paymentList = paymentService.findByPhoneNumber(phoneNumber);
            List<PaymentVO> paymentVOList = new ArrayList<>();
            for (Payment payment : paymentList) {
                PaymentVO paymentVO = new PaymentVO(payment);
                paymentVOList.add(paymentVO);
            }

            if (!paymentVOList.isEmpty()) {
                log.info("Payment found successfully-customer phone number : " + phoneNumber);
                return Response.ok(paymentVOList).build();
            } else {
                log.error("Payment not found-customer phone number : " + phoneNumber);
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("No records found for Phone Number: " + phoneNumber)
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
