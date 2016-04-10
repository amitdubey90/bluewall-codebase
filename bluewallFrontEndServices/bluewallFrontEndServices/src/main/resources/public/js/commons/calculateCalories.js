<script type="text/javascript">
$('#foodWeight').keyup(function () {

var arr = $('#foodName').val().split(':');

var calVal = $.trim(arr[2]).split(' ');

var totalCal = (calVal[0]*this.value)/100;
  $('#foodCalories').val(totalCal);
});

</script>