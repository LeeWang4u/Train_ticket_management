package com.tauhoa.train.services;


import com.tauhoa.train.models.CarriageList;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

public interface ICarriageListService {
    Optional<CarriageList> getSeatById(int id);

    List<CarriageList> findAllCarriageListByIdTrip(int idTrip);
}
