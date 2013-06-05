//=======================================================================
//  ClassName : ClalisMain
//  �T�v      : Clalis ���{�ꕶ�͒��o���\�b�h�̎g�p�T���v��
//
//  Clalis
//  Copyright(c) 2011-2013 LipliStyle. All Rights Reserved.
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
			//�|�X�g�f�[�^�̍쐬
			final List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>();
			nameValuePair.add(new BasicNameValuePair("url", "http://www.yahoo.co.jp/"));

			//���ʂ̎擾
			String jsonText = inputStreemToString(post("http://liplis.mine.nu/Clalis/v30/Post/Json/clalisWebExtractJpSentenceList.aspx", nameValuePair).getEntity().getContent());

			//API���ʎ󂯎��p�N���X
			ResWebSummaryList result =  new Gson().fromJson(jsonText, ResWebSummaryList.class);

			//ResWebSummaryList�N���X�́uresWordList�v�v���p�e�B�Ɍ��ʂ��i�[����Ă���̂ŁA�񂵂Ď��o���B
			for(String msg : result.resWordList)
			{
				System.out.println(msg);
			}
		} catch (IOException e) {
			return ;
		}
	}

    /**
     *�C���v�b�g�X�g���[�����X�g�����O�ɕϊ����܂��B
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
	 *HTTP�|�X�g�𑗐M���A���ʂ��󂯎��܂��B
	 * @param pUrl
	 * @param nameValuePair
	 * @return
	 */
	public static HttpResponse post(String pUrl, List<NameValuePair> nameValuePair)
	{
        try
        {
        	final HttpClient httpclient = new DefaultHttpClient();				//�N���C�A���g
        	final HttpParams httpParamsObj = httpclient.getParams();				//http�p�����[�^�[
            HttpConnectionParams.setConnectionTimeout(httpParamsObj, 4500);	//�^�C���A�E�g�ݒ�
            HttpConnectionParams.setSoTimeout(httpParamsObj,4500);			//�^�C���A�E�g�ݒ�
            HttpPost httppost = new HttpPost(pUrl);							//�|�X�g

            //POST���M
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePair, "UTF-8"));
            HttpResponse response = httpclient.execute(httppost);

            //�T�[�o�[����̉������擾
            return response;

	    } catch (MalformedURLException e) {
	        //System.err.println("Invalid URL format: " + urlString);
	        return null;
	    } catch (IOException e) {
	        return null;
	    }
	}


}
