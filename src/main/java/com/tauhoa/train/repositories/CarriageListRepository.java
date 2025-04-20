package com.tauhoa.train.repositories;

import com.tauhoa.train.models.CarriageList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CarriageListRepository extends JpaRepository<CarriageList, Integer> {
//    List<CarriageList> findAllByTripId(int trip_id);
    @Query("SELECT cl FROM CarriageList cl WHERE cl.trip.tripId = :tripId")
    List<CarriageList> findAllByTripId(@Param("tripId") int tripId);
    @Override
    Optional<CarriageList> findById(Integer id);

    List<CarriageList> findByTripTripId(int tripId);

    @Query("SELECT cl FROM CarriageList cl WHERE cl.trip.tripId = :tripId ORDER BY cl.stt")
    List<CarriageList> findByTripId(@Param("tripId") int tripId);


    // Tìm CarriageList theo tripId và stt
    @Query("SELECT cl FROM CarriageList cl WHERE cl.trip.tripId = :tripId AND cl.stt = :stt")
    Optional<CarriageList> findByTripIdAndStt(@Param("tripId") int tripId, @Param("stt") int stt);

    @Query("SELECT cl FROM CarriageList cl WHERE cl.trip.tripId = :tripId AND cl.carriageListId = :carriageListId")
    Optional<CarriageList> findByTripIdAndCarriageListId(@Param("tripId") int tripId, @Param("carriageListId") int carriageListId);

//    @Query("SELECT tr FROM TicketReservation tr WHERE tr.trip.tripId = :tripId AND " +
//            "EXISTS (SELECT ts1 FROM TrainSchedule ts1 WHERE ts1.train.trainId = tr.trip.train.trainId AND ts1.station.stationId = tr.departureStation.stationId AND ts1.ordinalNumber < :requestArrivalOrdinal) AND " +
//            "EXISTS (SELECT ts2 FROM TrainSchedule ts2 WHERE ts2.train.trainId = tr.trip.train.trainId AND ts2.station.stationId = tr.arrivalStation.stationId AND ts2.ordinalNumber > :requestDepartureOrdinal)")
//    List<TicketReservation> findOverlappingReservations(@Param("tripId") int tripId,
//                                                        @Param("requestDepartureOrdinal") int requestDepartureOrdinal,
//                                                        @Param("requestArrivalOrdinal") int requestArrivalOrdinal);
}
