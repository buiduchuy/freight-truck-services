<%-- 
    Document   : manage-deals
    Created on : Jan 13, 2015, 3:03:29 PM
    Author     : KhuongNguyen-PC
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<jsp:include page="header.jsp"/>
<div class="row">
    <div class="col-sm-2">

    </div>
    <div class="col-sm-6">
        <h2>Thông tin hàng hoá</h2>
        <table class="table table-bordered">
            <tr>
                <td>#</td>
                <td>Tên bài</td>
                <td>Cột_1</td>
                <td>Cột_1</td>
                <td>Cột_1</td>
                <td>Gợi ý tài xế</td>
            </tr>
            <tr>
                <td>1</td>
                <td>Chuyển hàng rau wả</td>
                <td>Cột_1</td>
                <td>Cột_1</td>
                <td>Cột_1</td>
                <td><a href="find-driver.jsp"><button class="btn btn-default">Hiện danh sách</button></a></td>
            </tr>
            <tr>
                <td>2</td>
                <td>Chuyển hàng cá</td>
                <td>Cột_1</td>
                <td>Cột_1</td>
                <td>Cột_1</td>
                <td><button class="btn btn-danger" disabled="">Cant</button></td>
            </tr>
        </table>
    </div>
    <div class="col-sm-4">
        <button class="btn btn-primary" type="button" data-toggle="collapse" data-target="#collapseExample" aria-expanded="false" aria-controls="collapseExample">
            Tìm bài đăng
        </button>
        <div class="collapse" id="collapseExample">
            <div class="well">
                <jsp:include page="form_search_deal.jsp"/>
            </div>
        </div>
    </div>
</div>
<jsp:include page="footer.jsp"/>

