<%@page contentType="text/html" pageEncoding="UTF-8"%>
<footer>
    <section class="footer-copyright">
        <div class="row">
            <div class="small-6 large-6 columns">
                Bản quyền © 2015
            </div> 
            <div class="small-6 large-6 columns">
                <nav id="footer-nav" class="text-right">
                    <ul id="bot-nav" class="inline-list">
                        <li>
                            <a href="index.jsp" target="_blank">Trang chủ</a>
                        </li>
                        <li>
                            <a href="#" target="_blank">Giới thiệu</a>
                        </li>
                    </ul>
                </nav>

            </div>
        </div>
    </section>
</footer>
<div id="notify" class="notify">
</div>
<script>
    function dlgCancel() {
        dlgHide();
        document.getElementsByTagName("H1")[0].innerHTML = "You clicked Cancel.";
    }

    function dlgOK() {
        dlgHide();
        document.getElementsByTagName("H1")[0].innerHTML = "You clicked OK.";
    }

    function dlgHide() {
        var whitebg = document.getElementById("white-background");
        var dlg = document.getElementById("dlgbox");
        whitebg.style.display = "none";
        dlg.style.display = "none";
    }
  
    function showDialog() {
        var whitebg = document.getElementById("white-background");
        var dlg = document.getElementById("dlgbox");
        whitebg.style.display = "block";
        dlg.style.display = "block";

        var winWidth = window.innerWidth;

        dlg.style.left = (winWidth / 2) - 480 / 2 + "px";
        dlg.style.top = "150px";
    }
</script>
<script>
    $(document).foundation();
</script> 


<script type="text/javascript" src="js/foundation.js" charset="UTF-8"></script>
<script type="text/javascript" src="js/scripts.js" charset="UTF-8"></script>
<script type="text/javascript" src="js/foundation.min.js" charset="UTF-8"></script>
<script type="text/javascript" src="js/civem-0.0.7.min.js" charset="UTF-8"></script>
</body>
</html>
