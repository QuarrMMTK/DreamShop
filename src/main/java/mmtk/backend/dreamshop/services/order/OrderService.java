package mmtk.backend.dreamshop.services.order;

import lombok.RequiredArgsConstructor;
import mmtk.backend.dreamshop.dtos.OrderDto;
import mmtk.backend.dreamshop.enums.OrderStatus;
import mmtk.backend.dreamshop.exceptions.ResourceNotFoundException;
import mmtk.backend.dreamshop.models.Cart;
import mmtk.backend.dreamshop.models.Order;
import mmtk.backend.dreamshop.models.OrderItem;
import mmtk.backend.dreamshop.models.Product;
import mmtk.backend.dreamshop.repositories.OrderRepository;
import mmtk.backend.dreamshop.repositories.ProductRepository;
import mmtk.backend.dreamshop.services.cart.CartService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService implements IOrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final CartService cartService;
    private final ModelMapper modelMapper;

    @Transactional
    @Override
    public Order placeOrder(Long userId) {
        Cart cart = cartService.getCartByUserId(userId);
        Order order = createOrder(cart);
        List<OrderItem> orderItemList = createOrderItem(order, cart);
        order.setOrderItems(new HashSet<>(orderItemList));
        Order savedOrder = orderRepository.save(order);
        cartService.clearCart(cart.getId());
        return savedOrder;
    }

    private Order createOrder(Cart cart) {
        Order order = new Order();
        order.setUser(cart.getUser());
        order.setOrderStatus(OrderStatus.PENDING);
        order.setOrderDate(LocalDate.now());
        return order;
    }

    private List<OrderItem> createOrderItem(Order order, Cart cart) {
     return cart.getItems().stream().map(cartItem ->{
         Product product = cartItem.getProduct();
         product.setInventory(product.getInventory() - cartItem.getQuantity());
         productRepository.save(product);
         return new OrderItem(
                 order,
                 product,
                 cartItem.getQuantity(),
                 cartItem.getUnitPrice());
             }).toList();
    }

    private BigDecimal CalculateTotalAmount(List<OrderItem> orderItemList) {
        return orderItemList
                .stream()
                .map(item -> item.getPrice()
                        .multiply(new BigDecimal(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @Override
    public OrderDto getOrder(Long OrderId) {
        return orderRepository.findById(OrderId)
                .map(this :: convertToDto)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found"));
    }

    private OrderDto convertToDto(Order order) {
        return modelMapper.map(order, OrderDto.class);
    }

    @Override
    public List<OrderDto> getUserOrders(Long userId) {
        List<Order> orders = orderRepository.findByUserId(userId);
        return orders.stream().map(this :: convertToDto).toList();
    }
}
