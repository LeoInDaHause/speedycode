package io.leoindahause.model;

import jakarta.persistence.*;

@Entity
@Table(name = "exercise_status")
public class ExerciseStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "easy_exer_1", nullable = false)
    private boolean easyExer1 = false;

    @Column(name = "easy_exer_2", nullable = false)
    private boolean easyExer2 = false;

    @Column(name = "easy_exer_3", nullable = false)
    private boolean easyExer3 = false;

    @Column(name = "medium_exer_1", nullable = false)
    private boolean mediumExer1 = false;

    @Column(name = "medium_exer_2", nullable = false)
    private boolean mediumExer2 = false;

    @Column(name = "medium_exer_3", nullable = false)
    private boolean mediumExer3 = false;

    @Column(name = "hard_exer_1", nullable = false)
    private boolean hardExer1 = false;

    @Column(name = "hard_exer_2", nullable = false)
    private boolean hardExer2 = false;

    @Column(name = "hard_exer_3", nullable = false)
    private boolean hardExer3 = false;

    @OneToOne(mappedBy = "exerciseStatus")
    private Person person;

    // Getters and setters
    public int getId() {
        return id;
    }

    public boolean isEasyExer1() {
        return easyExer1;
    }

    public void setEasyExer1(boolean easyExer1) {
        this.easyExer1 = easyExer1;
    }

    public boolean isEasyExer2() {
        return easyExer2;
    }

    public void setEasyExer2(boolean easyExer2) {
        this.easyExer2 = easyExer2;
    }

    public boolean isEasyExer3() {
        return easyExer3;
    }

    public void setEasyExer3(boolean easyExer3) {
        this.easyExer3 = easyExer3;
    }

    public boolean isMediumExer1() {
        return mediumExer1;
    }

    public void setMediumExer1(boolean mediumExer1) {
        this.mediumExer1 = mediumExer1;
    }

    public boolean isMediumExer2() {
        return mediumExer2;
    }

    public void setMediumExer2(boolean mediumExer2) {
        this.mediumExer2 = mediumExer2;
    }

    public boolean isMediumExer3() {
        return mediumExer3;
    }

    public void setMediumExer3(boolean mediumExer3) {
        this.mediumExer3 = mediumExer3;
    }

    public boolean isHardExer1() {
        return hardExer1;
    }

    public void setHardExer1(boolean hardExer1) {
        this.hardExer1 = hardExer1;
    }

    public boolean isHardExer2() {
        return hardExer2;
    }

    public void setHardExer2(boolean hardExer2) {
        this.hardExer2 = hardExer2;
    }

    public boolean isHardExer3() {
        return hardExer3;
    }

    public void setHardExer3(boolean hardExer3) {
        this.hardExer3 = hardExer3;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
        if (person != null) {
            this.id = person.getId();
        }
    }
}