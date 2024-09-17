package io.leoindahause.model;

import jakarta.persistence.*;

@Entity
@Table(name = "exercise_status")
public class Exercise {

    @Id
    @Column(name = "user_id")
    private Long userId;

    @OneToOne
    @MapsId
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "easy_1")
    private boolean exercise1 = false;

    @Column(name = "easy_2")
    private boolean exercise2 = false;

    @Column(name = "easy_3")
    private boolean exercise3 = false;

    @Column(name = "easy_4")
    private boolean exercise4 = false;

    @Column(name = "medium_1")
    private boolean exercise5 = false;

    @Column(name = "medium_2")
    private boolean exercise6 = false;

    @Column(name = "medium_3")
    private boolean exercise7 = false;

    @Column(name = "medium_4")
    private boolean exercise8 = false;

    @Column(name = "hard_1")
    private boolean exercise9 = false;

    @Column(name = "hard_2")
    private boolean exercise10 = false;

    @Column(name = "hard_3")
    private boolean exercise11 = false;

    @Column(name = "hard_4")
    private boolean exercise12 = false;

    // Getters and Setters
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public boolean isExercise1() {
        return exercise1;
    }

    public void setExercise1(boolean exercise1) {
        this.exercise1 = exercise1;
    }

    public boolean isExercise2() {
        return exercise2;
    }

    public void setExercise2(boolean exercise2) {
        this.exercise2 = exercise2;
    }

    public boolean isExercise3() {
        return exercise3;
    }

    public void setExercise3(boolean exercise3) {
        this.exercise3 = exercise3;
    }

    public boolean isExercise4() {
        return exercise4;
    }

    public void setExercise4(boolean exercise4) {
        this.exercise4 = exercise4;
    }

    public boolean isExercise5() {
        return exercise5;
    }

    public void setExercise5(boolean exercise5) {
        this.exercise5 = exercise5;
    }

    public boolean isExercise6() {
        return exercise6;
    }

    public void setExercise6(boolean exercise6) {
        this.exercise6 = exercise6;
    }

    public boolean isExercise7() {
        return exercise7;
    }

    public void setExercise7(boolean exercise7) {
        this.exercise7 = exercise7;
    }

    public boolean isExercise8() {
        return exercise8;
    }

    public void setExercise8(boolean exercise8) {
        this.exercise8 = exercise8;
    }

    public boolean isExercise9() {
        return exercise9;
    }

    public void setExercise9(boolean exercise9) {
        this.exercise9 = exercise9;
    }

    public boolean isExercise10() {
        return exercise10;
    }

    public void setExercise10(boolean exercise10) {
        this.exercise10 = exercise10;
    }

    public boolean isExercise11() {
        return exercise11;
    }

    public void setExercise11(boolean exercise11) {
        this.exercise11 = exercise11;
    }

    public boolean isExercise12() {
        return exercise12;
    }

    public void setExercise12(boolean exercise12) {
        this.exercise12 = exercise12;
    }
}