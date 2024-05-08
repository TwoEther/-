package kr.co.ordermanagement.application;

import kr.co.ordermanagement.domain.order.Order;
import kr.co.ordermanagement.domain.order.OrderRepository;
import kr.co.ordermanagement.domain.order.State;
import kr.co.ordermanagement.domain.product.Product;
import kr.co.ordermanagement.domain.product.ProductRepository;
import kr.co.ordermanagement.presentation.dto.OrderResponseDto;
import kr.co.ordermanagement.presentation.dto.PatchStateRequestDto;
import kr.co.ordermanagement.presentation.dto.ProductDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SimpleOrderService {

    private ProductRepository productRepository;
    private OrderRepository orderRepository;

    @Autowired
    public SimpleOrderService(ProductRepository productRepository, OrderRepository orderRepository) {
        this.productRepository = productRepository;
        this.orderRepository = orderRepository;
    }

    public List<ProductDto> findAll() {
        List<Product> products = productRepository.findAll();
        return products.stream()
                .map(ProductDto::toDto)
                .toList();
    }

    public OrderResponseDto findById(Long orderId) {
        Order order = orderRepository.findById(orderId);
        return OrderResponseDto.toDto(order);
    }

    // 실질적 비즈니스 로직 수행
    public OrderResponseDto changeState(Long orderId, PatchStateRequestDto patchStateRequestDto) {
        State state = patchStateRequestDto.getState();
        Order order = orderRepository.findById(orderId);

        order.changeStateForce(state);

        return OrderResponseDto.toDto(order);
    }

    public List<OrderResponseDto> findByState(State state) {
        List<Order> orders = orderRepository.findByState(state);
        List<OrderResponseDto> orderResponseDtos = orders.stream()
                .map(order -> OrderResponseDto.toDto(order))
                .toList();
        return orderResponseDtos;
    }

    public OrderResponseDto cancelOrderById(Long orderId) {
        Order order = orderRepository.findById(orderId);
        order.cancel();

        return OrderResponseDto.toDto(order);
    }
}
