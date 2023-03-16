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
@Table(name = "resort_locations")
@Builder
public class ResortLocationDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String location;

    private String lattitude;

    private String longitude;

    @OneToOne
    @JoinColumn(name = "resort_id")
    @JsonIgnore
    private Resort resort;

    @Override
    public String toString() {
        return "ResortLocationDetails{" +
                "id=" + id +
                ", location='" + location + '\'' +
                ", lattitude='" + lattitude + '\'' +
                ", longitude='" + longitude + '\'' +
                '}';
    }
}
