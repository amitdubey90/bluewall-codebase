<script type="text/javascript">
myFunction = function(val) {
		var colorInc = 100 / 3;
    	
	    if(val != ""
	      && !isNaN(val)
	      && val <= 100
	      && val >= 0)
	    {
	      console.log(val);
	      
	      var valOrig = val;
	      val = 100 - val;
	      
	      if(valOrig == 0)
	      {
	        $("#abc").val(0);
	        $(".progress .percent").text(0 + "%");
	      }
	      else $(".progress .percent").text(valOrig + "%");
	      
	      $(".progress").parent().removeClass();
	      $(".progress .water").css("top", val + "%");
	      
	      if(valOrig < colorInc * 1)
	        $(".progress").parent().addClass("red");
	      else if(valOrig < colorInc * 2)
	        $(".progress").parent().addClass("orange");
	      else
	        $(".progress").parent().addClass("green");
	    }
	    else
	    {
	     /* $(".progress").parent().removeClass();
	      $(".progress").parent().addClass("green");
	      $(".progress .water").css("top", 100 - 67 + "%");
	      $(".progress .percent").text(67 + "%");
	      $("#abc").val("");*/
	    }
	}
</script>