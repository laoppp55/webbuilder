<%@page contentType="text/html;charset=gbk"%>
<%@ page import="java.util.*,
                 com.bizwink.cms.toolkit.companyinfo.*,
                 com.bizwink.cms.util.*" contentType="text/html;charset=gbk" %>
<%@ page import="com.bizwink.webapps.register.*" %>
<%@ page import="com.bizwink.webapps.feedback.*" %>

<%
    String sitename = request.getServerName();
    ICompanyinfoManager companyManager = CompanyinfoPeer.getInstance();
    List companyList = new ArrayList();
    List companyClassList = new ArrayList();
    int columnID = ParamUtil.getIntParameter(request, "column", 0);
    IFeedbackManager feedMgr = FeedbackPeer.getInstance();
    int siteid = feedMgr.getSiteID(sitename);
    sitename = StringUtil.replace(sitename,".","_");
    companyClass companyclass = null;
    try {
        companyClassList = companyManager.getCompanyClassList(siteid);
        if (columnID == 0) {
            if (companyClassList.size() > 0) {
                companyclass = (companyClass)companyClassList.get(0);
                //companyList = companyManager.getAllCompanyInfos(siteid,0,50,"",0);
                companyList = companyManager.getCompanyList(siteid);
            }
        } else {
            companyList = companyManager.getCompanyInfos(siteid,columnID, 0, 50, "",0);
        }
    } catch (Exception e) {
        e.printStackTrace();
    }

    float latf = 39.999373437683296f;
    float lngf = 116.40782803259279f;
    StringBuffer buf = new StringBuffer();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
    <head xmlns="">
        <title>牛街清真美食</title>
        <meta content="text/html; charset=gb2312" http-equiv="Content-Type" />
        <link href="/css/web_cs.css" type="text/css" rel="stylesheet" /><SCRIPT language="JavaScript" src="/js/menu.js"></SCRIPT><SCRIPT src="/js/yc.js" type="text/javascript"></SCRIPT>
        <link href="http://code.google.com/apis/maps/documentation/javascript/examples/standard.css" rel="stylesheet" type="text/css" />
    <script type="text/javascript" src="http://maps.google.com/maps/api/js?sensor=false"></script>
    <script type="text/javascript">
        var Demo = {
            map: null,
            infoWindow: null
        };

        /**
         * Called when clicking anywhere on the map and closes the info window.
         */
        Demo.closeInfoWindow = function() {
            Demo.infoWindow.close();
        };

        /**
         * Opens the shared info window, anchors it to the specified marker, and
         * displays the marker's position as its content.
         */
        Demo.openInfoWindow = function(markers,i,pics,medias) {
            var pic_s = pics.split("||");
            var media_s = medias.split("||");
            var mediaplayurl = "/_commons/play.jsp";

            var disphtml="";
            var picfilename = "";

            for (var ii=0; ii<pic_s.length-1; ii++) {
                picfilename = pic_s[ii];
                var posi = picfilename.lastIndexOf(".");
                if (posi>-1) {
                    var extname = picfilename.substring(posi);
                    picfilename = picfilename.substring(0,posi) + "_s" + extname;
                    disphtml = disphtml + "<a href=\"" + pic_s[ii] + "\" target=\"_blank\"><img src=\"" + picfilename + "\" border=\"0\">";
                }
            }

            for(var ii=0; ii<media_s.length - 1; ii++) {
                picfilename = media_s[ii];
                var posi = picfilename.lastIndexOf(".");
                if (posi>-1) {
                    picfilename = picfilename.substring(0,posi) + ".jpg";
                    disphtml = disphtml + "<a href=\"" + mediaplayurl + "?mf=" + media_s[ii] + "\" target=\"_blank\"><img src=\"" + picfilename + "\" border=\"0\">";
                }
            }

            var marker = markers[i];
            var markerLatLng = marker.getPosition();
            Demo.infoWindow.setContent([
                '<b>欢迎你:' + marker.getTitle() + '</b><br/>',
                //'<a href="' + mediaplayurl + '?mf=' +media_s[0] +  '" target="_blank"><img src="' + pic_s[0] + '" border=\"0\" /></a>',
                disphtml,
                markerLatLng.lat(),
                ',',
                markerLatLng.lng()
            ].join(''));
            Demo.infoWindow.open(Demo.map, marker);
        },

      /*Called only once on initial page load to initialize the map.*/
      Demo.init = function(clat,clng,scale) {
        var ln = new Array();
        var marker = new Array();
        var i = 0;

        // Create single instance of a Google Map.
        //var centerLatLng = new google.maps.LatLng(<%=latf%>, <%=lngf%>);
        var centerLatLng = new google.maps.LatLng(clat,clng);
        Demo.map = new google.maps.Map(document.getElementById('map_canvas'), {
             zoom: 17,
             center: centerLatLng,
             mapTypeId: google.maps.MapTypeId.ROADMAP
        });

       // Create a single instance of the InfoWindow object which will be shared
       // by all Map objects to display information to the user.
       Demo.infoWindow = new google.maps.InfoWindow();

       // Make the info window close when clicking anywhere on the map.
       google.maps.event.addListener(Demo.map, 'click', Demo.closeInfoWindow);


       <%
         for(int i=0; i<companyList.size(); i++) {
            Companyinfo company = new Companyinfo();
            company = (Companyinfo)companyList.get(i);
            List pics = companyManager.getCompanyPicsInfos(siteid,company.getId());
            String s_pics = "";
            for(int j=0; j<pics.size(); j++) {
                s_pics = s_pics + (String)pics.get(j) + "||";
           }

           List medias = companyManager.getCompanyMediasInfos(siteid,company.getId());
           String s_medias = "";
           for (int j=0; j<medias.size(); j++) {
               s_medias = s_medias + (String)medias.get(j) + "||";
           }

           //buf.append("Demo.openInfoWindow(marker," + i + ",'" + s_pics + "','" + s_medias + "');\r\n");
           buf.append("google.maps.event.addListener(marker[" + i + "], 'click', function() {\r\n");
           buf.append("window.open(\"detail.jsp?id=" + company.getId() + "\"," + "\"qzxc\"," + "\"width=1000,height=600,left=0,top=0,status=no,scrollbars=yes\"" + ");");
           buf.append("});\r\n\r\n");
   %>
                    ln[i] = new Array();
                    ln[i][0] = '<%=company.getCompanyname()%>';
                    ln[i][1] = <%=company.getCompanylatitude()%>;
                    ln[i][2] = <%=company.getCompanylongitude()%>;
                    ln[i][3] = <%=(i+1)%>;

                    centerLatLng = new google.maps.LatLng(ln[i][1], ln[i][2]);

                    // Add multiple markers in a few random locations around San Francisco.
                    // First random marker
                    marker[i] = new google.maps.Marker({
                        map: Demo.map,
                        position: centerLatLng,
                        title:ln[i][0]
                    });
                    i = i + 1;
                <%}%>

                <%=buf.toString()%>
                }
    </script></head>
    <body xmlns="">
        <center>
        <table cellspacing="0" cellpadding="0">
            <tbody>
                <tr>
                    <td><img alt="" hspace="0" border="0" src="/images/picp55cggg6.jpg" /></td>
                </tr>
            </tbody>
        </table>
        <table cellspacing="0" cellpadding="0">
            <tbody>
                <tr>
                    <td align="center"></td>
                </tr>
            </tbody>
        </table>
        <table cellspacing="0" cellpadding="0" width="1002" border="0">
            <tbody>
                <tr>
                    <td valign="middle" align="left"><img height="2" alt="" width="1" src="/images/tm.gif" /></td>
                </tr>
                <tr>
                    <td valign="middle" align="left" style="padding-right: 0px; padding-left: 35px; font-weight: bold; font-size: 14px; background: url(/images/title_bg.jpg) no-repeat; padding-bottom: 0px; color: #ffffff; padding-top: 3px; height: 26px">便民服务</td>
                </tr>
            </tbody>
        </table>
        <table cellspacing="0" cellpadding="0" width="1002" border="0" style="border-right: #6dbe09 1px solid; border-left: #6dbe09 1px solid; border-bottom: #6dbe09 1px solid">
            <tbody>
                <tr>
                    <td align="center" style="padding-right: 10px; padding-left: 10px; padding-bottom: 10px; padding-top: 10px"><div id="map_canvas" style="width:900px;height:600px"></div>
