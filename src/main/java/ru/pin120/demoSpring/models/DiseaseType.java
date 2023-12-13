package ru.pin120.demoSpring.models;

import lombok.*;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "Diseasetypes")
@Setter
@Getter
@Data
@NoArgsConstructor
//@AllArgsConstructor
public class DiseaseType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name")
    private String name;
    @OneToMany(mappedBy = "diseaseType")
    private List<Disease> diseases;

    @Override
    public String toString() {
        return "DiseaseType{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
