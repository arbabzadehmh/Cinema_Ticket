package com.example.cinema_test.controller.api;

import com.example.cinema_test.model.entity.Bank;
import com.example.cinema_test.model.entity.Message;
import com.example.cinema_test.model.service.BankService;
import com.example.cinema_test.model.service.MessageService;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.extern.slf4j.Slf4j;

import java.util.List;


@Slf4j
@Path("/support")
public class SupportApi {

    @Inject
    private MessageService messageService;


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/message/findAll/{id}")
    public Response findMessage(@PathParam(value = "id") Long id) {
        try {
           List<Message> messageList= messageService.findAll();
            if (messageList != null) {
                return Response.ok(messageList).build();
            }else {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("There Is No message List for Support " + id)
                        .build();
            }


        } catch (Exception e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Error : " + e.getMessage())
                    .build();
        }
    }
}