<script type="text/javascript">Demo.init(39.887508392333984,116.36348724365234,18);</script>
</td>
                </tr>
            </tbody>
        </table>
        <table cellspacing="0" cellpadding="0" width="1002" bgcolor="#ffffff">
            <tbody>
                <tr>
                    <td valign="top">
                    <table cellspacing="0" cellpadding="0" border="0" style="margin-top: 0px; margin-left: 0px; width: 1002px">
                        <tbody>
                            <tr>
                                <td>
                                <table cellspacing="0" cellpadding="0" style="background-image: url(/img/tm.gif); height: 50px">
                                    <tbody>
                                        <tr>
                                            <td style="background-position: 0% 0%; background-image: none"><img alt="" style="height: 0px" src="/images/tm.gif" /></td>
                                            <td valign="top" style="background-position: 0% 0%; background-image: none; width: 100%">
                                            <div id="CNT_4701/NJZWqcbs/14279" style="margin-top: 5px; margin-left: 0px; width: 1000px; margin-right: 0px">
                                            <table cellspacing="0" cellpadding="0" width="100%">
                                                <tbody>
                                                    <tr>
                                                        <td>
                                                        <p align="center"><font face="Verdana" color="#06a506">最佳观看效果：1024＊768；IE5.0或以上版本</font></p>
                                                        </td>
                                                    </tr>
                                                </tbody>
                                            </table>
                                            <!--PAGELETSUCCESS--></div>
                                            </td>
                                            <td style="background-position: 0% 0%; background-image: none"><img alt="" style="height: 0px" src="/images/tm.gif" /></td>
                                        </tr>
                                    </tbody>
                                </table>
                                </td>
                            </tr>
                            <tr>
                                <td>
                                <table cellspacing="0" cellpadding="0" style="background-position: 0% 0%; background-image: none">
                                    <tbody>
                                        <tr>
                                            <td style="background-image: none"><img alt="" style="width: 0px; height: 0px" src="/images/tm.gif" /></td>
                                            <td style="width: 100%"><img alt="" style="width: 0px" src="/images/tm.gif" /></td>
                                            <td style="background-image: none"><img alt="" style="width: 0px; height: 0px" src="/images/tm.gif" /></td>
                                        </tr>
                                    </tbody>
                                </table>
                                </td>
                            </tr>
                        </tbody>
                    </table>
                    </td>
                </tr>
            </tbody>
        </table>
        </center>
    </body>
</html>