package ru.pin120.demoSpring.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.pin120.demoSpring.models.Doctor;
import ru.pin120.demoSpring.models.Specialty;
import ru.pin120.demoSpring.models.Visiting;
import ru.pin120.demoSpring.service.serviceImpl.DoctorServiceImpl;
import ru.pin120.demoSpring.service.serviceImpl.SpecialtyServiceImpl;

import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/doctors")
public class DoctorController {
    @Autowired
    private DoctorServiceImpl doctorService;
    @Autowired
    private SpecialtyServiceImpl specialtyService;

    @GetMapping("/main")
    public String getDoctors(Model model){
        List<Doctor> doctors =
                (List<Doctor>)doctorService.findAll();
        model.addAttribute("doctors", doctors);
        //model.addAttribute("specialties", "Какие-то");
        return "doctor\\main";
    }
    @GetMapping("/new")
    public String newDoctor(Model model){
        model.addAttribute("doctor", new Doctor());
        return "doctor\\new";
    }
    @PostMapping("/new")
    public String newDoctor(@ModelAttribute Doctor doctor, Model model){
        doctorService.create(doctor.getFirstName(),doctor.getLastName(),doctor.getPatr(), doctor.getWorkExp(),doctor.getPathPhoto(), new ArrayList<Specialty>());
        return "redirect:/doctors/main";
    }

    @GetMapping("/update/{id}")
    public String editDoctor(Model model, @PathVariable("id") Long id){
        Optional<Doctor> optionalDoctor = doctorService.findOneById(id);
        if(optionalDoctor.isEmpty()){
            return "redirect:/doctors/main";
        }
        model.addAttribute("doctor", optionalDoctor.get());
        return "doctor\\edit";
    }
    @PostMapping("/update")
    public String editDoctor(@ModelAttribute Doctor doctor, Model model){
        List<Specialty> specialties = doctorService.findOneById(doctor.getId()).get().getSpecialties();
        List<Visiting> visitings = doctorService.findOneById(doctor.getId()).get().getVisitings();
        doctorService.update(doctor.getId(), doctor.getFirstName(),doctor.getLastName(),doctor.getPatr(), doctor.getWorkExp(), doctor.getPathPhoto(), specialties, visitings);
        return "redirect:/doctors/main";
    }

    @GetMapping("/update-specialties/{id}")
    public String editDoctorSpec(Model model, @PathVariable("id") Long id){
        Optional<Doctor> optionalDoctor = doctorService.findOneById(id);
        List<Specialty> oldSpecialties = optionalDoctor.get().getSpecialties();
        List<Specialty> allSpecialties = specialtyService.findAll().stream().toList();
        //List<Specialty> otherSpecialties = specialtyService.findAll().stream().toList();
//        for (Specialty sp:
//             olsSpecialties) {
//            otherSpecialties.remove(sp);
//        }
        if(optionalDoctor.isEmpty()){
            return "redirect:/doctors/main";
        }
        model.addAttribute("doctor", optionalDoctor.get());
        model.addAttribute("oldSpecialties", oldSpecialties);
        model.addAttribute("allSpecialties", allSpecialties);
        //model.addAttribute("otherSpecialties", otherSpecialties);
        return "doctor\\edit-specialties";
    }
    @PostMapping("/remove-specialties")
    public String removeDoctorSpec(@ModelAttribute("iddoc") Long iddoc, @ModelAttribute("idspec") Long idspec, Model model){
        Doctor doctor = doctorService.findOneById(iddoc).get();
        List<Specialty> specialties = doctor.getSpecialties();
        List<Visiting> visitings = doctor.getVisitings();
        Specialty remove = specialtyService.findOneById(idspec).get();
        specialties.remove(remove);
        doctorService.update(doctor.getId(), doctor.getFirstName(),doctor.getLastName(),doctor.getPatr(), doctor.getWorkExp(), doctor.getPathPhoto(), specialties, visitings);
        return "redirect:/doctors/update-specialties/"+doctor.getId();
    }
    @PostMapping("/add-specialties")
    public String addDoctorSpec(@ModelAttribute("iddoc") Long iddoc, @ModelAttribute("idspec") Long idspec, Model model){
        Doctor doctor = doctorService.findOneById(iddoc).get();
        List<Specialty> specialties = doctor.getSpecialties();
        List<Visiting> visitings = doctor.getVisitings();
        Specialty newSpec = specialtyService.findOneById(idspec).get();
        specialties.add(newSpec);
        doctorService.update(doctor.getId(), doctor.getFirstName(),doctor.getLastName(),doctor.getPatr(), doctor.getWorkExp(), doctor.getPathPhoto(), specialties, visitings);
        return "redirect:/doctors/update-specialties/"+doctor.getId();
    }
    @GetMapping("/delete/{id}")
    public String deleteDoctor(Model model, @PathVariable("id") Long id){
        if(doctorService.existsById(id)){
            doctorService.delete(id);
        }
        return "redirect:/doctors/main";
    }
}
