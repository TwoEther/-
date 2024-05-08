package kr.co.ordermanagement.application;

import kr.co.ordermanagement.domain.order.Order;
import kr.co.ordermanagement.domain.order.OrderRepository;
import kr.co.ordermanagement.domain.product.Product;
import kr.co.ordermanagement.domain.product.ProductRepository;
import kr.co.ordermanagement.presentation.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SimpleProductService {

    private ProductRepository productRepository;
    private OrderRepository orderRepository;

    public SimpleProductService(ProductRepository productRepository, OrderRepository orderRepository) {
        this.productRepository = productRepository;
        this.orderRepository = orderRepository;
    }

    public List<ProductDto> findAll() {
        List<Product> products = productRepository.findAll();
        List<ProductDto> productDtos = products.stream()
                .map(product -> ProductDto.toDto(product))
                .toList();
        return productDtos;
    }

    public OrderResponseDto findById(Long orderId) {
        Order order = orderRepository.findById(orderId);
        return OrderResponseDto.toDto(order);
    }

    public OrderResponseDto createOrder(List<OrderProductRequestDto> orderProductRequestDtos) {
        List<Product> orderProducts = makeOrderProducts(orderProductRequestDtos);
        decreaseProductsAmount(orderProducts);

        Order order = new Order(orderProducts);
        orderRepository.Add(order);

        OrderResponseDto orderResponseDto = OrderResponseDto.toDto(order);
        return orderResponseDto;
    }

    public List<Product> makeOrderProducts(List<OrderProductRequestDto> orderProductRequestDtos) {
        return orderProductRequestDtos.stream()
                .map(orderProductRequestDto -> {
                    Long productId = orderProductRequestDto.getId();
                    Product product = productRepository.findById(productId);
                    Integer amount = product.getAmount();
                    product.checkEnoughAmount(amount);

                   return new Product(
                           productId,
                           product.getName(),
                           product.getPrice(),
                           orderProductRequestDto.getAmount()
                   );
                }).toList();
    }

    private void decreaseProductsAmount(List<Product> orderedProducts) {
        orderedProducts.stream()
            .forEach(orderedProduct -> {
                Long productId = orderedProduct.getId();
                Product product = productRepository.findById(productId);
                Integer amount = orderedProduct.getAmount();
                product.decreaseAmount(amount);

                productRepository.update(product);
            });
    }



}
