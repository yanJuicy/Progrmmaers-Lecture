package org.prgms.kdt;

import java.util.List;
import java.util.UUID;

public class Order {

    private final UUID orderId;
    private final UUID custoemrId;
    private final List<OrderItem> orderItems;
    private Voucher voucher;
    private OrderStatus orderStatus = OrderStatus.ACCEPTED;

    public Order(UUID orderId, UUID custoemrId, List<OrderItem> orderItem, Voucher voucher) {
        this.orderId = orderId;
        this.custoemrId = custoemrId;
        this.orderItems = orderItem;
        this.voucher = voucher;
    }
    
    public long totalAmount() {
        long beforeDiscount = orderItems.stream().map(v -> v.getProductPrice() * v.getQuantValue())
                .reduce(0L, Long::sum);
        return voucher.discount(beforeDiscount);
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }
}
