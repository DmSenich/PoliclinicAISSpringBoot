package ru.pin120.demoSpring.repository;



import org.springframework.data.repository.CrudRepository;
import ru.pin120.demoSpring.models.Doctor;
import ru.pin120.demoSpring.models.Specialty;

import javax.print.Doc;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

/**
 * Интерфейс Репозитория Доктора
 * Данный интерфейс необходим для реализации класса для работы с базой данных
 * @author Автор Сеничкин Д.О.
 */
public interface DoctorRepository extends CrudRepository<Doctor, Long>{
//    Collection<Doctor> findAll();
//    Optional<Doctor> findOneById(int id);
//    Collection<Doctor> findAllOfSpecialty(int id);
//    Doctor save(Doctor doctor);
////    Doctor updateSpecialty(int id,List<Specialty> specialties);
//    void delete(Doctor doctor);
//    void update(Doctor doctor);
}
