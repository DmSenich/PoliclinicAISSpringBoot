package ru.pin120.demoSpring.service.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.pin120.demoSpring.models.Doctor;
import ru.pin120.demoSpring.models.Specialty;
import ru.pin120.demoSpring.models.Visiting;
import ru.pin120.demoSpring.repository.DoctorRepository;
import ru.pin120.demoSpring.repository.repositoryImpl.DoctorRepositoryImpl;
import ru.pin120.demoSpring.repository.repositoryImpl.SpecialtyRepositoryImpl;
import ru.pin120.demoSpring.service.DoctorService;

import javax.print.Doc;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class DoctorServiceImpl implements DoctorService {
    @Autowired
    private DoctorRepository doctorRepository;

    //private SpecialtyRepositoryImpl specialtyRepository = new SpecialtyRepositoryImpl();
    @Override
    public Collection<Doctor> findAll() {
        return (Collection<Doctor>) doctorRepository.findAll();
    }

    @Override
    public Optional<Doctor> findOneById(Long id) {
        return doctorRepository.findById(id);
    }

//    @Override
//    public Collection<Doctor> findAllOfSpecialty(Long id) {
//        //return doctorRepository.findAllOfSpecialty(id);
//        return null;
//    }

//    @Override
//    public void updateSpecialty(int id, List<Specialty> specialties) {
//        Doctor doctor = findOneById(id);
//        doctor.setSpecialties(specialties);
//    }

    @Override
    public void create(String firstName, String lastName, String part, int workExp, String pathPhoto,byte[] photo, List<Specialty> specialties) {
        Doctor doctor = new Doctor();
        doctor.setFirstName(firstName);
        doctor.setLastName(lastName);
        doctor.setPatr(part);
        doctor.setWorkExp(workExp);
        doctor.setPathPhoto(pathPhoto);
        doctor.setPhoto(photo);
        doctor.setSpecialties(specialties);
        doctorRepository.save(doctor);
    }

    @Override
    public void update(Long id, String firstName, String lastName, String part, int workExp, String pathPhoto,byte[] photo, List<Specialty> specialties, List<Visiting> visitings) {
        Doctor doctor = new Doctor();
        doctor.setId(id);
        doctor.setFirstName(firstName);
        doctor.setLastName(lastName);
        doctor.setPatr(part);
        doctor.setWorkExp(workExp);
        doctor.setPathPhoto(pathPhoto);
        doctor.setPhoto(photo);
        doctor.setSpecialties(specialties);
        doctor.setVisitings(visitings);
        doctorRepository.save(doctor);
    }

    @Override
    public boolean delete(Long id) {
        Doctor doctor = doctorRepository.findById(id).get();
        Collection<Visiting> visitings = doctor.getVisitings();
        if(visitings.isEmpty()){
            doctorRepository.delete(doctor);
            return true;
        }
        else return false;
    }

    @Override
    public boolean existsById(Long id) {
        return doctorRepository.existsById(id);
    }
//    @Override
//    public String getStringSpecialtiesByDoctorId(Long id){
//        Doctor doctor = doctorRepository.findById(id).get();
//        List<Specialty> specialtiesList = doctor.getSpecialties();
//        List<String> specialties = new ArrayList<>();
//        for (Specialty sp:
//             specialtiesList) {
//            specialties.add(sp.getName());
//        }
//        return String.join(", ", specialties);
//    }
}
