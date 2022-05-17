package com.programmers.programmersjpalecture.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.bind.annotation.GetMapping;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "order_item")
@Getter
@Setter
public class OrderItem extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private int price;
    private int quantity;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", referencedColumnName = "id")
    private Order order;

    @OneToMany(mappedBy = "orderItem")
    private List<Item> items;

    public void setOrder(Order order) {
        if (Objects.nonNull(this.order)) {
            this.order.getOrderItems().remove(this);
        }

        this.order = order;
        order.getOrderItems().add(this);
    }

    public void addItem(Item item) {
        item.setOrderItem(this);
    }

}
