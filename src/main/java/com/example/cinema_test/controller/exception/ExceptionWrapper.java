package com.example.cinema_test.controller.exception;

import jakarta.persistence.OptimisticLockException;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class ExceptionWrapper {
    public static Map<Integer, String> getMessage(Exception e) {
        Map<Integer, String> error = new HashMap<>();
        if (e instanceof NullPointerException) {
            error.put(400, "NullPointerException");
        } else if (e instanceof SQLException) {
            error.put(500, "Database Error");
        } else if (e instanceof OptimisticLockException) {
            error.put(500, "Error synchronizing changes on a record");
        } else if (
                e instanceof AdminNotFoundException ||
                        e instanceof AttachmentNotFoundException ||
                        e instanceof CinemaNotFoundException ||
                        e instanceof CustomerNotFoundException ||
                        e instanceof ManagerNotFoundException ||
                        e instanceof MessageNotFoundException ||
                        e instanceof ModeratorNotFoundException ||
                        e instanceof RoleNotFoundException ||
                        e instanceof SaloonNotFoundException ||
                        e instanceof SeatNotFoundException ||
                        e instanceof ShowNotFoundException ||
                        e instanceof ShowTimeNotFoundException ||
                        e instanceof SupportNotFoundException ||
                        e instanceof UserNotFoundException ||
                        e instanceof TicketNotFoundException
        ) {
            error.put(204, e.getMessage());
        } else if (e instanceof AccessDeniedException) {
            error.put(403, "Access denied");
        } else if (
                e instanceof DuplicateShowException
                        || e instanceof DuplicateUserException
                        || e instanceof DuplicateManagerException
                        || e instanceof DuplicateSaloonException
        ) {
            error.put(213, e.getMessage());
        } else if (
                e instanceof ShowIsPlayingException ||
                        e instanceof SaloonIsPlayinShowException
        ) {
            error.put(500, e.getMessage());
        } else {
            error.put(500, "Unknown Error - please contact with admin");
        }
        return error;
    }
}

