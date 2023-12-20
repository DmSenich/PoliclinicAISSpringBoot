package ru.pin120.demoSpring.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.pin120.demoSpring.models.Doctor;
import ru.pin120.demoSpring.models.Patient;
import ru.pin120.demoSpring.models.Specialty;
import ru.pin120.demoSpring.models.Visiting;
import ru.pin120.demoSpring.service.serviceImpl.DoctorServiceImpl;
import ru.pin120.demoSpring.service.serviceImpl.SpecialtyServiceImpl;

import javax.naming.spi.DirectoryManager;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.net.SocketException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/doctors")
public class DoctorController {
    private static String PHOTO_DIR = System.getProperty("user.dir") + "\\photos";
    private static String REPORTS_DIR = System.getProperty("user.dir") + "\\reports";
    private @DateTimeFormat(pattern = "yyyy-MM-dd") Date minDate = null, maxDate = null;
    private SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    private static String defaultPhoto = "defaultPhoto.jpeg";
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
    public String newDoctor(@ModelAttribute Doctor doctor,@RequestPart(name = "file", required = false) MultipartFile file, Model model){
//        MultipartFile file = doctor.getPhotoFile();
//        byte[] photo = null;
//        try{
//            if(file != null && !file.isEmpty()){
//                photo = file.getBytes();
//            }
//        }catch (IOException exception){
//            System.out.println(exception.getMessage());
//        }
        byte[] photo = null;
        try{
            File dirPhoto = new File(PHOTO_DIR);
            if(!dirPhoto.exists()){
                dirPhoto.mkdir();
            }
            Path filePath;
            StringBuilder filename = new StringBuilder();
            if(file != null && !file.isEmpty()){
                filePath = Paths.get(PHOTO_DIR, file.getOriginalFilename());
                File newFile = new File(filePath.toString());
                newFile.createNewFile();
                Files.write(filePath, file.getBytes());
                doctor.setPathPhoto(file.getOriginalFilename());
            }
            else{
                doctor.setPathPhoto(defaultPhoto);
            }
        }catch (IOException exception){
            System.out.println(exception.getMessage());
        }

        doctorService.create(doctor.getFirstName(),doctor.getLastName(),doctor.getPatr(), doctor.getWorkExp(),doctor.getPathPhoto(), new ArrayList<Specialty>());
        return "redirect:/doctors/main";
    }

