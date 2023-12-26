package ru.pin120.demoSpring.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.pin120.demoSpring.models.*;
import ru.pin120.demoSpring.service.serviceImpl.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
//@RequestMapping("/diseases")
public class DiseaseController {
    @Autowired
    private DiseaseServiceImpl diseaseService;
    @Autowired
    private VisitingServiceImpl visitingService;
    @Autowired
    private DiseaseTypeServiceImpl diseaseTypeService;
    private long selectIdType = -1;
    private long selectIdVisiting = -1;
    @GetMapping("/diseases/main")
    public String getDiseasesFull(Model model){
        List<Disease> diseases =
                (List<Disease>)diseaseService.findAll();
        selectIdType = -1;
        selectIdVisiting = -1;
        model.addAttribute("diseases", diseases);
        model.addAttribute("OnlyVisiting",false);
        //model.addAttribute("specialties", "Какие-то");
        return "disease\\main";
    }
    @GetMapping("/visitings/details/{id-visiting}/diseases/main")
    public String getDiseasesVisiting(@PathVariable("id-visiting") Long idVisiting,Model model){
        List<Disease> diseases =
                (List<Disease>)visitingService.findOneById(idVisiting).get().getDiseases();
        model.addAttribute("diseases", diseases);
        model.addAttribute("idVisiting", idVisiting);
        model.addAttribute("OnlyVisiting",true);
        //model.addAttribute("specialties", "Какие-то");
        return "disease\\main";
    }
//    @GetMapping("/diseases/new")
//    public String newDisease(Model model){
//        model.addAttribute("disease", new Disease());
//        model.addAttribute("OnlyVisiting",false);
//        return "disease\\new";
//    }
//    @PostMapping("/diseases/new")
//    public String newDisease(@ModelAttribute Disease disease, Model model){
//        diseaseService.create(disease.getDiseaseType(),disease.getDescription(),disease.getVisiting());
//        return "redirect:/diseases/main";
//    }
    @GetMapping("/visitings/details/{id-visiting}/diseases/new")
    public String newDiseaseVisiting(@PathVariable("id-visiting") long idVisitind, Model model){
        selectIdVisiting = idVisitind;
//        List<DiseaseType> diseaseTypes = diseaseTypeService.findAll().stream().toList();
//        model.addAttribute("diseaseTypes", diseaseTypes);
        Visiting visiting = visitingService.findOneById(idVisitind).get();
        DiseaseType diseaseType = new DiseaseType();
        if(selectIdType != -1){
            diseaseType = diseaseTypeService.findOneById(selectIdType).get();
        }
        Disease disease = new Disease();
        disease.setVisiting(visiting);
        disease.setDiseaseType(diseaseType);
        model.addAttribute("visiting", visiting);
        model.addAttribute("diseaseType",diseaseType);
        model.addAttribute("disease", disease);
        return "disease\\new";
    }
    @PostMapping("/visitings/{id-visiting}/diseases/new")
    public String newDiseaseVisiting(@ModelAttribute("disease") Disease disease, Model model){
        diseaseService.create(disease.getDiseaseType(), disease.getDescription(), disease.getVisiting());
        selectIdType= -1;
        selectIdVisiting = -1;
        return "redirect:/visitings/details/"+disease.getVisiting().getId()+"/diseases/main";
    }

    @GetMapping("/disease/disease-type")
    public String chooseDiseaseType(Model model){
        List<DiseaseType> diseaseTypes = diseaseTypeService.findAll().stream().toList();
        model.addAttribute("diseaseTypes", diseaseTypes);
        model.addAttribute("visiting", selectIdVisiting);
        return "disease\\choice-type";
    }
    @PostMapping("/disease/disease-type")
    public String chooseDiseaseType(@ModelAttribute("idType") Long idType,Model model){
        selectIdType = idType;
        if(selectIdVisiting == -1){
            selectIdType = -1;
            return "redirect:/visitings/main";
        }
        return "redirect:/visitings/details/"+selectIdVisiting+"/diseases/new";
    }

//    @GetMapping("/visitings/details/{id-visiting}/diseases/new")
//    public String newDisease(@PathVariable("id-visiting") Long idVisitind,Model model){
//        Visiting visiting = visitingService.findOneById(idVisitind).get();
//        model.addAttribute("visiting", visiting);
//        model.addAttribute("disease", new Disease());
//        model.addAttribute("OnlyVisiting",true);
//        return "disease\\new";
//    }
//    @PostMapping("/visitings/{id-visiting}/diseases/new")
//    public String newDiseaseFull(@ModelAttribute Disease disease, Model model){
//        diseaseService.create(disease.getDiseaseType(),disease.getDescription(),disease.getVisiting());
//        return "redirect:/visitings/details/"+disease.getVisiting().getId()+"/diseases/main";
//    }

//    @GetMapping("/diseases/update/{id}")
//    public String editDisease(Model model, @PathVariable("id") Long id){
//        Optional<Disease> optionalDisease = diseaseService.findOneById(id);
//        if(optionalDisease.isEmpty()){
//            return "redirect:/diseases/main";
//        }
//        model.addAttribute("disease", optionalDisease.get());
//        model.addAttribute("OnlyVisiting",false);
//        return "disease\\edit";
//    }
//    @GetMapping("/visitings/details/{id-visiting}/diseases/update/{id}")
//    public String editDiseaseFull(Model model, @PathVariable("id") Long id){
//        Optional<Disease> optionalDisease = diseaseService.findOneById(id);
//        if(optionalDisease.isEmpty()){
//            return "redirect:/diseases/main";
//        }
//        model.addAttribute("OnlyVisiting",true);
//        model.addAttribute("disease", optionalDisease.get());
//        return "disease\\edit";
//    }
//    @PostMapping("/diseases/update")
//    public String editDisease(@ModelAttribute Disease disease, Model model){
//        diseaseService.update(disease.getId(), disease.getDiseaseType(),disease.getDescription(),disease.getVisiting());
//        return "redirect:/diseases/main";
//    }
//    @PostMapping("/visitings/{id-visiting}/diseases/update")
//    public String editDiseaseFull(@ModelAttribute Disease disease, Model model){
//        diseaseService.update(disease.getId(), disease.getDiseaseType(),disease.getDescription(),disease.getVisiting());
//        return "redirect:/visitings/details/"+disease.getVisiting().getId()+"/diseases/main";
//    }

    @GetMapping("/diseases/delete/{id}")
    public String deleteDiseaseFull(Model model, @PathVariable("id") Long id){
        if(diseaseService.existsById(id)){
            diseaseService.delete(id);
        }
        model.addAttribute("OnlyVisiting", false);
        return "redirect:/diseases/main";
    }
    @GetMapping("/visitings/details/{id-visiting}/diseases/delete/{id}")
    public String deleteDiseaseVisiting(Model model, @PathVariable("id") Long id){
        Visiting visiting = new Visiting();
        if(diseaseService.existsById(id)){
            visiting = diseaseService.findOneById(id).get().getVisiting();
            diseaseService.delete(id);
        }
        model.addAttribute("OnlyVisiting", true);
        return "redirect:/visitings/details/"+ visiting.getId()+"/diseases/main";
    }
}
