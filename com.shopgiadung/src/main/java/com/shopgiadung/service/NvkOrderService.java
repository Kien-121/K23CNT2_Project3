package com.shopgiadung.service;

import com.shopgiadung.dto.NvkOrderItemDto;
import com.shopgiadung.dto.NvkOrderRequest;
import com.shopgiadung.entity.*;
import com.shopgiadung.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class NvkOrderService {

    @Autowired
    private NvkOrderRepository orderRepository;

    @Autowired
    private NvkProductRepository productRepository;

    @Autowired
    private NvkUserRepository userRepository;

    @Autowired
    private NvkInventoryRepository inventoryRepository;

    /**
     * Helper: Lấy ID của người dùng đang đăng nhập từ Token JWT
     */
    private Long getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            String email = ((UserDetails) authentication.getPrincipal()).getUsername();
            NvkUser user = userRepository.findByEmail(email)
                    .orElseThrow(() -> new RuntimeException("Lỗi xác thực: Không tìm thấy người dùng."));
            return user.getId();
        }
        throw new RuntimeException("Yêu cầu cần đăng nhập.");
    }

    /**
     * ADMIN: Lấy tất cả đơn hàng, sắp xếp mới nhất lên đầu
     */
    public List<NvkOrder> getAllOrders() {
        return orderRepository.findAll(Sort.by(Sort.Direction.DESC, "orderDate"));
    }

    /**
     * CUSTOMER: Lấy danh sách đơn hàng của người dùng hiện tại
     */
    public List<NvkOrder> getMyOrders() {
        Long userId = getCurrentUserId();
        // Lưu ý: Cần đảm bảo NvkOrderRepository có method này
        return orderRepository.findByUser_IdOrderByOrderDateDesc(userId);
    }

    /**
     * ADMIN/CUSTOMER: Cập nhật trạng thái đơn hàng
     */
    public NvkOrder updateOrderStatus(Long orderId, String newStatus) {
        NvkOrder order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy đơn hàng ID: " + orderId));

        try {
            NvkOrderStatus status = NvkOrderStatus.valueOf(newStatus);
            // Có thể thêm logic kiểm tra: Nếu user thường thì chỉ được CANCELLED khi đang PENDING
            order.setOrderStatus(status);
            return orderRepository.save(order);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Trạng thái không hợp lệ: " + newStatus);
        }
    }

    /**
     * CUSTOMER: Tạo đơn hàng mới
     */
    @Transactional
    public NvkOrder createOrder(NvkOrderRequest request) {
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
            // Kiểm tra sản phẩm
            NvkProduct product = productRepository.findById(itemDto.getProductId())
                    .orElseThrow(() -> new RuntimeException("Sản phẩm không tồn tại: ID " + itemDto.getProductId()));

            // Kiểm tra và trừ kho
            NvkInventory inventory = inventoryRepository.findByProductId(itemDto.getProductId())
                    .orElseThrow(() -> new RuntimeException("Lỗi dữ liệu kho cho sản phẩm: " + product.getName()));

            if (inventory.getStockQuantity() < itemDto.getQuantity()) {
                throw new RuntimeException("Sản phẩm '" + product.getName() + "' không đủ hàng (Còn: " + inventory.getStockQuantity() + ").");
            }

            inventory.setStockQuantity(inventory.getStockQuantity() - itemDto.getQuantity());
            inventoryRepository.save(inventory);

            // Tạo chi tiết đơn hàng
            BigDecimal priceAtOrder = product.getSellingPrice();
            NvkOrderItem item = new NvkOrderItem();
            item.setOrder(order);
            item.setProduct(product);
            item.setQuantity(itemDto.getQuantity());
            item.setPriceAtOrder(priceAtOrder);

            orderItems.add(item);
            calculatedTotal = calculatedTotal.add(priceAtOrder.multiply(new BigDecimal(itemDto.getQuantity())));
        }

        // Logic áp dụng voucher (nếu có, tính toán ở đây và update totalAmount)
        // ...

        order.setTotalAmount(calculatedTotal);
        order.setItems(orderItems);

        return orderRepository.save(order);
    }
}