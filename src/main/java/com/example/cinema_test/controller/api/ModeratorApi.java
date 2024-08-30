package com.example.cinema_test.controller.api;

import com.example.cinema_test.controller.exception.ExceptionWrapper;
import com.example.cinema_test.model.service.ModeratorService;
import jakarta.inject.Inject;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.core.Response;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Path("/moderator")
public class ModeratorApi {


    @Inject
    private ModeratorService moderatorService;

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id) {
        try {
            if (moderatorService.findAll().size()>1){
                moderatorService.remove(id);
                log.info("Moderator removed successfully-ID : " + id);
                return Response.accepted().build();
            } else {
                return Response.status(Response.Status.NOT_ACCEPTABLE)
                        .entity("Can not remove all moderators !!!")
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
