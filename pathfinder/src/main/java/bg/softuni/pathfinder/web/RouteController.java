package bg.softuni.pathfinder.web;

import bg.softuni.pathfinder.dtos.route.RouteDetailsDTO;
import bg.softuni.pathfinder.dtos.route.RouteShortInfoDTO;
import bg.softuni.pathfinder.service.RouteService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

        List<RouteShortInfoDTO> allRoutes = this.routeService.getAllRoutesShortInfo();
        mav.addObject("routes", allRoutes);

        return mav;
    }

    @GetMapping("/routes/details/{id}")
    public ModelAndView getRouteDetails(@PathVariable Long id) {
        RouteDetailsDTO routeDetails = this.routeService.getRouteDetailsById(id);

        ModelAndView mav = new ModelAndView("route-details");
        mav.addObject("routeDetails", routeDetails);

        return mav;
    }
}
