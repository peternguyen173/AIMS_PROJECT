package com.example.demo.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

import java.util.Date;

@Data
@Entity
@Table(name = "dvd")
public class DVD extends Media {

    private String discType;
    private String director;
    private Integer runtime;
    private String studio;
    private String subtitles;
    private Date releasedDate;
    private String filmType;
}
