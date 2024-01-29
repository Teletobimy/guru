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
        var newUrl = "/admin/userList?size=10&page=0&part=" + category;

        window.location.href = newUrl;
    }
    $('.modifyB').on('click',function(){
        var parent = $(this).closest('tr');

        var userId = parent.find("input[name='userId']").val();
        var roles = parent.find("select[name='roles'] option:selected").val();
        console.log(roles)
        Swal.fire({
              title: "Are you sure?",
              text: "You won't be able to revert this!",
              icon: "warning",
              showCancelButton: true,
              confirmButtonColor: "#3085d6",
              cancelButtonColor: "#d33",
              confirmButtonText: "Yes, update it!"
        }).then((result) => {
             $.ajax({
                        url: "/admin/updateRoles?userId=" + userId + "&roles=" + roles,
                        type : "PUT",
                        contentType:'application/json',
                        success:function(response){

                        }
                    })
              if (result.isConfirmed) {
                Swal.fire({
                  title: "Update!",
                  text: "Your file has been deleted.",
                  icon: "success"
                }).then(()=>{
                    window.location.href="/admin/userList";
                });

              }
        });

    })
    $('.deleteB').on('click',function(){
        var parent = $(this).closest('tr');

        var userId = parent.find("input[name='userId']").val();
        Swal.fire({
          title: "Are you sure?",
          text: "You won't be able to revert this!",
          icon: "warning",
          showCancelButton: true,
          confirmButtonColor: "#3085d6",
          cancelButtonColor: "#d33",
          confirmButtonText: "Yes, delete it!"
        }).then((result) => {
          if (result.isConfirmed) {
              $.ajax({
                          url: "/admin/deleteUser?userId=" + userId,
                          type : "DELETE",
                          contentType:'application/json',
                          success:function(response){

                          }
                      })
            Swal.fire({
              title: "Deleted!",
              text: "Your file has been deleted.",
              icon: "success"
            }).then(()=>{
                       window.location.href="/admin/userList";
            });
          }
        });

    })