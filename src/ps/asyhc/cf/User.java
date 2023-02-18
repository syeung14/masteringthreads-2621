package ps.asyhc.cf;

import java.time.*;

public class User {
    private long id;
    private String name;
    private String surname;
    private LocalDate birthDate;
    private Integer age;
    private Boolean canDriveACar = false;
    public User() {}

    public User(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Boolean getCanDriveACar() {
        return canDriveACar;
    }

    public void setCanDriveACar(Boolean canDriveACar) {
        this.canDriveACar = canDriveACar;
    }

    @Override
    public String toString() {
        return "User [id=" + id + "]";
    }
}
