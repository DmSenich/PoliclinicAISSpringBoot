package ru.pin120.demoSpring.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.pin120.demoSpring.models.Doctor;
import ru.pin120.demoSpring.models.Specialty;
import ru.pin120.demoSpring.service.serviceImpl.DoctorServiceImpl;
import ru.pin120.demoSpring.service.serviceImpl.SpecialtyServiceImpl;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/specialties")
public class SpecialtyController {
    @Autowired
    private SpecialtyServiceImpl specialtyService;

    @GetMapping("/main")
    public String getSpecialties(Model model){
        List<Specialty> specialties =
                specialtyService.findAll().stream().toList();
        model.addAttribute("specialties", specialties);
        return "specialty\\main";
    }
    @GetMapping("/new")
    public String newSpecialty(Model model){
        model.addAttribute("specialty", new Specialty());
        return "specialty\\new";
    }
    @PostMapping("/new")
    public String newSpecialty(@ModelAttribute Specialty specialty, Model model){
        specialtyService.create(specialty.getName());
        return "redirect:/specialties/main";
    }

    @GetMapping("/update/{id}")
    public String editSpecialty(Model model, @PathVariable("id") Long id){
        Optional<Specialty> optionalSpecialty = specialtyService.findOneById(id);
        if(optionalSpecialty.isEmpty()){
            return "redirect:/specialties/main";
        }
        model.addAttribute("specialty", optionalSpecialty.get());
        return "specialty\\edit";
    }
    @PostMapping("/update")
    public String editSpecialty(@ModelAttribute Specialty specialty, Model model){
        specialtyService.update(specialty.getId(), specialty.getName());
        return "redirect:/specialties/main";
    }
    @GetMapping("/delete/{id}")
    public String deleteSpecialty(Model model, @PathVariable("id") Long id){
        if(specialtyService.existsById(id)){
            specialtyService.delete(id);
        }
        return "redirect:/specialties/main";
    }

}
