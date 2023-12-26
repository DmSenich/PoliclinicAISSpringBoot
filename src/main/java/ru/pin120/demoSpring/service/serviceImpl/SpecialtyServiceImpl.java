package ru.pin120.demoSpring.service.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.pin120.demoSpring.models.Doctor;
import ru.pin120.demoSpring.models.Specialty;
import ru.pin120.demoSpring.repository.DoctorRepository;
import ru.pin120.demoSpring.repository.SpecialtyRepository;
import ru.pin120.demoSpring.service.SpecialtyService;

import java.sql.Connection;
import java.util.Collection;
import java.util.Optional;

@Service
public class SpecialtyServiceImpl implements SpecialtyService {
    @Autowired
    private SpecialtyRepository specialtyRepository;

    @Override
    public Collection<Specialty> findAll() {
        return (Collection<Specialty>) specialtyRepository.findAll();
    }

    @Override
    public Optional<Specialty> findOneById(Long id) {
        return specialtyRepository.findById(id);
    }


    @Override
    public void create(String name) {
        Specialty specialty = new Specialty();
        specialty.setName(name);
        specialtyRepository.save(specialty);
    }

    @Override
    public void update(Long id, String name) {
        Specialty specialty = new Specialty();
        specialty.setId(id);
        specialty.setName(name);
        specialtyRepository.save(specialty);
    }

    @Override
    public boolean delete(Long id) {
        Specialty specialty = specialtyRepository.findById(id).get();
        Collection<Doctor> doctors = specialty.getDoctors();
        if(doctors.isEmpty()){
            specialtyRepository.delete(specialty);
            return true;
        }
        else return false;
    }

    @Override
    public boolean existsById(Long id) {
        return specialtyRepository.existsById(id);
    }
}
