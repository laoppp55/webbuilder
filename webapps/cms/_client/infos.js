		$(function() {
			$.get("/_prog/getPurchaserData.jsp", function(result) {
             	if(status==200){
			         $('#SINOPEC_ORDERNO').html(result.SINOPEC_ORDERNO);
			         $('#SINOPEC_AMOUNT').html(result.SINOPEC_AMOUNT);
			         $('#CHEMIS_ORDERNO').html(result.CHEMIS_ORDERNO);
			         $('#CHEMIS_AMOUNT').html(result.CHEMIS_AMOUNT);
			         $('#COMMON_ORDERNO').html(result.COMMON_ORDERNO);
			         $('#COMMON_AMOUNT').html(result.COMMON_AMOUNT);
 		        }else if (status==500) {
 			         alert("系统繁忙！请您见谅 ");
		        }
		    });

            $.ajax({
                 url:"/_prog/isLogin.jsp",
                 dataType:"json",
                 success:function(result){
		 		     var status = result.status;
		 		     var sjhweb = result.sjhweb;
		 		     var suppweb = result.suppweb;
		 		     var infoweb = result.infoweb;
                     var username = result.username;
                     var userid = result.userid;

    		 		 if(status==1){
		 			     //var sjhIframe = '<iframe src="http://' + sjhweb + '/cloudplatform/loginUser.do?name=' + result.username + '&code=' + result.password + '&verifycode=' + result.verifycode + '"></iframe>';
					     //$('#sjhDiv').append(sjhIframe);
		 			     loginSuccess(result);
		 		     }

					 var chooseurl = null;
		 		     $('#search-btn').click(function(){
		 			     var searchtext = $('#searchtext').val();
			 		     var chooseparam = $(".chooseQuery").html();
		 			     var id = $(".chooseQuery").attr("id");
						 /*if (chooseparam == "商机信息") {
			 			     chooseurl = "http://" + sjhweb + "/pcbidnotice/business.htm?tab=1&name=" + searchtext + "&searchindex=2";
					     }else if (chooseparam == "供应信息") {
						     chooseurl = "http://" + infoweb + "/_prog/search.jsp?keyword=" + searchtext;
					     }else if (chooseparam == "供应商") {
						     chooseurl = "http://" + suppweb + "/SupplierAction!selectSupplierBasicInfo.do?id=321654987&supplierName=" + searchtext;
					     }*/
                	     if (id == 1) {
     		 			     chooseurl = "http://" + sjhweb + "/pcbidnotice/business.htm?tab=1&name=" + searchtext + "&searchindex=2";
     				     }else if (id == 2) {
     					     chooseurl = "http://" + infoweb + "/_prog/search.jsp?keyword=" + encodeURI(searchtext);
     				     }else if (id == 3) {
     					     chooseurl = "http://" + suppweb + "/SupplierAction!selectSupplierBasicInfo.do?id=321654987&supplierName=" + searchtext;
     				     }

						 window.open(chooseurl,'_blank')
		 		     });

		 		     $('#searchtext').on("keyup" , function (event) {
                	     var searchtext = $('#searchtext').val();
 			 		     var chooseparam = $(".chooseQuery").html();
						 var id = $(".chooseQuery").attr("id");
                	     /*if (chooseparam == "商机信息") {
     		 			     chooseurl = "http://" + sjhweb + "/pcbidnotice/business.htm?tab=1&name=" + searchtext + "&searchindex=2";
     				     }else if (chooseparam == "供应信息") {
     					     chooseurl = "http://" + infoweb + "/_prog/search.jsp?searchcontent=" + searchtext;
     				     }else if (chooseparam == "供应商") {
     					     chooseurl = "http://" + suppweb + "/SupplierAction!selectSupplierBasicInfo.do?id=321654987&supplierName=" + searchtext;
     				     }*/

                	     if (id == 1) {
     		 			     chooseurl = "http://" + sjhweb + "/pcbidnotice/business.htm?tab=1&name=" + searchtext + "&searchindex=2";
     				     }else if (id == 2) {
     					     chooseurl = "http://" + infoweb + "/_prog/search.jsp?keyword=" + encodeURI(searchtext);
     				     }else if (id == 3) {
     					     chooseurl = "http://" + suppweb + "/SupplierAction!selectSupplierBasicInfo.do?id=321654987&supplierName=" + searchtext;
     				     }

						 var e = event || window.event;
		                 var keyCode = e.keyCode || e.which;
		                 switch (keyCode) {
		                    case 13:
		                	    window.open(chooseurl,'_blank')
		                        break;
		                    default:
		                        break;
		                 }
		              })

     	 		      var jumpForm1 = '<form hidden="true" target="_blank" id="junptosuppForm" action="http://' + suppweb + '/SupplierAction!selectSupplierBasicInfo.do" method="post"><input name="id" value="321654987"/></form>';
	    		      $("#jumptoecaiDiv").append(jumpForm1);
			
		    	      var jumpForm2 = '<form hidden="true"  target="_blank" id="junptosjhForm" action="http://' + sjhweb + '/pcbidnotice/business.htm?tab=1" method="post"></form>';
			          $("#jumptoecaiDiv").append(jumpForm2);
			
		 	          $("#aboutUs").attr("href","http://" + infoweb + "/about/introduction/");
		 	
		 	          $("#purchase").attr("href","http://" + infoweb + "/case/");
		 	
		 	          //$("#scrollNews").attr("src","http://" + infoweb + "/ifream/news.shtml");
		 	
		 	          //$("#suppIframe").attr("src","http://" + infoweb + "/ifream/supplier.shtml");
		 	
		 	          //$("#productIframe").attr("src","http://" + infoweb + "/ifream/product.shtml");
		 	
		 	          //$("#adviceIframe").attr("src","http://" + infoweb + "/ifream/banner.shtml");
		         },
                 error: function (jqXHR, textStatus, errorThrown) {
                    /*弹出jqXHR对象的信息*/
                    alert(jqXHR.responseText);
                    alert(jqXHR.status);
                    //alert(jqXHR.readyState);
                    //alert(jqXHR.statusText);
                    /*弹出其他两个参数的信息*/
                    //alert(textStatus);
                    //alert(errorThrown);
                 }


            });
        });

		function jumptosjh(){
			$("#junptosjhForm").submit();
		}
		function jumptosupp(){
			$("#junptosuppForm").submit();
		}
		function moremessage(){
			$("#junptosjhForm").submit();
		}

        function loginSuccess(res){
            var name = res.username;
          //  alert(name);
            $("#nologinview1").hide();
            $("#loginview1").html(name);
            $("#loginview10").show();
            $("#loginview4").html("欢迎来到长城云采");
            $("#loginview5").html("退出登录");
            var jumpForm = '<form id="junptoecaiForm" action="http://' + res.sinopecport + '/LoginAction!siglelogin.do" method="post">'
                + '<input type="hidden" name="name" value="' + res.username + '"/>'
                + '<input type="hidden" name="password" value="' + res.password + '"/></form>';
            $("#jumptoecaiDiv").append(jumpForm);
        }

        function loginOut(){
            var o = new Object();
            o.str = "您确定要退出系统吗？";
            o.callback = function(flag){
                if(flag){
                    $.get("../loginOut.do", function(res) {
                        var status = eval(res.status);
                        var arr,reg=new RegExp("(^| )sessionid=([^;]*)(;|$)");
                        if(arr=document.cookie.match(reg)){
                            var sessionid = unescape(arr[2]);
                        }else{
                            var sessionid = null;
                        }
                        if(status==1){
                            /* 					window.location.href = "http://" + res.sjhweb + "/cloudplatform/logout.do"; */
                            var sjhLogOutIframe = '<iframe src="http://' + res.sjhweb + '/cloudplatform/logout.do"></iframe>';
                            $('#sjhDiv').append(sjhLogOutIframe);
                            $("#loginview10").hide();
                            $("#loginview9").hide();
                            $("#nologinview1").show();
                            $("#nologinview2").show();
                            $("#error").html("", "系统提示");
                            $.get("../getSjhData.do",[],function(result){
                                $('#sjhlistshow').html("");
                                var sjhList = JSON.parse(result.sjhList);
                                if (result.status == 1) {
                                    $(sjhList).each(
                                        function(index, element){
                                            var sjhitem = '<li><span class="release-time">' + element.sendtime.substring(0,11)
                                                + '</span><span class="project-message"><a target="_blank" href="http://' + result.sjhweb + element.url
                                                + '">' + element.title
                                                + '</a></span><span class="expiry-date">截止时间:' + element.endtime + '</span></li>';
                                            $('#sjhlistshow').append(sjhitem);
                                        }
                                    );
                                }else {
                                    $(sjhList).each(
                                        function(index, element){
                                            var sjhitem = '<li><span class="release-time">' + element.sendtime.substring(0,11)
                                                + '</span><span class="project-message"><a href="javascript:void(0);">' + element.title
                                                + '</a></span><span class="expiry-date">截止时间:' + element.endtime + '</span></li>';
                                            $('#sjhlistshow').append(sjhitem);
                                        }
                                    );
                                }
                            },"json");
                        }else if(status==0){
                            messageBox.alert({str:res.message});
                        }
                    },"json");
                }
            };
            messageBox.confirm(o);
        }
