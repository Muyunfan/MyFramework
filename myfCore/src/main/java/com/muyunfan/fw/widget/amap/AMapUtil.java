/**
 *
 */
package com.muyunfan.fw.widget.amap;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import com.amap.api.maps.AMap;
import com.amap.api.maps.model.LatLng;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.route.BusPath;
import com.amap.api.services.route.BusStep;
import com.amap.api.services.route.RouteBusLineItem;
import com.amap.api.services.route.RouteRailwayItem;
import com.muyunfan.fw.widget.utils.common.LogUtil;

import java.io.File;
import java.net.URISyntaxException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.muyunfan.fw.widget.amap.ChString.address;

public class AMapUtil {

    public static final String MAP_BAIDU= "com.baidu.BaiduMap";
    public static final String MAP_AMAP = "com.autonavi.minimap";

    public static boolean isInstallByread(String packageName) {
        return new File("/data/data/" + packageName).exists();
    }

    /**
     * 确定起终点坐标BY高德
     */
    public static void setUpGaodeAppByLoca(Context context,double dLat,double dLng,String address){
        try {
            Intent intent = Intent.getIntent("androidamap://route?sourceApplication=嘟会修&sname=我的位置&dlat="
                    +dLat+"&dlon="+dLng+"&dname="+address+"&dev=0&m=0&t=1");
            if(isInstallByread("com.autonavi.minimap")){
                context.startActivity(intent);
                LogUtil.e("高德地图客户端已经安装") ;
            }else {
                LogUtil.e("没有安装高德地图客户端") ;
            }
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    /**
     * 我的位置到终点通过百度地图
     */
    public static void setUpBaiduAPPByMine(Context context,String address){
        try {
            Intent intent = Intent.getIntent("intent://map/direction?origin=我的位置&destination=" +
                    address +
                    "&mode=driving&src=myf#Intent;scheme=bdapp;package=com.baidu.BaiduMap;end");
            if(isInstallByread("com.baidu.BaiduMap")){
                context.startActivity(intent);
                LogUtil.e("百度地图客户端已经安装") ;
            }else {
                LogUtil.e("没有安装百度地图客户端") ;
            }
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

}
