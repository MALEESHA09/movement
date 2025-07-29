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

    // Display all movements
    @GetMapping
    public String showAllMovements(Model model) {
        List<Location> movements = movementService.getAllMovements();
        model.addAttribute("movements", movements);
        return "adminMovement"; // Your Thymeleaf template name
    }

    // Display movement history (filtered by date)
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
        
        model.addAttribute("movements", movements);
        return "movementHistory"; // Your Thymeleaf template name for history tab
    }

    // Show form to add new movement
    @GetMapping("/new")
    public String showAddMovementForm(Model model) {
        model.addAttribute("movement", new Location());
        return "addMovement"; // Your Thymeleaf template name for add form
    }

    // Process adding new movement
    @PostMapping("/save")
    public String saveMovement(@ModelAttribute("movement") Location location) {
        movementService.recordMovement(location);
        return "redirect:/adminMovement";
    }

    // Show form to edit movement
    @GetMapping("/edit/{id}")
    public String showEditMovementForm(@PathVariable String id, Model model) {
        Location movement = movementService.getMovementById(id);
        model.addAttribute("movement", movement);
        return "editMovement"; // Your Thymeleaf template name for edit form
    }

    // Process updating movement
    @PostMapping("/update/{id}")
    public String updateMovement(@PathVariable String id, @ModelAttribute("movement") Location location) {
        movementService.updateMovement(id, location);
        return "redirect:/adminMovement";
    }

    // Delete a movement
    @GetMapping("/delete/{id}")
    public String deleteMovement(@PathVariable String id) {
        movementService.deleteMovement(id);
        return "redirect:/adminMovement";
    }

    // Search movements by department
    @GetMapping("/search")
    public String searchMovements(@RequestParam String departmentName, Model model) {
        List<Location> movements = movementService.searchMovementsByDepartment(departmentName);
        model.addAttribute("movements", movements);
        return "adminMovement"; // Return to main view with filtered results
    }

    // View movement details
    @GetMapping("/view/{id}")
    public String viewMovementDetails(@PathVariable String id, Model model) {
        Location movement = movementService.getMovementById(id);
        model.addAttribute("movement", movement);
        return "viewMovement"; // Your Thymeleaf template name for details view
    }
}