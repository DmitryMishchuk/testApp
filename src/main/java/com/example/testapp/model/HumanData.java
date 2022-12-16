package com.example.testapp.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Data
public class HumanData {
    private Long id;
    private Long date;
    private Long counter;
    private String name;
    private String secondName;
    private String sizeOfFamily;
    private String allNamesOfFamily;
    private String agesOfFamily;
    private String contactPhone;
    private String region;
    private String adress;
    private String status;
    private String adressBefore24;
    private String partOfRegion;

    @JsonIgnore
    private static final SimpleDateFormat DT = new SimpleDateFormat("dd.MM.yyyy");

    public List<String> getData() {
        return List.of(
                this.id.toString(),
                DT.format(new Date(this.date)),
                this.counter.toString(),
                this.name,
                this.secondName,
                this.sizeOfFamily,
                this.allNamesOfFamily,
                this.agesOfFamily,
                this.contactPhone,
                this.region,
                this.adress,
                this.status,
                this.adressBefore24,
                this.partOfRegion);
    }
}
