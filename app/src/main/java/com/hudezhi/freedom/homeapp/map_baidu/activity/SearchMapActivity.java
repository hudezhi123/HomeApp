package com.hudezhi.freedom.homeapp.map_baidu.activity;

import android.app.Activity;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.CircleOptions;
import com.baidu.mapapi.map.GroundOverlay;
import com.baidu.mapapi.map.GroundOverlayOptions;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.Stroke;
import com.baidu.mapapi.map.SupportMapFragment;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.model.LatLngBounds;
import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiBoundSearchOption;
import com.baidu.mapapi.search.poi.PoiCitySearchOption;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiDetailSearchOption;
import com.baidu.mapapi.search.poi.PoiIndoorResult;
import com.baidu.mapapi.search.poi.PoiNearbySearchOption;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.poi.PoiSearch;
import com.baidu.mapapi.search.poi.PoiSortType;
import com.baidu.mapapi.search.sug.OnGetSuggestionResultListener;
import com.baidu.mapapi.search.sug.SuggestionResult;
import com.baidu.mapapi.search.sug.SuggestionSearch;
import com.hudezhi.freedom.homeapp.R;
import com.hudezhi.freedom.homeapp.map_baidu.baiduutils.PoiOverlay;
import com.hudezhi.freedom.homeapp.utils.AnnotateUtil;
import com.hudezhi.freedom.homeapp.utils.BindView;

import java.util.ArrayList;
import java.util.List;

public class SearchMapActivity extends FragmentActivity implements OnGetPoiSearchResultListener, OnGetSuggestionResultListener {

    @BindView(id = R.id.spinner_city_baidu_map)
    private Spinner citySpinner;
    @BindView(id = R.id.spinner_poi_baidu_map)
    private Spinner poiSpinner;
    private BaiduMap mBaiduMap;
    private PoiSearch mPoiSearch;
    private SuggestionSearch mSuggestionSearch;
    private String city = null;
    private String keyWord = null;
    private int loadIndex = 0;
    private int searchType = -1;
    private ArrayAdapter<String> sugAdapter = null;
    private List<String> sugList = null;

