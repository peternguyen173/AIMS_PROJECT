package com.example.demo.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

import java.util.Date;

@Data
@Entity
@Table(name = "cd")
public class CD extends Media {

    private String artist;
    private String recordLabel;
    private String musicType;
    private Date releasedDate;
}
