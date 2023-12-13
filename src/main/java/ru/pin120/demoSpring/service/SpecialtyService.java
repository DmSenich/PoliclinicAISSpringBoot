package ru.pin120.demoSpring.service;

import ru.pin120.demoSpring.models.Specialty;

import java.util.Collection;
import java.util.Optional;

public interface SpecialtyService {
    Collection<Specialty> findAll();
    Optional<Specialty> findOneById(Long id);
    void create(String name);
    void update(Long id, String name);
    boolean delete(Long id);

    boolean existsById(Long id);
}
