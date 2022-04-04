package org.prgms.kdt;

import java.util.UUID;

public class OrderItem {
    public final UUID productId;
    private final long productPrice;
    private final long quantValue;

    public OrderItem(UUID productId, long productPrice, long quantValue) {
        this.productId = productId;
        this.productPrice = productPrice;
        this.quantValue = quantValue;
    }

    public UUID getProductId() {
        return productId;
    }

    public long getProductPrice() {
        return productPrice;
    }

    public long getQuantValue() {
        return quantValue;
    }
}
