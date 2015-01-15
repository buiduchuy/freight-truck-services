<%-- 
    Document   : form_search_deal
    Created on : Jan 15, 2015, 8:37:55 AM
    Author     : KhuongNguyen-PC
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<div class="row">
    <div class="form-group">
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
    <button type="button" class="btn btn-default">Tìm</button>
</div>

