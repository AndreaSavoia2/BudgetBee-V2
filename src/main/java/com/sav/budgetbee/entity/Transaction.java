package com.sav.budgetbee.entity;

import com.sav.budgetbee.entity.common.CreationUpdate;
import com.sav.budgetbee.entity.enumeration.TransactionType;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
@Table(name = "table_transaction")
public class Transaction extends CreationUpdate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private long id;

    @Column(nullable = false)
    private BigDecimal amount;

    @Column(length = 100, nullable = false)
    private String title;

    @Column(length = 65535)
    private String description;

    @Column(nullable = false)
    private LocalDate transactionDate;

    @Column(nullable = false)
    private boolean enable = true;

    @ManyToOne
    @JoinColumn(name = "id_user")
    private User user;

    @ManyToOne
    @JoinColumn(name = "id_category")
    private Category category;

    public Transaction(BigDecimal amount, LocalDate transactionDate, User user, Category category) {
        this.amount = amount;
        this.transactionDate = transactionDate;
        this.user = user;
        this.category = category;
    }
}
