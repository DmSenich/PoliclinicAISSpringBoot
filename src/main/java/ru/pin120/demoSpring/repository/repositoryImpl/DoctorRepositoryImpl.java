package ru.pin120.demoSpring.repository.repositoryImpl;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;
import ru.pin120.demoSpring.DBHelper;
import ru.pin120.demoSpring.models.Doctor;
import ru.pin120.demoSpring.models.Specialty;
import ru.pin120.demoSpring.repository.DoctorRepository;

import java.sql.Connection;
import java.sql.Statement;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

//@Component
public class DoctorRepositoryImpl  {
//    private Connection connection;
//    @PostConstruct
//    public void init(){
//        connection = DBHelper.getConnection();
//    }
//
//    @Override
//    public Collection<Doctor> findAll() {
//        Statement statement = connection.createStatement();
//        return ;
//    }
//
//    @Override
//    public Optional<Doctor> findOneById(int id) {
//        return Optional.empty();
//    }
//
//    @Override
//    public Collection<Doctor> findAllOfSpecialty(int id) {
//        return null;
//    }
//
//    @Override
//    public Doctor save(Doctor doctor) {
//        return null;
//    }
//
////    @Override
////    public Doctor updateSpecialty(int id, List<Specialty> specialties) {
////        return null;
////    }
//
//    @Override
//    public void delete(Doctor doctor) {
//
//    }
//
//    @Override
//    public void update(Doctor doctor) {
//
//    }
}
