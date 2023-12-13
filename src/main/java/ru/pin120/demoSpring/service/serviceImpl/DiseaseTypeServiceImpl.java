package ru.pin120.demoSpring.service.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.pin120.demoSpring.models.Disease;
import ru.pin120.demoSpring.models.DiseaseType;
import ru.pin120.demoSpring.repository.DiseaseTypeRepository;
import ru.pin120.demoSpring.service.DiseaseTypeService;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class DiseaseTypeServiceImpl implements DiseaseTypeService {
    @Autowired
    private DiseaseTypeRepository diseaseTypeRepository;
    @Override
    public Collection<DiseaseType> findAll() {
        return (Collection<DiseaseType>) diseaseTypeRepository.findAll();
    }

    @Override
    public Optional<DiseaseType> findOneById(Long id) {
        return diseaseTypeRepository.findById(id);
    }

    @Override
    public void create(String name) {
        DiseaseType diseaseType = new DiseaseType();
        diseaseType.setName(name);
        diseaseTypeRepository.save(diseaseType);
    }

    @Override
    public void update(Long id, String name, List<Disease> diseases) {
        DiseaseType diseaseType = new DiseaseType();
        diseaseType.setId(id);
        diseaseType.setName(name);
        diseaseType.setDiseases(diseases);
        diseaseTypeRepository.save(diseaseType);
    }

    @Override
    public boolean delete(Long id) {
        DiseaseType diseaseType = diseaseTypeRepository.findById(id).get();
        Collection<Disease> diseases = diseaseType.getDiseases();
        if(diseases.isEmpty()){
            diseaseTypeRepository.delete(diseaseType);
            return true;
        }
        else return false;

    }

    @Override
    public boolean existsById(Long id) {
        return diseaseTypeRepository.existsById(id);
    }
}
