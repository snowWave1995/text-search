define(function (require, exports, module) {

    var $ = require('../lib/jquery'),
        Ajax = require('../mod/ajax'),
        PageView = require('../mod/pageView');

    var getItemHtml = function (item, i, key) {
        return ['<li>',
            '<div class="li-content">',
            ' <a href=' + '"detail?id='+ item.id + '&type=' + item.type + '" target="_blank" class="title">',
            item.title,
            ' </a>',
            ' <div class="meta">',
            '   <span class="author">',
            item.author,
            '   </span><span style="font-size:12px;">&nbsp;&nbsp;|&nbsp;&nbsp;</span>',
            '   <span class="type">',
            item.type,
            '   </span><span style="font-size:12px;">&nbsp;&nbsp;|&nbsp;&nbsp;</span>',
            '   <span class="updateTime">',
            item.update_time,
            '   </span>',
            ' </div>',
            ' <p id="textContent-' + i + '" class="textContent">',
            // item.textContent,
            findKeyText(item.textContent, i, key),
            ' </p>',
            '</div>',
            '</li>'].join('');
    };

    var html_404 = function() {
        return [
            '  <div class="image"></div>',
            '  <h3 class="title">',
            '未找到您查询的内容',
            '  </h3>',
            '  <div class="oper">',
            '    <p><a href="javascript:history.go(-1)">返回上一级页面></a></p>',
            '    <p><a href="/search">回到网站首页></a></p>',
            '  </div>'].join('');
    };

    var str = window.location.search;
    if (str.indexOf('keyword') != -1){
        var pos_start = str.indexOf('keyword') + 'keyword'.length + 1;
        var pos_end = str.indexOf("&", pos_start);
        if (pos_end == -1){
            var keyword = decodeURI(str.substring(pos_start));
        }else{
            alert("没有关键字!");
        }
    }

    // 数据分页渲染
    getData = function () {
        pageView.disable();
        var data = {};
        data.keyword = encodeURI(encodeURI(keyword));
        data.page = pageView.getParams().page;
        data.page_size = pageView.getParams().page_size;

        Ajax.get('/searchAll', data)
            .done(function (res) {
                if (res.code == 200) {
                    var total = res.data.total;
                    pageView.refresh(total);
                    var html = [],
                        renderItems = res.data.textDTOList,
                        num = pageView.pageSize,
                        start = pageView.data.start,
                        end = pageView.data.end,
                        flag = false;
                    for(var i = start-1; i < end ; i++) {
                        html.push(getItemHtml(renderItems[i], i, keyword));
                    }
                    $result.html("共搜索到 <span style='color:red;'>" + res.data.total + "</span> 条结果");
                    $list.html(html.join(''));
                    markKeyWord(keyword);
                }else if(res.code == 404) {
                    var html = [];
                    $('.page-wrapper').css("display",'none');
                    $('.info_404').css("display",'block');
                    html.push(html_404());
                    $('.info_404').html(html.join(''));
                }
            })
            .always(function(){
                pageView.enable();
            });
    }

    // 寻找整篇文章中包含关键字的文本
    findKeyText = function(item, i, key) {
        var textContent = $('#textContent-' + i),
            text = item.replace('\r\n', ''),
            reg = new RegExp(key, 'g');
        if(reg.test(text)) {
            var index = text.indexOf(key),
                step = 20,
                len = key.length,
                start = index - step,
                end = index + len + step,
                end_step = end - index - len,
                result;
            if(start <= 0 && end_step >= step) {
                result = text.substr(0, end);
            } else if(start >= 0 && end_step <= step ){
                result = text.substr(start, text.length - 1);
            }else if(start >= 0 && end_step >= step){
                result = text.substr(start, end);
            }else {
                return false;
            }
        }
        return result;
    }

    // 搜索关键字高亮
    markKeyWord = function(key) {
        var content = $list.html();
        var values = content.split(key);
        $list.html(values.join('<span style="color:red;">' + key + '</span>'));
    }

    $list = $('#list'),
        $result = $('#result'),
        $title = $('#title'),
        pageView = new PageView('#page-view', {
            defaultSize: 6,
            onChange: getData
        });
    getData();
});