package ru.pin120.demoSpring.service;


import ru.pin120.demoSpring.models.Doctor;
import ru.pin120.demoSpring.models.Specialty;
import ru.pin120.demoSpring.models.Visiting;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

/**
 * Интерфейс Сервиса Доктора
 * Данный интерфейс необходим для реализации класса для работы с репозиторием Доктора
 * @author Автор Сеничкин Д.О.
 */
public interface DoctorService {
    Collection<Doctor> findAll();
    Optional<Doctor> findOneById(Long id);
//    void updateSpecialty(int id, List<Specialty> specialties);
//    Collection<Doctor> findAllOfSpecialty(Long id);
    void create(String firstName, String lastName, String part, int workExp, String pathPhoto, List<Specialty> specialties) ;
    void update(Long id, String firstName, String lastName, String part, int workExp, String pathPhoto, List<Specialty> specialties, List<Visiting> visitings) ;
    boolean delete(Long id);

    boolean existsById(Long id);
//    String getStringSpecialtiesByDoctorId(Long id);
}
