<script type="text/javascript">
        $(function() {
                $(".search").autocomplete({ 
                	
                source : function(request, response) {
                $.ajax({
                        url : "/user/food/getFoodItems",
                        type : "GET",
                        data : {
                                foodName : request.term
                        },
                        dataType : "json",
                        success : function(data) {
                        	 
                        	response($.map(data, function(v,i){
                        	    return {
                        	                label: v.foodName+" "+v.foodCalorie+" Kcals",
                        	                value: v.foodId+" : "+v.foodName+" : "+v.foodCalorie+" Kcals"
                        	                
                        	               };
                        	}));
                        	
                        }
                });
        }
}).data('ui-autocomplete')._renderItem = function( ul, item ) {
                	var $a = $("<a></a>").text(item.label);
    				highlightText(this.term, $a);
    				return $("<li></li>").append($a).appendTo(ul);
            };
    
});

        function highlightText(text, $node) {
			var searchText = $.trim(text).toLowerCase(), currentNode = $node.get(0).firstChild, matchIndex, newTextNode, newSpanNode;
			while ((matchIndex = currentNode.data.toLowerCase().indexOf(searchText)) >= 0) {
				newTextNode = currentNode.splitText(matchIndex);
				currentNode = newTextNode.splitText(searchText.length);
				newSpanNode = document.createElement("span");
				newSpanNode.className = "highlight";
				currentNode.parentNode.insertBefore(newSpanNode, currentNode);
				newSpanNode.appendChild(newTextNode);
			}
		}

           

</script>