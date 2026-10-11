package com.example.ecommerceapplication.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jdk.jfr.Name;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Entity
@NoArgsConstructor
public class Role {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer roleId ;
    @Enumerated(EnumType.STRING)
    @Column(length = 20 , name = "role_name")
    private AppRole roleName ;

    public Role(AppRole roleName){
        this.roleName = roleName ;
    }

}