    @GetMapping("/details/{id}")
    public String detailsDoctor(Model model, @PathVariable("id") Long id){
        Optional<Doctor> optionalDoctor = doctorService.findOneById(id);
        if(optionalDoctor.isEmpty()){
            return "redirect:/doctors/main";
        }
        Path fullPathPhoto = Path.of(PHOTO_DIR,optionalDoctor.get().getPathPhoto());
        byte[] photo;
        try {
            photo = Files.readAllBytes(fullPathPhoto);
        } catch (IOException e) {
            System.out.println(e.getMessage());
            photo = null;
        }
        optionalDoctor.get().setPhoto(photo);
        String[] fullpath = optionalDoctor.get().getPathPhoto().split("\\.");
        String type = fullpath[fullpath.length - 1];
        String encodedPhoto = Base64.getEncoder().encodeToString(photo);
        model.addAttribute("type", type);
        model.addAttribute("encodedPhoto", encodedPhoto);
        model.addAttribute("doctor", optionalDoctor.get());
        return "doctor\\details";
    }
    @GetMapping("/details/{id}/visitings")
    public String doctorVisitings(Model model, @PathVariable("id") Long id){
        minDate = null;
        maxDate = null;
        Doctor doctor = doctorService.findOneById(id).get();
        List<Visiting> visitings = doctor.getVisitings();
        model.addAttribute("ofDoctor", true);
        model.addAttribute("doctorId", id);
        model.addAttribute("visitings", visitings);
        return "visiting\\main";
    }
    @GetMapping("/details/{id}/visitings/filter")
    public String filter(@RequestParam(name = "startDate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
                         @RequestParam(name = "endDate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate,
                         Model model, @PathVariable("id") Long id){
        minDate = startDate;
        maxDate = endDate;
        Doctor doctor = doctorService.findOneById(id).get();
        List<Visiting> allVisitings = doctor.getVisitings();
        List<Visiting> visitings = new ArrayList<>(allVisitings);
        if(startDate != null && endDate != null){
            visitings = allVisitings.stream().filter(visiting -> !visiting.getDate().before(startDate) && !visiting.getDate().after(endDate)).collect(Collectors.toList());
        }
        else{
            if(startDate == null && endDate == null){
                return "redirect:/doctors/details/"+doctor.getId()+"/visitings";
            }
            if(startDate != null){
                visitings = allVisitings.stream().filter(visiting -> !visiting.getDate().before(startDate)).collect(Collectors.toList());
            }
            if(endDate != null){
                visitings = allVisitings.stream().filter(visiting -> !visiting.getDate().after(endDate)).collect(Collectors.toList());
            }
        }

        model.addAttribute("ofDoctor", true);
        model.addAttribute("doctorId", id);
        model.addAttribute("visitings", visitings);
        return "visiting\\main";
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
    public String editDoctor(@ModelAttribute Doctor doctor, @RequestPart(name="file", required = false) MultipartFile file, Model model){
        //MultipartFile docfile = doctor.getPhotoFile();
        byte[] photo = null;
        try{
            if(file != null && !file.isEmpty()){
                StringBuilder filename = new StringBuilder();
                File dirPhoto = new File(PHOTO_DIR);
                if(!dirPhoto.exists()){
                    dirPhoto.mkdir();
                }
                Path filePath = Paths.get(PHOTO_DIR, file.getOriginalFilename());
                filename.append(file.getOriginalFilename());
                File newFile = new File(filePath.toString());
                newFile.createNewFile();
                Files.write(filePath, file.getBytes());
                doctor.setPathPhoto(file.getOriginalFilename());
            }
        }catch (IOException exception){
            System.out.println(exception.getMessage());
        }
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

    @GetMapping("/details/{id}/visitings/generate-doc")
    public ResponseEntity<Resource> generateDoc(Model model, @PathVariable("id") Long id) throws FileNotFoundException {
        Doctor doctor = doctorService.findOneById(id).get();
        if(doctor == null){
            return null;
        }
        List<Visiting> allVisitings = doctor.getVisitings();
        List<Visiting> visitings = new ArrayList<>(allVisitings);
        if(minDate != null && maxDate != null){
            visitings = allVisitings.stream().filter(visiting -> !visiting.getDate().before(minDate) && !visiting.getDate().after(maxDate)).collect(Collectors.toList());
        }
        else{
            if(minDate != null){
                visitings = allVisitings.stream().filter(visiting -> !visiting.getDate().before(minDate)).collect(Collectors.toList());
            }
            if(maxDate != null){
                visitings = allVisitings.stream().filter(visiting -> !visiting.getDate().after(maxDate)).collect(Collectors.toList());
            }
        }
        if(visitings.isEmpty()){
            return null;
        }
        StringBuilder content = new StringBuilder();
        int count = 0;
        content.append("Доктор: "+ visitings.stream().findFirst().get().getFIODoctor() + "\n");
        content.append("Диапазон дат:");
        if(minDate == null && maxDate == null){
            content.append("неограниченный\n");
        }
        else{
            if(minDate != null){
                content.append("от " + formatter.format(minDate) + " ");
            }
            if(maxDate != null){
                content.append("до " + formatter.format(maxDate));
            }
            content.append("\n");
        }
        content.append("Общее количество визитов: " + visitings.stream().count() + " \n");
        for(Visiting visiting : visitings){
            count++;
            content.append(count + ") Дата: " + visiting.getDate() + "\n");
            content.append("Пациент:" + visiting.getFIOPatient() + "\n");
        }
        String fileName = "VisitingsOfDoctor" + ".txt";
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
