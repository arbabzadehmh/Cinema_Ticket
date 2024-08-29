package com.example.cinema_test.model.service;


import com.example.cinema_test.controller.exception.ShowTimeNotFoundException;
import com.example.cinema_test.model.entity.*;
import com.example.cinema_test.model.entity.enums.ShowType;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class ShowTimeService implements Serializable {

    @PersistenceContext(unitName = "cinema")
    private EntityManager entityManager;

    @Transactional
    public ShowTime save(ShowTime showTime) throws Exception {

        for (Seat seat : showTime.getSaloon().getSeats()) {
            SeatVo seatVo = new SeatVo(seat);
            if (showTime.getShow().getShowType().equals(ShowType.MOVIE)) {
                seatVo.setPriceRatio(1);
                seatVo.setSeatPrice(seatVo.getPriceRatio() * showTime.getShow().getBasePrice());
            }
//            showTime.getShowSeats().add(seatVo);
        }

        entityManager.persist(showTime);
        return showTime;
    }

    @Transactional
    public ShowTime edit(ShowTime showTime) throws Exception {
        ShowTime foundShowTime = entityManager.find(ShowTime.class, showTime.getId());
        if (foundShowTime != null) {
            entityManager.merge(showTime);
            return showTime;
        }
        throw new ShowTimeNotFoundException();
    }

    @Transactional
    public ShowTime remove(Long id) throws Exception {
        ShowTime showTime = entityManager.find(ShowTime.class, id);
        if (showTime != null) {
            showTime.setDeleted(true);
            entityManager.merge(showTime);
            return showTime;
        }
        throw new ShowTimeNotFoundException();
    }

    @Transactional
    public List<ShowTime> findAll() throws Exception {
        return entityManager
                .createQuery("select oo from showTimeEntity oo where oo.deleted=false order by id", ShowTime.class)
                .getResultList();
    }

    @Transactional
    public ShowTime findById(Long id) throws Exception {
        return entityManager.find(ShowTime.class, id);
    }

    @Transactional
    public List<ShowTime> findByCinemaName(String name) throws Exception {
        return entityManager
                .createQuery("select s from showTimeEntity s where s.cinema.name =:name and s.deleted=false", ShowTime.class)
                .setParameter("name", name)
                .getResultList();
    }

    @Transactional
    public List<Show> findActiveShows() throws Exception {
        return entityManager
                .createQuery("select distinct s.show from showTimeEntity s where s.startTime between :startTime and :endTime and s.status = true and s.deleted = false ", Show.class)
                .setParameter("startTime", LocalDateTime.now())
                .setParameter("endTime", LocalDateTime.now().plusDays(7))
                .getResultList();
    }

    @Transactional
    public List<ShowTime> findByShowId(Long showId) throws Exception {
        return entityManager
                .createQuery("select s from showTimeEntity s where s.startTime between :startTime and :endTime and s.show.id =:showId and s.status = true and s.deleted = false ", ShowTime.class)
                .setParameter("startTime", LocalDateTime.now())
                .setParameter("endTime", LocalDateTime.now().plusDays(5))
                .setParameter("showId", showId)
                .getResultList();
    }

    @Transactional
    public List<LocalDate> findDistinctDatesByShowId(Long showId) throws Exception {
        List<LocalDate> dates = new ArrayList<>();
        List<LocalDateTime> dateTimeList =
                entityManager
                        .createQuery("select distinct s.startTime from showTimeEntity s where s.startTime between :startTime and :endTime and s.show.id =:showId and s.status = true and s.deleted = false ", LocalDateTime.class)
                        .setParameter("startTime", LocalDateTime.now())
                        .setParameter("endTime", LocalDateTime.now().plusDays(5))
                        .setParameter("showId", showId)
                        .getResultList();
        for (LocalDateTime localDateTime : dateTimeList) {
            LocalDate date = localDateTime.toLocalDate();
            if (!dates.contains(date)) {
                dates.add(date);
            }
        }
        return dates;
    }

    @Transactional
    public List<ShowTime> findByShowIdAndDate(Long showId, LocalDate date) throws Exception {
        return entityManager
                .createQuery("select s from showTimeEntity s where s.startTime between :startTime and :endTime and s.show.id =:showId and s.status = true and s.deleted = false ", ShowTime.class)
                .setParameter("startTime", date.atTime(1, 0, 0))
                .setParameter("endTime", date.atTime(23, 59, 59))
                .setParameter("showId", showId)
                .getResultList();
    }


    @Transactional
    public List<Cinema> findDistinctCinemasByShowIdAndDate(Long showId, LocalDate date) throws Exception {
        return entityManager
                .createQuery("select distinct s.cinema from showTimeEntity s where s.startTime between :startTime and :endTime and s.show.id = :showId and s.status = true and s.deleted = false", Cinema.class)
                .setParameter("startTime", date.atTime(1, 0, 0))
                .setParameter("endTime", date.atTime(23, 59, 59))
                .setParameter("showId", showId)
                .getResultList();
    }


    @Transactional
    public List<ShowTime> findByShowIdAndDateAndCinemaId(Long ShowId, LocalDate date, Long cinemaId) throws Exception {
        return entityManager
                .createQuery("select s from showTimeEntity s where s.startTime between :startTime and :endTime and s.show.id =:ShowId and s.cinema.id =:cinemaId and s.status = true and s.deleted = false ", ShowTime.class)
                .setParameter("startTime", date.atTime(1, 0, 0))
                .setParameter("endTime", date.atTime(23, 59, 59))
                .setParameter("ShowId", ShowId)
                .setParameter("cinemaId", cinemaId)
                .getResultList();
    }

    @Transactional
    public List<ShowTime> findActiveShowsBySaloonId(Long saloonId) throws Exception {
        return entityManager
                .createQuery("select s from showTimeEntity s where s.startTime between :startTime and :endTime and s.status = true and s.saloon.id =:saloonId and s.deleted = false ", ShowTime.class)
                .setParameter("startTime", LocalDateTime.now())
                .setParameter("endTime", LocalDateTime.now().plusDays(7))
                .setParameter("saloonId", saloonId)
                .getResultList();
    }

}
