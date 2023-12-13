package ru.pin120.demoSpring.models;

import lombok.*;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "Specialties")
@Setter
@Getter
//@AllArgsConstructor
@NoArgsConstructor
@Data
public class Specialty {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name")
    private String name;
    @ManyToMany
    @JoinTable(name = "doctors_specialties",
            joinColumns = @JoinColumn(name = "iddoctor"),
            inverseJoinColumns = @JoinColumn(name = "idspecialty"))
    private List<Doctor> doctors;

    @Override
    public String toString() {
        return "Specialty{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
