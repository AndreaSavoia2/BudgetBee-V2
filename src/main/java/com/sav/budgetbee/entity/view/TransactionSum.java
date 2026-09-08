package com.sav.budgetbee.entity.view;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "transactions_sum")
public class TransactionSum {
    @Id
    private long idUser;

    private Double budget;
}

