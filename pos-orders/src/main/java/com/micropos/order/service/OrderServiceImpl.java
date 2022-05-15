package com.micropos.order.service;

import com.micropos.dto.CartDto;
import com.micropos.dto.CartItemDto;
import com.micropos.order.model.Item;
import com.micropos.order.model.Order;
import com.micropos.order.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Streamable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService {

    private OrderRepository orderRepository;

    @Autowired
    public void setOrderRepository(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public Order createOrder(CartDto cart, String time) {
        Order order = new Order().time(time);
        order = orderRepository.save(order);
        List<Item> items = new ArrayList<>();
        for (CartItemDto cartItem : cart.getItems()) {
            items.add(
                    new Item().id(null)
                            .productId(cartItem.getProduct().getId())
                            .productName(cartItem.getProduct().getName())
                            .unitPrice(cartItem.getProduct().getPrice())
                            .quantity(cartItem.getAmount()));

        }
        order.items(items);
        return orderRepository.save(order);
    }

    @Override
    public List<Order> getAllOrders() {
        return Streamable.of(orderRepository.findAll()).toList();
    }

    @Override
    public Optional<Order> getOrder(Integer orderId) {
        return orderRepository.findById(orderId);
    }
}
