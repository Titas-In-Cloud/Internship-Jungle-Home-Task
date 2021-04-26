package com.task.luminorjungletask;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.sql.Timestamp;

@Entity
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private Double amount;
    private String iban;
    private Timestamp createdAt;

    public Payment(){

    }

    public Payment(Double amount, String iban){
        super();
        this.amount = amount;
        this.iban = iban;
        this.createdAt = new Timestamp(System.currentTimeMillis());
    }

    public Integer getId(){
        return id;
    }

    public void setId(Integer id){
        this.id = id;
    }

    public Double getAmount(){
        return amount;
    }

    public void setAmount(Double amount){
        this.amount = amount;
    }

    public String getIban(){
        return iban;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }

    public Timestamp getTimestamp() {
        return createdAt;
    }

    public void setTimestamp(Timestamp createdAt) {
        this.createdAt = createdAt;
    }
}
