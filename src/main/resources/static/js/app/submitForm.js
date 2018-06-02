function submitForm() {
  var oForm = document.querySelector("input[type=file]").files[0];
  var formData = new FormData();
  formData.append('file', oForm);

  var title = $.trim($('#title').val());
  var author = $.trim($('#author').val());
  var type = $.trim($('#type').val());

  formData.append('title', title);
  formData.append('author', author);
  formData.append('type', type);

  $.ajax({
      url: '/add/text',
      data: formData,
      type: 'post',
      cache: false,                          //上传文件无需缓存
      processData: false,                    //用于对data参数进行序列化处理 这里必须false
      contentType: false,                    //必须
      enctype: 'multipart/form-data',
      beforeSend: function() {
        $("#submitBtn").attr({ disabled: "disabled" });
        $("#tip").html("<span class='tiptext' style='color:slategrey'>正在上传...</span>");
        return true;
      },
      success: function(res){
        if( res.code == 200) {
          $("#tip").html("<span class='tiptext' style='color:#428bca'>上传成功!</span>"); 
        }else if(res.code == 500) {
          $("#tip").html("<span class='tiptext' style='color:red'>" + res.msg + "!</span>");
        }
      },
      error: function(res){
        console.log("res.statusText");
      },
      complete: function() {
          $("#submitBtn").removeAttr("disabled");
          $('#title').val('');
          $('#author').val('');
          $('#type').val('');

          var timer = window.setTimeout(function(){
              $('#tip').html('');
              window.clearTimeout(timer);
          },2000);
      }
  });
}