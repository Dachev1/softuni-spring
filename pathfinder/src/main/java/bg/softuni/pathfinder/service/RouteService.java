package bg.softuni.pathfinder.service;

import bg.softuni.pathfinder.dtos.route.RouteShortInfoDTO;

import java.util.List;

public interface RouteService {
    List<RouteShortInfoDTO> getAllRoutesShortInfo();
}
