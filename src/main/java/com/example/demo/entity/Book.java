package com.example.demo.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

import java.util.Date;

@Data
@Entity
@Table(name = "book")
public class Book extends Media {

    private String author;
    private String coverType;
    private String publisher;
    private Date publishDate;
    private Integer numOfPages;
    private String language;
    private String bookCategory;
}
