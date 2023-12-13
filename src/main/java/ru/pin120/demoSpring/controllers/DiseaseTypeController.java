package ru.pin120.demoSpring.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.pin120.demoSpring.models.Disease;
import ru.pin120.demoSpring.models.DiseaseType;
import ru.pin120.demoSpring.service.serviceImpl.DiseaseTypeServiceImpl;
import ru.pin120.demoSpring.service.serviceImpl.SpecialtyServiceImpl;

import java.util.List;
import java.util.Optional;
@Controller
@RequestMapping("/disease-types")
public class DiseaseTypeController {
    @Autowired
    private DiseaseTypeServiceImpl diseaseTypeService;

    @GetMapping("/main")
    public String getDiseaseTypes(Model model){
        List<DiseaseType> diseaseTypes =
                diseaseTypeService.findAll().stream().toList();
        model.addAttribute("diseaseTypes", diseaseTypes);
        return "disease-type\\main";
    }
    @GetMapping("/new")
    public String newDiseaseType(Model model){
        model.addAttribute("diseaseType", new DiseaseType());
        return "disease-type\\new";
    }
    @PostMapping("/new")
    public String newDiseaseType(@ModelAttribute DiseaseType diseaseType, Model model){
        diseaseTypeService.create(diseaseType.getName());
        return "redirect:/disease-types/main";
    }

    @GetMapping("/update/{id}")
    public String editDiseaseType(Model model, @PathVariable("id") Long id){
        Optional<DiseaseType> optionalDiseaseType = diseaseTypeService.findOneById(id);
        if(optionalDiseaseType.isEmpty()){
            return "redirect:/disease-types/main";
        }
        model.addAttribute("diseaseType", optionalDiseaseType.get());
        return "disease-type\\edit";
    }
    @PostMapping("/update")
    public String editDiseaseType(@ModelAttribute DiseaseType diseaseType, Model model){
        List<Disease> diseases = diseaseType.getDiseases();
        diseaseTypeService.update(diseaseType.getId(), diseaseType.getName(), diseases);
        return "redirect:/disease-types/main";
    }
    @GetMapping("/delete/{id}")
    public String deleteDiseaseType(Model model, @PathVariable("id") Long id){
        if(diseaseTypeService.existsById(id)){
            diseaseTypeService.delete(id);
        }
        return "redirect:/disease-types/main";
    }

}
