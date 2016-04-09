<script type="text/javascript">
$('#foodWeight').keyup(function () {

var arr = $('#foodName').val().split(':');

var calVal = $.trim(arr[2]).split(' ');

var totalCal = (calVal[0]*this.value)/100;
  $('#foodCalories').val(totalCal);
});


//$('#hours,#min,#activityType').change(function(){
//	var hours = parseInt($('#hours :selected').text()*60);
//	if(isNaN(hours)){
//		hours=0;
//	}
//	var minutes = parseInt($('#min :selected').text());
//	if(isNaN(minutes)){
//		minutes=0;
//	}
//	var time =  hours+minutes;
//	var met = $('#activityType :selected').val();
//	var calExp = 0.0175*met*30*time;
//	$('#activityCal').val(calExp.toFixed(2));
//	
//});
</script>