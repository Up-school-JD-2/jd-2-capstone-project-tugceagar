package io.upschool.capstoneProject.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "credit_card")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreditCard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "card_number",nullable = false)
    private String cardNumber;

    @Column(name = "ccv",nullable = false)
    private String  ccv;

    @Column(name = "expiration_month",nullable = false)
    private String expirationMonth;

    @Column(name = "expiration_year",nullable = false)
    private String  expirationYear;

    //passengerid ekle

//    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
//    @JoinColumn(name = "passenger_id")
//    private Passenger passenger;

}
