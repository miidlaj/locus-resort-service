package com.midlaj.resort.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "resort_address")
@Builder
public class ResortAddress {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "zip_code", length = 6)
    private String zipCode;

    @Column(name = "city", length = 40)
    private String city;

    @Column(name = "state", length = 40)
    private String state;

    @Column(name = "country", length = 40)
    private String country;

    @OneToOne
    @JoinColumn(name = "resort_id")
    @JsonIgnore
    private Resort resort;

    @Override
    public String toString() {
        return "ResortAddress{" +
                "id=" + id +
                ", zipCode='" + zipCode + '\'' +
                ", city='" + city + '\'' +
                ", state='" + state + '\'' +
                ", country='" + country + '\'' +
                '}';
    }
}
