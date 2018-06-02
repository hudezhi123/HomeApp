package com.hudezhi.freedom.homeapp.map_baidu.activity;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.SeekBar;


import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.Gradient;
import com.baidu.mapapi.map.GroundOverlayOptions;
import com.baidu.mapapi.map.HeatMap;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.PolygonOptions;
import com.baidu.mapapi.map.Stroke;
import com.baidu.mapapi.map.TextOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.model.LatLngBounds;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.poi.PoiResult;
import com.hudezhi.freedom.homeapp.R;
import com.hudezhi.freedom.homeapp.map_baidu.baiduutils.PoiOverlay;
import com.hudezhi.freedom.homeapp.map_baidu.bean.MarkerPoint;
import com.hudezhi.freedom.homeapp.utils.AnnotateUtil;
import com.hudezhi.freedom.homeapp.utils.BindView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 矢量地图，卫星地图，空白地图
 */
public class BaseBaiduActivity extends AppCompatActivity {

    @BindView(id = R.id.bmapView_base)
    private MapView mMapView;
    @BindView(id = R.id.seekBar_alpha_base)
    private SeekBar alphaBar;
    @BindView(id = R.id.check_animation_base)
    private CheckBox animationCheck;
    @BindView(id = R.id.btn_reset_base)
    private Button btnReset;
    @BindView(id = R.id.btn_clear_base)
    private Button btnClear;
    private BaiduMap mBaiduMap;
    private InfoWindow mInfoWindow;

    private Marker markerLayer = null;

    private List<LatLng> mPointList = null;

    private static final int TYPE_NORMAL = 1;
    private static final int TYPE_SATELLITE = 2;
    private static final int TYPE_NONE = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_baidu);
        AnnotateUtil.initBindView(this);
        initView();
        mBaiduMap = mMapView.getMap();
        //设置地图类型
        setMapType(TYPE_NORMAL);

        //设置地图一些属性
        setMapAttr();

        //设置一个marker
//        onePointMarker(new MarkerPoint(39.86923f, 116.397428f));

//        setMarkerDrag(new MarkerPoint(39.963175f, 116.400244f));

//        setMarkerClick(new MarkerPoint(39.963175f, 116.400244f));


//        List<MarkerPoint> markerPointList = new ArrayList<>();
//        markerPointList.add(new MarkerPoint(39.963175f, 116.400244f));
//        markerPointList.add(new MarkerPoint(39.942821f, 116.369199f));
//        markerPointList.add(new MarkerPoint(39.939723f, 116.425541f));
//        markerPointList.add(new MarkerPoint(39.906965f, 116.401394f));
//        listPointMaker(markerPointList);


        //是否显示POI
//        bottomLayerMark(true);

        //多边形
//        addMultiShape(null);

        //文字覆盖物
//        textOverLay(null);

        // 设置poplayer
//        setPopLayer(null);

        //设置地形图图层
//        groundOverlay();

        //热图功能
