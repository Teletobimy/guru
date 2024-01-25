 /* Shipment List */
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
        function updateLocation(category) {
            var newUrl = '/shipment/shipmentList?size=10&page=0&category=' + category;

            window.location.href = newUrl;
        }
        function performSearch(){
            var urlParams = new URLSearchParams(window.location.search);
            var category = urlParams.get('category');
            var searchValue = document.getElementById('searchInput').value;

            var encodedSearchValue = encodeURIComponent(searchValue);

            console.log(encodedSearchValue);
            console.log('Category:', category);

            window.location.href = '/shipment/shipmentList?size=10&page=0&category='+ category +'&keyword=' + encodedSearchValue;
        }
         document.getElementById('searchInput').addEventListener('keyup', function (event) {
            if (event.key === 'Enter') {
                performSearch();
            }
        });
        function gotoDetail(shipmentId){
            var newUrl = '/shipment/shipmentDetail?shipmentId=' + shipmentId;

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
       })

       $('input[name="daterange"]').val("StartDate -> EndDate");

       $('input[name="daterange"]').on('apply.daterangepicker', function(ev, picker) {
            var startDate = picker.startDate.format('YYYY-MM-DDTHH:mm:ss');
            var endDate = picker.endDate.format('YYYY-MM-DDTHH:mm:ss');
            var urlParams = new URLSearchParams(window.location.search);
            var category = urlParams.get('processStatus');
            var page = urlParams.get('page');
            var size = urlParams.get('size');
            var search = $('#searchInput').val();
            if(category === null){
                category = -1;
            }
            var newUrl =  "/shipment/shipmentList?page=" + page +
                            "&size=" + size +
                            "&category=" + category +
                            "&keyword=" + encodeURIComponent(search) +
                            "&startDate=" + startDate +
                            "&endDate=" + endDate;
            $(this).val("Choose Date");
          window.location.href = newUrl;
          });

          $('input[name="daterange"]').on('cancel.daterangepicker', function(ev, picker) {
              $(this).val('');
          });
        })


