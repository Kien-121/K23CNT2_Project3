package com.shopgiadung.service;

import com.shopgiadung.dto.NvkOrderItemDto;
import com.shopgiadung.dto.NvkOrderRequest;
import com.shopgiadung.entity.*;
import com.shopgiadung.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Sort; // Thêm import này

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class NvkOrderService {

    @Autowired private NvkOrderRepository orderRepository;
    @Autowired private NvkProductRepository productRepository;
    @Autowired private NvkUserRepository userRepository;
    @Autowired private NvkInventoryRepository inventoryRepository;

    private Long getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            String email = ((UserDetails) authentication.getPrincipal()).getUsername();
            NvkUser user = userRepository.findByEmail(email)
                    .orElseThrow(() -> new RuntimeException("Lỗi: Không tìm thấy User."));
            return user.getId();
        }
        throw new RuntimeException("Yêu cầu cần xác thực.");
    }

    public List<NvkOrder> getAllOrders() {
        // Sắp xếp đơn hàng mới nhất lên đầu
        return orderRepository.findAll(Sort.by(Sort.Direction.DESC, "orderDate"));
    }

    // --- MỚI: Cập nhật trạng thái đơn hàng ---
    public NvkOrder updateOrderStatus(Long orderId, String newStatus) {
        NvkOrder order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy đơn hàng ID: " + orderId));

        try {
            NvkOrderStatus status = NvkOrderStatus.valueOf(newStatus);
            order.setOrderStatus(status);
            return orderRepository.save(order);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Trạng thái không hợp lệ: " + newStatus);
        }
    }

    @Transactional
    public NvkOrder createOrder(NvkOrderRequest request) {
        // ... (Giữ nguyên logic tạo đơn hàng cũ của bạn) ...
        Long userId = getCurrentUserId();
        NvkUser user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found: " + userId));

        NvkOrder order = new NvkOrder();
        order.setUser(user);
        order.setReceiverName(request.getReceiverName());
        order.setReceiverPhone(request.getReceiverPhone());
        order.setShippingAddress(request.getShippingAddress());

        try {
            order.setPaymentMethod(NvkPaymentMethod.valueOf(request.getPaymentMethod()));
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Phương thức thanh toán không hợp lệ.");
        }

        BigDecimal calculatedTotal = BigDecimal.ZERO;
        List<NvkOrderItem> orderItems = new ArrayList<>();

        for (NvkOrderItemDto itemDto : request.getItems()) {
            NvkProduct product = productRepository.findById(itemDto.getProductId())
                    .orElseThrow(() -> new RuntimeException("Product not found: " + itemDto.getProductId()));

            NvkInventory inventory = inventoryRepository.findByProductId(itemDto.getProductId())
                    .orElseThrow(() -> new RuntimeException("Inventory not found: " + itemDto.getProductId()));

            if (inventory.getStockQuantity() < itemDto.getQuantity()) {
                throw new RuntimeException("Sản phẩm '" + product.getName() + "' không đủ hàng.");
            }

            // Giảm tồn kho
            inventory.setStockQuantity(inventory.getStockQuantity() - itemDto.getQuantity());
            inventoryRepository.save(inventory);

            BigDecimal priceAtOrder = product.getSellingPrice();

            NvkOrderItem item = new NvkOrderItem();
            item.setOrder(order);
            item.setProduct(product);
            item.setQuantity(itemDto.getQuantity());
            item.setPriceAtOrder(priceAtOrder);

            orderItems.add(item);
            calculatedTotal = calculatedTotal.add(priceAtOrder.multiply(new BigDecimal(itemDto.getQuantity())));
        }

        order.setTotalAmount(calculatedTotal);
        order.setItems(orderItems);
        return orderRepository.save(order);
    }
}