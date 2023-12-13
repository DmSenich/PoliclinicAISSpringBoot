package ru.pin120.demoSpring.models;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

/**
 * Класс Пациент со свойствами <b>id</b>, <b>firstName</b>, <b>lastName</b>,<b>patr</b>,<b>birthDate</b>,<b>area</b>,<b>city</b>,<b>house</b>,<b>apartment</b>.
 * Данный класс является моделью для работы с базой данных
 * Конструкторы, геттеры и сеттеры реализованы с помощью lombok
 * @author Автор Сеничкин Д.О.
 */
@Entity
@Table(name = "Patients")
@Setter
@Getter
@Data
@NoArgsConstructor
//@AllArgsConstructor
public class Patient {
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
    /** Поле дата рождения */
    @Column(name = "birthdate")
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date birthDate;
    /** Поле область */
    @Column(name = "area")
    private String area;
    /** Поле город */
    @Column(name = "city")
    private String city;
    /** Поле дом */
    @Column(name = "house")
    private String house;
    /** Поле квартира */
    @Column(name = "apartment")
    private Integer apartment;
    @OneToMany(mappedBy = "patient")
    private List<Visiting> visitings;

//    public Patient(int id, String firstName, String lastName, String part, Date birthDate) {
//        this.id = id;
//        this.firstName = firstName;
//        this.lastName = lastName;
//        if(part != null){
//            this.part = part;
//        }
//        this.birthDate = birthDate;
//    }

//    /**
//     * Конструктор - создание нового экземпляра с заданными парамертами
//     * @param firstName
//     * @param lastName
//     * @param patr
//     * @param birthDate
//     * @param area
//     * @param city
//     * @param house
//     * @param apartment
//     */
//    public Patient(String firstName, String lastName, String patr, String birthDate, String area, String city, String house, int apartment) {
//        this.firstName = firstName;
//        this.lastName = lastName;
//        this.patr = patr;
//        this.birthDate = birthDate;
//        this.area = area;
//        this.city = city;
//        this.house = house;
//        this.apartment = apartment;
//    }

//    public int getId() {
//        return id;
//    }
//
//    public void setId(int id) {
//        this.id = id;
//    }
//
//    public String getFirstName() {
//        return firstName;
//    }
//
//    public void setFirstName(String firstName) {
//        this.firstName = firstName;
//    }
//
//    public String getLastName() {
//        return lastName;
//    }
//
//    public void setLastName(String lastName) {
//        this.lastName = lastName;
//    }
//
//    public String getPart() {
//        return part;
//    }
//
//    public void setPart(String part) {
//        this.part = part;
//    }
//
//    public Date getBirthDate() {
//        return birthDate;
//    }
//
//    public void setBirthDate(Date birthDate) {
//        this.birthDate = birthDate;
//    }
}
