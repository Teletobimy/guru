/*Shipment Detail Page*/
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
    function gotoList(){
        var newUrl = '/shipment/shipmentList?size=10&page=0&category=-1'
        window.location.href= newUrl;
    }

    function addQcCheck(){
        var shipmentId = $("input[name=shipmentId]").val();
        var modal = document.getElementById("modalP");

        $.ajax({
            type:'POST',
            url:'/shipment/addQcCheck?shipmentId=' + shipmentId,
            success:function(res){
                 Swal.fire({
                      position: "center",
                      icon: "success",
                      title: "Success!",
                      text:"자재 검수 처리를 시작합니다.",
                      showConfirmButton: false,
                      timer: 1500
                    });
            },
            error: function(xhr, status, error){
                if(xhr.status === 403){
                    var message = xhr.responseText;

                     Swal.fire({
                          position: "center",
                          icon: "error",
                          title: "Warning",
                          text: message,
                          showConfirmButton: false,
                          timer: 1500
                        });


                } else{
                    console.error("Error: " , status, error);
                }
            }
        })
    }