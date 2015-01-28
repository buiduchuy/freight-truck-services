function formatNumber(e) {
    if (e.val().match(/^.+?\.0?$/)) return;
    e.parseNumber({ format: "#,##0.##", locale: "vn" });
    e.formatNumber({ format: "#,##0.##", locale: "vn" });
}
var ctrlDown = false;
function handleKeyDown(e) {
    if (e.which == 17) ctrlDown = true;
}
function handleKeyUp(e) {
    if (e.which == 17) ctrlDown = false;
}
function ignoreEvent(e) {
    if (e.which >= 16 && e.which <= 18) return true;
    if (e.which >= 33 && e.which <= 40) return true;
    if (ctrlDown && (e.which == 65 || e.which == 67)) return true;
    return false;
}

$(document).ready(function() {
    $("input[kmoney]").each(function() {
        formatNumber($(this));
    });
    $("input[kmoney]").keydown(function(e) {
        handleKeyDown(e);
    }).keyup(function(e) {
        handleKeyUp(e);
        if (!ignoreEvent(e)) formatNumber($(this));
    });
});