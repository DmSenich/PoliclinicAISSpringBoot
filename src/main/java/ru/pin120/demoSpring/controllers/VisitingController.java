package ru.pin120.demoSpring.controllers;

import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.SneakyThrows;
import org.opencv.calib3d.StereoBM;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.pin120.demoSpring.models.Disease;
import ru.pin120.demoSpring.models.Doctor;
import ru.pin120.demoSpring.models.Patient;
import ru.pin120.demoSpring.models.Visiting;
import ru.pin120.demoSpring.service.serviceImpl.DoctorServiceImpl;
import ru.pin120.demoSpring.service.serviceImpl.PatientServiceImpl;
import ru.pin120.demoSpring.service.serviceImpl.VisitingServiceImpl;

import javax.print.Doc;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/visitings")
public class VisitingController {
    @Autowired
    private VisitingServiceImpl visitingService;
    @Autowired
    private DoctorServiceImpl doctorService;
    @Autowired
    private PatientServiceImpl patientService;
    private long selectIdDoctor = -1;
    private long selectIdPatient = -1;
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date selectDate = null;
    private SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

    @GetMapping("/main")
    public String getVisitings(Model model){
        List<Visiting> visitings =
                (List<Visiting>)visitingService.findAll();
        model.addAttribute("visitings", visitings);
        model.addAttribute("iddoctor", -1);
        model.addAttribute("idpatient", -1);
        return "visiting\\main";
    }
//    @GetMapping("/new")
//    public String newVisitings(Model model){
//        model.addAttribute("visiting", new Visiting());
//        return "visiting\\new";
//    }
//    @PostMapping("/new")
//    public String newVisiting(@ModelAttribute Visiting visiting, Model model){
//        visitingService.create(visiting.getDoctor(), visiting.getPatient(), visiting.getDate());
//        return "redirect:/visitings/main";
//    }
//    @GetMapping("/new")
//    public String chDoc(Model model){
//        List<Doctor> doctors = doctorService.findAll().stream().toList();
//        model.addAttribute("doctors", doctors);
////        if(idpat == -1){
//        model.addAttribute("idpatient", -1);
//
////        else{
////            model.addAttribute("idpatient", idpat);
////        }
//        return "visiting\\choice-doctor";
//    }
//    @PostMapping("/new")
//    public String chDocr(@ModelAttribute("iddoctor") long iddoc, Model model){
////        Long iddoc = (Long)model.getAttribute("iddoctor");
//        model.addAttribute("iddoctor",iddoc);
//        return "redirect:/visitings/choice-patient/"+iddoc;

//        visiting.setDoctor(doctor);
        //model.addAttribute("doctor", doctor);
//        if(visiting.getPatient() != null){
//            return "visiting\\choice-date";
//        }
//        return "visiting\\choice-patient";
//    }

    @GetMapping("/choice-doctor")
    public String chooseDoctor(Model model){
        selectDate = null;
        selectIdPatient = -1;
        selectIdDoctor = -1;
        List<Doctor> doctors = doctorService.findAll().stream().toList();
        model.addAttribute("doctors", doctors);
//        if(idpat == -1){
//        model.addAttribute("idpatient", -1);

//        else{
//            model.addAttribute("idpatient", idpat);
//        }
        return "visiting\\choice-doctor";
    }
    @PostMapping("/choice-doctor")
    public String chooseDoctor(@ModelAttribute("iddoctor") long iddoc, Model model){
//        Long iddoc = (Long)model.getAttribute("iddoctor");
        //model.addAttribute("iddoctor",iddoc);

        selectIdDoctor = iddoc;
        return "redirect:/visitings/choice-patient";

//        visiting.setDoctor(doctor);
        //model.addAttribute("doctor", doctor);
//        if(visiting.getPatient() != null){
//            return "visiting\\choice-date";
//        }
//        return "visiting\\choice-patient";
    }

    @GetMapping("/choice-patient")
    public String choosePatient(Model model){
        List<Patient> patients = patientService.findAll().stream().toList();
        model.addAttribute("patients", patients);
//        if(iddoc == -1){
//        model.addAttribute("iddoctor", iddoc);

//        else{
//            model.addAttribute("iddoctor", iddoc);
//        }
        return "visiting\\choice-patient";
    }

