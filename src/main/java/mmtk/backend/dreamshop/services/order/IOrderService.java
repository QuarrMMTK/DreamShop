package mmtk.backend.dreamshop.services.order;

import mmtk.backend.dreamshop.dtos.OrderDto;
import mmtk.backend.dreamshop.models.Order;

import java.util.List;

public interface IOrderService {
    Order placeOrder(Long userId);
    OrderDto getOrder(Long OrderId);
    List<OrderDto> getUserOrders(Long userId);
}
