package com.yeseung.buckettest.parking;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/parking")
public class ParkingController {

    private final ParkingApplyService parkingApplyService;

    @PostMapping
    public ResponseEntity<ParkingApplyResponse> apply(@RequestBody ParkingApplyRequest parkingApplyRequest) {
        return ResponseEntity.ok(parkingApplyService.apply(parkingApplyRequest));
    }

}
