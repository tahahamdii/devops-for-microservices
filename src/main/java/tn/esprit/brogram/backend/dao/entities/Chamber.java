package tn.esprit.brogram.backend.dao.entities;
import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name="chamber")
@Builder
public class Chamber {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idChamber ;

    @Column(name="numeroChamber")
    private int numerochamber ;

    @Column(name="TypeC")
    private TypeChamber typeC ;

    @Column(name="Description")
    private String description;

    @Column(name="Etat")
    private boolean etat;

    @Column(name="CreatedAt")
    private Date createdAt;

    @Column(name="UpdatedAt")
    private Date updatedAt;
    @Lob
    @Column(name = "imagebyte", length = 100000)  // Adjust the length as needed
    private byte[] imagebyte;

    @JsonIgnore
    @ManyToOne
    Bloc bloc ;

    //@OneToMany(cascade =  CascadeType.ALL)
    //private  Set<Reservation> res  = new HashSet<>();

}
