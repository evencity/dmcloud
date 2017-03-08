package com.apical.dmcloud.commons.infra.CoordinateConvert;

import java.io.IOException;
import java.util.Date;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import com.apical.dmcloud.commons.infra.CONSTANT;
import com.apical.dmcloud.commons.infra.HttpRequestUtils;

public class GeoCoder
{
	private static final String params = "ak=" + CONSTANT.LBSGeocodingACCESSKey
			+ CONSTANT.LBSGeocodingParams + "&location=";
	
	/**
	 * 使用百度地图的geocoding api进行逆地址解析，得到地址信息
	 * @param longitude 经度
	 * @param latitude 纬度
	 * @return 结构化地址信息，如：“广东省深圳市南山区朗山五号路”
	 * @throws IOException
	 * @throws JSONException
	 */
	public static String getFormattedAddressByLBS(float longitude, float latitude) throws IOException, JSONException
	{
		//拼接参数，可能是：
		String param = params + latitude + "," + longitude;
		String address = HttpRequestUtils.doPostRequest(CONSTANT.LBSGeocodingUrl, param, 3000,
				2000);
		
		JSONObject response = new JSONObject(address);
		JSONObject result = response.getJSONObject("result");
		String formattedAddress = result.getString("formatted_address");
		
		return formattedAddress;
	}
	
	/**
	 * 使用百度地图的geocoding api进行逆地址解析，得到地址信息
	 * @param longitude 经度
	 * @param latitude 纬度
	 * @return 地址信息，如：“中国，广东省，深圳市，南山区，朗山五号路”
	 * @throws IOException
	 * @throws JSONException
	 */
	public static AddressComponent getAddressComponentByLBS(float longitude, float latitude) throws IOException, JSONException
	{
		String param = params + latitude + "," + longitude;
		String address = HttpRequestUtils.doPostRequest(CONSTANT.LBSGeocodingUrl, param,
				3000, 2000);
		
		JSONObject response = new JSONObject(address);
		JSONObject result = response.getJSONObject("result");
		JSONObject component = result.getJSONObject("addressComponent");
		
		AddressComponent addressComponent = new AddressComponent();
		addressComponent.setCountry(component.getString("country"));
		addressComponent.setProvince(component.getString("province"));
		addressComponent.setCity(component.getString("city"));
		addressComponent.setDistrict(component.getString("district"));
		addressComponent.setStreet(component.getString("street"));
		
		return addressComponent;
	}
	
	public static void main(String args[])
	{
		try
		{
			System.out.println(new Date(System.currentTimeMillis()));
			//请求的格式为：
			/*
			 http://api.map.baidu.com/geocoder/v2/?ak=5X0unSx0GZIsdcLrFGsjCCGl&coordtype=wgs84ll
			 	&output=json&pois=0&location=113.354337,23.022244
			 */
			String address = GeoCoder.getFormattedAddressByLBS(113.94271996031f, 22.560485980116f);
			System.out.println(address);
			
			System.out.println(new Date(System.currentTimeMillis()));
			
			AddressComponent component = GeoCoder.getAddressComponentByLBS(113.94271996031f, 22.560485980116f);
			System.out.println(component.getCountry());
			System.out.println(component.getProvince());
			System.out.println(component.getCity());
			System.out.println(component.getDistrict());
			System.out.println(component.getStreet());
			/*
			 response=
			 {
			 	"status":0,
			 	"result":
			 	{
			 		"location":
			 		{
			 			"lng":113.9546089439,
			 			"lat":22.559827566218
			 		},
			 		"formatted_address":"广东省深圳市南山区朗山五号路",
			 		"business":"科技园,南山医院,大冲",
			 		"addressComponent":
			 		{
			 			"city":"深圳市",
			 			"country":"中国",
			 			"direction":"",
			 			"distance":"",
			 			"district":"南山区",
			 			"province":"广东省",
			 			"street":"朗山五号路",
			 			"street_number":"",
			 			"country_code":0
			 		},
			 		"poiRegions":[],
			 		"sematic_description":"同方信息港西南169米",
			 		"cityCode":340
			 	}
			 }
			 */
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
