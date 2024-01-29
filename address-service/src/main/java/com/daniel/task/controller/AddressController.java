package com.daniel.task.controller;

import com.daniel.task.Response;
import com.daniel.task.entity.Address;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/api/address")
public class AddressController {

    //Task: "Legyen benne egy végpont, ami visszaad egy Address objektumot, aminek két mezöje van: id, address"
    @Operation(summary = "Get a random address", description = "Fetch a random address", tags = {"Address"})
    @GetMapping("/")
    public ResponseEntity<Response> getAddress() {

        //! because we don't need a database, therefore I am generating a random UUID, and I am not using a mapper and
        //! a service, and the other not needed things for this simple example
        log.info("Getting address");
        UUID uuid = UUID.randomUUID();
        Address address = Address.builder()
                .id(uuid)
                .address("1234 Random St")
                .build();
        log.info("Address: " + address.getAddress());
        return ResponseEntity.ok(Response.builder()
                .status(HttpStatus.OK.value())
                .data(address)
                .build());
    }
}
