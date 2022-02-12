# Để có thể gửi và lấy dữ liêu từ 1 server thì mình chọn thư viện volley của Google
## Gửi 
Cần nắm một tí về OOP (Cái này là t nghĩ là OOP thôi chứ chả biết phải ko)
- Lưu địa chỉ url từ server 
- Xác định dạng respone từ server (là String hay JSON Array hay JSON object,....)
-  Tùy vào dạng dữ liệu respone thì ta sẽ có các cách viết hàm khác nhau nhưng nhìn chung đều có đặc điểm 
`
 StringRequest stringRequest = new StringRequest(Request.Method.GET, Url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String ServerResponse) {
                        try{
                        //Xử lí dữ liệu nhận về 
                        }
                        catch (Exception e) {
                            //Xử lí lỗi
                                  
                                }
                    }
                } , new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        //Xử lí lỗi khi kết nối tới Url
                    }
                });
         RequestQueue requestQueue = Volley.newRequestQueue(context);
         //Context là gì thì t chưa hiểu mà đa phần nó là lớp Activity cần dùng
        // Adding the StringRequest object into requestQueue.
        requestQueue.add(stringRequest);
`
## Lấy dữ liệu tương tự do mà chưa vững nên chỉ biết dùng nối chuỗi ở Url mà thôi 