package bg.softuni.pathfinder.service.impl;

import bg.softuni.pathfinder.dtos.route.RouteDetailsDTO;
import bg.softuni.pathfinder.dtos.route.RouteShortInfoDTO;
import bg.softuni.pathfinder.model.entity.Picture;
import bg.softuni.pathfinder.model.entity.Route;
import bg.softuni.pathfinder.repository.PictureRepository;
import bg.softuni.pathfinder.repository.RouteRepository;
import bg.softuni.pathfinder.service.RouteService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RouteServiceImpl implements RouteService {

    private final RouteRepository routeRepository;
    private final PictureRepository pictureRepository;
    private final ModelMapper modelMapper;

    public RouteServiceImpl(RouteRepository routeRepository, PictureRepository pictureRepository, ModelMapper modelMapper) {
        this.routeRepository = routeRepository;
        this.pictureRepository = pictureRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<RouteShortInfoDTO> getAllRoutesShortInfo() {
        return routeRepository.findAll()
                .stream()
                .map(this::mapToShortInfoDTO)
                .collect(Collectors.toList());
    }

    @Override
    public RouteDetailsDTO getRouteDetailsById(Long routeId) {
        Route route = routeRepository.findById(routeId)
                .orElseThrow(() -> new IllegalArgumentException("Route not found for ID: " + routeId));

        String pictureUrl = pictureRepository.findFirstByRouteId(routeId)
                .map(Picture::getUrl)
                .orElse(null);

        RouteDetailsDTO detailsDTO = modelMapper.map(route, RouteDetailsDTO.class);

        detailsDTO.setPictureUrl(pictureUrl);
        detailsDTO.setTotalDistance(22.257);

        return detailsDTO;
    }


    private RouteShortInfoDTO mapToShortInfoDTO(Route route) {
        String pictureUrl = getPictureUrl(route);

        RouteShortInfoDTO shortInfoDTO = modelMapper.map(route, RouteShortInfoDTO.class);

        shortInfoDTO.setPictureUrl(pictureUrl);

        return shortInfoDTO;
    }

    private String getPictureUrl(Route route) {
        return this.pictureRepository.findFirstByRouteId(route.getId())
                .map(Picture::getUrl)
                .orElse(null);
    }
}
