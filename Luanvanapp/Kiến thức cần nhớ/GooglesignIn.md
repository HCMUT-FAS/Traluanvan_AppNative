# Đăng ký project app với google ở 
Lần lượt đăng ký thoe hướng dẫn trên mạng ở các trang 
## Api ở gg
[ApiGG](https://console.cloud.google.com)
Ở đây yeu cầu cần phải cung cập cho google đầy đủ các thứ như email hổ trợ,Logo của app,domain bla bla(chả biết chi nhưng coi trên mạng người ta hay để là http://orhttps://)
## Đăng ký xác thực app ở firebase
[Firebase](https://console.firebase.google.com//)
Tại đây ta sẽ set up cho project các quyền ở mục authentication phần signin method
Cung cấp cho project mã SHA-1 trong app (có thể tra gg để coi mã trong máy)
Và email hỗ trợ 
# Một vài lỗi thường gặp 
## Lỗi 12500
Lỗi này khá mệt (theo cá nhân t thấy thế tại kiếm cách mệt vcl) nhưng đa phần lỗi có thể năm ở các nguyên do
- Update google service trong điện thoại lên bản mới nhất 
- Chưa cung cấp SHA-1 cho project
- Có quá nhiều project cùng mã SHA-1
-Chưa cung cấp email hỗ trợ
-Chưa mở quyền đăng nhập gg ở Sign-in method tại trang web [Firebase](https://console.firebase.google.com//)
-
-Chưa cung cấp token trong app
Có thể cần đăng nhập tk google trong Android Studio
[Xem thêm tại video](https://www.youtube.com/watch?v=_tpnqOeXvvk)
## Lỗi 10 
-  Chưa có client id từ file credentials.json lấy ở [Hướng dẫn của google](https://developers.google.com/identity/sign-in/android/start-integrating) 