package ru.pin120.demoSpring.service;

import ru.pin120.demoSpring.models.Patient;
import ru.pin120.demoSpring.models.Visiting;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * Класс реализации Сервиса Пациента
 * Данный класс необходим для работы с репозиторием
 * @author Автор Сеничкин Д.О.
 */
public interface PatientService {
    Collection<Patient> findAll();
    Optional<Patient> findOneById(Long id);
    void create(String firstName, String lastName, String part, Date birthDate, String area, String city, String house, Integer apartment);

    void update(Long id, String firstName, String lastName, String part, Date birthDate, String area, String city, String house, Integer apartment, List<Visiting> visitings);
    boolean delete(Long id);
    boolean existsById(Long id);
}
