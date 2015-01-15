<%-- 
    Document   : form_create_deal
    Created on : Jan 15, 2015, 6:44:50 AM
    Author     : KhuongNguyen-PC
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>





<div class="modal fade bs-example-modal-sl" id="form_create_deal"  tabindex="-1" role="dialog" aria-labelledby="mySmallModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-sl">
        <div class="modal-content">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                    <h4 class="modal-title" id="myModalLabel">Đăng hàng</h4>
                </div>
                <div class="modal-body">
                    <div class="row row-fluid">
                        <div class="row">
                            <div class="form-group">
                                <div class="col-sm-6">
                                    Khối lượng
                                    <input type="text" class="form-control" placeholder="">
                                    Nơi khởi hành
                                    <input type="text" class="form-control" placeholder="">
                                    Nơi kết thúc
                                    <input type="text" class="form-control"  placeholder="">
                                    Thời gian
                                    <div class="form-group">
                                        <div class="controls input-append date form_date" data-date="" data-date-format="dd MM yyyy" data-link-field="dtp_input2" data-link-format="yyyy-mm-dd">
                                            <input size="16" type="text" value="" readonly>
                                            <span class="add-on"><i class="icon-remove"></i></span>
                                            <span class="add-on"><i class="icon-th"></i></span>
                                        </div>
                                    </div>
                                </div>
                                <div class="col-sm-6">
                                    <div class="form-group">
                                        Ghi chú
                                        <textarea class="form-control" id="message-text" rows="10"></textarea>
                                    </div>
                                </div>
                            </div>
                        </div>








                    </div>
                </div>

            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">Đóng</button>
                <button type="button" class="btn btn-primary tool-tip" title="Điền đủ thông tin trước khi đăng hãng">Đăng hàng</button>
            </div>
        </div>
    </div>
</div>