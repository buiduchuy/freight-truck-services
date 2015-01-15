<%-- 
    Document   : create-account
    Created on : Jan 12, 2015, 11:35:02 PM
    Author     : KhuongNguyen-PC
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <jsp:include page="header.jsp"/>
    </head>
    <body>
        <div class="wrapper">
            <jsp:include page="menu-ngang.jsp"/>
            <div class="container">
                <div id="page-content">
                    <!-- Breadcrumb -->
                    <div class="container">
                        <ol class="breadcrumb">
                            <li><a href="#">Trang Chủ</a></li>
                            <li class="active">Đăng Ký Tài Khoản</li>
                        </ol>
                    </div>
                    <!-- end Breadcrumb -->
                    <div class="container">
                        <header><h1>Đăng Ký Tài Khoản</h1></header>
                        <div class="row">
                            <div class="col-md-8 col-sm-6 col-md-offset-2 col-sm-offset-3">
                                <div class="row">
                                    <div class="col-sm-4">
                                        <h2>Tạo Tài Khoản FTS</h2>
                                        » Miêu tả chi tiết sản phẩm<br/>
                                        » Tin chỉ quảng cáo cho cửa hàng sẽ không được đăng.<br/>
                                        » Đăng tin bằng tiếng Việt có dấu.<br/>

                                    </div>
                                    <div class="col-sm-8">
                                        <h2>Tạo Tài Khoản FTS</h2>

                                        <form action="#" method="">
                                            <h4><b>Loại Tài Khoản</b></h4>
                                            <figure class="note">
                                                Chọn loại tài khoản muốn tạo</figure>
                                            <div class="row">
                                                <div class="col-sm-4">
                                                    <div class="radio" id="create-account-user">
                                                        <label>
                                                            <input type="radio" id="account-type-user" checked="" name="account-type" required>Chủ Hàng:
                                                        </label>
                                                    </div>
                                                </div>
                                                <div class="col-sm-8">
                                                    <div class="radio" id="agent-switch" data-agent-state="">
                                                        <label>
                                                            <input type="radio" id="account-type-agent" name="account-type" required>Tài Xế Xe Tải:
                                                        </label>
                                                    </div>
                                                    <div id="agency" class="disabled">
                                                        <div class="row">
                                                            <div class="col-sm-6">
                                                                <div class="form-group">
                                                                    <label for="payload-truck">Chọn Trọng Tải:</label>
                                                                    <select name="payload-truck">
                                                                        <option><16 Tấn</option>
                                                                        <option><24 Tấn</option>
                                                                        <option><30 Tấn</option>
                                                                        <option><34 Tấn</option>
                                                                        <option><44 Tấn</option>
                                                                    </select>
                                                                </div><!-- /.Payload-->   
                                                            </div>
                                                            <div class="col-sm-6">
                                                                <div class="form-group"> 
                                                                    <label for="account-agency">Thùng đông lạnh:</label><br/>
                                                                    <label>
                                                                        <input type="radio" checked="" name="contrainer" required>Có
                                                                    </label>
                                                                    <label>
                                                                        <input type="radio" name="contrainer" required>Không
                                                                    </label>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div><!-- /.Truck Driver -->
                                                </div>
                                            </div>
                                            <h4><b>Tên xưng hô</b></h4>
                                            <figure class="note">
                                                Vui lòng nhập tên đầy đủ của bạn</figure>
                                            <input type="text" class="form-control" placeholder="Điền tên đầy đủ" required />
                                            <h4><b>FTS ID và mật khẩu</b></h4>
                                            <figure class="note">
                                                Để đăng nhập và tìm lại mật khẩu, không công khai.</figure>
                                            <div class="form-group">
                                                <input type="email" class="form-control" placeholder="Điền email" required/>
                                            </div>


                                            <div class="form-group">
                                                <input type="password" class="form-control" placeholder="Điền mật khẩu" required>
                                            </div>
                                            <div class="form-group">
                                                <input type="password" class="form-control" placeholder="Xác nhận mật khẩu" required/>
                                            </div>
                                            <h4><b>Câu hỏi bí mật</b></h4>
                                            <figure class="note">Chọn ba câu hỏi bảo mật dưới đây . Những câu hỏi này sẽ giúp chúng tôi xác minh danh tính của bạn,
                                                nếu quên mật khẩu của bạn .</figure>
                                            <select>
                                                <option selected="">Chọn câu hỏi bí mật</option> 
                                            </select>
                                            <input type="text" class="form-control" placeholder="Điền câu trả lời" required>
                                            <hr/>
                                            <select>
                                                <option selected="">Chọn câu hỏi bí mật</option> 
                                            </select>
                                            <input type="text" class="form-control" placeholder="Điền câu trả lời" required>
                                            <hr/>
                                            <select>
                                                <option selected="">Chọn câu hỏi bí mật</option> 
                                            </select>
                                            <input type="text" class="form-control" placeholder="Điền câu trả lời" required>
                                            <h4><b>Ngày sinh</b></h4>
                                            <figure class="note">Kết hợp với câu hỏi bảo mật của bạn , 
                                                điều này sẽ giúp chúng tôi xác minh danh tính của bạn nếu bạn quên mật khẩu 
                                                của bạn hoặc cần phải thiết lập lại nó .</figure>
                                            <div class="row">
                                                <div class="col-sm-4">
                                                    <select>
                                                        <option selected="">Ngày</option> 
                                                    </select>
                                                </div>
                                                <div class="col-sm-4">
                                                    <select>
                                                        <option selected="">Tháng</option> 
                                                    </select>
                                                </div>
                                                <div class="col-sm-4">
                                                    <select>
                                                        <option selected="">Năm</option> 
                                                    </select>
                                                </div>
                                            </div>
                                            <h4><b>Số điện thoại</b></h4>
                                            <figure class="note">Số điện thoại của bạn sẽ giúp chúng tôi giữ an toàn cho tài khoản của bạn </figure>
                                            <div class="form-group">
                                                <input type="text" class="form-control" placeholder="Xác nhận số điện thoại" required/>
                                            </div>
                                            <figure class="note">Số điện thoại dùng để xác minh tạo tài khoản (yêu cầu chính xác).</figure>
                                            <h4><b>Nhập các ký tự mà bạn nhìn thấy</b></h4>
                                            <div class="form-group">

                                                <img src="img/capcha_demo.jpg" alt=""/>
                                            </div>
                                            <div class="left">
                                                <figure class="note">Nhấn vào nút "Tạo tài khoản" đồng nghĩ với việc chấp thuận chính sách bảo mật của FTS</figure>
                                            </div>
                                            <hr/>
                                            <div class="form-group clearfix">
                                                <button type="submit" class="btn pull-left btn-default" id="account-submit">Tạo tài khoản</button>
                                            </div>
                                    </div>
                                </div>
                            </div>  
                        </div>
                    </div>
                </div>
                <jsp:include page="footer.jsp"/>
            </div>
        </div>
    </body>
</html>