//        setHeatFunc();


    }

    private void initList() {
        mPointList = new ArrayList<>();
        LatLng pt1 = new LatLng(39.93923, 116.357428);
        LatLng pt2 = new LatLng(39.91923, 116.327428);
        LatLng pt3 = new LatLng(39.89923, 116.347428);
        LatLng pt4 = new LatLng(39.89923, 116.367428);
        LatLng pt5 = new LatLng(39.91923, 116.387428);
        mPointList.add(pt1);
        mPointList.add(pt2);
        mPointList.add(pt3);
        mPointList.add(pt4);
        mPointList.add(pt5);
    }

    private void initView() {

    }


    private void setMapAttr() {
        //设置交通图
//        mBaiduMap.setTrafficEnabled(true);
        //设置热力图
//        mBaiduMap.setBaiduHeatMapEnabled(true);

        //设置地图LOGO

    }


    /**
     * POI检索覆盖物
     */
    private void setPOILayer() {

    }


    //设置热力图
    private void setHeatFunc() {
        //1、第一步渐变颜色值
        //设置渐变颜色值
        int[] DEFAULT_GRADIENT_COLORS = {Color.rgb(102, 225, 0), Color.rgb(255, 0, 0)};
        float[] DEFAULT_GRADIENT_START_POINTS = {0.2f, 1f};
        //构造颜色渐变对象
        Gradient gradient = new Gradient(DEFAULT_GRADIENT_COLORS, DEFAULT_GRADIENT_START_POINTS);

        //2、准备数据
        //以下数据为随机生成地理位置点，开发者根据自己的实际业务，传入自有位置数据即可
        List<LatLng> randomList = new ArrayList<LatLng>();
        Random r = new Random();
        for (int i = 0; i < 500; i++) {
            // 116.220000,39.780000 116.570000,40.150000
            int rlat = r.nextInt(370000);
            int rlng = r.nextInt(370000);
            int lat = 39780000 + rlat;
            int lng = 116220000 + rlng;
            LatLng ll = new LatLng(lat / 1E6, lng / 1E6);
            randomList.add(ll);
        }

        //3、添加、显示热力图
        //在大量热力图数据情况下，build过程相对较慢，建议放在新建线程实现
        HeatMap heatMap = new HeatMap.Builder()
                .data(randomList)
                .gradient(gradient)
                .build();
        mBaiduMap.addHeatMap(heatMap);
        removeHeatMap(heatMap, false);
    }

    //删除热力图
    private void removeHeatMap(HeatMap heatMap, boolean isRemove) {
        if (isRemove) {
            heatMap.removeHeatMap();
        }
    }

    /**
     * 地图图层，又叫图片图层即开发者可在地图的指定位置上添加图片。该图片可随地图的平移、缩放、旋转等操作做相应的变换。该图层是一种特殊的Overlay，
     * 它位于底图和底图标注层之间（即该图层不会遮挡地图标注信息）。
     */
    private void groundOverlay() {
        LatLng pt1 = new LatLng(39.92235, 116.380338);
        LatLng pt2 = new LatLng(39.947246, 116.414977);
        LatLngBounds bounds = new LatLngBounds.Builder()
                .include(pt1)
                .include(pt2)
                .build();
        BitmapDescriptor bitmap = BitmapDescriptorFactory.fromResource(R.mipmap.ground_layer);
        OverlayOptions options = new GroundOverlayOptions()
                .positionFromBounds(bounds)
                .image(bitmap)
                .transparency(0.8f);
        mBaiduMap.addOverlay(options);
    }

    private void setPopLayer(MarkerPoint point) {
        LatLng pt = new LatLng(39.86923, 116.397428);
        Button button = new Button(getApplicationContext());
        button.setBackgroundResource(R.mipmap.pop_icon);
        button.setText("更换图标");
        InfoWindow mInfoWindow = new InfoWindow(button, pt, -47);
        mBaiduMap.showInfoWindow(mInfoWindow);
    }

    /**
     * @param point
     */
    private void textOverLay(MarkerPoint point) {
        LatLng llTextPoint = new LatLng(39.86923, 116.397428);
        //构建文字Options 对象
        OverlayOptions textOptions = new TextOptions()
                .bgColor(0xAAFFFF00)
                .fontSize(24)
                .fontColor(0xFFFF00FF)
                .text("百度地图")
                .rotate(-30)
                .position(llTextPoint);
        mBaiduMap.addOverlay(textOptions);
    }

    /**
     * 多彩折线
     *
     * @param list
     */
    private void polyline(List<MarkerPoint> list) {

    }

    /**
     * 设置多边形图形覆盖物:点(Dot),折线(Polyline),弧线（Arc）,圆（Circle）,多边形（PolyGon）
     *
     * @param list
     */
    private void addMultiShape(List<MarkerPoint> list) {
        initList();
        OverlayOptions polygonOption = new PolygonOptions()
                .points(mPointList)
                .stroke(new Stroke(1, 0xAA00FF00))
                .fillColor(0xAA00FF00);
        mBaiduMap.addOverlay(polygonOption);
    }


    /**
     * 底图标注：SDK提供控制底图标注的showMapPoint方法，默认显示底图标注。利用此
     * 属性可得到仅系那是道路信息的地图
     *
     * @param isShowPoi
     */
    private void bottomLayerMark(boolean isShowPoi) {
        mBaiduMap.showMapPoi(isShowPoi);
    }

    //设置单个定位标记
    private void onePointMarker(MarkerPoint markerPoint) {
        LatLng point = new LatLng(markerPoint.getLatitude(), markerPoint.getLongitude());
        BitmapDescriptor bitmap = BitmapDescriptorFactory.fromResource(R.mipmap.mark_icon);
        OverlayOptions option = new MarkerOptions()
                .position(point)
                .icon(bitmap);
        mBaiduMap.addOverlay(option);
    }

    /**
     * 设置多个marker
     */
    private void listPointMaker(List<MarkerPoint> markePointList) {
        LatLng point = null;
        BitmapDescriptor bitmap = null;
        OverlayOptions options = null;
        for (int i = 0; i < markePointList.size(); i++) {
            MarkerPoint markePoint = markePointList.get(i);
            bitmap = BitmapDescriptorFactory.fromResource(R.mipmap.mark_icon);
            point = new LatLng(markePoint.getLatitude(), markePoint.getLongitude());
            options = new MarkerOptions()
                    .position(point)
                    .icon(bitmap)
                    .animateType(MarkerOptions.MarkerAnimateType.drop);
            mBaiduMap.addOverlay(options);
        }
    }


    private void setMarkerDrag(MarkerPoint markePoint) {
        LatLng point = new LatLng(markePoint.getLatitude(), markePoint.getLongitude());
        OverlayOptions options = new MarkerOptions()
                .position(point)
                .icon(new BitmapDescriptorFactory().fromResource(R.mipmap.mark_icon))
                .draggable(true)
                .zIndex(9);
        markerLayer = (Marker) mBaiduMap.addOverlay(options);
        mBaiduMap.setOnMarkerDragListener(new BaiduMap.OnMarkerDragListener() {
            @Override
            public void onMarkerDrag(Marker marker) {
                LatLng point = marker.getPosition();
                double latitude = point.latitude;
                double longitude = point.longitude;
                Log.i("_point_drag", "纬度： " + latitude + " 经度： " + longitude);
            }

            @Override
            public void onMarkerDragEnd(Marker marker) {
                LatLng point = marker.getPosition();
                double latitude = point.latitude;
                double longitude = point.longitude;
                Log.i("_point_end", "纬度： " + latitude + " 经度： " + longitude);
            }

            @Override
            public void onMarkerDragStart(Marker marker) {
                LatLng point = marker.getPosition();
                double latitude = point.latitude;
                double longitude = point.longitude;
                Log.i("_point_start", "纬度： " + latitude + " 经度： " + longitude);
            }
        });
    }

    private void setMarkerClick(MarkerPoint markePoint) {
        LatLng point = new LatLng(markePoint.getLatitude(), markePoint.getLongitude());
        OverlayOptions options = new MarkerOptions()
                .position(point)
                .icon(new BitmapDescriptorFactory().fromResource(R.mipmap.mark_icon))
                .draggable(true)
                .zIndex(9);
        markerLayer = (Marker) mBaiduMap.addOverlay(options);
        mBaiduMap.setOnMarkerClickListener(new BaiduMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                LatLng point = marker.getPosition();
                double latitude = point.latitude;
                double longitude = point.longitude;
                Log.i("_point_", "纬度： " + latitude + " 经度： " + longitude);
                return false;
            }
        });
    }


    private void setMapType(int type) {
        switch (type) {
            case TYPE_NONE:
                mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NONE);
                break;
            case TYPE_NORMAL:
                mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
                break;
            case TYPE_SATELLITE:
                mBaiduMap.setMapType(BaiduMap.MAP_TYPE_SATELLITE);
                break;
        }
    }

    public void initOverlay() {

    }

    @Override
    protected void onPause() {
        // MapView的生命周期与Activity同步，当activity挂起时需调用MapView.onPause()
        mMapView.onPause();
        super.onPause();
    }

    @Override
    protected void onResume() {
        // MapView的生命周期与Activity同步，当activity恢复时需调用MapView.onResume()
        mMapView.onResume();
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        // MapView的生命周期与Activity同步，当activity销毁时需调用MapView.destroy()
        mMapView.onDestroy();
        super.onDestroy();
        // 回收 bitmap 资源
    }

}
