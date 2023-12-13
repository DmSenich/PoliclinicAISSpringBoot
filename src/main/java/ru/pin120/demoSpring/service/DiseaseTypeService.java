package ru.pin120.demoSpring.service;


import ru.pin120.demoSpring.models.Disease;
import ru.pin120.demoSpring.models.DiseaseType;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface DiseaseTypeService {
    Collection<DiseaseType> findAll();
    Optional<DiseaseType> findOneById(Long id);
    void create(String name);
    void update(Long id, String name, List<Disease> diseases);
    boolean delete(Long id);
    boolean existsById(Long id);
}
