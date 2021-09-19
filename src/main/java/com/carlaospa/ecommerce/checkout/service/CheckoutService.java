package com.carlaospa.ecommerce.checkout.service;

import com.carlaospa.ecommerce.checkout.entity.CheckoutEntity;
import com.carlaospa.ecommerce.checkout.resource.checkout.CheckoutRequest;

import java.util.Optional;

public interface CheckoutService {

   Optional<CheckoutEntity> create(CheckoutRequest checkoutRequest);
   Optional<CheckoutEntity> updateStatus(String checkoutCode, CheckoutEntity.Status status);
}
