package ru.pin120.demoSpring.models;

import lombok.*;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;


/**
 * Класс Доктор со свойствами <b>id</b>, <b>firstName</b>, <b>lastName</b>,<b>patr</b>,<b>workExp</b>,<b>pathPhoto</b>.
 * Данный класс является моделью для работы с базой данных
 * Конструкторы, геттеры и сеттеры реализованы с помощью lombok
 * @author Автор Сеничкин Д.О.
 */
@Entity
@Table(name = "Doctors")
@Setter
@Getter
@Data
@NoArgsConstructor
//@AllArgsConstructor
public class Doctor {
    /** Поле идентификатора */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /** Поле имя */
    @Column(name = "firstname")
    private String firstName;
    /** Поле фамилия */
    @Column(name = "lastname")
    private String lastName;
    /** Поле отчество */
    @Column(name = "patr")
    private String patr;
    /** Поле стаж */
    @Column(name = "workexp")
    private int workExp;
    /** Поле имя файла(фото) */
    @Column(name = "pathphoto")
    private String pathPhoto;
    @Column(name = "photo", columnDefinition = "BLOB")
    private byte[] photo;
    /** Список специальностей */
    @ManyToMany
    @JoinTable(name = "doctors_specialties",
    joinColumns = @JoinColumn(name = "idspecialty"),
    inverseJoinColumns = @JoinColumn(name = "iddoctor"))
    private List<Specialty> specialties;

    @OneToMany(mappedBy = "doctor")
    private List<Visiting> visitings;

    public String getStringSpecialties(){
        List<String> specialtiesString = new ArrayList<>();
        for (Specialty sp:
             specialties) {
            specialtiesString.add(sp.getName());
        }
        return String.join(", ", specialtiesString);
    }

    @Override
    public String toString() {
        return "Doctor{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", patr='" + patr + '\'' +
                ", workExp=" + workExp +
                ", pathPhoto='" + pathPhoto + '\'' +
                ", specialties=" + getStringSpecialties() +
                '}';
    }
    //    public Doctor(int id, String firstName, String lastName, String part, String specialties) {
//        this.id = id;
//        this.firstName = firstName;
//        this.lastName = lastName;
//        if(part != null){
//            this.part = part;
//        }
//        this.specialties = specialties;
//    }

//    public Doctor(String firstName, String lastName, String part, int workExp, List<Specialty> specialties) {
//        this.firstName = firstName;
//        this.lastName = lastName;
//        if(part != null){
//            this.patr = part;
//        }
//        this.workExp = workExp;
//        this.specialties = specialties;
//    }
//    public Doctor(int id, String firstName, String lastName, String part, int workExp) {
//        this.id = id;
//        this.firstName = firstName;
//        this.lastName = lastName;
//        if(part != null){
//            this.patr = part;
//        }
//        this.workExp = workExp;
//        //this.specialties = specialties;
//    }

//    /**
//     * Конструктор - создание нового экземпляра с заданными парамертами
//     * @param firstName
//     * @param lastName
//     * @param patr
//     * @param workExp
//     * @param pathPhoto
//     */
//    public Doctor(String firstName, String lastName, String patr, int workExp, String pathPhoto) {
//        this.firstName = firstName;
//        this.lastName = lastName;
//        if(patr != null){
//            this.patr = patr;
//        }
//        this.pathPhoto = pathPhoto;
//        this.workExp = workExp;
//    }

//    public int getId() {
//        return id;
//    }
//
//    public String getFirstName() {
//        return firstName;
//    }
//
//    public String getLastName() {
//        return lastName;
//    }
//
//    public String getPart() {
//        return part;
//    }
//
//    public String getSpecialties() {
//        return specialties;
//    }
//
//    public void setId(int id) {
//        this.id = id;
//    }
//
//    public void setFirstName(String firstName) {
//        this.firstName = firstName;
//    }
//
//    public void setLastName(String lastName) {
//        this.lastName = lastName;
//    }
//
//    public void setPart(String part) {
//        this.part = part;
//    }
//
//    public void setSpecialties(String specialties) {
//        this.specialties = specialties;
//    }

}
