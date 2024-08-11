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

    @Inject
    private RoleService roleService;

    @Inject
    private UserService userService;

    @Inject
    private ManagerService managerService;

    @Inject
    private CinemaService cinemaService;


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
//            Role role1 = Role.builder().role("manager").deleted(false).build();
//
//
//
//            User user1 =
//                    User
//                            .builder()
//                            .username("ali")
//                            .password("123")
//                            .role(role1)
//                            .locked(false)
//                            .deleted(false)
//                            .build();
//
//
//
//            Manager manager1 =
//                    Manager
//                            .builder()
//                            .name("ali")
//                            .family("alipour")
//                            .phoneNumber("09121114455")
//                            .email("ali@gmail.com")
//                            .nationalCode("0112233445")
//                            .address("zzz")
//                            .user(user1)
//                            .cinema(null)
//                            .build();
//
//            managerService.save(manager1);
//
//
//            Cinema cinema1 =
//                    Cinema
//                            .builder()
//                            .name("Azadi")
//                            .address("beheshti")
//                            .status(true)
//                            .description("test")
//                            .saloonList(null)
//                            .showList(null)
//                            .showTimeList(null)
//                            .build();
//
//            cinemaService.save(cinema1);
//
//
//
//            List<Seat> seats = new ArrayList<>();
//            double ratio = 1;
//            for (int i=1; i<=6; i++) {
//                if (i<= 2){
//                    ratio = 2;
//                } else if (i>2 && i<5) {
//                    ratio = 1.5;
//                } else {
//                    ratio = 1;
//                }
//                for (int j=1; j<=10; j++) {
//                    Seat seat =
//                            Seat
//                                    .builder()
//                                    .rowNumber(i)
//                                    .seatNumber(j)
//                                    .label(null)
//                                    .priceRatio(ratio)
//                                    .status(true)
//                                    .deleted(false)
//                                    .description("test")
//                                    .build();
//
//                    seat.seatLabelMaker();
//                    seats.add(seat);
//                    seatService.save(seat);
//                }
//            }
//
//            List<Saloon> saloons = new ArrayList<>();
//            Saloon saloon1 =
//                    Saloon
//                            .builder()
//                            .saloonNumber(10)
//                            .capacity(75)
//                            .seats(seats)
//                            .status(true)
//                            .deleted(false)
//                            .description("test")
//                            .build();
//            saloons.add(saloon1);
//
//            saloonService.save(saloon1);
//
//
//            List<Show> shows = new ArrayList<>();
//            Show show1 =
//                    Show
//                            .builder()
//                            .name("aa")
//                            .genre("bb")
//                            .director("cc")
//                            .producer("dd")
//                            .singer(null)
//                            .speaker(null)
//                            .releasedDate(LocalDate.of(2022,8,12))
//                            .basePrice(30000)
//                            .showType(ShowType.MOVIE)
//                            .status(true)
//                            .deleted(false)
//                            .description("test")
//                            .build();
//            shows.add(show1);
//
//            showService.save(show1);
//
//
//            List<ShowTime> showTimes = new ArrayList<>();
//            ShowTime showTime1 =
//                    ShowTime
//                            .builder()
//                            .saloon(saloon1)
//                            .show(show1)
//                            .startTime(LocalDateTime.of(2024,1,1,18,30))
//                            .endTime(LocalDateTime.of(2024,1,1,20,30))
//                            .showSeats(null)
//                            .status(true)
//                            .deleted(false)
//                            .description("test")
//                            .cinema(cinema1)
//                            .build();
//
//            showTime1.showSeatCreator();
//            showTimes.add(showTime1);
//            showTimeService.save(showTime1);
//
//            cinema1.setSaloonList(saloons);
//            cinema1.setShowList(shows);
//            cinema1.setShowTimeList(showTimes);
//            cinemaService.edit(cinema1);
//
//            manager1.setCinema(cinema1);
//            managerService.edit(manager1);


            req.getSession().setAttribute("saloons", managerService.findById(1L).getCinema().getSaloonList());
            req.getSession().setAttribute("shows", managerService.findById(1L).getCinema().getShowList());
            req.getSession().setAttribute("showTimes", managerService.findById(1L).getCinema().getShowTimeList());
            req.getRequestDispatcher("/manager-panel.jsp").forward(req, resp);
            


        } catch (Exception e) {
            resp.getWriter().write("<h1 style=\"background-color: green;\">" + e.getMessage() + "</h1>");
            e.printStackTrace();
        }
    }



}
