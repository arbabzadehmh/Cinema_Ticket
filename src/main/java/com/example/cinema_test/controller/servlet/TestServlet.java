package com.example.cinema_test.controller.servlet;

import com.example.cinema_test.model.entity.*;
import com.example.cinema_test.model.entity.enums.ShowType;
import com.example.cinema_test.model.service.*;
import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@WebServlet(urlPatterns = "/test.do")
public class TestServlet extends HttpServlet {

    @Inject
    private SeatService seatService;

    @Inject
    private SaloonService saloonService;

    @Inject
    private ShowService showService;

    @Inject
    private ShowTimeService showTimeService;


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            Seat seat1 =
                    Seat
                            .builder()
                            .seatNumber(1)
                            .rowNumber(1)
                            .priceRatio(1.5)
                            .status(true)
                            .deleted(false)
                            .description("test")
                            .build();

            Seat seat2 =
                    Seat
                            .builder()
                            .seatNumber(2)
                            .rowNumber(2)
                            .priceRatio(2)
                            .status(true)
                            .deleted(false)
                            .description("test")
                            .build();

            seatService.save(seat1);
            seatService.save(seat2);

            List<Seat> seats = new ArrayList<>();
            seats.add(seat1);
            seats.add(seat2);



            Saloon saloon1 =
                    Saloon
                            .builder()
                            .name("Iran")
                            .capacity(30)
                            .seats(seats)
                            .status(true)
                            .deleted(false)
                            .description("test")
                            .build();

            saloonService.save(saloon1);


            Show show1 =
                    Show
                            .builder()
                            .name("aa")
                            .genre("bb")
                            .director("cc")
                            .releasedDate(LocalDate.of(2022,8,12))
                            .basePrice(30000)
                            .showType(ShowType.MOVIE)
                            .status(true)
                            .deleted(false)
                            .description("test")
                            .build();

            showService.save(show1);


            ShowTime showTime1 =
                    ShowTime
                            .builder()
                            .saloon(saloon1)
                            .show(show1)
                            .startTime(LocalDateTime.of(2024,1,1,18,30))
                            .endTime(LocalDateTime.of(2024,1,1,20,30))
                            .showSeats(null)
                            .status(true)
                            .deleted(false)
                            .description("test")
                            .build();

            showTime1.showSeatCreator();
            System.out.println(showTimeService.save(showTime1));



            System.out.println("*** seat findAll : " + seatService.findAll());
            System.out.println("*** saloon findAll : " + saloonService.findAll());
            System.out.println("*** showtime findAll : " + showTimeService.findAll());

            ShowTime showTime2 = showTimeService.findById(1L);
            showTime2.getShowSeats().get(1).setStatus(false);
            showTimeService.edit(showTime2);

            System.out.println("*** seat findAll : " + seatService.findAll());
            System.out.println("*** saloon findAll : " + saloonService.findAll());
            System.out.println("*** showtime findAll : " + showTimeService.findAll());


        } catch (Exception e) {
            resp.getWriter().write("<h1 style=\"background-color: green;\">" + e.getMessage() + "</h1>");
            e.printStackTrace();
        }
    }



}
