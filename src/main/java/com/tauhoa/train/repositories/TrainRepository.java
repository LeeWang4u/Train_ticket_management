package com.tauhoa.train.repositories;

import com.tauhoa.train.models.Train;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TrainRepository extends JpaRepository<Train, Integer> {

    boolean existsByTrainName(String trainName);

    Optional<Train> findByTrainName(String trainName);
}
