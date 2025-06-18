package com.tauhoa.train.repositories;


import com.tauhoa.train.models.Train;
import com.tauhoa.train.models.Trip;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface TripRepository extends JpaRepository<Trip, Integer> {
//    @Override
//    List<Trip> findAll();
    @Query("SELECT t FROM Trip t WHERE t.tripStatus = 'Scheduled'")
    List<Trip> findAllWithStatus();

    List<Trip> findAllByTripId(int tripId);

    List<Trip> findByTripDate(LocalDate tripDate);

    Trip findByTripId(int tripId);

    @Query("SELECT t FROM Trip t WHERE t.train.trainId = :trainId AND t.tripDate = :tripDate")
    List<Trip> findByTrainIdAndTripDate(@Param("trainId") int trainId, @Param("tripDate") LocalDate tripDate);

    List<Trip> findByTrainAndTripDate(Train train, LocalDate tripDate);


    @Query("SELECT t FROM Trip t WHERE " +
            "(:train IS NULL OR t.train = :train) AND " +
            "(:fromDate IS NULL OR t.tripDate >= :fromDate) AND " +
            "(:toDate IS NULL OR t.tripDate <= :toDate)")
    List<Trip> searchTrips(
            @Param("train") Train train,
            @Param("fromDate") LocalDate fromDate,
            @Param("toDate") LocalDate toDate
    );

//    @Query("SELECT t FROM Trip t WHERE t.train = :train AND t.tripDate = :tripDate AND t.tripStatus = 'Scheduled'")
//    List<Trip> findByTrainAndTripDate(@Param("train") Train train, @Param("tripDate") LocalDate tripDate);
//


//    @Query("SELECT t FROM Trip t " +
//            "WHERE t.train.trainId IN (" +
//            "    SELECT ts1.train.trainId " +
//            "    FROM TrainSchedule ts1 " +
//            "    JOIN TrainSchedule ts2 ON ts1.train.trainId = ts2.train.trainId " +
//            "    WHERE ts1.station.stationName = :departureStation " +
//            "    AND ts2.station.stationName = :arrivalStation " +
//            "    AND ts1.ordinalNumber < ts2.ordinalNumber" +
//            ") " +
//            "AND DATE(t.tripDate) = :tripDate")
//    List<Trip> findTripsByStationsAndDate(
//            @Param("departureStation") String departureStation,
//            @Param("arrivalStation") String arrivalStation,
//            @Param("tripDate") LocalDate tripDate);

//    @Query(value = "SELECT DISTINCT t.* " +
//            "FROM trip t " +
//            "JOIN train_schedule ts1 ON t.train_id = ts1.train_id " +
//            "JOIN train_schedule ts2 ON t.train_id = ts2.train_id " +
//            "JOIN station s1 ON ts1.station_id = s1.station_id " +
//            "JOIN station s2 ON ts2.station_id = s2.station_id " +
//            "WHERE s1.station_name = :departureStation " +
//            "AND s2.station_name = :arrivalStation " +
//            "AND ts1.ordinal_number < ts2.ordinal_number " +
//            "AND DATE(DATEADD(HOUR, HOUR(ts1.departure_time), DATEADD(MINUTE, MINUTE(ts1.departure_time), t.trip_date))) = :tripDate",
//            nativeQuery = true)
//    List<Trip> findTripsByStationsAndDate(
//            @Param("departureStation") String departureStation,
//            @Param("arrivalStation") String arrivalStation,
//            @Param("tripDate") LocalDate tripDate);

//    @Query("SELECT DISTINCT t FROM Trip t " +
//            "JOIN t.train.trainSchedules ts1 " +
//            "JOIN t.train.trainSchedules ts2 " +
//            "WHERE ts1.station.stationName = :departureStation " +
//            "AND ts2.station.stationName = :arrivalStation " +
//            "AND ts1.ordinalNumber < ts2.ordinalNumber")
//    List<Trip> findTripsByStations(
//            @Param("departureStation") String departureStation,
//            @Param("arrivalStation") String arrivalStation);
}
