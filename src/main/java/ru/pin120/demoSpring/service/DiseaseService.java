package ru.pin120.demoSpring.service;


import ru.pin120.demoSpring.models.Disease;
import ru.pin120.demoSpring.models.DiseaseType;
import ru.pin120.demoSpring.models.Visiting;

import java.util.Collection;
import java.util.Optional;

public interface DiseaseService {
    Collection<Disease> findAll();
    Optional<Disease> findOneById(Long id);
    //Collection<Disease> findAllOfVisiting(int id);
    void create(DiseaseType diseaseType, String description, Visiting visiting);
    void update(Long id,DiseaseType diseaseType, String description, Visiting visiting);
    boolean delete(Long id);
    boolean existsById(Long id);
}
