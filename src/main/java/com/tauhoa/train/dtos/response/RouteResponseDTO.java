package com.tauhoa.train.dtos.response;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RouteResponseDTO {
    private int routeId;

    private String routeName;

    public RouteResponseDTO(int routeId, String routeName) {
        this.routeId = routeId;
        this.routeName = routeName;
    }
}
