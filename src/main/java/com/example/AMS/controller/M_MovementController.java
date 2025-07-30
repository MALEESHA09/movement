package com.example.AMS.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.AMS.model.Location;
import com.example.AMS.service.M_MovementService;

@Controller
@RequestMapping("/adminMovement")
public class M_MovementController {

    private final M_MovementService movementService;

    @Autowired
    public M_MovementController(M_MovementService movementService) {
        this.movementService = movementService;
    }

    @GetMapping
    public String showAllMovements(Model model) {
        model.addAttribute("locations", movementService.getAllMovements());
        return "Movement/Admin";
    }

    @GetMapping("/history")
    public String showMovementHistory(
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate,
            Model model) {
        
        List<Location> movements;
        if (startDate != null && endDate != null) {
            movements = movementService.getMovementsByDateRange(startDate, endDate);
        } else {
            movements = movementService.getAllMovements();
        }
        
        model.addAttribute("locations", movements);
        return "Movement/Admin";
    }

    @GetMapping("/new")
    public String showAddForm(Model model) {
        model.addAttribute("location", new Location());
        return "Movement/Admin";
    }

    @PostMapping("/save")
    public String saveMovement(@ModelAttribute Location location) {
        movementService.recordMovement(location);
        return "redirect:/adminMovement";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        model.addAttribute("location", movementService.getMovementById(id));
        return "Movement/Admin";
    }

    @PostMapping("/update/{id}")
    public String updateMovement(@PathVariable Long id, @ModelAttribute Location location) {
        movementService.updateMovement(id, location);
        return "redirect:/adminMovement";
    }

    @GetMapping("/delete/{id}")
    public String deleteMovement(@PathVariable Long id) {
        movementService.deleteMovement(id);
        return "redirect:/adminMovement";
    }

    @GetMapping("/asset/{assetId}")
    public String getAssetMovementHistory(@PathVariable Long assetId, Model model) {
        model.addAttribute("locations", movementService.getMovementHistoryForAsset(assetId));
        model.addAttribute("assetId", assetId);
        return "Movement/Admin";
    }
}