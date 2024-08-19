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
    private ManagerService managerService;

    @Inject
    private CinemaService cinemaService;

    @Inject
    private RoleService roleService;


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
//            Role role1 = Role.builder().role("manager").deleted(false).build();
//            roleService.save(role1);
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
//            User user2 =
//                    User
//                            .builder()
//                            .username("reza")
//                            .password("123")
//                            .role(role1)
//                            .locked(false)
//                            .deleted(false)
//                            .build();
//
//            User user3 =
//                    User
//                            .builder()
//                            .username("mohsen")
//                            .password("123")
//                            .role(role1)
//                            .locked(false)
//                            .deleted(false)
//                            .build();
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
//            Manager manager2 =
//                    Manager
//                            .builder()
//                            .name("reza")
//                            .family("rezai")
//                            .phoneNumber("09126663354")
//                            .email("ali@gmail.com")
//                            .nationalCode("0548963210")
//                            .address("xxx")
//                            .user(user2)
//                            .cinema(null)
//                            .build();
//
//            Manager manager3 =
//                    Manager
//                            .builder()
//                            .name("mohsen")
//                            .family("akbari")
//                            .phoneNumber("09115487799")
//                            .email("ali@gmail.com")
//                            .nationalCode("065983210")
//                            .address("ccc")
//                            .user(user3)
//                            .cinema(null)
//                            .build();
//
//            managerService.save(manager1);
//            managerService.save(manager2);
//            managerService.save(manager3);
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
//
//            Cinema cinema2 =
//                    Cinema
//                            .builder()
//                            .name("Farhang")
//                            .address("valiasr")
//                            .status(true)
//                            .description("test2")
//                            .saloonList(null)
//                            .showList(null)
//                            .showTimeList(null)
//                            .build();
//
//            Cinema cinema3 =
//                    Cinema
//                            .builder()
//                            .name("Mellat")
//                            .address("niayesh")
//                            .status(true)
//                            .description("test3")
//                            .saloonList(null)
//                            .showList(null)
//                            .showTimeList(null)
//                            .build();
//
//            cinemaService.save(cinema1);
//            cinemaService.save(cinema2);
//            cinemaService.save(cinema3);
//
//
//            double ratio;
//
//            List<Seat> seats = new ArrayList<>();
//            for (int i = 1; i <= 6; i++) {
//                if (i <= 2) {
//                    ratio = 2;
//                } else if (i > 2 && i < 5) {
//                    ratio = 1.5;
//                } else {
//                    ratio = 1;
//                }
//                for (int j = 1; j <= 10; j++) {
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
//            List<Seat> seats2 = new ArrayList<>();
//            for (int i = 1; i <= 7; i++) {
//                if (i <= 2) {
//                    ratio = 2;
//                } else if (i > 2 && i < 5) {
//                    ratio = 1.5;
//                } else {
//                    ratio = 1;
//                }
//                for (int j = 1; j <= 8; j++) {
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
//                    seats2.add(seat);
//                    seatService.save(seat);
//                }
//            }
//
//            List<Seat> seats3 = new ArrayList<>();
//            for (int i = 1; i <= 8; i++) {
//                if (i <= 2) {
//                    ratio = 2;
//                } else if (i > 2 && i < 5) {
//                    ratio = 1.5;
//                } else {
//                    ratio = 1;
//                }
//                for (int j = 1; j <= 11; j++) {
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
//                    seats3.add(seat);
//                    seatService.save(seat);
//                }
//            }
//
//            List<Seat> seats4 = new ArrayList<>();
//            for (int i = 1; i <= 9; i++) {
//                if (i <= 2) {
//                    ratio = 2;
//                } else if (i > 2 && i < 5) {
//                    ratio = 1.5;
//                } else {
//                    ratio = 1;
//                }
//                for (int j = 1; j <= 6; j++) {
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
//                    seats4.add(seat);
//                    seatService.save(seat);
//                }
//            }
//
//            List<Saloon> saloons1 = new ArrayList<>();
//            Saloon saloon1 =
//                    Saloon
//                            .builder()
//                            .saloonNumber(10)
//                            .capacity(60)
//                            .saloonRow(6)
//                            .saloonColumn(10)
//                            .seats(seats)
//                            .status(true)
//                            .deleted(false)
//                            .description("test")
//                            .build();
//            saloons1.add(saloon1);
//
//            saloonService.save(saloon1);
//
//
//            Saloon saloon2 =
//                    Saloon
//                            .builder()
//                            .saloonNumber(11)
//                            .capacity(56)
//                            .saloonRow(7)
//                            .saloonColumn(8)
//                            .seats(seats2)
//                            .status(true)
//                            .deleted(false)
//                            .description("test")
//                            .build();
//            saloons1.add(saloon2);
//
//            saloonService.save(saloon2);
//
//            List<Saloon> saloons2 = new ArrayList<>();
//            Saloon saloon3 =
//                    Saloon
//                            .builder()
//                            .saloonNumber(7)
//                            .capacity(88)
//                            .saloonRow(8)
//                            .saloonColumn(11)
//                            .seats(seats3)
//                            .status(true)
//                            .deleted(false)
//                            .description("test")
//                            .build();
//            saloons2.add(saloon3);
//
//            saloonService.save(saloon3);
//
//            List<Saloon> saloons3 = new ArrayList<>();
//            Saloon saloon4 =
//                    Saloon
//                            .builder()
//                            .saloonNumber(3)
//                            .capacity(54)
//                            .saloonRow(9)
//                            .saloonColumn(6)
//                            .seats(seats4)
//                            .status(true)
//                            .deleted(false)
//                            .description("test")
//                            .build();
//            saloons3.add(saloon4);
//
//            saloonService.save(saloon4);
//
//
//            List<Show> shows1 = new ArrayList<>();
//            Show show1 =
//                    Show
//                            .builder()
//                            .name("Others")
//                            .genre("horror")
//                            .director("cc")
//                            .producer("dd")
//                            .singer(null)
//                            .speaker(null)
//                            .releasedDate(LocalDate.of(2022, 8, 18))
//                            .basePrice(30000)
//                            .showType(ShowType.MOVIE)
//                            .status(true)
//                            .deleted(false)
//                            .description("test")
//                            .build();
//            shows1.add(show1);
//
//            showService.save(show1);
//
//
//            Show show2 =
//                    Show
//                            .builder()
//                            .name("Her")
//                            .genre(null)
//                            .director(null)
//                            .producer(null)
//                            .singer(null)
//                            .speaker(null)
//                            .releasedDate(LocalDate.of(2020, 9, 5))
//                            .basePrice(30000)
//                            .showType(ShowType.MOVIE)
//                            .status(true)
//                            .deleted(false)
//                            .description("test")
//                            .build();
//            shows1.add(show2);
//
//            showService.save(show2);
//
//
//            List<Show> shows2 = new ArrayList<>();
//            Show show3 =
//                    Show
//                            .builder()
//                            .name("Father")
//                            .genre(null)
//                            .director(null)
//                            .producer(null)
//                            .singer(null)
//                            .speaker(null)
//                            .releasedDate(LocalDate.of(2023, 10, 22))
//                            .basePrice(30000)
//                            .showType(ShowType.MOVIE)
//                            .status(true)
//                            .deleted(false)
//                            .description("test")
//                            .build();
//            shows1.add(show3);
//            shows2.add(show3);
//
//            showService.save(show3);
//
//
//            List<Show> shows3 = new ArrayList<>();
//            Show show4 =
//                    Show
//                            .builder()
//                            .name("Sali")
//                            .genre(null)
//                            .director(null)
//                            .producer(null)
//                            .singer(null)
//                            .speaker(null)
//                            .releasedDate(LocalDate.of(2019, 1, 3))
//                            .basePrice(30000)
//                            .showType(ShowType.MOVIE)
//                            .status(true)
//                            .deleted(false)
//                            .description("test")
//                            .build();
//            shows3.add(show4);
//            shows1.add(show4);
//
//            showService.save(show4);
//
//
//            List<ShowTime> showTimes1 = new ArrayList<>();
//            ShowTime showTime1 =
//                    ShowTime
//                            .builder()
//                            .saloon(saloon1)
//                            .show(show1)
//                            .startTime(LocalDateTime.of(2024, 8, 19, 18, 30))
//                            .endTime(LocalDateTime.of(2024, 8, 19, 20, 30))
//                            .status(true)
//                            .deleted(false)
//                            .description("test")
//                            .cinema(cinema1)
//                            .build();
//
//
//            showTimes1.add(showTime1);
//            showTimeService.save(showTime1);
//
//
//            ShowTime showTime2 =
//                    ShowTime
//                            .builder()
//                            .saloon(saloon1)
//                            .show(show1)
//                            .startTime(LocalDateTime.of(2024, 8, 19, 21, 0))
//                            .endTime(LocalDateTime.of(2024, 8, 19, 23, 0))
//                            .status(true)
//                            .deleted(false)
//                            .description("test")
//                            .cinema(cinema1)
//                            .build();
//
//
//            showTimes1.add(showTime2);
//            showTimeService.save(showTime2);
//
//
//            ShowTime showTime3 =
//                    ShowTime
//                            .builder()
//                            .saloon(saloon2)
//                            .show(show2)
//                            .startTime(LocalDateTime.of(2024, 8, 20, 17, 0))
//                            .endTime(LocalDateTime.of(2024, 8, 20, 19, 0))
//                            .status(true)
//                            .deleted(false)
//                            .description("test")
//                            .cinema(cinema1)
//                            .build();
//
//            showTimes1.add(showTime3);
//            showTimeService.save(showTime3);
//
//
//            ShowTime showTime4 =
//                    ShowTime
//                            .builder()
//                            .saloon(saloon1)
//                            .show(show3)
//                            .startTime(LocalDateTime.of(2024, 8, 20, 17, 0))
//                            .endTime(LocalDateTime.of(2024, 8, 20, 19, 0))
//                            .status(true)
//                            .deleted(false)
//                            .description("test")
//                            .cinema(cinema1)
//                            .build();
//
//            showTimes1.add(showTime4);
//            showTimeService.save(showTime4);
//
//
//            ShowTime showTime5 =
//                    ShowTime
//                            .builder()
//                            .saloon(saloon2)
//                            .show(show4)
//                            .startTime(LocalDateTime.of(2024, 8, 20, 20, 0))
//                            .endTime(LocalDateTime.of(2024, 8, 20, 22, 0))
//                            .status(true)
//                            .deleted(false)
//                            .description("test")
//                            .cinema(cinema1)
//                            .build();
//
//            showTimes1.add(showTime5);
//            showTimeService.save(showTime5);
//
//
//            cinema1.setSaloonList(saloons1);
//            cinema1.setShowList(shows1);
//            cinema1.setShowTimeList(showTimes1);
//            cinemaService.edit(cinema1);
//
//            manager1.setCinema(cinema1);
//            managerService.edit(manager1);
//
//
//            List<ShowTime> showTimes2 = new ArrayList<>();
//            ShowTime showTime6 =
//                    ShowTime
//                            .builder()
//                            .saloon(saloon3)
//                            .show(show3)
//                            .startTime(LocalDateTime.of(2024, 8, 19, 18, 30))
//                            .endTime(LocalDateTime.of(2024, 8, 19, 20, 30))
//                            .status(true)
//                            .deleted(false)
//                            .description("test")
//                            .cinema(cinema2)
//                            .build();
//
//            showTimes2.add(showTime6);
//            showTimeService.save(showTime6);
//
//
//            ShowTime showTime7 =
//                    ShowTime
//                            .builder()
//                            .saloon(saloon3)
//                            .show(show3)
//                            .startTime(LocalDateTime.of(2024, 8, 19, 21, 0))
//                            .endTime(LocalDateTime.of(2024, 8, 19, 23, 0))
//                            .status(true)
//                            .deleted(false)
//                            .description("test")
//                            .cinema(cinema2)
//                            .build();
//
//            showTimes2.add(showTime7);
//            showTimeService.save(showTime7);
//
//
//            ShowTime showTime8 =
//                    ShowTime
//                            .builder()
//                            .saloon(saloon3)
//                            .show(show3)
//                            .startTime(LocalDateTime.of(2024, 8, 20, 18, 30))
//                            .endTime(LocalDateTime.of(2024, 8, 20, 20, 30))
//                            .status(true)
//                            .deleted(false)
//                            .description("test")
//                            .cinema(cinema2)
//                            .build();
//
//            showTimes2.add(showTime8);
//            showTimeService.save(showTime8);
//
//
//            cinema2.setSaloonList(saloons2);
//            cinema2.setShowList(shows2);
//            cinema2.setShowTimeList(showTimes2);
//            cinemaService.edit(cinema2);
//
//            manager2.setCinema(cinema2);
//            managerService.edit(manager2);
//
//
//            List<ShowTime> showTimes3 = new ArrayList<>();
//            ShowTime showTime9 =
//                    ShowTime
//                            .builder()
//                            .saloon(saloon4)
//                            .show(show4)
//                            .startTime(LocalDateTime.of(2024, 8, 19, 18, 30))
//                            .endTime(LocalDateTime.of(2024, 8, 19, 20, 30))
//                            .status(true)
//                            .deleted(false)
//                            .description("test")
//                            .cinema(cinema3)
//                            .build();
//
//            showTimes3.add(showTime9);
//            showTimeService.save(showTime9);
//
//
//            ShowTime showTime10 =
//                    ShowTime
//                            .builder()
//                            .saloon(saloon4)
//                            .show(show4)
//                            .startTime(LocalDateTime.of(2024, 8, 20, 18, 0))
//                            .endTime(LocalDateTime.of(2024, 8, 20, 20, 0))
//                            .status(true)
//                            .deleted(false)
//                            .description("test")
//                            .cinema(cinema3)
//                            .build();
//
//            showTimes3.add(showTime10);
//            showTimeService.save(showTime10);
//
//
//            cinema3.setSaloonList(saloons3);
//            cinema3.setShowList(shows3);
//            cinema3.setShowTimeList(showTimes3);
//            cinemaService.edit(cinema3);
//
//            manager3.setCinema(cinema3);
//            managerService.edit(manager3);


            req.getSession().setAttribute("saloons", managerService.findById(1L).getCinema().getSaloonList());
            req.getSession().setAttribute("shows", managerService.findById(1L).getCinema().getShowList());
            req.getSession().setAttribute("showTimes", managerService.findById(1L).getCinema().getShowTimeList());
            req.getRequestDispatcher("/managers/managers-panel.jsp").forward(req, resp);


        } catch (Exception e) {
            resp.getWriter().write("<h1 style=\"background-color: green;\">" + e.getMessage() + "</h1>");
            e.printStackTrace();
        }
    }


}
