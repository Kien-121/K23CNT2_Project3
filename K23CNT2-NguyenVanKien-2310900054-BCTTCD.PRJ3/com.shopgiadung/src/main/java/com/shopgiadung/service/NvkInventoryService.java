package com.shopgiadung.service;

import com.shopgiadung.entity.NvkInventory;
import com.shopgiadung.repository.NvkInventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class NvkInventoryService {

    @Autowired
    private NvkInventoryRepository inventoryRepository;

    /**
     * Lấy danh sách tất cả sản phẩm trong kho
     * @return List<NvkInventory>
     */
    public List<NvkInventory> getAllInventory() {
        return inventoryRepository.findAll();
    }

    /**
     * Lấy thông tin kho của một sản phẩm cụ thể
     * @param productId ID sản phẩm
     * @return Optional<NvkInventory>
     */
    public Optional<NvkInventory> getInventoryByProductId(Long productId) {
        return inventoryRepository.findByProductId(productId);
    }

    /**
     * Cập nhật số lượng tồn kho cho một sản phẩm
     * @param productId ID sản phẩm
     * @param newQuantity Số lượng mới
     * @return NvkInventory đã cập nhật
     */
    @Transactional
    public NvkInventory updateStock(Long productId, Integer newQuantity) {
        if (newQuantity < 0) {
            throw new IllegalArgumentException("Số lượng tồn kho không thể âm.");
        }

        NvkInventory inventory = inventoryRepository.findByProductId(productId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy thông tin kho cho sản phẩm ID: " + productId));

        inventory.setStockQuantity(newQuantity);
        // updatedAt sẽ tự động cập nhật nhờ @PreUpdate hoặc logic trong Entity (nếu có),
        // hoặc bạn có thể set thủ công: inventory.setUpdatedAt(LocalDateTime.now());

        return inventoryRepository.save(inventory);
    }
}