    private LatLng center = null;
    private LatLngBounds bounds = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_map);
        AnnotateUtil.initBindView(this);
        init();
    }

    private void init() {
        initView();
        initPoiSearch();

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

    private void initView() {
        mBaiduMap = ((SupportMapFragment) (getSupportFragmentManager().findFragmentById(R.id.fragment_map_poi))).getBaiduMap();
        citySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                city = (String) parent.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        poiSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                keyWord = (String) parent.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void initPoiSearch() {
        mPoiSearch = PoiSearch.newInstance();
        mPoiSearch.setOnGetPoiSearchResultListener(this);
        mSuggestionSearch = SuggestionSearch.newInstance();
        mSuggestionSearch.setOnGetSuggestionResultListener(this);
        center = new LatLng(39.92235, 116.380338);
        LatLng southwest = new LatLng(39.92235, 116.380338);
        LatLng northeast = new LatLng(39.947246, 116.414977);
        bounds = new LatLngBounds
                .Builder()
                .include(southwest)
                .include(northeast)
                .build();
    }

    /**
     * 市内搜索  btn onclick 事件
     *
     * @param view
     */
    public void inCitySearch(View view) {
        PoiCitySearchOption cityOptions = new PoiCitySearchOption();
        mPoiSearch.searchInCity(cityOptions.city(city).keyword(keyWord).pageNum(loadIndex));
    }

    /**
     * 周边区域搜索  btn onclick 事件
     *
     * @param view
     */
    public void atAroundSearch(View view) {
        searchType = 2;
        PoiNearbySearchOption nearbyOption = new PoiNearbySearchOption()
                .keyword(city)
                .keyword(keyWord)
                .sortType(PoiSortType.distance_from_near_to_far)
                .location(new LatLng(39.92235, 116.380338))
                .radius(100)
                .pageNum(loadIndex);
        mPoiSearch.searchNearby(nearbyOption);
    }

    /**
     * 区域搜索  btn onclick 事件
     *
     * @param view
     */
    public void onAreaSearch(View view) {
        searchType = 3;

        PoiBoundSearchOption boundOption = new PoiBoundSearchOption()
                .keyword(keyWord)
                .bound(bounds);
        mPoiSearch.searchInBound(boundOption);
    }


    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        mPoiSearch.destroy();
        mSuggestionSearch.destroy();
        super.onDestroy();
    }

    private class MyPoiOverlay extends PoiOverlay {

        /**
         * 构造函数
         *
         * @param baiduMap 该 PoiOverlay 引用的 BaiduMap 对象
         */
        public MyPoiOverlay(BaiduMap baiduMap) {
            super(baiduMap);
        }

        @Override
        public boolean onPoiClick(int index) {
            super.onPoiClick(index);
            PoiInfo info = getPoiResult().getAllPoi().get(index);
            mPoiSearch.searchPoiDetail(new PoiDetailSearchOption().poiUid(info.uid));
            return true;
        }
    }

    /**
     * 获取到搜索的结果 City、NearBy、Bounds
     *
     * @param poiResult
     */
    @Override
    public void onGetPoiResult(PoiResult poiResult) {
        if (poiResult == null || poiResult.error == SearchResult.ERRORNO.RESULT_NOT_FOUND) {
            Toast.makeText(SearchMapActivity.this, "未找到结果", Toast.LENGTH_LONG)
                    .show();
            return;
        }
        if (poiResult.error == SearchResult.ERRORNO.NO_ERROR) {
            mBaiduMap.clear();
            PoiOverlay overlay = new MyPoiOverlay(mBaiduMap);
            mBaiduMap.setOnMarkerClickListener(overlay);
            overlay.setData(poiResult);
            overlay.addToMap();
            overlay.zoomToSpan();
        }
        switch (searchType) {
            case 2:
                showNearby();
                break;
            case 3:
                showBound();
                break;
            default:
                break;
        }
    }


    /**
     * 画Nearby Layer
     */
    private void showNearby() {
        BitmapDescriptor bitmap = BitmapDescriptorFactory.fromResource(R.drawable.icon_geo);
        OverlayOptions markerOption = new MarkerOptions()
                .icon(bitmap)
                .position(center);
        mBaiduMap.addOverlay(markerOption);

        OverlayOptions circleOption = new CircleOptions()
                .center(center)
                .fillColor(0xCCCCCC00)
                .radius(100)
                .stroke(new Stroke(5, 0xFFFF00FF));
        mBaiduMap.addOverlay(circleOption);
    }

    /**
     * 区域 Layer
     */
    private void showBound() {
        BitmapDescriptor bitmap = BitmapDescriptorFactory.fromResource(R.drawable.ground_overlay);
        OverlayOptions groundOption = new GroundOverlayOptions()
                .image(bitmap)
                .positionFromBounds(bounds)
                .transparency(0.8f);
        mBaiduMap.addOverlay(groundOption);
        bitmap.recycle();
    }


    /**
     * 获取POI详情搜索结果，得到searchPoiDetail返回的搜索结果
     *
     * @param poiDetailResult
     */
    @Override
    public void onGetPoiDetailResult(PoiDetailResult poiDetailResult) {
        if (poiDetailResult.error != SearchResult.ERRORNO.NO_ERROR) {
            Toast.makeText(SearchMapActivity.this, "抱歉，未找到结果", Toast.LENGTH_SHORT)
                    .show();
        } else {
            Toast.makeText(SearchMapActivity.this, poiDetailResult.getName() + ": " + poiDetailResult.getAddress(), Toast.LENGTH_SHORT)
                    .show();
        }
    }

    /**
     * 室内搜索结果
     *
     * @param poiIndoorResult
     */
    @Override
    public void onGetPoiIndoorResult(PoiIndoorResult poiIndoorResult) {

    }


    /**
     * 获取在线建议搜索结果，得到requestSuggestion返回的搜索结果
     *
     * @param suggestionResult
     */
    @Override
    public void onGetSuggestionResult(SuggestionResult suggestionResult) {
//        if (suggestionResult == null || suggestionResult.getAllSuggestions() == null || suggestionResult.getAllSuggestions().size() <= 0) {
//            return;
//        }
//        sugList = new ArrayList<>();
//        for (SuggestionResult.SuggestionInfo sugInfo : suggestionResult.getAllSuggestions()) {
//            if (!TextUtils.isEmpty(sugInfo.key)) {
//                sugList.add(sugInfo.key);
//            }
//        }
//        sugAdapter = new ArrayAdapter<String>(SearchMapActivity.this, android.R.layout.simple_dropdown_item_1line, sugList);

    }
}
