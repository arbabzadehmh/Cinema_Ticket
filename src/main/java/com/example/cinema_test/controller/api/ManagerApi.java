package com.example.cinema_test.controller.api;

import com.example.cinema_test.controller.exception.ExceptionWrapper;
import com.example.cinema_test.model.entity.Manager;
import com.example.cinema_test.model.entity.ManagerVO;
import com.example.cinema_test.model.service.ManagerService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Path("/manager")
public class ManagerApi {

    @Inject
    private ManagerService managerService;

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id) {
        try {

            Manager manager = managerService.findById(id);

            if (manager.getCinema() == null){
                managerService.remove(id);
                log.info("Manager removed successfully-ID : " + id);
                return Response.accepted().build();
            } else {
                return Response.status(Response.Status.NOT_ACCEPTABLE)
                        .entity("This manager is belong to a cinema !!!")
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
    @Path("/findAll")
    public Response findAll() {
        try {
            Object result = managerService.findAll();

            if (result != null) {
                return Response.ok(result).build();
            } else {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("No records found for manager")
                        .build();
            }
        }catch (Exception e) {
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
            Object result = managerService.findByNameAndFamily(name, "alipour");

            if (result != null) {
                return Response.ok(result).build();
            } else {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("No records found for name: " + name)
                        .build();
            }
        }catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("An error occurred: " + e.getMessage())
                    .build();
        }
    }


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/findByPhone/{phone}")
    public Response findByPhoneNumber(@PathParam(value = "phone") String phone) {
        try {
            Object result = managerService.findByPhoneNumber(phone);

            if (result != null) {
                return Response.ok(result).build();
            } else {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("No records found for phone: " + phone)
                        .build();
            }
        }catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("An error occurred: " + e.getMessage())
                    .build();
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/manager-for-cinema")
    public Response findManagersWantingCinema() {
        try {
            Object result = managerService.findManagersWantingCinema();

            if (result != null) {
                return Response.ok(result).build();
            } else {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("No records found for manager")
                        .build();
            }
        }catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("An error occurred: " + e.getMessage())
                    .build();
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/findByNationalCode/{nationalCode}")
    public Response findManagerByNationalCode(@PathParam(value = "nationalCode") String nationalCode) {
        try {
            Manager manager = managerService.findByNationalCode(nationalCode);

            if (manager != null) {
                ManagerVO managerVO = new ManagerVO(manager);
                return Response.ok(managerVO).build();
            } else {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("No records found for manager")
                        .build();
            }
        }catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("An error occurred: " + e.getMessage())
                    .build();
        }
    }

}
