package ru.pin120.demoSpring.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.pin120.demoSpring.Downloader;
import ru.pin120.demoSpring.models.Doctor;
import ru.pin120.demoSpring.models.Specialty;
import ru.pin120.demoSpring.service.serviceImpl.SpecialtyServiceImpl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/specialties")
public class SpecialtyController {
    private static String REPORTS_DIR = System.getProperty("user.dir") + "\\reports";
    private static String VoidTXT = "void.txt";
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

    @GetMapping("/generate-doc/{id}")
    public ResponseEntity<Resource> generateDoc(Model model, @PathVariable("id") Long id) throws FileNotFoundException {
        Optional<Specialty> specialtyOptional = specialtyService.findOneById(id);
        if(specialtyOptional.isEmpty()){
            ResponseEntity<Resource> resource = Downloader.downloadFile(VoidTXT);
            return resource;
        }
        Specialty specialty = specialtyOptional.get();
        List<Doctor> doctors = specialty.getDoctors();
        if(doctors.isEmpty()){
            ResponseEntity<Resource> resource = Downloader.downloadFile(VoidTXT);
            return resource;
        }
        StringBuilder content = new StringBuilder();
        int count = 0;
        content.append("Специальность: "+ specialty.getName() + "\n\n");
        for(Doctor doctor : doctors){
            count++;
            content.append(count + ") " + doctor.getLastName() + " " + doctor.getFirstName());
            if(doctor.getPatr() != null){
                content.append(" "+ doctor.getPatr() + "\n");
            }
        }
        String fileName = "DoctorsOfSpecialty" + ".txt";
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
        return resource;

    }
}
