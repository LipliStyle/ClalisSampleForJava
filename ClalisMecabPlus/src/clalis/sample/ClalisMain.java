//=======================================================================
//ClalisSample
//  ClassName : ClalisMain
//  概要      : Clalis 形態素解析 ClalisMecabPlus サンプル
//
// Copyright  : 2011-2013 LipliStyle
//
// ライセンス : MIT License
// ・本ソフトウェアは無保証です。作者は責任を追いません。
// ・上記の著作権表示を記載して下さい。
// ・上記の２項に同意頂ければ自由に使用して頂けます。
//
//=======================================================================
package clalis.sample;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import com.google.gson.Gson;

public class ClalisMain {

	/**
	 *
	 * @param args
	 */
	public static void main(String[] args) {
		try{
			//ポストデータの作成
			final List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>();
			nameValuePair.add(new BasicNameValuePair("sentence", "今日はお天気がいいですね。お洗濯にはもってこいです！"));

			//結果の取得
			String jsonText = inputStreemToString(post("http://liplis.mine.nu/Clalis/v30/Post/Json/clalisMecabFull.aspx", nameValuePair).getEntity().getContent());

			//API結果受け取り用クラス
			ResMecabResultFull result =  new Gson().fromJson(jsonText, ResMecabResultFull.class);

			//ResMecabResultFullクラスの「resWordList」プロパティに結果が格納されているので、回して取り出す。
			for(MsgMecabResultFull msg : result.resWordList)
			{
				System.out.println("単語:" + msg.name + " , 品詞:" + msg.pos + " , 品詞細分類1:" + msg.pos1 + " , 品詞細分類2:" + msg.pos2 + " , 品詞細分類3:" + msg.pos3 + " , 活用形:" + msg.infetted1 + " , 活用形:" + msg.infetted2 + " , 原形:" + msg.prototype + " , 読み:" + msg.read + " , 発音:" + msg.pronunciation);
			}
		} catch (IOException e) {
			return ;
		}
	}

    /**
     *インプットストリームをストリングに変換します。
     * @param in
     * @return
     * @throws IOException
     */
	public static String inputStreemToString(InputStream in) throws IOException{
	    BufferedReader reader = new BufferedReader(new InputStreamReader(in, "UTF-8"));
	    StringBuffer buf = new StringBuffer();
	    String str;
	    while ((str = reader.readLine()) != null) {
	            buf.append(str);
	    }
	    return buf.toString();
	}

	/**
	 *HTTPポストを送信し、結果を受け取ります。
	 * @param pUrl
	 * @param nameValuePair
	 * @return
	 */
	public static HttpResponse post(String pUrl, List<NameValuePair> nameValuePair)
	{
        try
        {
        	final HttpClient httpclient = new DefaultHttpClient();				//クライアント
        	final HttpParams httpParamsObj = httpclient.getParams();				//httpパラメーター
            HttpConnectionParams.setConnectionTimeout(httpParamsObj, 4500);	//タイムアウト設定
            HttpConnectionParams.setSoTimeout(httpParamsObj,4500);			//タイムアウト設定
            HttpPost httppost = new HttpPost(pUrl);							//ポスト

            //POST送信
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePair, "UTF-8"));
            HttpResponse response = httpclient.execute(httppost);

            //サーバーからの応答を取得
            return response;

	    } catch (MalformedURLException e) {
	        //System.err.println("Invalid URL format: " + urlString);
	        return null;
	    } catch (IOException e) {
	        return null;
	    }
	}


}
