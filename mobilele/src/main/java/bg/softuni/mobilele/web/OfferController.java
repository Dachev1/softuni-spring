package bg.softuni.mobilele.web;

import bg.softuni.mobilele.model.dtos.offer.NewOfferDTO;
import bg.softuni.mobilele.model.dtos.offer.OfferDetailsDTO;
import bg.softuni.mobilele.service.OfferService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Controller
@RequestMapping("/offers")
public class OfferController {

    private final OfferService offerService;

    public OfferController(OfferService offerService) {
        this.offerService = offerService;
    }

    @GetMapping("/add")
    public String addOffer(Model model) {
        model.addAttribute("newOfferDTO", new NewOfferDTO(null, 0.0, null, null, 0, 0, null, null));
        model.addAttribute("engineTypes", offerService.getAllEngineTypes());
        model.addAttribute("transmissionTypes", offerService.getAllTransmissionTypes());
        return "offer-add";
    }

    @PostMapping("/add")
    public String addOffer(
            @Valid @ModelAttribute("newOfferDTO") NewOfferDTO newOfferDTO,
            BindingResult bindingResult,
            Model model
    ) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("engineTypes", offerService.getAllEngineTypes());
            model.addAttribute("transmissionTypes", offerService.getAllTransmissionTypes());
            return "offer-add";
        }
        UUID createdOfferId = offerService.addOffer(newOfferDTO);
        return "redirect:/offers/" + createdOfferId;
    }

    @GetMapping("/{id}")
    public String showOffer(@PathVariable String id, Model model) {
        UUID offerId = UUID.fromString(id);
        OfferDetailsDTO offerDetails = offerService.getOfferById(offerId);
        model.addAttribute("offerDetails", offerDetails);
        return "details";
    }
}
