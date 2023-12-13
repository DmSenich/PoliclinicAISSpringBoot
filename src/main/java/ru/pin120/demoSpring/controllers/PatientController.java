package ru.pin120.demoSpring.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.pin120.demoSpring.models.*;
import ru.pin120.demoSpring.service.serviceImpl.DoctorServiceImpl;
import ru.pin120.demoSpring.service.serviceImpl.PatientServiceImpl;
import ru.pin120.demoSpring.service.serviceImpl.SpecialtyServiceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/patients")
public class PatientController {
    @Autowired
    private PatientServiceImpl patientService;

    @GetMapping("/main")
    public String getPatients(Model model){
        List<Patient> patients =
                (List<Patient>)patientService.findAll();
        model.addAttribute("patients", patients);
        return "patient\\main";
    }
    @GetMapping("/new")
    public String newPatient(Model model){
        model.addAttribute("patient", new Patient());
        return "patient\\new";
    }
    @PostMapping("/new")
    public String newPatient(@ModelAttribute Patient patient, Model model){
        patientService.create(patient.getFirstName(),patient.getLastName(),patient.getPatr(), patient.getBirthDate(), patient.getArea(), patient.getCity(), patient.getHouse(), patient.getApartment());
        return "redirect:/patients/main";
    }
    @GetMapping("/details/{id}")
    public String detailsPatient(Model model, @PathVariable("id") Long id){
        Optional<Patient> optionalPatient = patientService.findOneById(id);
        if(optionalPatient.isEmpty()){
            return "redirect:/patients/main";
        }
        model.addAttribute("patient", optionalPatient.get());
        return "patient\\details";
    }

    @GetMapping("/details-with-visitings/{id}")
    public String detailsWithVisitingsPatient(Model model, @PathVariable("id") Long id){
        Optional<Patient> optionalPatient = patientService.findOneById(id);
        if(optionalPatient.isEmpty()){
            return "redirect:/patients/main";
        }
        List<Visiting> visitings = optionalPatient.get().getVisitings();
        model.addAttribute("visitings", visitings);
        model.addAttribute("patient", optionalPatient.get());
        return "patient\\details";
    }
    @GetMapping("/details-with-diseases/{id}")
    public String detailsWithDiseasesPatient(Model model, @PathVariable("id") Long id){
        Optional<Patient> optionalPatient = patientService.findOneById(id);
        if(optionalPatient.isEmpty()){
            return "redirect:/patients/main";
        }
        List<Visiting> visitings = optionalPatient.get().getVisitings();
        List<Disease> diseases = new ArrayList<>();
        for (Visiting visiting:
             visitings) {
            diseases.addAll(visiting.getDiseases());
        }
        model.addAttribute("diseases", diseases);
        model.addAttribute("patient", optionalPatient.get());
        return "patient\\details";
    }

    @GetMapping("/update/{id}")
    public String editPatient(Model model, @PathVariable("id") Long id){
        Optional<Patient> optionalPatient = patientService.findOneById(id);
        if(optionalPatient.isEmpty()){
            return "redirect:/patients/main";
        }
        model.addAttribute("patient", optionalPatient.get());
        return "patient\\edit";
    }
    @PostMapping("/update")
    public String editPatient(@ModelAttribute Patient patient, Model model){
        //Patient p = (Patient) model.getAttribute("patient");
        List<Visiting> visitings = patientService.findOneById(patient.getId()).get().getVisitings();
        patientService.update(patient.getId(), patient.getFirstName(),patient.getLastName(), patient.getPatr(), patient.getBirthDate(), patient.getArea(), patient.getCity(), patient.getHouse(), patient.getApartment(), visitings);
        return "redirect:/patients/main";
    }

    @GetMapping("/delete/{id}")
    public String deletePatient(Model model, @PathVariable("id") Long id){
        if(patientService.existsById(id)){
            patientService.delete(id);
        }
        return "redirect:/patients/main";
    }
}
