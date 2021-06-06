package com.fot.Canteen_Management_System.Entity;

import org.hibernate.annotations.Table;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
public class ChangePwdLog {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private  String details;
    private  Integer user_id;
    private LocalDateTime chang_pwd_date;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public LocalDateTime getChang_pwd_date() {
        return chang_pwd_date;
    }

    public void setChang_pwd_date(LocalDateTime chang_pwd_date) {
        this.chang_pwd_date = chang_pwd_date;
    }
}
