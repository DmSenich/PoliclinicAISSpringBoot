package ru.pin120.demoSpring.repository;

import org.springframework.data.repository.CrudRepository;
import ru.pin120.demoSpring.models.Disease;
import ru.pin120.demoSpring.models.DiseaseType;

import java.util.Collection;
import java.util.Optional;

public interface DiseaseRepository extends CrudRepository<Disease, Long>  {
//    Collection<Disease> findAll();
//    Optional<Disease> findOneById(int id);
//    Collection<Disease> findAllOfVisiting(int id);
//    Disease save(Disease disease);
//    void delete(Disease disease);
    //void update(Disease disease);
}
