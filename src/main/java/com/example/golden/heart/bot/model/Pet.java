package com.example.golden.heart.bot.model;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
public class Pet {
    @Id
    @GeneratedValue
    private long id;
    private String nick;

    @ManyToOne
    @JoinColumn(name = "petOwner_id")
    private PetOwner petOwner;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public PetOwner getPetOwner() {
        return petOwner;
    }

    public void setPetOwner(PetOwner petOwner) {
        this.petOwner = petOwner;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pet pet = (Pet) o;
        return id == pet.id && Objects.equals(nick, pet.nick) && Objects.equals(petOwner, pet.petOwner);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nick, petOwner);
    }

    @Override
    public String toString() {
        return "Pet{" +
                "id=" + id +
                ", nick='" + nick + '\'' +
                ", petOwner=" + petOwner +
                '}';
    }
}