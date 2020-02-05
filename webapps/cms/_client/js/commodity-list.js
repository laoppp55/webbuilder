/**
 * Created by Administrator on 2017/2/28.
 */
$(function(){
    /*分类 添加选中背景色*/
    $(".class li").on("click",function(){
        $(".class li").removeClass("loc-select");
        $(this).addClass("loc-select");
    })
    /*所在地选择添加选中背景色*/
    $(".location li").on("click",function(){
        $(".location li").removeClass("loc-select");
        $(this).addClass("loc-select");
    })
    /*调用分页链接方法   渲染相应分页数据*/
    $('.tcdPageCode').Paging({pagesize:10,count:85,toolbar:true,callback:function(page,size,count){
        console.log(arguments)
        alert('当前第 ' +page +'页,每页 '+size+'条,总页数：'+count+'页')
    }
    });
})