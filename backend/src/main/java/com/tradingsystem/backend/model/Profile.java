package com.tradingsystem.backend.model;

import java.time.LocalDate;

import com.tradingsystem.backend.utils.types.Gender;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="profiles")
@Builder
@Getter @Setter
public class Profile {
    @Id
    private Long id;

    @OneToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="user_id", nullable=false)
    @MapsId
    private User user;
    
    private String email;
    private String name;
    private String surname; 

    @Enumerated(EnumType.STRING)
    private Gender gender;

    private LocalDate dateOfBirth;
    private String city;
    private String address;

    @Column(columnDefinition="bpchar(9)")
    private String phone;
    @Column(columnDefinition="bpchar(11)")
    private String pesel;
}
