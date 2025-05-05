package com.tauhoa.train.repositories;

import com.tauhoa.train.models.Route;
import com.tauhoa.train.models.Seat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface RouteRepository  extends JpaRepository<Route, Integer>{

    Optional<Route> findRouteByRouteName(String name);
}
