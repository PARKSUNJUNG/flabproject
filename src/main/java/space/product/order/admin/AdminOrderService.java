package space.product.order.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import space.page.PageRequestDto;
import space.page.PageResponseDto;
import space.product.order.Order;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AdminOrderService {

    private final AdminOrderListRepository adminOrderListRepository;

    public PageResponseDto<AdminOrderListDto> getOrderList(PageRequestDto req) {

        Pageable pageable = PageRequest.of(
                req.getPage() -1,
                req.getSize(),
                Sort.by(Sort.Direction.DESC,"orderedAt")
        );

        List<Order> orders = adminOrderListRepository.findAdminList(pageable);

        long totalCount = adminOrderListRepository.countAdminList();

        List<AdminOrderListDto> content = orders.stream()
                .map(AdminOrderListDto::from)
                .toList();

        return new PageResponseDto<>(content, req, totalCount);
    }

    public AdminOrderDetailDto getOrderDetail(Long orderId) {

        Order order = adminOrderListRepository.findDetailById(orderId)
                .orElseThrow(()->new IllegalArgumentException("주문을 찾을 수 없습니다."));

        return AdminOrderDetailDto.from(order);
    }
}
