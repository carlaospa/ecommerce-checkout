package com.carlaospa.ecommerce.checkout.listener;

import com.carlaospa.ecommerce.payment.event.PaymentCreatedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.stereotype.Component;
import com.carlaospa.ecommerce.checkout.streaming.PaymentPaidSink;
import com.carlaospa.ecommerce.checkout.entity.CheckoutEntity;
import com.carlaospa.ecommerce.checkout.service.CheckoutService;

@Component
@RequiredArgsConstructor
public class PaymentPaidListener {

    //private final CheckoutRepository checkoutRepository;
    /*
    @StreamListener(PaymentPaidSink.INPUT)
    public void handler(PaymentCreatedEvent event){
        final CheckoutEntity checkoutEntity = checkoutRepository.findByCode(event.getCheckoutCode().toString()).orElseThrow();
        checkoutEntity.setStatus(CheckoutEntity.Status.APPROVED);
        checkoutRepository.save(checkoutEntity);
    }
    */
    private final CheckoutService checkoutService;

    @StreamListener(PaymentPaidSink.INPUT)
    public void handler(PaymentCreatedEvent paymentCreatedEvent) {
        checkoutService.updateStatus(paymentCreatedEvent.getCheckoutCode().toString(), CheckoutEntity.Status.APPROVED);
    }
}
