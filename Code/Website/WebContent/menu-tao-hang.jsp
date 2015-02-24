<%-- 
    Document   : menu-tao-hang
    Created on : Feb 5, 2015, 12:46:19 AM
    Author     : KhuongNguyen-PC
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:set var="router" value="${sessionScope.router}" />
<c:set var="good" value="${sessionScope.good}" />
<c:set var="price" value="${sessionScope.price}" />
<c:set var="namePage" value="${sessionScope.namePage}" />
<div class="row"></div>
<div class="large-12 columns">
	<h1 class="page-title">
		<font color="orange">Tạo hàng</font>
	</h1>
	<c:set var="messageSuccess" value="${sessionScope.messageSuccess }" />
			<c:set var="messageError" value="${sessionScope.messageError }" />
			<c:if test="${not empty messageSuccess}">
				<div class="row">
					<div data-alert class="alert-box success radius inline">
						${messageSuccess} <a href="#" class="close">&times;</a>
					</div>
				</div>
				<%
					request.getSession().removeAttribute("messageSuccess");
				%>
			</c:if>
			<c:if test="${not empty messageError}">
				<div class="row">
					<div data-alert class="alert-box alert radius inline">
						${messageError} <a href="#" class="close">&times;</a>
					</div>

				</div>
				<%
					request.getSession().removeAttribute("messageError");
				%>
			</c:if>
	<ol class="progtrckr" data-progtrckr-steps="5">
    <li class="progtrckr-todo">Order Processing</li><!--
 --><li class="progtrckr-todo">Pre-Production</li><!--
 --><li class="progtrckr-todo">In Production</li><!--
 --><li class="progtrckr-todo">Shipped</li><!--
 --><li class="progtrckr-todo">Delivered</li>
</ol>
</div>
<script src="http://code.jquery.com/jquery-1.11.0.min.js"></script>
	<style>
		ol.progtrckr {
		    margin: 0;
		    padding: 0;
		    list-style-type none;
		}

		ol.progtrckr li {
	    	display: inline-block;
	    	text-align: center;
	    	line-height: 3em;
		}

		ol.progtrckr[data-progtrckr-steps="2"] li { width: 49%; }
		ol.progtrckr[data-progtrckr-steps="3"] li { width: 33%; }
		ol.progtrckr[data-progtrckr-steps="4"] li { width: 24%; }
		ol.progtrckr[data-progtrckr-steps="5"] li { width: 19%; }
		ol.progtrckr[data-progtrckr-steps="6"] li { width: 16%; }
		ol.progtrckr[data-progtrckr-steps="7"] li { width: 14%; }
		ol.progtrckr[data-progtrckr-steps="8"] li { width: 12%; }
		ol.progtrckr[data-progtrckr-steps="9"] li { width: 11%; }		

		ol.progtrckr li.progtrckr-done {
			    color: black;
			    border-bottom: 4px solid yellowgreen;
			}
			ol.progtrckr li.progtrckr-todo {
			    color: silver; 
			    border-bottom: 4px solid silver;
			}

			ol.progtrckr li:after {
			    content: "\00a0\00a0";
			}
			ol.progtrckr li:before {
			    position: relative;
			    bottom: -2.5em;
			    float: left;
			    left: 50%;
			    line-height: 1em;
			}
			ol.progtrckr li.progtrckr-done:before {
			    content: "\2713";
			    color: white;
			    background-color: yellowgreen;
			    height: 1.2em;
			    width: 1.2em;
			    line-height: 1.2em;
			    border: none;
			    border-radius: 1.2em;
			}
			ol.progtrckr li.progtrckr-todo:before {
			    content: "\039F";
			    color: silver;
			    background-color: white;
			    font-size: 1.5em;
			    bottom: -1.6em;
			}
	</style>
	<script>


$(document).ready(function(){

$(window).load(function(){
    $("ol.progtrckr").each(function(){
        $(this).attr("data-progtrckr-steps", 
                     $(this).children("li").length);
    });
})


$("#submit").click(function(event){

	event.preventDefault();

	$("ol.progtrckr").find("li.progtrckr-todo:first").removeClass("progtrckr-todo").addClass("progtrckr-done");


});


});


</script>