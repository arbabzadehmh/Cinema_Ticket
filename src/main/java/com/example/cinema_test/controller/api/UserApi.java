package com.example.cinema_test.controller.api;

import com.example.cinema_test.controller.exception.ExceptionWrapper;
import com.example.cinema_test.model.entity.Manager;
import com.example.cinema_test.model.entity.ManagerVO;
import com.example.cinema_test.model.entity.User;
import com.example.cinema_test.model.service.AdminService;
import com.example.cinema_test.model.service.UserService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Path("/user")
public class UserApi {


    @Inject
    private UserService userService;

//    @DELETE
//    @Path("/{id}")
//    public Response delete(@PathParam("id") Long id) {
//        try {
//            if (adminService.findAll().size()>1){
//                adminService.remove(id);
//                log.info("Admin removed successfully-ID : " + id);
//                return Response.accepted().build();
//            } else {
//                return Response.status(Response.Status.NOT_ACCEPTABLE)
//                        .entity("Can not remove all admins !!!")
//                        .build();
//            }
//        }catch (Exception e) {
//            log.error(ExceptionWrapper.getMessage(e).toString());
//            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
//                    .entity("An error occurred: " + e.getMessage())
//                    .build();
//        }
//    }


    @GET
    @Path("/{username}")
    @Produces("text/plain")
    public Response resetPassword(@PathParam("username") String username) {
        try {

            if (userService.findByUsername(username) != null) {
                User user = userService.findByUsername(username);
                int newPassword = 1000 + (int) (Math.random()*8999);
                user.setPassword(String.valueOf(newPassword));
                userService.edit(user);
                return Response.ok(newPassword).build();
            } else {
                return Response.status(Response.Status.NOT_FOUND).entity("User not found").build();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("An error occurred while processing the request: " + e.getMessage()).build();
        }
    }





}
