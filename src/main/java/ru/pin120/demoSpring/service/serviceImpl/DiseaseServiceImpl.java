package ru.pin120.demoSpring.service.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.pin120.demoSpring.models.Disease;
import ru.pin120.demoSpring.models.DiseaseType;
import ru.pin120.demoSpring.models.Visiting;
import ru.pin120.demoSpring.repository.DiseaseRepository;
import ru.pin120.demoSpring.service.DiseaseService;

import java.util.Collection;
import java.util.Optional;

@Service
public class DiseaseServiceImpl implements DiseaseService {
    @Autowired
    private DiseaseRepository diseaseRepository;
    @Override
    public Collection<Disease> findAll() {
        return (Collection<Disease>) diseaseRepository.findAll();
    }

    @Override
    public Optional<Disease> findOneById(Long id) {
        return diseaseRepository.findById(id);
    }

    @Override
    public void create(DiseaseType diseaseType, String description, Visiting visiting) {
        Disease disease = new Disease();
        disease.setDiseaseType(diseaseType);
        disease.setVisiting(visiting);
        disease.setDescription(description);
        diseaseRepository.save(disease);
    }

    @Override
    public void update(Long id, DiseaseType diseaseType, String description, Visiting visiting) {
        Disease disease = new Disease();
        disease.setId(id);
        disease.setDiseaseType(diseaseType);
        disease.setVisiting(visiting);
        disease.setDescription(description);
        diseaseRepository.save(disease);
    }

    @Override
    public boolean delete(Long id) {
        Disease disease = diseaseRepository.findById(id).get();
        diseaseRepository.delete(disease);
        return true;
    }

    @Override
    public boolean existsById(Long id) {
        return diseaseRepository.existsById(id);
    }

}
