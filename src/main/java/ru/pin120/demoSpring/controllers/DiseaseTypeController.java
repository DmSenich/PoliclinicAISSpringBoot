package ru.pin120.demoSpring.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.pin120.demoSpring.models.Disease;
import ru.pin120.demoSpring.models.DiseaseType;
import ru.pin120.demoSpring.service.serviceImpl.DiseaseTypeServiceImpl;
import ru.pin120.demoSpring.service.serviceImpl.SpecialtyServiceImpl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static org.springframework.beans.factory.support.InstanceSupplier.using;

@Controller
@RequestMapping("/disease-types")
public class DiseaseTypeController {
    private static String REPORTS_DIR = System.getProperty("user.dir") + "\\reports";
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

    @GetMapping("/generate-doc/{id}")
    public ResponseEntity<Resource> generateDoc(Model model, @PathVariable("id") Long id) throws FileNotFoundException {
        DiseaseType diseaseType = diseaseTypeService.findOneById(id).get();
        if(diseaseType == null){
            return null;
        }
        List<Disease> diseases = diseaseType.getDiseases();
        if(diseases.isEmpty()){
            return null;
        }
        StringBuilder content = new StringBuilder();
        int count = 0;
        for(Disease disease : diseases){
            count++;
            content.append(count + ") " + disease.getVisiting().getDate() + "\n");
            content.append(disease.getDescription() + "\n\n");
        }
        String fileName = "Type" + ".txt";
        String filePath = REPORTS_DIR + File.separator + fileName;
        File file = new File(filePath);
        try(FileWriter writer = new FileWriter(file)){
            writer.write(content.toString());
            writer.flush();
        }
        catch (IOException e){
            e.printStackTrace();
        }
        ResponseEntity<Resource> resource = Downloader.downloadFile(fileName);
        model.addAttribute("report", resource);
        return resource;

    }

}
