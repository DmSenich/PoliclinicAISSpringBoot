package ru.pin120.demoSpring.models;

import lombok.*;
import jakarta.persistence.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

/**
 * Класс Прием со свойствами <b>id</b>, <b>doctorId</b>, <b>patientId</b>,<b>date</b>.
 * Данный класс является моделью для работы с базой данных
 * Конструкторы, геттеры и сеттеры реализованы с помощью lombok
 * @author Автор Сеничкин Д.О.
 */
@Entity
@Table(name = "Visitings")
@Setter
@Getter
@NoArgsConstructor
@Data
//@AllArgsConstructor
public class Visiting {
    /** Поле идентификатора */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
//    /** Поле идентификатора доктора */
//    private int doctorId;
    @ManyToOne
    @JoinColumn(name = "iddoctor")
    private Doctor doctor;
//    /** Поле идентификатора пациента */
//    private int patientId;
    @ManyToOne
    @JoinColumn(name = "idpatient")
    private Patient patient;
    @OneToMany(mappedBy = "visiting")
    private List<Disease> diseases;

    /** Поле даты приема */
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date date;

    public String getFIODoctor(){
        if(doctor == null) return "";
        String fio = String.join(" ", doctor.getLastName(), doctor.getFirstName(), doctor.getPatr());
        return fio;
    }
    public String getFIOPatient(){
        if(patient == null) return "";
        String fio = String.join(" ", patient.getLastName(), patient.getFirstName(), patient.getPatr());
        return fio;
    }

    @Override
    public String toString() {
        return "Visiting{" +
                "id=" + id +
                ", doctor=" + getFIODoctor() +
                ", patient=" + getFIOPatient() +
                ", date=" + date +
                '}';
    }
    //    public Visiting(int id, int doctorId, int patientId, String disease, Date date) {
//        this.id = id;
//        this.doctorId = doctorId;
//        this.patientId = patientId;
//        if(disease != null){
//            this.disease = disease;
//        }
//        this.date = date;
//    }

//    public Visiting(Doctor doctor, Patient patient, String date, List<Disease> diseases) {
//        this.doctor = doctor;
//        this.doctorId = doctor.getId();
//        this.patient = patient;
//        this.patientId = patient.getId();
//        this.diseases = diseases;
//        this.date = date;
//    }
//    public Visiting(int id, Doctor doctor, Patient patient, String date, List<Disease> diseases) {
//        this.id = id;
//        this.doctor = doctor;
//        this.doctorId = doctor.getId();
//        this.patient = patient;
//        this.patientId = patient.getId();
//        this.diseases = diseases;
//        this.date = date;
//    }

//    /**
//     * Конструктор - создание нового экземпляра с заданными парамертами
//     * @param doctorId
//     * @param patientId
//     * @param date
//     */
//    public Visiting(int doctorId, int patientId, String date) {
//        this.doctorId = doctorId;
//        this.patientId = patientId;
//        //this.diseases = diseases;
//        this.date = date;
//    }
//    public Visiting(int id, int doctorId, int patientId, String date) {
//        this.id = id;
//        this.doctorId = doctorId;
//        this.patientId = patientId;
//        //this.diseases = diseases;
//        this.date = date;
//    }


//    public int getId() {
//        return id;
//    }
//
//    public int getDoctorId() {
//        return doctorId;
//    }
//
//    public int getPatientId() {
//        return patientId;
//    }
//
//    public String getDisease() {
//        return disease;
//    }
//    public Date getDate(){
//        return date;
//    }
}
