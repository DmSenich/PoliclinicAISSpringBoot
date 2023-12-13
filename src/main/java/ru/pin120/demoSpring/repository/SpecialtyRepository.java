package ru.pin120.demoSpring.repository;


import org.springframework.data.repository.CrudRepository;
import ru.pin120.demoSpring.models.Specialty;

import java.util.Collection;
import java.util.Locale;
import java.util.Optional;

public interface SpecialtyRepository extends CrudRepository<Specialty, Long>{
//    Collection<Specialty> findAll();
//    Optional<Specialty> findOneById(int id);
//    Collection<Specialty> findAllOfDoctor(int id);
//    Specialty save(Specialty specialty);
//    void delete(Specialty specialty);
//    void update(Specialty specialty);
}
