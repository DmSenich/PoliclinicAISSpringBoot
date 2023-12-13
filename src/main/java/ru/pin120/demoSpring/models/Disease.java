package ru.pin120.demoSpring.models;

import lombok.*;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "Diseases")
@Setter
@Getter
@Data
@NoArgsConstructor
//@AllArgsConstructor
public class Disease {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "idtype")
    private DiseaseType diseaseType;
    @ManyToOne
    @JoinColumn(name = "idvisiting")
    private Visiting visiting;
    private String description;

//    public Disease(int typeId, String description, int visitingId) {
//        this.typeId = typeId;
//        this.description = description;
//        this.visitingId = visitingId;
//    }
}
