Mô tả các Test Case:
testValidSearch:
Mục tiêu: Kiểm tra tìm kiếm hợp lệ với ga đi, ga đến và ngày đi hợp lệ.
Kịch bản: Nhập "Hà Nội" (ga đi), "Sài Gòn" (ga đến), ngày mai.
Kỳ vọng:
Chuyển hướng đến trang /booking.
Hiển thị danh sách chuyến tàu.
Có ít nhất một chuyến tàu được liệt kê.
testInvalidFromStation:
Mục tiêu: Kiểm tra trường hợp ga đi không hợp lệ.
Kịch bản: Nhập ga đi không tồn tại ("Invalid Station"), ga đến hợp lệ, ngày hợp lệ.
Kỳ vọng:
Hiển thị thông báo lỗi "Vui lòng chọn Ga đi hợp lệ".
Không chuyển hướng đến trang /booking.
testInvalidToStation:
Mục tiêu: Kiểm tra trường hợp ga đến không hợp lệ.
Kịch bản: Nhập ga đi hợp lệ, ga đến không tồn tại ("Invalid Station"), ngày hợp lệ.
Kỳ vọng:
Hiển thị thông báo lỗi "Vui lòng chọn Ga đến hợp lệ".
Không chuyển hướng đến trang /booking.
testSameStation:
Mục tiêu: Kiểm tra trường hợp ga đi và ga đến trùng nhau.
Kịch bản: Nhập "Hà Nội" cho cả ga đi và ga đến, ngày hợp lệ.
Kỳ vọng:
Hiển thị thông báo lỗi "Ga đi và Ga đến không được trùng nhau".
Không chuyển hướng đến trang /booking.
testPastDate:
Mục tiêu: Kiểm tra trường hợp ngày đi trong quá khứ.
Kịch bản: Nhập ga đi và ga đến hợp lệ, ngày trong quá khứ.
Kỳ vọng:
Hiển thị thông báo lỗi ngày không hợp lệ (giả định trang web có kiểm tra này).
Không chuyển hướng đến trang /booking.
testNoResults:
Mục tiêu: Kiểm tra trường hợp không có chuyến tàu cho dữ liệu tìm kiếm.
Kịch bản: Nhập ga đi và ga đến hợp lệ, ngày trong tương lai xa (30 ngày sau).
Kỳ vọng:
Chuyển hướng đến trang /booking.
Hiển thị thông báo "Không tìm thấy chuyến tàu".