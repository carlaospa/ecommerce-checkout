package com.carlaospa.ecommerce.checkout.resource.checkout;


import com.carlaospa.ecommerce.checkout.entity.CheckoutEntity;
import com.carlaospa.ecommerce.checkout.service.CheckoutService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/checkout")
public class CheckoutResource {

    private final CheckoutService checkoutService;

    @PostMapping("/")
    public ResponseEntity<CheckoutEntity> create(@RequestBody CheckoutRequest checkoutRequest) {

       final CheckoutEntity checkoutEntity = checkoutService.create(checkoutRequest).orElseThrow();
       final CheckoutResponse checkoutResponse = CheckoutResponse.builder()
               .code(checkoutEntity.getCode())
               .build();
       return ResponseEntity.status(HttpStatus.CREATED).body(checkoutEntity);

    }
}
