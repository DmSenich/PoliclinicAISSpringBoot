package ru.pin120.demoSpring.service.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.pin120.demoSpring.models.Disease;
import ru.pin120.demoSpring.models.Doctor;
import ru.pin120.demoSpring.models.Patient;
import ru.pin120.demoSpring.models.Visiting;
import ru.pin120.demoSpring.repository.VisitingRepository;
import ru.pin120.demoSpring.service.VisitingService;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class VisitingServiceImpl implements VisitingService {
    @Autowired
    private VisitingRepository visitingRepository;
    @Override
    public Collection<Visiting> findAll() {
        return (Collection<Visiting>) visitingRepository.findAll();
    }

    @Override
    public Optional<Visiting> findOneById(Long id) {
        return visitingRepository.findById(id);
    }

    @Override
    public void create(Doctor doctor, Patient patient, Date date) {
        Visiting visiting = new Visiting();
        visiting.setDoctor(doctor);
        visiting.setPatient(patient);
        visiting.setDate(date);
        visitingRepository.save(visiting);
    }

    @Override
    public void update(Long id, Doctor doctor, Patient patient, Date date, List<Disease> diseases) {
        Visiting visiting = new Visiting();
        visiting.setId(id);
        visiting.setDoctor(doctor);
        visiting.setPatient(patient);
        visiting.setDate(date);
        visiting.setDiseases(diseases);
    }

    @Override
    public boolean delete(Long id) {
        Visiting visiting = visitingRepository.findById(id).get();
        Collection<Disease> diseases = visiting.getDiseases();
        if(diseases.isEmpty()){
            visitingRepository.delete(visiting);
            return true;
        }
        else return false;
    }

    @Override
    public boolean existsById(Long id) {
        return visitingRepository.existsById(id);
    }
}
