package com.app.yjw.net;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import com.app.yjw.imgtool.ImgTool;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

public class ImgNetManager {

	public static Bitmap getImageFromURLUsePostMethod(String url,
			List<BasicNameValuePair> parameters) {
		Bitmap bm = null;
		try {
			HttpEntity httpentity = new UrlEncodedFormEntity(parameters, "UTF8");
			HttpPost post = new HttpPost(url);
			post.setEntity(httpentity);
			HttpResponse response = new DefaultHttpClient().execute(post);
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				// TODO �����ַ���
				// alreadyGotResponse = true;
				InputStream iStream = response.getEntity().getContent();
				ByteArrayOutputStream outstream = new ByteArrayOutputStream();
				byte[] buffer = new byte[1024];
				int len = -1;
				while ((len = iStream.read(buffer)) != -1) {
					outstream.write(buffer, 0, len);
				}
				outstream.close();
				iStream.close();
				bm = BitmapFactory.decodeByteArray(outstream.toByteArray(), 0,
						outstream.toByteArray().length);
			} else {
				// TODO �յ���״̬���д���
				Log.e("doPost", "error status code"
						+ response.getStatusLine().getStatusCode());
			}
		} catch (HttpResponseException hre) {
			hre.printStackTrace();
		} catch (ConnectTimeoutException cte) {
			cte.printStackTrace();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
		return bm;
	}

	public static Bitmap getImageFromURL(String url) throws IOException {
		return ImgNetManager.getImageFromURL(new URL(url));
	}

	public static Bitmap getImageFromURL(URL url) throws IOException {
		Bitmap bm = null;
		HttpURLConnection conn;
		conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		conn.setConnectTimeout(10 * 1000);
		if (conn.getResponseCode() == 200) {
			InputStream inputStream = conn.getInputStream();
			ByteArrayOutputStream outstream = new ByteArrayOutputStream();
			byte[] buffer = new byte[1024];
			int len = -1;
			while ((len = inputStream.read(buffer)) != -1) {
				outstream.write(buffer, 0, len);
			}
			outstream.close();
			inputStream.close();
			bm = BitmapFactory.decodeByteArray(outstream.toByteArray(), 0,
					outstream.toByteArray().length);
		}
		return bm;
	}

	public String getImageDataFromURL(URL url) throws IOException {
		String returnStr = null;
		HttpURLConnection conn;
		conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		conn.setConnectTimeout(10 * 1000);
		if (conn.getResponseCode() == 200) {
			InputStream inputStream = conn.getInputStream();
			ByteArrayOutputStream outstream = new ByteArrayOutputStream();
			byte[] buffer = new byte[1024];
			int len = -1;
			while ((len = inputStream.read(buffer)) != -1) {
				outstream.write(buffer, 0, len);
			}
			outstream.close();
			inputStream.close();
			returnStr = outstream.toString();
		}
		return returnStr;
	}

	public static String uploadImage(URL url, Bitmap image) {
		StringBuffer b = new StringBuffer();
		try {
			String end = "\r\n";
			String twoHyphens = "--";
			String boundary = "*****";
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			/* ����Input��Output����ʹ��Cache */
			con.setDoInput(true);
			con.setDoOutput(true);
			con.setUseCaches(false);
			/* ���ô��͵�method=POST */
			con.setRequestMethod("POST");
			/* setRequestProperty */
			con.setRequestProperty("Connection", "Keep-Alive");
			con.setRequestProperty("Charset", "UTF-8");
			con.setRequestProperty("Content-Type",
					"multipart/form-data;boundary=" + boundary);
			/* ����DataOutputStream */
			DataOutputStream ds = new DataOutputStream(con.getOutputStream());
			ds.writeBytes(twoHyphens + boundary + end);
			ds.writeBytes("Content-Disposition: form-data;"
					+ "name=\"picture\";filename=\"aaaa.jpg\"" + end);
			ds.writeBytes(end);
			// /* ȡ���ļ���FileInputStream */
			// FileInputStream fStream = new FileInputStream(uploadFile);
			// /* ����ÿ��д��1024bytes */
			// int bufferSize = 1024;
			// byte[] buffer = new byte[bufferSize];
			// int length = -1;
			// /* ���ļ���ȡ������������ */
			// while ((length = fStream.read(buffer)) != -1) {
			// /* ������д��DataOutputStream�� */
			// ds.write(buffer, 0, length);
			// }
			ds.write(ImgTool.Bitmap2Bytes(image));
			ds.writeBytes(end);
			ds.writeBytes(twoHyphens + boundary + twoHyphens + end);
			/* close streams */
			// fStream.close();
			ds.flush();
			/* ȡ��Response���� */
			System.out.println(con.getResponseMessage());
			InputStream is = con.getInputStream();
			int ch;
			while ((ch = is.read()) != -1) {
				b.append((char) ch);
			}
			/* �ر�DataOutputStream */
			ds.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new String(b);
	}
}
