package com.carlaospa.ecommerce.checkout.service;

import com.carlaospa.ecommerce.checkout.entity.CheckoutEntity;
import com.carlaospa.ecommerce.checkout.event.CheckoutCreatedEvent;
import com.carlaospa.ecommerce.checkout.resource.checkout.CheckoutRequest;
import com.carlaospa.ecommerce.checkout.repository.CheckoutRepository;
import com.carlaospa.ecommerce.checkout.streaming.CheckoutCreatedSource;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CheckoutServiceImpl implements CheckoutService{

    private final CheckoutRepository checkoutRepository;
    private final CheckoutCreatedSource checkoutCreatedSource;

    @Override
    public Optional<CheckoutEntity> create(CheckoutRequest checkoutRequest) {

         final CheckoutEntity checkoutEntity = CheckoutEntity.builder()
                .code(UUID.randomUUID().toString())
                 .status(CheckoutEntity.Status.CREATED)
                .build();

         final CheckoutEntity entity = checkoutRepository.save(checkoutEntity);

         final CheckoutCreatedEvent checkoutCreatedEvent = CheckoutCreatedEvent.newBuilder()
                 .setCheckoutCode(entity.getCode())
                 .setStatus(entity.getStatus().name())
                 .build();

         checkoutCreatedSource.output().send(MessageBuilder.withPayload(checkoutCreatedEvent).build());

        return Optional.of(entity);
    }

    @Override
    public Optional<CheckoutEntity> updateStatus(String checkoutCode, CheckoutEntity.Status status) {
        final CheckoutEntity checkoutEntity = checkoutRepository.findByCode(checkoutCode).orElse(CheckoutEntity.builder().build());
        checkoutEntity.setStatus(CheckoutEntity.Status.APPROVED);
        return Optional.of(checkoutRepository.save(checkoutEntity));
    }
}
