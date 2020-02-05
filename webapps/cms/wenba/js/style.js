function selectChang(what,opt) {  
   if (opt=="removeFormat") {
      what=opt;
      opt=null;
    }
   if (opt==null) 
      Composition.document.execCommand(what);
   else
      Composition.document.execCommand(what,"",opt);  
   Composition.focus();
}

//查看源代码
function generate(){
   str = Composition.document.body.innerHTML; 
   if (str!=""){
      alert(str);
   }
   else 
      alert("请编辑您的页面\n然后再查看代码");
}

//加粗字体
function cmd_bold(){
   Composition.document.execCommand("Bold");
}

//加粗字体
function cmd_ita(){
   Composition.document.execCommand("Italic");
}

//建MAIL连接
function creat_mail(){
   var sel=Composition.document.selection.createRange();
   var URL=createForm.e_mail.value;
   var str=sel.text;
   if (URL!=""){
      sel.pasteHTML("<A HREF="+URL+"><font color=#990000>"+str+"</font></A>");
   }
   else 
      alert("请输入有效的URL!");
}

function body_source(str){
   html_head = "<html><head><meta http-equiv=Content-Type content='text/html; charset=gb2312'><style>.line {font-family: 宋体, Arial, Helvetica, Times New Roman; font-size: 12px;line-height:1.4}A:hover {COLOR: #FF6600; FONT-FAMILY: 宋体; TEXT-DECORATION: none}</style><title>DESIGN</title></head><BODY class=line bgcolor=#F3F3F3 text=#000000>";
   html_end  = "</body></html>";
   Composition.document.write(html_head + str + html_end);
   return false;
}

function clean_all(){
   html_head = "<html><head><meta http-equiv=Content-Type content='text/html; charset=gb2312'><style>.line {font-family: 宋体, Arial, Helvetica, Times New Roman; font-size: 12px;line-height:1.4}A:hover {COLOR: #FF6600; FONT-FAMILY: 宋体; TEXT-DECORATION: none}</style><title>DESIGN</title></head><BODY class=line bgcolor=#F3F3F3 text=#000000>";
   html_end  = "</body></html>";
   str = Composition.document.body.innerText;
   Composition.document.body.innerHTML = "";
   Composition.document.body.innerText = "";
   Composition.document.write(html_head + str + html_end);
}

