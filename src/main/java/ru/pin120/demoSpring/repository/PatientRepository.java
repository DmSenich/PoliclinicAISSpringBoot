package ru.pin120.demoSpring.repository;


import org.springframework.data.repository.CrudRepository;
import ru.pin120.demoSpring.models.Doctor;
import ru.pin120.demoSpring.models.Patient;

import java.util.Collection;
import java.util.Optional;

/**
 * Интерфейс Репозитория Пациента
 * Данный интерфейс необходим для реализации класса для работы с базой данных
 * @author Автор Сеничкин Д.О.
 */
public interface PatientRepository extends CrudRepository<Patient, Long> {
//    Collection<Patient> findAll();
//    Optional<Patient> findOneById(int id);
//    Patient save(Patient patient);
//    void delete(Patient patient);
//    void update(Patient patient);
}
