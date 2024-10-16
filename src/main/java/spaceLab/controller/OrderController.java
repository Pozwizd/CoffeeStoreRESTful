package spaceLab.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import spaceLab.entity.Customer;
import spaceLab.model.customer.CustomerResponse;
import spaceLab.model.order.request.OrderRequest;
import spaceLab.model.order.response.OrderResponse;
import spaceLab.service.CustomerService;
import spaceLab.service.OrderService;

/**
OrderController
- orderService: OrderService
- cityService: CityService
--
+ getOrdersByUser(): ResponseEntity<?>
+ reorder(@PathVariable Long id): ResponseEntity<?>
+ reorderWithDelivery(@PathVariable Long id): ResponseEntity<?>
+ saveOrder(@Valid @RequestBody OrdersDto ordersDto): ResponseEntity<?>
+ saveOrderWithDelivery(@Valid @RequestBody OrdersDto ordersDto): ResponseEntity<?>
 */
@Tag(name = "Order")
@RestController
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;
    private final CustomerService customerService;

    @Operation(summary = "Get orders",description = "Get orders for order history")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK",content = {@Content(mediaType = "application/json",schema = @Schema(implementation = OrderResponse.class))}),
            @ApiResponse(responseCode = "401", description = "Customer unauthorized",content = {@Content(mediaType = "application/json",schema = @Schema())}),
            @ApiResponse(responseCode = "400", description = "Bad request",content = {@Content(mediaType = "application/json",schema = @Schema())})})
    @GetMapping("/api/v1/orders")
    public ResponseEntity<?> getOrdersByUser(
            @Parameter(description = "Page number for pagination", example = "0")
            @RequestParam(defaultValue = "0") Integer page,
            @Parameter(description = "Number of items per page", example = "5")
            @RequestParam(defaultValue = "5") Integer size
    ) {
        Customer customer = (Customer) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Page<OrderResponse> ordersResponse = orderService.getOrdersByCustomerIdPage(customer.getId(), page, size);
        return ResponseEntity.ok(ordersResponse);
    }

    @Operation(summary = "Create new order from old order", description = "Create new order from old order")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK",content = {@Content(mediaType = "application/json",schema = @Schema(implementation = OrderRequest.class))}),
            @ApiResponse(responseCode = "401", description = "Customer unauthorized",content = {@Content(mediaType = "application/json",schema = @Schema())}),
            @ApiResponse(responseCode = "400", description = "Bad request",content = {@Content(mediaType = "application/json",schema = @Schema())})})
    @PostMapping("/api/v1/orders/reorder/{id}")
    public ResponseEntity<?> reorder(@PathVariable Long id) {
        return ResponseEntity.ok(orderService.reorder(id));
    }



    @Operation(summary = "Create new order", description = "Create new order")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK",content = {@Content(mediaType = "application/json",schema = @Schema(implementation = OrderRequest.class))}),
            @ApiResponse(responseCode = "401", description = "Customer unauthorized",content = {@Content(mediaType = "application/json",schema = @Schema())}),
            @ApiResponse(responseCode = "400", description = "Bad request",content = {@Content(mediaType = "application/json",schema = @Schema())})})
    @PostMapping("/api/v1/order/new/")
    public ResponseEntity<?> saveOrder(@Valid @RequestBody OrderRequest ordersDto) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        String email = userDetails.getUsername();
        Customer customer = customerService.getCustomerByEmail(email);

        return ResponseEntity.ok(orderService.saveOrderFromOrderRequest(ordersDto, customer));
    }

}