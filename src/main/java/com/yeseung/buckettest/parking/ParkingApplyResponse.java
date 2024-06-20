package com.yeseung.buckettest.parking;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class ParkingApplyResponse {

    private String carNo;

    private String maxHm;

    private String maxYmd;

    private String playhubYn;

    private String playmuseumYn;

    private String transferYmd;

}
