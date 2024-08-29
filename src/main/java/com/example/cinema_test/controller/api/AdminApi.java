package com.example.cinema_test.controller.api;

import com.example.cinema_test.controller.exception.ExceptionWrapper;
import com.example.cinema_test.model.entity.Cinema;
import com.example.cinema_test.model.entity.CinemaVO;
import com.example.cinema_test.model.entity.Manager;
import com.example.cinema_test.model.service.AdminService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Path("/admins")
public class AdminApi {


    @Inject
    private AdminService adminService;

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id) {
        try {
            if (adminService.findAll().size()>1){
                adminService.remove(id);
                log.info("Admin removed successfully-ID : " + id);
                return Response.accepted().build();
            } else {
                return Response.status(Response.Status.NOT_ACCEPTABLE)
                        .entity("Can not remove all admins !!!")
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
