package com.tauhoa.train.repositories;

import com.tauhoa.train.models.RouteDetail;
import com.tauhoa.train.models.Seat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
public interface RouteDetailRepository extends JpaRepository<RouteDetail, Integer>{
}
