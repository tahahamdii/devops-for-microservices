package tn.esprit.brogram.backend.dao.entities;

import jakarta.persistence.*;
import lombok.*;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

@Table(name="image")
@Builder

@Entity

public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "image_id")
    private Long id;

    @Column(name = "image_name")
    private String name;

    @Column(name = "image_type")
    private String type;

    @Lob
    @Column(name = "picbyte", length = 100000)
    private byte[] picbyte;



}


