package com.example.cinema_test.controller.api;

import com.example.cinema_test.controller.exception.ExceptionWrapper;
import com.example.cinema_test.model.entity.*;
import com.example.cinema_test.model.service.SupportService;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;


@Slf4j
@Path("/support")
public class SupportApi {

    @Inject
    private SupportService supportService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/findById/{id}")
    public Response findById(@PathParam(value = "id") Long id) {
        try {
            Support support = supportService.findById(id);
            SupportVO supportVO = new SupportVO(support);

            if (supportVO != null) {
                log.info("Support found successfully-ID : " + id);
                return Response.ok(supportVO).build();
            } else {
                log.error("Support not found-ID : " + id);
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("No records found for id: " + id)
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
    @Path("/findByCustomerPhone/{phoneNumber}")
    public Response findByCustomerPhoneNumber(@PathParam(value = "phoneNumber") String phone) {
        try {
            List<Support> supportList = supportService.findByCustomerPhoneNumber(phone);
            List<SupportVO> supportVOList = new ArrayList<>();
            for (Support support : supportList) {
                SupportVO supportVO = new SupportVO(support);
                supportVOList.add(supportVO);
            }

            if (!supportVOList.isEmpty()) {
                log.info("Support found successfully-customer phone : " + phone);
                return Response.ok(supportVOList).build();
            } else {
                log.error("Support not found-customer phone : " + phone);
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("No records found for phone: " + phone)
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
    @Path("/findByModeratorFamily/{family}")
    public Response findByModeratorFamily(@PathParam(value = "family") String family) {
        try {
            List<Support> supportList = supportService.findByModeratorFamily(family);
            List<SupportVO> supportVOList = new ArrayList<>();
            for (Support support : supportList) {
                SupportVO supportVO = new SupportVO(support);
                supportVOList.add(supportVO);
            }

            if (!supportVOList.isEmpty()) {
                log.info("Support found successfully-moderator family : " + family);
                return Response.ok(supportVOList).build();
            } else {
                log.error("Support not found-moderator family : " + family);
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("No records found for moderator family: " + family)
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
