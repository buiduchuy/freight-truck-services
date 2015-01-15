<%-- 
    Document   : form_login
    Created on : Jan 15, 2015, 3:59:00 AM
    Author     : KhuongNguyen-PC
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<div class="modal fade" id="form_login" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-sm">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="myModalLabel">Đăng nhập tài khoản</h4>
            </div>
            <div class="modal-body">
                    <input type="email" class="form-control" id="form-create-account-email" required size="35" placeholder="Email">
                    <input type="password" class="form-control" id="form-create-account-password" required size="35" placeholder="Password">
                <a href="#">Quên mật khẩu ?</a>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">Đóng</button>
                <button type="button" class="btn btn-primary tool-tip" title="Nhấn đăng nhập khi điều đủ thông tin đăng nhập">Đăng nhập</button>
            </div>
        </div>
    </div>
</div>
