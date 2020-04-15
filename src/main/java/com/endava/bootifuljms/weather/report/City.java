package com.endava.bootifuljms.weather.report;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class City {

    @Id
    @SequenceGenerator(name = "city_sequence_generator", sequenceName = "city_sequence", allocationSize = 1)
    @GeneratedValue(generator = "city_sequence_generator")
    private Long id;

    @Column(name = "city_name")
    private String name;
}
