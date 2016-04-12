<script>
    new jQueryCollapse($("#custom-show-hide-example"), {
      open: function() {
        this.slideDown(150);
      },
      close: function() {
        this.slideUp(150);
      }
    });
</script>