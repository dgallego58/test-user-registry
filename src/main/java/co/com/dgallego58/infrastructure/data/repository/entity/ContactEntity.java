package co.com.dgallego58.infrastructure.data.repository.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table
@Getter
@Setter
public class ContactEntity {

    @Id
    @GeneratedValue
    private int id;
    private String number;
    private String cityCode;
    private String countryCode;
    @ManyToOne
    private UserEntity user;

}
