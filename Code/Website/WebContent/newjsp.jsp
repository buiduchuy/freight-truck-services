<input type='date' id='date' />

<div id='date'></div>
<script>
dateVariable = undefined;

$("#date").datepicker({ 
    dateFormat: 'dd, MM, yy', 
    onClose: function(dateText) { 
        dateVariable = dateText; 
        alert(dateVariable); 
        alert($("#date").val()) 
    }
});

var ds = $.datepicker.formatDate('dd, MM, yy', new Date("20 April 2012"));
        
$("div#date").html(ds);
</script>