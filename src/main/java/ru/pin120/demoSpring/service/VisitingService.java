package ru.pin120.demoSpring.service;

import ru.pin120.demoSpring.models.Disease;
import ru.pin120.demoSpring.models.Doctor;
import ru.pin120.demoSpring.models.Patient;
import ru.pin120.demoSpring.models.Visiting;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * Класс реализации Сервиса Приемов
 * Данный класс необходим для работы с репозиторием
 * @author Автор Сеничкин Д.О.
 */
public interface VisitingService {
    Collection<Visiting> findAll();
    Optional<Visiting> findOneById(Long id);
    void create(Doctor doctor, Patient patient, Date date);
    void update(Long id, Doctor doctor, Patient patient, Date date, List<Disease> diseases);
    boolean delete(Long id);
    boolean existsById(Long id);
}

