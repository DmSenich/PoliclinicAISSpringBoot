package ru.pin120.demoSpring.repository;

import org.springframework.data.repository.CrudRepository;
import ru.pin120.demoSpring.models.DiseaseType;
import ru.pin120.demoSpring.models.Doctor;

import java.util.Collection;
import java.util.Optional;

public interface DiseaseTypeRepository extends CrudRepository<DiseaseType, Long> {
//    Collection<DiseaseType> findAll();
//    Optional<DiseaseType> findOneById(int id);
//    DiseaseType save(DiseaseType diseaseType);
//    void update(DiseaseType diseaseType);
//    void delete(DiseaseType diseaseType);
}
