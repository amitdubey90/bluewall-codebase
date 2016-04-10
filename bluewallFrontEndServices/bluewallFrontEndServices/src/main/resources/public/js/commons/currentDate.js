<script type="text/javascript">
$(document).ready( function() {
    var now = new Date();
 
    var day = ("0" + now.getDate()).slice(-2);
    var month = ("0" + (now.getMonth() + 1)).slice(-2);

    var today = now.getFullYear()+"-"+(month)+"-"+(day);

   $('#datePicker').val(today);
  
   $.ajax({
	   type: "GET",
	   url: '/calorieDetails/13/'+today,
	   success: function(response) {
		 var res = response.split(",");
	     alert("Res1: " + res[0]);
	     alert("Res2: " + res[1]);
	     alert("Res3: " + parseInt(res[0]) - parseInt(res[1]));
	     $('#caloriesConsumed').val(res[0]);
	     $('#caloriesBurnt').val(res[1]);
	     $('#netCalories').val( parseInt(res[0]) - parseInt(res[1]) );
	  }
   	 }
   );
});
</script>