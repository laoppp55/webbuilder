package com.bizwink.cms.toolkit.bbs;
import java.util.regex.*;

public class  bbcode{
    public String HTMLEncode(String Str){
        Str=YYReplace(Str,"<","&gt;");
        Str=YYReplace(Str,">","&lt;");
        Str=YYReplace(Str,"\n","<BR>");
        return Str;
    }
    public String YYReplace(String Str,String oldStr,String newStr){
        String ReturnStr="";
        int i,j,t,m,n;
        n=0;
        j=oldStr.length();
        if (Str.indexOf(oldStr)>-1)
        {
            while(Str.indexOf(oldStr,n)>-1)
            {
                i=Str.length();
                if (Str.indexOf(oldStr)==0)
                    Str=newStr+Str.substring(j,i);
                else
                {
                    t=Str.indexOf(oldStr);
                    m=(t+j);
                    Str=Str.substring(0,t)+newStr+Str.substring(m,i);
                    n=t+newStr.length()-j+1;
                }
            }
        }
        ReturnStr=Str;
        return ReturnStr;

    }
    public String LCReplace(String Str,String BStr,String EStr,String ReStr){
        String ReturnStr="",Str1="",Str2="";
        int i,j,n;
        n=0;
        if ((Str.indexOf(BStr)>-1)&&((Str.indexOf(EStr)>-1)))
        {
            while(Str.indexOf(BStr,n)>-1)
            {
                i=Str.indexOf(BStr);
                j=Str.indexOf(EStr);
                Str1=Str.substring((i+BStr.length()),j);
                Str2=YYReplace(ReStr,"$lichao$",Str1);
                Str1=BStr+Str1+EStr;
                Str=YYReplace(Str,Str1,Str2);
                n=i+Str2.length()-Str1.length();
            }
        }
        ReturnStr=Str;
        return ReturnStr;
    }
    public String yyBBCODE(String Str){
        String BStr,EStr,ReStr;
        BStr="[b]";
        EStr="[/b]";
        ReStr="<b>$lichao$</b>";
        Str=LCReplace(Str,BStr,EStr,ReStr);

        BStr="[i]";
        EStr="[/i]";
        ReStr="<i>$lichao$</i>";
        Str=LCReplace(Str,BStr,EStr,ReStr);

        BStr="[u]";
        EStr="[/u]";
        ReStr="<u>$lichao$</u>";
        Str=LCReplace(Str,BStr,EStr,ReStr);

        BStr="[fly]";
        EStr="[/fly]";
        ReStr="<marquee width = 90% behavior = alternate scrollamount = 3>$lichao$</marquee>";
        Str=LCReplace(Str,BStr,EStr,ReStr);

        BStr="[align=center]";
        EStr="[/align]";
        ReStr="<div align=center>$lichao$</div>";
        Str=LCReplace(Str,BStr,EStr,ReStr);

        BStr="[align=left]";
        EStr="[/align]";
        ReStr="<div align=left>$lichao$</div>";
        Str=LCReplace(Str,BStr,EStr,ReStr);

        BStr="[align=right]";
        EStr="[/align]";
        ReStr="<div align=right>$lichao$</div>";
        Str=LCReplace(Str,BStr,EStr,ReStr);

        BStr="[move]";
        EStr="[/move]";
        ReStr="<marquee scrollamount = 3>$lichao$</marquee>";
        Str=LCReplace(Str,BStr,EStr,ReStr);

        BStr="[email]";
        EStr="[/email]";
        ReStr="<img align=absmiddle src=IMAGES/EMAIL1.GIF><A HREF='mailto:$lichao$' >$lichao$</A>";
        Str=LCReplace(Str,BStr,EStr,ReStr);

        BStr="[quote]";
        EStr="[/quote]";
        ReStr="<br>引用<hr noshade size=1 color=#C0C0C0>$lichao$<br><hr noshade size=1 color=#C0C0C0><br>";
        Str=LCReplace(Str,BStr,EStr,ReStr);

        BStr="[url]";
        EStr="[/url]";
        ReStr="<A HREF=$lichao$ TARGET=_blank>$lichao$</A>";
        Str=LCReplace(Str,BStr,EStr,ReStr);

        BStr="[img]";
        EStr="[/img]";
        ReStr="<a href='$lichao$' target=_blank><IMG SRC=$lichao$ border=0 alt=按此在新窗口浏览图片 onload='javascript:if(this.width>screen.width-333)this.width=screen.width-333'></a>";
        Str=LCReplace(Str,BStr,EStr,ReStr);

        BStr="[flash]";
        EStr="[/flash]";
        ReStr="<OBJECT codeBase=http://download.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=4,0,2,0 classid=clsid:D27CDB6E-AE6D-11cf-96B8-444553540000 width=500 height=400><PARAM NAME=movie VALUE=''$lichao$''><PARAM NAME=quality VALUE=high><embed src=''$lichao$'' quality=high pluginspage='http://www.macromedia.com/shockwave/download/index.cgi?P1_Prod_Version=ShockwaveFlash' type='application/x-shockwave-flash' >$lichao$</embed></OBJECT>";
        Str=LCReplace(Str,BStr,EStr,ReStr);

        BStr=":)";
        EStr="<img src=IMAGES/SMILE.GIF border=0>";
        Str=YYReplace(Str,BStr,EStr);

        BStr=":(";
        EStr="<img src=IMAGES/SAD.GIF border=0>";
        Str=YYReplace(Str,BStr,EStr);

        BStr=":D";
        EStr="<img src=IMAGES/BIGSMILE.GIF border=0>";
        Str=YYReplace(Str,BStr,EStr);

        BStr=";)";
        EStr="<img src=IMAGES/WINK.GIF border=0>";
        Str=YYReplace(Str,BStr,EStr);

        BStr=":cool:";
        EStr="<img src=IMAGES/COOL.GIF border=0>";
        Str=YYReplace(Str,BStr,EStr);

        BStr=":mad:";
        EStr="<img src=IMAGES/MAD.GIF border=0>";
        Str=YYReplace(Str,BStr,EStr);

        BStr=":o";
        EStr="<img src=IMAGES/SHOCKED.GIF border=0>";
        Str=YYReplace(Str,BStr,EStr);

        BStr=":P";
        EStr="<img src=IMAGES/TONGUE.GIF border=0>";
        Str=YYReplace(Str,BStr,EStr);

        BStr="[em01]";
        EStr="<img src=PIC/em01.GIF border=0>";
        Str=YYReplace(Str,BStr,EStr);

        BStr="[em02]";
        EStr="<img src=PIC/em02.GIF border=0>";
        Str=YYReplace(Str,BStr,EStr);

        BStr="[em03]";
        EStr="<img src=PIC/em03.GIF border=0>";
        Str=YYReplace(Str,BStr,EStr);

        BStr="[em04]";
        EStr="<img src=PIC/em04.GIF border=0>";
        Str=YYReplace(Str,BStr,EStr);

        BStr="[em05]";
        EStr="<img src=PIC/em05.GIF border=0>";
        Str=YYReplace(Str,BStr,EStr);

        BStr="[em06]";
        EStr="<img src=PIC/em06.GIF border=0>";
        Str=YYReplace(Str,BStr,EStr);

        BStr="[em07]";
        EStr="<img src=PIC/em07.GIF border=0>";
        Str=YYReplace(Str,BStr,EStr);

        BStr="[em08]";
        EStr="<img src=PIC/em08.GIF border=0>";
        Str=YYReplace(Str,BStr,EStr);

        BStr="[em09]";
        EStr="<img src=PIC/em09.GIF border=0>";
        Str=YYReplace(Str,BStr,EStr);

        BStr="[em10]";
        EStr="<img src=PIC/em10.GIF border=0>";
        Str=YYReplace(Str,BStr,EStr);

        BStr="[em11]";
        EStr="<img src=PIC/em11.GIF border=0>";
        Str=YYReplace(Str,BStr,EStr);

        BStr="[em12]";
        EStr="<img src=PIC/em12.GIF border=0>";
        Str=YYReplace(Str,BStr,EStr);

        BStr="[em13]";
        EStr="<img src=PIC/em13.GIF border=0>";
        Str=YYReplace(Str,BStr,EStr);

        BStr="[em14]";
        EStr="<img src=PIC/em14.GIF border=0>";
        Str=YYReplace(Str,BStr,EStr);

        BStr="[em15]";
        EStr="<img src=PIC/em15.GIF border=0>";
        Str=YYReplace(Str,BStr,EStr);

        BStr="[em16]";
        EStr="<img src=PIC/em16.GIF border=0>";
        Str=YYReplace(Str,BStr,EStr);

        BStr="[em17]";
        EStr="<img src=PIC/em17.GIF border=0>";
        Str=YYReplace(Str,BStr,EStr);

        BStr="[em18]";
        EStr="<img src=PIC/em18.GIF border=0>";
        Str=YYReplace(Str,BStr,EStr);

        BStr="[em19]";
        EStr="<img src=PIC/em19.GIF border=0>";
        Str=YYReplace(Str,BStr,EStr);

        BStr="[em20]";
        EStr="<img src=PIC/em20.GIF border=0>";
        Str=YYReplace(Str,BStr,EStr);

        BStr="[em21]";
        EStr="<img src=PIC/em21.GIF border=0>";
        Str=YYReplace(Str,BStr,EStr);

        BStr="[em22]";
        EStr="<img src=PIC/em22.GIF border=0>";
        Str=YYReplace(Str,BStr,EStr);

        BStr="[em23]";
        EStr="<img src=PIC/em23.GIF border=0>";
        Str=YYReplace(Str,BStr,EStr);

        BStr="[em24]";
        EStr="<img src=PIC/em24.GIF border=0>";
        Str=YYReplace(Str,BStr,EStr);

        BStr="[em25]";
        EStr="<img src=PIC/em25.GIF border=0>";
        Str=YYReplace(Str,BStr,EStr);

        BStr="[em26]";
        EStr="<img src=PIC/em26.GIF border=0>";
        Str=YYReplace(Str,BStr,EStr);

        BStr="[em27]";
        EStr="<img src=PIC/em27.GIF border=0>";
        Str=YYReplace(Str,BStr,EStr);

        BStr="[em28]";
        EStr="<img src=PIC/em28.GIF border=0>";
        Str=YYReplace(Str,BStr,EStr);

        Pattern p = Pattern.compile("(\\[color=(.[^\\[]*)\\])(.[^\\[]*)(\\[\\/color\\])");
        Matcher matcher = p.matcher(Str);
        if(matcher.find()){
            Str = matcher.replaceAll("<font color=$2>$3</font>");
        }

        p = Pattern.compile("(\\[face=(.[^\\[]*)\\])(.[^\\[]*)(\\[\\/face\\])");
        matcher = p.matcher(Str);
        if(matcher.find()){
            Str = matcher.replaceAll("<font face=$2>$3</font>");
        }

        p = Pattern.compile("(\\[size=(.[^\\[]*)\\])(.[^\\[]*)(\\[\\/size\\])");
        matcher = p.matcher(Str);
        if(matcher.find()){
            Str = matcher.replaceAll("<font size=$2>$3</font>");
        }

        p = Pattern.compile("\\[glow=*([0-9]*),*(#*[a-z0-9]*),*([0-9]*)\\](.[^\\[]*)\\[\\/glow]");
        matcher = p.matcher(Str);
        if(matcher.find()){
            Str = matcher.replaceAll("<table width=$1 style=\"filter:glow(color=$2, strength=$3)\">$4</table>");
        }

        p = Pattern.compile("\\[shadow=*([0-9]*),*(#*[a-z0-9]*),*([0-9]*)\\](.[^\\[]*)\\[\\/shadow]");
        matcher = p.matcher(Str);
        if(matcher.find()){
            Str = matcher.replaceAll("<table width=$1 style =\"filter:shadow(color=$2, strength=$3)\">$4</table>");
        }

        p = Pattern.compile("(\\[upload\\])(.[^\\[]*)(\\[\\/upload\\])");
        matcher = p.matcher(Str);
        if(matcher.find()){
            Str = matcher.replaceAll("<br><IMG SRC=\"uploadfiles/$2\" border=0>");
        }

        p = Pattern.compile("(\\[zip\\])(.[^\\[]*)(\\[\\/zip\\])");
        matcher = p.matcher(Str);
        if(matcher.find()){
            Str = matcher.replaceAll("<br><IMG SRC=IMAGES/zip.gif border = 0> <a href = \"/uploadfiles/$2\">点击下载该文件</a>");
        }

        p = Pattern.compile("(\\[rar\\])(.[^\\[]*)(\\[\\/rar\\])");
        matcher = p.matcher(Str);
        if(matcher.find()){
            Str = matcher.replaceAll("<br><IMG SRC=IMAGES/rar.gif border = 0> <a href = \"/uploadfiles/$2\">点击下载该文件</a>");
        }
        return Str;
    }
}
