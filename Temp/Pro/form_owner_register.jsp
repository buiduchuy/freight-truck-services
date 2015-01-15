<%-- 
    Document   : form_register
    Created on : Jan 15, 2015, 4:27:37 AM
    Author     : KhuongNguyen-PC
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<div class="modal fade bs-example-modal-sl" id="form_owner_register"  tabindex="-1" role="dialog" aria-labelledby="mySmallModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-sl">
        <div class="modal-content">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                    <h4 class="modal-title" id="myModalLabel">Đăng ký tài khoản</h4>
                </div>
                <div class="modal-body">
                    <div class="text-right"><figure class="note">* Bắt buộc</figure></div>
                    <div class="panel-group" id="accordion" role="tablist" aria-multiselectable="true">
                        <div class="panel panel-default">
                            <div class="panel-heading" role="tab" id="headingOne">
                                <h4 class="panel-title">
                                    <a data-toggle="collapse" data-parent="#accordion" href="#collapseOne" aria-expanded="true" aria-controls="collapseOne">
                                        <label>Tài khoản</label>
                                    </a>
                                </h4>
                            </div>
                            <div id="collapseOne" class="panel-collapse collapse in" role="tabpanel" aria-labelledby="headingOne">
                                <div class="panel-body">
                                    <div class="form-group">
                                        * Email
                                        <input type="email" class="form-control" id="exampleInputEmail1" placeholder="name@example.com">
                                    </div>
                                    <div class="form-group">
                                        * Password
                                        <input type="password" class="form-control" id="exampleInputPassword1" placeholder="At least 5 characters">
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="panel panel-default">
                            <div class="panel-heading" role="tab" id="headingTwo">
                                <h4 class="panel-title">
                                    <a class="collapsed" data-toggle="collapse" data-parent="#accordion" href="#collapseTwo" aria-expanded="false" aria-controls="collapseTwo">
                                        <label> Hồ sơ</label>
                                    </a>
                                </h4>
                            </div>
                            <div id="collapseTwo" class="panel-collapse collapse" role="tabpanel" aria-labelledby="headingTwo">
                                <div class="panel-body">
                                    <div class="form-group">
                                        *Họ và tên:
                                        <div class="row">
                                            <div class="col-sm-6">
                                                <input type="text" class="form-control" placeholder="Họ">
                                            </div>
                                            <div class="col-sm-6">
                                                <input type="text" class="form-control"  placeholder="Tên">
                                            </div>
                                        </div>
                                        *Số điện thoại:
                                        <input type="text" class="form-control"  placeholder="123 4567 8910">
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="panel panel-default">
                            <div class="panel-heading" role="tab" id="headingThree">
                                <h4 class="panel-title">
                                    <a class="collapsed" data-toggle="collapse" data-parent="#accordion" href="#collapseThree" aria-expanded="false" aria-controls="collapseThree">
                                        <label> Thanh toán</label>
                                    </a>
                                </h4>
                            </div>
                            <div id="collapseThree" class="panel-collapse collapse" role="tabpanel" aria-labelledby="headingThree">
                                <div class="panel-body">
                                    <div class="row">
                                        <div class="form-group">
                                            <div class="col-sm-10">
                                                *Số thẻ tín dụng:
                                                <input type="text" class="form-control" placeholder="1234 5678 9012 3456">
                                            </div>
                                            <div class="col-sm-2">
                                                *CVV:
                                                <input type="text" class="form-control"  placeholder="123">
                                            </div>
                                        </div>
                                    </div>
                                    <div class="row">
                                        <div class="col-sm-8">
                                            *Ngày hết hạn:
                                            <div class="row">
                                                <div class="col-sm-6">
                                                    <select>
                                                        <option>Tháng</option>
                                                        <option>Tháng</option>
                                                        <option>Tháng</option>
                                                        <option>Tháng</option>
                                                    </select>
                                                </div>
                                                <div class="col-sm-6">
                                                    <select>
                                                        <option>Năm</option>
                                                        <option>Năm</option>
                                                        <option>Năm</option>
                                                        <option>Năm</option>
                                                    </select>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="col-sm-4">
                                            *Mã bưu chính:
                                            <input type="text" class="form-control"  placeholder="123 4567 8910">
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <a href="#">Điều khoản, điều kiện và chính sách bảo mật</a>
                </div>

            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">Đóng</button>
                <button type="button" class="btn btn-primary tool-tip" title=" Nhấn vào Tạo tài khoản nếu bạn đồng ý các điều khoản">Tạo tài khoản OWNER</button>
                <button type="button" class="btn btn-primary tool-tip" title=" Nhấn vào Tạo tài khoản nếu bạn đồng ý các điều khoản">Tạo tài khoản DRIVER</button>
                <figure class="note">Hãy điền vào tất cả các yêu cầu ( * )</figure>
            </div>
        </div>
    </div>
</div>

