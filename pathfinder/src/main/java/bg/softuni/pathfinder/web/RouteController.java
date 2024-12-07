package bg.softuni.pathfinder.web;

import bg.softuni.pathfinder.dtos.route.RouteShortInfoDTO;
import bg.softuni.pathfinder.service.RouteService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class RouteController {

    private final RouteService routeService;

    public RouteController(RouteService routeService) {
        this.routeService = routeService;
    }

    @GetMapping("/routes")
    public ModelAndView routes() {
        ModelAndView mav = new ModelAndView("routes");

        List<RouteShortInfoDTO> allRoutes = routeService.getAllRoutesShortInfo();
        mav.addObject("routes", allRoutes);

        return mav;
    }
}
