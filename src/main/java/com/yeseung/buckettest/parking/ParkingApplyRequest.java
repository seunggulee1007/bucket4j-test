package com.yeseung.buckettest.parking;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import static org.springframework.beans.BeanUtils.copyProperties;

@Getter
@Setter
@Builder
public class ParkingApplyRequest {

    private String carNo;

    private String maxHm;

    private String maxYmd;

    @Builder.Default
    private String playhubYn = "N";

    @Builder.Default
    private String playmuseumYn = "Y";

    private String transferYmd;

    @Override public String toString() {
        return "{" +
            "\"carNo\": \"" + carNo + "\"" +
            ", \"maxHm\": \"" + maxHm + "\"" +
            ", \"maxYmd\": \"" + maxYmd + "\"" +
            ", \"playhubYn\": \"" + playhubYn + "\"" +
            ", \"playmuseumYn\": \"" + playmuseumYn + "\"" +
            ", \"transferYmd\": \"" + transferYmd + "\"" +
            "}";
    }

    public ParkingApplyResponse convertResponse() {
        ParkingApplyResponse parkingApplyResponse = new ParkingApplyResponse();
        copyProperties(this, parkingApplyResponse);
        return parkingApplyResponse;
    }

}
