$(function(){
  //获取url中的参数
  function getUrlParam(name) {
   var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); //构造一个含有目标参数的正则表达式对象
   var r = window.location.search.substr(1).match(reg); //匹配目标参数
   if (r != null && name == 'id') {
      return unescape(r[2]); 
    } else if(r != null && name == 'type'){
      return decodeURI(r[2]);
    }
    return null; //返回参数值
  }

  var getHeaderHtml = function (item) {
    return [
      '<h1 class="post-title">',
        item.title,
      '</h1>',
      '<div class="post-meta">',
      ' <span class="author">',
        item.author,
      ' &nbsp;&nbsp;|&nbsp;&nbsp;<span class="type">',
        item.type,
      ' </span>&nbsp;&nbsp;|&nbsp;&nbsp;<span class="type">',
        item.update_time,
      ' </span>',
      '</div>'].join('');
  };
  
  var getBodyHtml = function (p) {
    return [
      '<p>', 
        p, 
      '</p>'].join('');
  };
   
  var html_404 = function() {
    return [
      '  <div class="image"></div>',
      '  <h3 class="title">',
           '未找到您查询的内容',
      '  </h3>',
      '  <div class="oper">',
      '    <p><a href="javascript:history.go(-1)">返回上一级页面></a></p>',
      '    <p><a href="/">回到网站首页></a></p>',
      '  </div>'].join('');
  };

  var id = getUrlParam('id'),
    type = getUrlParam('type'),
    $headerHtml = $('#header'),
    $postHeader = $('#post-header'),
    $postBody = $('#post-body');
  $.ajax({
    type:'get',
    url:'/get/text',
    data: {
      'id': id,
      'type': type
    },
    success: function(res, status){
      if(res.code == 200) {
        $.each(res.data, function(val) {
          var headerHtml = [];
          var bodyHtml = [];
          headerHtml.push(getHeaderHtml(res.data));
          $postHeader.html(headerHtml.join(''));
          // 文章段落循还渲染
          var text_content = res.data.TextContent.split('\r\n');
          text_content.forEach(item => {
            bodyHtml.push(getBodyHtml(item));
          });
          $postBody.html(bodyHtml.join(''));
        });
      }else if(res.code == 404) {
        var html = [];
        $('.container').css("display",'none');
        $('.info_404').css("display",'block');
        html.push(html_404());
        $('.info_404').html(html.join(''));
      }
    },
    error: function(res) {
      console.log(res.statusText);
    }
  })
})