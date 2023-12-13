package ru.pin120.demoSpring.repository;


import org.springframework.data.repository.CrudRepository;
import ru.pin120.demoSpring.models.Visiting;

import java.util.Collection;
import java.util.Optional;

/**
 * Интерфейс Репозитория Доктора
 * Данный интерфейс необходим для реализации класса для работы с базой данных
 * @author Автор Сеничкин Д.О.
 */
public interface VisitingRepository extends CrudRepository<Visiting, Long> {
//    Collection<Visiting> findAll();
//    Optional<Visiting> findOneById(int id);
//    Visiting save(Visiting visiting);
//    void delete(Visiting visiting);
//    void update(Visiting visiting);
}
