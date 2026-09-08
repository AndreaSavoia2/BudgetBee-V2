package com.sav.budgetbee.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sav.budgetbee.entity.common.CreationUpdate;
import com.sav.budgetbee.entity.enumeration.TransactionType;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
@Table(name = "table_category")
public class Category extends CreationUpdate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private long id;

    @Column(nullable = false, length = 100)
    private String categoryName;

    @Column(nullable = false)
    private boolean enable = true;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TransactionType transactionType;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "id_user")
    private User user;

    public Category(String categoryName, TransactionType transactionType, User user) {
        this.categoryName = categoryName;
        this.transactionType = transactionType;
        this.user = user;
    }
}
