package com.example.cinema_test.controller.api;

import com.example.cinema_test.controller.exception.ExceptionWrapper;
import com.example.cinema_test.model.entity.Moderator;
import com.example.cinema_test.model.entity.ModeratorVO;
import com.example.cinema_test.model.service.ModeratorService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
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
                log.error("Can not remove all moderators !!!");
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


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/findByNationalCode/{nationalCode}")
    public Response findModeratorByNationalCode(@PathParam(value = "nationalCode") String nationalCode) {
        try {
            Moderator moderator = moderatorService.findByNationalCode(nationalCode);

            if (moderator != null) {
                ModeratorVO moderatorVO = new ModeratorVO(moderator);
                log.info("Moderator found-national code : " + nationalCode);
                return Response.ok(moderatorVO).build();
            } else {
                log.error("Moderator not found-national code : " + nationalCode);
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("No records found for moderator")
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
