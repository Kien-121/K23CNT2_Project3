package com.shopgiadung.service;

import com.shopgiadung.dto.OrderItemDto;
import com.shopgiadung.dto.OrderRequest;
import com.shopgiadung.entity.Order;
import com.shopgiadung.entity.OrderItem;
import com.shopgiadung.entity.Product;
import com.shopgiadung.entity.User; // Cần import User entity
import com.shopgiadung.entity.PaymentMethod;
import com.shopgiadung.repository.OrderItemRepository;
import com.shopgiadung.repository.OrderRepository;
import com.shopgiadung.repository.ProductRepository; // Để lấy giá sản phẩm
import com.shopgiadung.repository.UserRepository; // Để lấy thông tin người dùng

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository; // Cần tạo UserRepository

    /**
     * Tạo đơn hàng mới từ OrderRequest DTO
     * @param request DTO chứa thông tin nhận hàng và chi tiết sản phẩm
     * @return Order đã được lưu
     */
    @Transactional
    public Order createOrder(OrderRequest request) {
        // 1. Lấy thông tin User (Giả định: userId luôn là 3L cho đến khi có Auth)
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found: " + request.getUserId()));

        // 2. Tạo đối tượng Order chính
        Order order = new Order();
        order.setUser(user);
        order.setReceiverName(request.getReceiverName());
        order.setReceiverPhone(request.getReceiverPhone());
        order.setShippingAddress(request.getShippingAddress());

        try {
            order.setPaymentMethod(PaymentMethod.valueOf(request.getPaymentMethod()));
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Phương thức thanh toán không hợp lệ.");
        }

        BigDecimal calculatedTotal = BigDecimal.ZERO;
        List<OrderItem> orderItems = new ArrayList<>();

        // 3. Xử lý từng sản phẩm trong đơn hàng
        for (OrderItemDto itemDto : request.getItems()) {
            Product product = productRepository.findById(itemDto.getProductId())
                    .orElseThrow(() -> new RuntimeException("Product not found: " + itemDto.getProductId()));

            // **********************************************
            // LƯU Ý QUAN TRỌNG: Tính toán giá tại Backend
            // **********************************************
            BigDecimal priceAtOrder = product.getSellingPrice();

            // TODO: Kiểm tra tồn kho (Bỏ qua tạm thời, cần thêm logic Inventory)

            OrderItem item = new OrderItem();
            item.setOrder(order); // Liên kết ngược
            item.setProduct(product);
            item.setQuantity(itemDto.getQuantity());
            item.setPriceAtOrder(priceAtOrder);

            orderItems.add(item);

            calculatedTotal = calculatedTotal.add(priceAtOrder.multiply(new BigDecimal(itemDto.getQuantity())));
        }

        // 4. Kiểm tra tổng tiền (để chống gian lận/sai sót)
        // Đây là một bước bảo mật: so sánh tổng tiền tính toán ở Backend với tổng tiền Frontend gửi lên.
        if (calculatedTotal.compareTo(request.getTotalAmount()) != 0) {
            // Log warning hoặc throw Exception nếu chênh lệch lớn
            System.err.println("Cảnh báo: Tổng tiền Backend tính toán (" + calculatedTotal + ") khác Frontend (" + request.getTotalAmount() + ").");
        }

        order.setTotalAmount(calculatedTotal);
        order.setItems(orderItems);

        // 5. Lưu Order và OrderItems
        Order savedOrder = orderRepository.save(order);

        // Do OrderItem có CascadeType.ALL, chúng sẽ được lưu cùng Order

        return savedOrder;
    }
}