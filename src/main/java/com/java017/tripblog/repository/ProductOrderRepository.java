package com.java017.tripblog.repository;

import com.java017.tripblog.entity.ProductOrder;
import com.java017.tripblog.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author YuCheng
 * @date 2021/11/9 - 下午 10:29
 */

@Repository
public interface ProductOrderRepository extends JpaRepository<ProductOrder, Long> {
    List<ProductOrder> findAllByUser(User user);
    ProductOrder findByUuid(String  uuid);
    ProductOrder findByOrderStatus(int  orderStatus);

}
