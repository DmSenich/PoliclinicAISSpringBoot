package ru.pin120.demoSpring.service.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.pin120.demoSpring.models.Doctor;
import ru.pin120.demoSpring.models.Patient;
import ru.pin120.demoSpring.models.Visiting;
import ru.pin120.demoSpring.repository.PatientRepository;
import ru.pin120.demoSpring.service.PatientService;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class PatientServiceImpl implements PatientService {
    @Autowired
    private PatientRepository patientRepository;

    @Override
    public Collection<Patient> findAll() {
        return (Collection<Patient>) patientRepository.findAll();
    }

    @Override
    public Optional<Patient> findOneById(Long id) {
        return patientRepository.findById(id);
    }

    @Override
    public void create(String firstName, String lastName, String part, Date birthDate, String area, String city, String house, Integer apartment) {
        Patient patient = new Patient();
        patient.setFirstName(firstName);
        patient.setLastName(lastName);
        patient.setPatr(part);
        patient.setBirthDate(birthDate);
        patient.setArea(area);
        patient.setCity(city);
        patient.setHouse(house);
        patient.setApartment(apartment);
        patientRepository.save(patient);
    }

    @Override
    public void update(Long id, String firstName, String lastName, String part, Date birthDate, String area, String city, String house, Integer apartment, List<Visiting> visitings) {
        Patient patient = new Patient();
        patient.setId(id);
        patient.setFirstName(firstName);
        patient.setLastName(lastName);
        patient.setPatr(part);
        patient.setBirthDate(birthDate);
        patient.setArea(area);
        patient.setCity(city);
        patient.setHouse(house);
        patient.setApartment(apartment);
        patient.setVisitings(visitings);
        patientRepository.save(patient);
    }

    @Override
    public boolean delete(Long id) {
        Patient patient = patientRepository.findById(id).get();
        Collection<Visiting> visitings = patient.getVisitings();
        if(visitings.isEmpty()){
            patientRepository.delete(patient);
            return true;
        }
        else return false;

    }

    @Override
    public boolean existsById(Long id) {
        return patientRepository.existsById(id);
    }
}
