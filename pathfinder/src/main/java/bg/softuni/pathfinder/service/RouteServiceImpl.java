package bg.softuni.pathfinder.service;

import bg.softuni.pathfinder.dtos.route.RouteShortInfoDTO;
import bg.softuni.pathfinder.model.entity.Picture;
import bg.softuni.pathfinder.model.entity.Route;
import bg.softuni.pathfinder.repository.PictureRepository;
import bg.softuni.pathfinder.repository.RouteRepository;
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

    private RouteShortInfoDTO mapToShortInfoDTO(Route route) {
        String pictureUrl = pictureRepository.findFirstByRouteId(route.getId())
                .map(Picture::getUrl)
                .orElse(null);

        return new RouteShortInfoDTO(route.getName(), route.getDescription(), pictureUrl);
    }
}