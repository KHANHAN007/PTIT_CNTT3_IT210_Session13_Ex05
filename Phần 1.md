Lỗi: LazyInitializationException

Kịch bản xảy ra:

Entity Prescription có details (LAZY)
Controller lấy danh sách đơn thuốc
View (Thymeleaf) truy cập prescription.details
Session đã đóng → Hibernate không load được dữ liệu → lỗi
Cách khắc phục
Dùng HQL JOIN FETCH
FROM Prescription p LEFT JOIN FETCH p.details
Hoặc chuyển sang:
fetch = FetchType.EAGER
Hoặc đảm bảo truy vấn nằm trong transaction (@Transactional)