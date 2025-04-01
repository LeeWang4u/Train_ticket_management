package com.tauhoa.train.repositories;

import com.tauhoa.train.models.CarriageList;
import com.tauhoa.train.models.Seat;
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
}
