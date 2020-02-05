/**
 * Created by Administrator on 2017/2/28.
 */
$(function(){
    /*所在地选择添加选中背景色*/
    $(".location li").on("click",function(){
        console.log(1)
        $(".location li").removeClass("loc-select");
        $(this).addClass("loc-select");
    })
    /*调用分页链接方法   渲染相应分页数据*/
    $('.tcdPageCode').Paging({pagesize:10,count:85,toolbar:true,callback:function(page,size,count){
        console.log(arguments)
        alert('当前第 ' +page +'页,每页 '+size+'条,总页数：'+count+'页')
    }
    });
    /*js截取字符串   文本超出隐藏并添加省略号*/
    $(".supplier-content .right-content").each(function(){
        var str=$(this).text();
        $(this).text(cutstr(str, 127))
    })





    /**
     * js截取字符串，中英文都能用
     * @param str：需要截取的字符串
     * @param len: 需要截取的长度
     */
    function cutstr(str, len) {
        var str_length = 0;
        var str_len = 0;
        str_cut = new String();
        str_len = str.length;
        for (var i = 0; i < str_len; i++) {
            a = str.charAt(i);
            str_length++;
            if (escape(a).length > 4) {
                //中文字符的长度经编码之后大于4
                str_length++;
            }
            str_cut = str_cut.concat(a);
            if (str_length >= len) {
                str_cut = str_cut.concat("...");
                return str_cut;
            }
        }
        //如果给定字符串小于指定长度，则返回源字符串；
        if (str_length < len) {
            return str;
        }
    }
})