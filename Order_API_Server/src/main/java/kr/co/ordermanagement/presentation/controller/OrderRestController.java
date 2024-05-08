package kr.co.ordermanagement.presentation.controller;

import kr.co.ordermanagement.application.SimpleOrderService;
import kr.co.ordermanagement.application.SimpleProductService;
import kr.co.ordermanagement.domain.order.Order;
import kr.co.ordermanagement.domain.order.State;
import kr.co.ordermanagement.presentation.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class OrderRestController {

    private SimpleProductService simpleProductService;
    private SimpleOrderService simpleOrderService;

    public OrderRestController(SimpleProductService simpleProductService, SimpleOrderService simpleOrderService) {
        this.simpleProductService = simpleProductService;
        this.simpleOrderService = simpleOrderService;
    }

    // 상품 주문 API
    @PostMapping(value = "/orders")
    public ResponseEntity<OrderResponseDto> createOrder(@RequestBody List<OrderProductRequestDto> orderProductRequestDtos) {
        OrderResponseDto orderResponseDto = simpleProductService.createOrder(orderProductRequestDtos);
        return ResponseEntity.ok(orderResponseDto);
    }

    //
    @PatchMapping(value = "/orders/{orderId}")
    public ResponseEntity<OrderResponseDto> getOrderById(@PathVariable Long orderId,
                                                         @RequestBody PatchStateRequestDto patchStateRequestDto) {
        // orderPatchRequestDto -> orderResponseDto
        OrderResponseDto orderResponseDto = simpleOrderService.changeState(orderId, patchStateRequestDto);
        return ResponseEntity.ok(orderResponseDto);
    }

    @GetMapping(value = "/orders")
    public ResponseEntity<List<OrderResponseDto>> getOrderByState(@RequestParam State state) {
        List<OrderResponseDto> orderResponseDtos = simpleOrderService.findByState(state);
        return ResponseEntity.ok(orderResponseDtos);
    }

    @PatchMapping(value = "/orders/{orderId}/cancel")
    public ResponseEntity<OrderResponseDto> cancelOrderById(@PathVariable Long orderId) {
        OrderResponseDto orderResponseDto = simpleOrderService.cancelOrderById(orderId);
        return ResponseEntity.ok(orderResponseDto);
    }
}
