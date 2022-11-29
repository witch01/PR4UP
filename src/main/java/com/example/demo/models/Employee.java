package com.example.demo.models;


import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.*;
import java.util.Date;

@Entity
public class Employee {

    public Employee() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NotEmpty(message = "Поле не может быть пустым")
    @Size(min=2, max = 200, message = "ФИО не может быть короче двух и длиннее двухсот символов.")
    private String surname;

    @NotNull
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @Past
    private Date dr;
    @NotNull
    @Column(nullable = false)
    private Boolean gender = false;

    @NotNull(message = "Поле не может быть пустым")
    private Double salary;
    @Min(value = 0, message = "Количество детей не может быть отрицательным")
    private int children;

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getSurname() {
        return surname;
    }
    public void setSurname(String surname) {
        this.surname = surname;
    }


    public Date getDr() {
        return dr;
    }

    public void setDr(Date dr) {
        this.dr = dr;
    }


    public void setSalary(Double salary) {
        this.salary = salary;
    }

    public Boolean getGender() {
        return gender;
    }
    public void setGender(Boolean gender) {
        this.gender = gender;
    }

    public Double getSalary() {
        return salary;
    }

    public int getChildren() {return children;}
    public void setChildren(int children){this.children=children;}


}


