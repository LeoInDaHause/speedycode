package io.leoindahause.model;

import jakarta.persistence.*;

@Entity
@Table(name = "person")
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @OneToOne
    @JoinColumn(name = "exercise_status_id")
    private ExerciseStatus exerciseStatus;

    public int getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public ExerciseStatus getExerciseStatus() {
        return exerciseStatus;
    }

    public void setExerciseStatus(ExerciseStatus exerciseStatus) {
        this.exerciseStatus = exerciseStatus;
    }

    @Override
public String toString() {
    return "{" +
            "\"id\": " + id +
            ", \"email\": \"" + email + "\"" +
            ", \"password\": \"" + password + "\"" +
            ", \"exerciseStatus\": " + (exerciseStatus != null ? exerciseStatus.getId() : "null") +
            '}';
}
}
