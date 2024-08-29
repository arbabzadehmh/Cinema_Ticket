package com.example.cinema_test.controller.api;

import com.example.cinema_test.model.service.ManagerService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/manager")
public class ManagerApi {

    @Inject
    private ManagerService managerService;

//    @GET
//    @Consumes(MediaType.APPLICATION_JSON)
//    @Produces(MediaType.APPLICATION_JSON)
//    public Response findById(Long id) throws Exception {
//        try {
//            System.out.println("manager api id : " + id);
//            return Response.ok(managerService.findById(id)).build();
//
//        }catch (Exception e) {
//            e.printStackTrace();
//            return null;
//        }
//    }



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

}
