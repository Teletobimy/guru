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
           var newUrl = '/qcCheck/qcCheckList?size=10&page=0&processStatus=' + category;

           window.location.href = newUrl;
       }

       function performSearch(){
           var urlParams = new URLSearchParams(window.location.search);
           var category = urlParams.get('processStatus');
           var startDate = urlParams.get('startDate');
           var endDate = urlParams.get('endDate');
           var searchValue = document.getElementById('searchInput').value;

           var encodedSearchValue = encodeURIComponent(searchValue);

           if (!startDate || startDate === 'null') {
               startDate = null;
           }

           if (!endDate || endDate === 'null') {
               endDate = null;
           }

           window.location.href = '/qcCheck/qcCheckList?size=10&page=0&processStatus='+ category
                                   +'&search=' + encodedSearchValue
                                   + (startDate ? '&startDate=' + startDate : '')
                                   + (endDate ? '&endDate=' + endDate : '');
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

       $(document).ready(function() {

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

                   var newUrl =  "/qcCheck/qcCheckList?"+ page +
                                   "&size=" + size +
                                   "&processStatus=" + category +
                                   "&search=" + encodeURIComponent(search) +
                                   "&startDate=" + startDate +
                                   "&endDate=" + endDate;
                   $(this).val("Choose Date");
                 window.location.href = newUrl;
             });

             $('input[name="daterange"]').on('cancel.daterangepicker', function(ev, picker) {
                 $(this).val('');
             });

           var modalList = $('.modalList');

           $(".qcCheckModal").on('click', function(){
               var shipmentId = $(this).val();
               console.log(shipmentId)
                $.ajax({
               url: "/api/qcCheckModal?shipmentId=" +  shipmentId,
               type: "GET",
               dataType:"json",
               success: function(data){
                   console.log(data);
                   var html = "";
                   html += '<input class="modalInput" name="shipmentId" value='+ data.shipmentId +' type="hidden" readOnly></input>';
                   html += '<input class="modalInput" name="companyId" value='+ data.materialId.companyId +' type="hidden" readOnly></input>';
                   html += '<input class="modalInput" name="materialMeasure" value='+ data.materialId.materialMeasure +' type="hidden" readOnly></input>';
                   html += '<input class="modalInput" name="materialPrice" value='+ data.materialId.materialPrice +' type="hidden" readOnly></input>';
                   html += '<input class="modalInput" name="materialCategory" value='+ data.materialId.materialCategory +' type="hidden" readOnly></input>';
                   html += '<input class="modalInput" name="materialNumber" value='+ data.materialId.materialId +' type="hidden" readOnly></input>';
                   html += '<input class="modalInput" name="purchaseOrderDetailId" value='+ data.purchaseOrderDetailId +' type="hidden" readOnly></input>';
                   html += '<input class="modalInput" name="manager" value='+ data.manager +' type="hidden" readOnly></input>';
                   html += '<tr><th>검수 번호</th><td><input name="qcCheckId" class="modalInput"  value='+ data.qcCheckId +' type="text" readOnly></input></td></tr>';
                   html += '<tr><th>자재 이름</th><td><input name="materialName" class="modalInput" value='+ data.materialId.materialName +'  type="text" readOnly></input></td></tr>';
                   html += '<tr><th>발주서 번호</th><td><input name="purchaseOrderId"  class="modalInput" value='+ data.purchaseOrderId  +' type="text" readOnly></input></td></tr>';
                   html += '<tr><th>검수 날짜</th><td><input class="modalInput" value='+ data.qccheckDate +' type="text" readOnly></input></td></tr>';
                   html += '<tr><th>자재 총 수량</th><td><input class="modalInput" value='+ data.qcCheckCnt +' type="text" readOnly></input></td></tr>';
                   html += '<tr><th>자재 수량</th><td><input name="qcCheckCnt" class="modalInput qcCheckCnt" data-shipment-id="'+ shipmentId +'"  type="text"></input></td></tr>';
                   modalList.html(html);
               }
           })
       })

           function onBtnClick() {
               window.location.href = "/qcCheck/qcCheckList?size=10&page=0&processStatus=0";
           }

            $(".purchase").on("click",function(e){
                   e.preventDefault();
                    var modal = $(this).closest('.modal');
                    var modalP2 = document.getElementById("modalP2");

                   var qcCheckCnt = modal.find('.qcCheckCnt').val();
                   var qcCheckId = $('input[name=qcCheckId]').val();

                   $.ajax({
                       url: "/api/updateQcCheckCnt?qcCheckId=" + qcCheckId + "&qcCheckCnt=" + qcCheckCnt,
                       type: "PUT",
                       contentType: 'application/json',
                       success: function(response){
                            Swal.fire({
                              position: "center",
                              icon: "success",
                              title: "Success!",
                              text: qcCheckCnt + "개가 정품 처리 되었습니다.",
                              showConfirmButton: false,
                              timer: 1500
                            }).then(() => {
                                onBtnClick();
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
                                }).then(() => {
                                    onBtnClick();
                                });
                           }else{
                               console.error("Error: " , status, error);
                               Swal.fire({
                                     position: "center",
                                     icon: "error",
                                     title: "Warning",
                                     text: "검수 개수를 초과하였습니다. 다시 입력 하세요.",
                                     showConfirmButton: false,
                                     timer: 1500
                                   }).then(() => {
                                       modal.find('.qcCheckCnt').focus();
                                   });
                           }
                       }
                     })

              })
            $(".return").on("click",function(e){
                   e.preventDefault();
                   var modal = $(this).closest('.modal');
                   var modalP = document.getElementById("modalP");

                   var qcCheckId = $('input[name=qcCheckId]').val();
                   var qcCheckCnt = modal.find('.qcCheckCnt').val();
                   var materialId = $('input[name=materialNumber]').val();
                   var manager = $('input[name=manager]').val();
                   var purchaseOrderId = $('input[name=purchaseOrderId]').val();
                   var manager = $('input[name=manager]').val();
                   var materialCategory = $('input[name=materialCategory]').val();
                   var materialMeasure = $('input[name=materialMeasure]').val();
                   var shipmentId = $('input[name=shipmentId]').val();
                   var materialPrice = $('input[name=materialPrice]').val();
                   var materialName = $('input[name=materialName]').val();
                   var materialNumber = $('input[name=materialNumber]').val();
                   var companyId = $('input[name=companyId]').val();
                   var detailId = $('input[name=purchaseOrderDetailId]').val();

                   var qcCheckRequest = {
                       materialId: materialId,
                       manager: manager,
                       purchaseOrderId: purchaseOrderId,
                       materialCategory: materialCategory,
                       materialMeasure: materialMeasure,
                       shipmentId: shipmentId,
                       materialPrice: materialPrice,
                       materialNumber:materialNumber,
                       materialName:materialName,
                       companyId:companyId
                   };

                   $.ajax({
                       url: "/api/updateReturnStatus?qcCheckId=" + qcCheckId + "&qcCheckCnt=" + qcCheckCnt,
                       type: "PUT",
                       contentType: 'application/json',
                       success: function(response){
                                Swal.fire({
                                  position: "center",
                                  icon: "success",
                                  title: "Success!",
                                  text: qcCheckCnt + "개가 반품 처리가 되었습니다.",
                                  showConfirmButton: false,
                                  timer: 1500
                                }).then(() => {
                                    onBtnClick();
                                });
                                $.ajax({
                                   url: "/api/fromQcCheckToShipment?cnt="+qcCheckCnt + "&detailId=" + detailId,
                                   type: "POST",
                                   contentType: 'application/json',
                                   data: JSON.stringify(qcCheckRequest),
                                   success: function(response){

                                   }
                                })
                           },
                       error: function(xhr, status, error){
                           if(xhr.status === 403){
                               var message = xhr.responseText;
                               Swal.fire({
                                  position: "center",
                                  icon: "error",
                                  title: message,
                                  showConfirmButton: false,
                                  timer: 1500
                                }).then(() => {
                                    onBtnClick();
                                });
                           }else{
                               console.log("Error: " , status, error);
                               Swal.fire({
                                 position: "center",
                                 icon: "error",
                                 title: "Warning",
                                 text: "검수 개수를 초과하였습니다. 다시 입력 하세요.",
                                 showConfirmButton: false,
                                 timer: 1500
                               }).then(() => {
                                   modal.find('.qcCheckCnt').focus();
                               });
                           }
                       }
                   })
            })
         })