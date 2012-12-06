package cn.com.tomy.utils;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class DataAcess {

	public static String getStreamByUrl(String _url) {

		InputStream in = null;
		String result = null;
		long start = 0;
		long end = 0;
		try {
			System.out.println("-----------------" + _url);
			URL url = new URL(_url);
			start = System.currentTimeMillis();
		 
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setConnectTimeout(2000);
			int code = conn.getResponseCode();
			if (code == 200) {
				in = conn.getInputStream();
				System.out.println("正常通信...");
			}
		} catch (Exception e) {
			System.out.println("通信异常...");
			end = System.currentTimeMillis();
			e.printStackTrace();
			System.out.println((end-start)/1000+"秒");
			return null;
		}

		/*
		 * HttpClient client=new DefaultHttpClient(); HttpGet httpGet=new
		 * HttpGet(_url); try { HttpResponse response=client.execute(httpGet);
		 * in=response.getEntity().getContent(); } catch
		 * (ClientProtocolException e) { // TODO Auto-generated catch block
		 * e.printStackTrace(); } catch (IOException e) { // TODO Auto-generated
		 * catch block e.printStackTrace(); }
		 */

		
		/**
		 * 由于在网络传过来的数据里面含有\n,\t等符号，会导致解析出现异常。这里先获得字
		 * 符串，然后将\n,\t等去掉。
		 */
		StringBuffer sb = new StringBuffer();
		BufferedReader reader = new BufferedReader(new InputStreamReader(in));
		char[] buffer = new char[1024];
		int len = -1;
		try {
			while ((len = reader.read(buffer)) != -1) {
				sb.append(buffer);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		result = sb.toString().replace("\n", "").replace("\t", "");
		result = result.substring(0, result.indexOf("</dict>") + 7);
		System.out.println(result);
		return result;
	}

}
