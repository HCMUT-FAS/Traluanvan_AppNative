# Tra luan van tot nghiep app - Faculity of Applied Science
App được viết bằng Android SDK (Android Studio) và được viết bằng Java 
## Tính năng
Có các 2 chức năng chính
### Tra luận văn
Hỗ trợ việc tra cứu tên luận văn trong database của Khoa Khoa học Ứng Dụng
Có thể tìm kiếm từ tên luận văn, tên luận văn tiếng anh, tên sinh viên, và tên giảng viên
Chỉ có thể tìm kiếm có dấu (VD: ngủ gõ ngu sẽ tìm không trả đúng kết quả)
### Gửi yêu cầu mượn luận văn tới server
Hỗ trợ điền form thong tin để gửi yêu cầu mượn luận văn tới web server
Có chức năng kiểm tra điền thiếu thông tin
### Đăng nhập bằng tài khoản google
Hiện tại đã có thể cho người sử dụng tài khoản google đăng nhập vào app
Ý tưởng chỉ cho sinh viên trường mượn luận văn nên chỉ những ai đăng nhập bằng tài khoản Bách Khoa HCM mới có thể gửi yêu cầu mượn tới server
## Cấu trúc app
App được xây dựng với các class chính 
### Database
Sử dụng thư viện SQLite 
Gồm có 1 bảng luận văn 
Đã có thể lấy dữ liệu có sẵn (gói dưới dạng zip và để vào asset) đưa vào database
### List luận văn
Đây là class trung gian để chứa kết quả khi query từ database hoặc add vào database
Đóng vai trò là một class trung gian để chứa và chỉnh sửa dữ liệu
Các câu lệnh phân loại sau này của class adapter sẽ dựa vào đây
### Class Adapter
Đây là class sẽ đổ dữ liệu từ class List luận văn lên các thành phần giao diện
Sử dụng adapter của recyclerview và ViewHolder để custom giao diện theo yêu cầu
Đóng vai trò sẽ giái quyết các vấn đề logic trước khi đổ dữ liệu lên các thành phần giao diện
### Class Connect server
Sử dụng thư viện Volley 
Là class giữ vai trò tương tác với server
## Giao diện
Gồm có 1 Activity chính và 4 fragment con
Có sử dụng Navigation (chưa được tối ưu)
Có các thành phần layout riêng cho các loại item (VD 1SV-1GV,1SV-2GV,2SV-1GV,2SV-2GV)