    @PostMapping("/choice-patient")
    public String choosePatient(@ModelAttribute("idpatient") long idpat, Model model){
//        Long idpat = (Long)model.getAttribute("idpatient");
//        Patient patient = patientService.findOneById(idpat).get();
//        visiting.setPatient(patient);
//        model.addAttribute("idpatient", idpat);
//        model.addAttribute("iddoctor", iddoc);
        selectIdPatient = idpat;
        return "redirect:/visitings/choice-date";
    }


    @GetMapping("/choice-date")
    public String chooseDate(Model model){
//        model.addAttribute("iddoctor", iddoc);
//        model.addAttribute("idpatient", idpat);
        Date date = new Date();
        model.addAttribute("date", date);
        return "visiting\\choice-date";
    }
    @SneakyThrows
    @PostMapping("/choice-date")
    public String chooseDate(@ModelAttribute("date") String dateString, Model model){
//        Doctor doctor = doctorService.findOneById(iddoc).get();
//        Patient patient = patientService.findOneById(idpat).get();
//        model.addAttribute("doctor", doctor);
//        model.addAttribute("patient", patient);
        selectDate = formatter.parse(dateString);
        return "redirect:/visitings/confirm-create";
    }

    @GetMapping("/confirm-create")
    public String confirmCreate(Model model){
        Doctor doctor = doctorService.findOneById(selectIdDoctor).get();
        Patient patient = patientService.findOneById(selectIdPatient).get();
        //doctor.setSpecialties(null);
        Visiting visiting = new Visiting();
        visiting.setDoctor(doctor);
        visiting.setPatient(patient);
        visiting.setDate(selectDate);
        //model.addAttribute("doctor", doctor);
        //model.addAttribute("patient", patient);
        model.addAttribute("FIODoctor", String.join(" ",doctor.getLastName(), doctor.getFirstName(),doctor.getPatr()));
        model.addAttribute("FIOPatient", String.join(" ", patient.getLastName(), patient.getFirstName(), patient.getPatr()));
        model.addAttribute("date", selectDate);

        model.addAttribute("visiting", visiting);
        return "visiting\\confirm-create";
    }
    @PostMapping("/confirm-create")
    public String confirmCreate(@ModelAttribute Visiting visiting, Model model){
        visitingService.create(visiting.getDoctor(), visiting.getPatient(), visiting.getDate());
//        String direct;
//        if(((Doctor)model.getAttribute("doctor")) == null){
//            model.addAttribute("visiting", new Visiting());
//            direct = "visiting\\choice-doctor";
//        }
//        else{
//            direct = "visiting\\choice-date";
//        }
        selectIdDoctor = -1;
        selectIdPatient = -1;
        return "redirect:/visitings/main";
    }


    @GetMapping("/details/{id}")
    public String detailsVisiting(Model model, @PathVariable("id") Long id){
        Optional<Visiting> optionalVisiting = visitingService.findOneById(id);
        if(optionalVisiting.isEmpty()){
            return "redirect:/visitings/main";
        }
        model.addAttribute("visiting", optionalVisiting.get());
        model.addAttribute("FIODoctor", optionalVisiting.get().getFIODoctor());
        model.addAttribute("FIOPatient", optionalVisiting.get().getFIOPatient());
        return "visiting\\details";
    }

//    @GetMapping("/update/{id}")
//    public String editVisiting(Model model, @PathVariable("id") Long id){
//        Optional<Visiting> optionalVisiting = visitingService.findOneById(id);
//        if(optionalVisiting.isEmpty()){
//            return "redirect:/visitings/main";
//        }
//        model.addAttribute("visiting", optionalVisiting.get());
//        return "visiting\\edit";
//    }
//    @PostMapping("/update")
//    public String editVisiting(@ModelAttribute Visiting visiting, Model model){
//        List<Disease> diseases = visitingService.findOneById(visiting.getId()).get().getDiseases();
//        visitingService.update(visiting.getId(), visiting.getDoctor(),visiting.getPatient(),visiting.getDate(),diseases);
//        return "redirect:/visitings/main";
//    }

    @GetMapping("/delete/{id}")
    public String deleteVisiting(Model model, @PathVariable("id") Long id){
        if(visitingService.existsById(id)){
            visitingService.delete(id);
        }
        return "redirect:/visitings/main";
    }
}
