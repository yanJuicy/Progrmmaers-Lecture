package org.prgms.kdt;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class Order {

    private final UUID orderId;
    private final UUID custoemrId;
    private final List<OrderItem> orderItems;
    private Optional<Voucher> voucher;
    private OrderStatus orderStatus = OrderStatus.ACCEPTED;

    public Order(UUID orderId, UUID customerId, List<OrderItem> orderItem, Voucher voucher) {
        this.orderId = orderId;
        this.custoemrId = customerId;
        this.orderItems = orderItem;
        this.voucher = Optional.of(voucher);
    }

    public Order(UUID orderId, UUID customerId, List<OrderItem> orderItem) {
        this.orderId = orderId;
        this.custoemrId = customerId;
        this.orderItems = orderItem;
        this.voucher = Optional.empty();
    }
    
    public long totalAmount() {
        long beforeDiscount = orderItems.stream().map(v -> v.getProductPrice() * v.getQuantValue())
                .reduce(0L, Long::sum);
        if (voucher.isPresent()) {
            return voucher.get().discount(beforeDiscount);
        } else {
            return beforeDiscount;
        }
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }
}
