package com.example.testapp.model;

import lombok.Data;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Optional;

@Data
public class RequestData {
    private long previousDataDay;
    private long previousDataNigh;
    private long currenDataDay;
    private long currentDataNigh;

    private Double tariffDay;
    private Double tariffNight;


    public String stringify() {
        this.tariffDay = Optional.ofNullable(this.tariffDay).orElse(1.68);
        this.tariffNight = Optional.ofNullable(this.tariffNight).orElse(0.84);
        long diffDay = this.currenDataDay - this.previousDataDay;
        long diffNight = this.currentDataNigh - this.previousDataNigh;

        double totalDay = diffDay * this.tariffDay;
        double totalNight = diffNight * this.tariffNight;
        double total = totalDay + totalNight;

        return this.currenDataDay + "-" + this.previousDataDay + "=" + diffDay + "*" + this.tariffDay + "=" + totalDay + "\n" +
                       this.currentDataNigh + "-" + this.previousDataNigh + "=" + diffNight + "*" + this.tariffNight + "=" + totalNight + "\n" +
                       totalDay + "+" + totalNight + "=" + total + "+1%=" + roundup(total * 0.01 + total);
    }

    private String roundup(double d){
        return BigDecimal.valueOf(d).setScale(2, RoundingMode.UP).toString();
    }
}
