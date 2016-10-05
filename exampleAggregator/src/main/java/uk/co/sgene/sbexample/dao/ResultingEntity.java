package uk.co.sgene.sbexample.dao;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResultingEntity {

    @Id
    @Column(name = "id", nullable = false, unique = true)
    private String id;

    private String name;

    private Date dateUpdated;
}
