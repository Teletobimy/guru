var $window = $(window);
    var nav = $('.fixed-button');
        $window.scroll(function(){
            if ($window.scrollTop() >= 200) {
             nav.addClass('active');
         }
         else {
             nav.removeClass('active');
         }
     });
      function gotoDetail(purchaseOderId){
            var newUrl = '/tradeDatail?purchaseOderId=' + purchaseOderId;

            window.location.href = newUrl;
        }
    $(document).ready(function(){
        $('input[name="daterange"]').daterangepicker({
            timePicker: true,
            startDate: moment().startOf('hour'),
            endDate: moment().endOf('hour'),
            locale: {
                format: 'YYYY-MM-DD HH:mm:ss'
            }
       });

       $('input[name="daterange"]').val("StartDate -> EndDate");

       $('input[name="daterange"]').on('apply.daterangepicker', function(ev, picker) {
            var startDate = picker.startDate.format('YYYY-MM-DDTHH:mm:ss');
            var endDate = picker.endDate.format('YYYY-MM-DDTHH:mm:ss');
            var urlParams = new URLSearchParams(window.location.search);
            var page = urlParams.get('page');
            var size = urlParams.get('size');
            var newUrl =  "/tradeList?page=" + page +
                            "&size=" + size +
                            "&startDate=" + startDate +
                            "&endDate=" + endDate;
            $(this).val("Choose Date");
            window.location.href = newUrl;
          });

          $('input[name="daterange"]').on('cancel.daterangepicker', function(ev, picker) {
              $(this).val('');
          });
    })