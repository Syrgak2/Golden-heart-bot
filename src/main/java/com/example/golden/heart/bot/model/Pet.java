package com.example.golden.heart.bot.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Objects;

@Entity
@Data
public class Pet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String nick;

    @ManyToOne
    @JoinColumn(name = "animalShelter_id")
    @JsonIgnore
    private AnimalShelter animalShelter;

    @OneToOne
    @JoinColumn(name = "owner_id")
    @JsonIgnore
    private User owner;


    public Pet() {
    }

    /**
     * Конструктор для создание тестовых объектов
     */
    public Pet(Long id, String nick) {
        this.id = id;
        this.nick = nick;
    }
}



