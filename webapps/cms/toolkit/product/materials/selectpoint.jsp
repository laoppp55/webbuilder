<%@ page import="com.bizwink.cms.util.ParamUtil" %>
<%@ page import="com.bizwink.cms.toolkit.companyinfo.*" %>
<%@ page import="com.bizwink.cms.util.SessionUtil" %>
<%@ page import="com.bizwink.cms.security.Auth" %>
<%@ page import="com.bizwink.cms.util.StringUtil" %>
<%@ page language="java" contentType="text/html;charset=GBK" %>
<%
    Auth authToken = SessionUtil.getUserAuthorization(request,response,session);
    if (authToken == null)
    {
        response.sendRedirect(response.encodeRedirectURL("../login.jsp?msg=系统超时，请重新登陆!"));
        return;
    }
    int siteid = authToken.getSiteID();
    int columnid = ParamUtil.getIntParameter(request,"column",0);
    double lat = ParamUtil.getDoubleParameter(request,"lat",0d);
    double lng = ParamUtil.getDoubleParameter(request,"lng",0d);
    double initlat = 39.998968d;
    double initlng = 116.406193249d;

    if (lat != 0d && lng != 0d) {
        initlat = lat;
        initlng = lng;
    }
%>
<!DOCTYPE html>
<html>
<head>
    <meta name="viewport" content="initial-scale=1.0, user-scalable=no" />
    <meta http-equiv="content-type" content="text/html; charset=UTF-8"/>
    <style type="text/css">
        html { height: 100% }
        body { height: 100%; margin: 0px; padding: 0px }
        #map_canvas { height: 100% }
    </style>
    <title>地图上快速找到经纬度坐标</title>
    <link href="http://code.google.com/apis/maps/documentation/javascript/examples/standard.css" rel="stylesheet" type="text/css" />
    <script type="text/javascript" src="http://maps.google.com/maps/api/js?sensor=false"></script>
    <script type="text/javascript">
        var centerLatlng = new google.maps.LatLng(<%=initlat%>,<%=initlng%>);
        var marker;

        function initialize() {
            var map;
            var mapOptions = {
                zoom: 12,
                center: centerLatlng,
                mapTypeId: google.maps.MapTypeId.ROADMAP
            };

            map = new google.maps.Map(document.getElementById("map_canvas"), mapOptions);

            marker = new google.maps.Marker({
                position: centerLatlng,
                map: map,
                draggable:true,
                animation: google.maps.Animation.DROP,
                title:"Hello World!"
            });

            //geocoder = new google.maps.Geocoder();
            google.maps.event.addListener(marker, 'dragend', getlatlng);
            google.maps.event.addListener(marker, 'click', myclick);

            function getlatlng() {
                var latlng = new google.maps.LatLng(0,0,false);
                latlng = marker.getPosition();
                document.getElementById("latid").value = latlng.lat();
                document.getElementById("lngid").value = latlng.lng();
            }

            function myclick() {
                opener.document.addForm.companylatitude.value = document.getElementById("latid").value;
                opener.document.addForm.companylongitude.value = document.getElementById("lngid").value;
                window.close();
            }
        }
    </script>
</head>
<body onload="initialize()">
<table>
    <tr>
        <td>维度Latitude：<input type="text" name="lat" id="latid" value="<%=(lat==0f)?"":lat%>" readonly="true"></td>
        <td>经度Longitude：<input type="text" name="lng" id="lngid" value="<%=(lng==0f)?"":lng%>" readonly="true"></td>
    </tr>
    <tr>
        <td colspan="2">
            <div id="map_canvas" style="width:800px;height:600px"></div>
        </td>
    </tr>
</table>
</body>
</html>
