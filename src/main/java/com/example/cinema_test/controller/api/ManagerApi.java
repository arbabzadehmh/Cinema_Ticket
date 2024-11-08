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

import java.util.ArrayList;
import java.util.List;

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
                log.error("Can not remove manager-ID : " + id);
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
            List<Manager> managerList = managerService.findAll();
            List<ManagerVO> managerVOList = new ArrayList<>();
            for (Manager manager : managerList) {
                ManagerVO managerVO = new ManagerVO(manager);
                managerVOList.add(managerVO);
            }

            if (!managerVOList.isEmpty()) {
                log.info("Manager found successfully-find all ");
                return Response.ok(managerVOList).build();
            } else {
                log.error("Manager not found-find all ");
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("No records found for manager")
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
    @Path("/findByFamily/{family}")
    public Response findManagerByFamily(@PathParam(value = "family") String family) {
        try {
            List<Manager> managerList = managerService.findByFamily(family);
            List<ManagerVO> managerVOList = new ArrayList<>();
            for (Manager manager : managerList) {
                ManagerVO managerVO = new ManagerVO(manager);
                managerVOList.add(managerVO);
            }

            if (!managerVOList.isEmpty()) {
                log.info("Manager found-family : " + family);
                return Response.ok(managerVOList).build();
            } else {
                log.error("Manager not found-family : " + family);
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("No records found for manager")
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
    @Path("/findByPhone/{phone}")
    public Response findByPhoneNumber(@PathParam(value = "phone") String phone) {
        try {
            Manager manager = managerService.findByPhoneNumber(phone);


            if (manager != null) {
                ManagerVO managerVO = new ManagerVO(manager);
                log.info("Manager found successfully-phone : " + phone);
                return Response.ok(managerVO).build();
            } else {
                log.error("Manager not found-phone : " + phone);
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("No records found for phone: " + phone)
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
    @Path("/manager-for-cinema")
    public Response findManagersWantingCinema() {
        try {
            List<Manager> managerList = managerService.findManagersWantingCinema();
            List<ManagerVO> managerVOList = new ArrayList<>();
            for (Manager manager : managerList) {
                ManagerVO managerVO = new ManagerVO(manager);
                managerVOList.add(managerVO);
            }


            if (!managerVOList.isEmpty()) {
                log.info("Manager found successfully-manager for cinema");
                return Response.ok(managerVOList).build();
            } else {
                log.error("Manager not found-manager for cinema");
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("No records found for manager")
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
    public Response findManagerByNationalCode(@PathParam(value = "nationalCode") String nationalCode) {
        try {
            Manager manager = managerService.findByNationalCode(nationalCode);

            if (manager != null) {
                ManagerVO managerVO = new ManagerVO(manager);
                log.info("Manager found successfully-nationCode : " + nationalCode);
                return Response.ok(managerVO).build();
            } else {
                log.error("Manager not found-nationalCode : " + nationalCode);
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("No records found for manager")
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
