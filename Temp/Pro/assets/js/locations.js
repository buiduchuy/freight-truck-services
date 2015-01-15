if(geo_position_js.init()){
   geo_position_js.getCurrentPosition(success_callback,error_callback);
}
else{
   alert("Functionality not available");
}