ClalisSampleForJava
===================
ClalisをJavaから使用するための簡単なサンプルです。  
ソースを読んで頂けると、APIの叩き方が分かるかと思います。  
ClalisAPIの実行結果をコンソール出力するコンソールアプリになっています。  

ClalisAPIはSOAP、JSON、XMLのインターフェースを用意していますが、  
JavaのサンプルはJsonのみです。  

動作環境
------
Eclipseでプロジェクトを作成しています。

ライセンス
------
 Copyright  : 2013 LipliStyle.
 
 ライセンス : MIT License  
 ・本ソフトウェアは無保証です。作者は責任を追いません。  
 ・上記の著作権表示を記載して下さい。  
 ・上記の２項に同意頂ければ自由に使用して頂けます。  

使用ライブラリ
-----
Jsonの読み込みに「Gson」を使用しています。
http://code.google.com/p/google-gson/

HTTPをPostするのに「Apache HttpComponents」を使用しています。
http://hc.apache.org/

ご注意
------
ソースコードの使用はライセンスに基づく通りですが、  
Clalisサーバーに不用意に負荷をかける用途には使用しないで下さい。   


サンプルの紹介と解説
------
Javaのコンソールアプリとして作成しています。  
Jsonインターフェースを使用したサンプルになっています。  

共通メソッドについて
------
サンプル内では、HtttpPostの処理、  
およびHTTPレスポンスのストリームをString型に変換する処理のため、  
以下の2つのメソッドを共通で使用しています。  

inputStreemToString
-------

        public static String inputStreemToString(InputStream in) throws IOException{
          BufferedReader reader = new BufferedReader(new InputStreamReader(in, "UTF-8"));
          StringBuffer buf = new StringBuffer();
          String str;
          while ((str = reader.readLine()) != null) {
            buf.append(str);
          }
          return buf.toString();
        }

post
------

        public static HttpResponse post(String pUrl, List<NameValuePair> nameValuePair){
          try
          {
            final HttpClient httpclient = new DefaultHttpClient();
            final HttpParams httpParamsObj = httpclient.getParams();
            HttpConnectionParams.setConnectionTimeout(httpParamsObj, 4500);
            HttpConnectionParams.setSoTimeout(httpParamsObj,4500);
            HttpPost httppost = new HttpPost(pUrl);
            
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePair, "UTF-8"));
            HttpResponse response = httpclient.execute(httppost);
            
            return response;
          } catch (MalformedURLException e) {
            return null;
          } catch (IOException e) {
            return null;
          }
        }
　
形態素解析 ClalisMecab
------
■概要
対象の日本語の文章を形態素解析して、単語に分解します。  
サンプルでは、「今日はお天気がいいですね。お洗濯にはもってこいです！」という文章を  
形態素解析し、コンソール出力する処理となっています。  

このメソッドは、日本語の分解結果と品詞のみを返します。  
単純に文章を形態素に分解するだけであれば、本メソッドが有効です。  
情報が足りない場合は、ClalisMecabPlusメソッドの使用を検討してください。  

サーバーサイドで形態素解析器「Mecab」を使用しています。  
Mecabについては下記アドレスを参照下さい。  
https://code.google.com/p/mecab/

■サンプル
mainメソッド

        public static void main(String[] args) {
          try
          {
            final List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>();
            nameValuePair.add(new BasicNameValuePair("sentence", 
            "今日はお天気がいいですね。お洗濯にはもってこいです！"));
            
            String jsonText = inputStreemToString(
            post("http://liplis.mine.nu/Clalis/v30/Post/Json/clalisMecab.aspx",
                                              nameValuePair).getEntity().getContent());
                  
            ResMecabResult result =  new Gson().fromJson(jsonText, 
            ResMecabResult.class);
            
            for(MsgMecabResult msg : result.resWordList)
            {
              System.out.println("単語:" + msg.name + " , 品詞:" + msg.pos 
                                                  + " , 品詞細分類1:" + msg.pos1);
            }
          } catch (IOException e) {
            return ;
          }
        }

ResMecabResult API取得結果格クラス

        public class ResMecabResult {
          public MsgMecabResult[] resWordList;
          
          public MsgMecabResult[] getResWordList() {
            return resWordList;
          }
        
          public void setResWordList(MsgMecabResult[] resWordList) {
            this.resWordList = resWordList;
          }
        }

MsgMecabResult 分解された1つの要素を表すクラス

        public class MsgMecabResult {
          public int idx;				//インデックス
          public String name;			//分解された文字です。
          public String pos;  		//分解された文字の品詞です。
          public String pos1;			//分解された文字の品詞細分類1です。
          
          public int getIdx() {
            return idx;
          }
          public void setIdx(int idx) {
              this.idx = idx;
          }
          public String getName() {
            return name;
          }
          public void setName(String name) {
            this.name = name;
          }
          public String getPos() {
            return pos;
          }
          public void setPos(String pos) {
            this.pos = pos;
          }
          public String getPos1() {
            return pos1;
          }
          public void setPos1(String pos1) {
            this.pos1 = pos1;
          }
        }
　
　
形態素解析 ClalisMecabPlus
------
■概要
このメソッドはMecabが返してくる全ての情報を取得します。  
サンプルでは、「今日はお天気がいいですね。お洗濯にはもってこいです！」という文章を  
形態素解析し、コンソール出力する処理となっています。  

■サンプル
mainメソッド

        public static void main(String[] args) {
          try{
            final List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>();
            nameValuePair.add(new BasicNameValuePair("sentence", 
           "今日はお天気がいいですね。お洗濯にはもってこいです！"));
          
            String jsonText = inputStreemToString(
            post("http://liplis.mine.nu/Clalis/v30/Post/Json/clalisMecabFull.aspx", 
            nameValuePair).getEntity().getContent());
          
            ResMecabResultFull result =  new Gson().fromJson(jsonText,
            ResMecabResultFull.class);
          
            for(MsgMecabResultFull msg : result.resWordList)
            {
              System.out.println("単語:" + msg.name + " , 品詞:" + msg.pos
                + " , 品詞細分類1:" + msg.pos1 + " , 品詞細分類2:" + msg.pos2
                + " , 品詞細分類3:" + msg.pos3 + " , 活用形:" + msg.infetted1
                + " , 活用形:" + msg.infetted2 + " , 原形:" + msg.prototype
                + " , 読み:" + msg.read + " , 発音:" + msg.pronunciation);
            }
          } catch (IOException e) {
            return ;
          }
        }


ResMecabResultFull API取得結果格クラス

      public class ResMecabResultFull {
      	public MsgMecabResultFull[] resWordList;
      
      	public MsgMecabResultFull[] getResWordList() {
      		return resWordList;
      	}
      
      	public void setResWordList(MsgMecabResultFull[] resWordList) {
      		this.resWordList = resWordList;
      	}
      }


MsgMecabResultFull 分解された1つの要素を表すクラス

      public class MsgMecabResultFull {
      	public int idx;			//インデックス
      	public String name;		//分解された文字です。
      	public String pos;		//分解された文字の品詞です。
      	public String pos1;		//分解された文字の品詞細分類1です。
      	public String pos2;		//分解された文字の品詞細分類2です。
      	public String pos3;		//分解された文字の品詞細分類3です。
      	public String infetted1;	//分解された文字の活用形です。
      	public String infetted2;	//分解された文字の活用形です。
      	public String prototype;	//分解された文字の原形です。
      	public String read;		//分解された文字の読みです。
          public String pronunciation;	//解された文字の発音です。
      
      	public int getIdx() {
      		return idx;
      	}
      	public void setIdx(int idx) {
      		this.idx = idx;
      	}
      	public String getName() {
      		return name;
      	}
      	public void setName(String name) {
      		this.name = name;
      	}
      	public String getPos() {
      		return pos;
      	}
      	public void setPos(String pos) {
      		this.pos = pos;
      	}
      	public String getPos1() {
      		return pos1;
      	}
      	public void setPos1(String pos1) {
      		this.pos1 = pos1;
      	}
      	public String getPos2() {
      		return pos2;
      	}
      	public void setPos2(String pos2) {
      		this.pos2 = pos2;
      	}
      	public String getPos3() {
      		return pos3;
      	}
      	public void setPos3(String pos3) {
      		this.pos3 = pos3;
      	}
      	public String getInfetted1() {
      		return infetted1;
      	}
      	public void setInfetted1(String infetted1) {
      		this.infetted1 = infetted1;
      	}
      	public String getInfetted2() {
      		return infetted2;
      	}
      	public void setInfetted2(String infetted2) {
      		this.infetted2 = infetted2;
      	}
      	public String getPrototype() {
      		return prototype;
      	}
      	public void setPrototype(String prototype) {
      		this.prototype = prototype;
      	}
      	public String getRead() {
      		return read;
      	}
      	public void setRead(String read) {
      		this.read = read;
      	}
      	public String getPronunciation() {
      		return pronunciation;
      	}
      	public void setPronunciation(String pronunciation) {
      		this.pronunciation = pronunciation;
      	}
      }

　
　
感情付与 ClalisEmotional
------
■概要
対象の文章にメタ感情を付与して結果を返します。  
サンプルでは、「今日はお天気がいいですね。お洗濯にはもってこいです！」という文章に  
感情付与処理を行い、コンソール出力する処理となっています。  

■サンプル
mainメソッド

        	public static void main(String[] args) {
        		try{
        			final List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>();
        			nameValuePair.add(new BasicNameValuePair("sentence", 
        					"今日はお天気がいいですね。お洗濯にはもってこいです！"));
        		
        			String jsonText = inputStreemToString(
        					post("http://liplis.mine.nu/Clalis/v30/Post/Json/clalisEmotional.aspx", 
        					nameValuePair).getEntity().getContent());
        
        			ResEmotional result =  new Gson().fromJson(jsonText, 
        					ResEmotional.class);
        		
        			for(MsgLeafAndEmotion msg : result.resWordList)
        			{
        				System.out.println("単語:" + msg.name + " , 感情:" + msg.emotion
        						 + " , 感情値:" + msg.point);
        			}
        		} catch (IOException e) {
        			return ;
        		}
        	}


ResEmotional API取得結果格クラス

        public class ResEmotional {
        	public MsgLeafAndEmotion[] resWordList;
        
        	public MsgLeafAndEmotion[] getResWordList() {
        		return resWordList;
        	}
        
        	public void setResWordList(MsgLeafAndEmotion[] resWordList) {
        		this.resWordList = resWordList;
        	}
        }


MsgLeafAndEmotion分解された1つの要素を表すクラス

        public class MsgLeafAndEmotion {
        	public String name;			//分解された文字です。
        	public int emotion;			//分解された文字の感情です。
        	public int point;			//分解された文字の感情値です。
        
        	public String getName() {
        		return name;
        	}
        	public void setName(String name) {
        		this.name = name;
        	}
        	public int getEmotion() {
        		return emotion;
        	}
        	public void setEmotion(int emotion) {
        		this.emotion = emotion;
        	}
        	public int getPoint() {
        		return point;
        	}
        	public void setPoint(int point) {
        		this.point = point;
        	}
        }

　
　
口調変換 ClalisTone
■概要
対象の文章を口調変換ルールファイルに従って変換します。  
サンプルでは、「今日はお天気がいいですね。お洗濯にはもってこい！」という文章を  
「http://liplis.mine.nu/xml/Tone/LiplisLili.xml」の口調変換ルールに従って  
口調変換を行い、結果をコンソール出力する処理となっています。  

■サンプル
mainメソッド

        	public static void main(String[] args) {
        		try{
        		//ポストデータの作成
        		final List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>();
        		nameValuePair.add(new BasicNameValuePair("sentence",
        					"今日はお天気がいいですね。お洗濯にはもってこい！"));
        		nameValuePair.add(new BasicNameValuePair("toneFileUrl", 
        					"http://liplis.mine.nu/xml/Tone/LiplisLili.xml"));
        
        		String jsonText = inputStreemToString(
        					post("http://liplis.mine.nu/Clalis/v30/Post/Json/ClalisTone.aspx", 
        					nameValuePair).getEntity().getContent());
        
        		ResTone result =  new Gson().fromJson(jsonText, 
        					ResTone.class);
        
        		System.out.println(result.result);
        
        		} catch (IOException e) {
        			return ;
        		}
        	}

ResTone API取得結果格クラス

      public class ResTone {
      	public String result;
      
      	public String getResult() {
      		return result;
      	}
      
      	public void setResult(String result) {
      		this.result = result;
      	}
      }

　
口調変換+感情付与 ClalisToneEmotion
------
■概要
対象の文章を口調変換ルールファイルに従って変換します。
さらに、感情付与を行います。
サンプルでは、「今日はお天気がいいですね。お洗濯にはもってこい！」という文章を
「http://liplis.mine.nu/xml/Tone/LiplisLili.xml」の口調変換ルールに従って
口調変換を行い、結果をコンソール出力する処理となっています。

■サンプル
mainメソッド

        public static void main(String[] args) {
      		try{
      			final List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>();
      			nameValuePair.add(new BasicNameValuePair("sentence", 
      					"今日はお天気がいいですね。お洗濯にはもってこい！"));
      			nameValuePair.add(new BasicNameValuePair("toneFileUrl", 
      					"http://liplis.mine.nu/xml/Tone/LiplisLili.xml"));
      
      			String jsonText = inputStreemToString(
      					post("http://liplis.mine.nu/Clalis/v30/Post/Json/clalisToneEmotional.aspx", 
      					nameValuePair).getEntity().getContent());
      
      			ResEmotional result =  new Gson().fromJson(jsonText, ResEmotional.class);
      
      			for(MsgLeafAndEmotion msg : result.resWordList)
      			{
      				System.out.println("単語:" + msg.name + " , 感情:" + msg.emotion 
      						+ " , 感情値:" + msg.point);
      			}
      		} catch (IOException e) {
      			return ;
      		}
      	}

ResEmotional API取得結果格クラス

      public class ResEmotional {
      	public MsgLeafAndEmotion[] resWordList;
      
      	public MsgLeafAndEmotion[] getResWordList() {
      		return resWordList;
      	}
      
      	public void setResWordList(MsgLeafAndEmotion[] resWordList) {
      		this.resWordList = resWordList;
      	}
      }

MsgLeafAndEmotion 分解された1つの要素を表すクラス

        public class MsgLeafAndEmotion {
        	public String name;			//分解された文字です。
        	public int emotion;			//分解された文字の感情です。
        	public int point;			//分解された文字の感情値です。
        
        	public String getName() {
        		return name;
        	}
        	public void setName(String name) {
        		this.name = name;
        	}
        	public int getEmotion() {
        		return emotion;
        	}
        	public void setEmotion(int emotion) {
        		this.emotion = emotion;
        	}
        	public int getPoint() {
        		return point;
        	}
        	public void setPoint(int point) {
        		this.point = point;
        	}
        }
　
**Web日本語抽出 ClalisWebExtractJp
-----
■概要
対象のURLのサイトから日本語を抽出して返します。  
サンプルでは、「http://www.yahoo.co.jp/」のサイトから日本語抽出し、  
結果をコンソール出力する処理となっています。  

■サンプル
mainメソッド

      	public static void main(String[] args) {
      		try{
      			final List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>();
      			nameValuePair.add(new BasicNameValuePair("url", 
      					"http://www.yahoo.co.jp/"));
      			
      			String jsonText = inputStreemToString(
      					post("http://liplis.mine.nu/Clalis/v30/Post/Json/clalisWebExtractJp.aspx", 
      					nameValuePair).getEntity().getContent());
      			
      			ResWebSummary result =  new Gson().fromJson(jsonText, 
      					ResWebSummary.class);
      			
      			System.out.println(result.result);
      		
      		} catch (IOException e) {
      			return ;
      		}
      	}


ResWebSummary API取得結果格クラス

      public class ResWebSummary {
      	public String result;
      
      	public String getResult() {
      		return result;
      	}
      
      	public void setResult(String result) {
      		this.result = result;
      	}
      }

　
　
Web日本語文章抽出 ClalisWebExtractJpSentenceList
------
■概要
対象のURLのサイトから日本語を文章ごとに抽出して返します。  
サンプルでは、「http://www.yahoo.co.jp/」のサイトから日本語文章を抽出し、  
結果をコンソール出力する処理となっています。  

サンプル
mainメソッド

      	public static void main(String[] args) {
      		try{
      			final List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>();
      			nameValuePair.add(new BasicNameValuePair("url", 
      					"http://www.yahoo.co.jp/"));
      
      			String jsonText = inputStreemToString(
      					post("http://liplis.mine.nu/Clalis/v30/Post/Json/clalisWebExtractJpSentenceList.aspx", 
      					nameValuePair).getEntity().getContent());
      
      			ResWebSummaryList result =  new Gson().fromJson(jsonText, 
      					ResWebSummaryList.class);
      
      			for(String msg : result.resWordList)
      			{
      				System.out.println(msg);
      			}
      		} catch (IOException e) {
      			return ;
      		}
      	}

ResWebSummaryList API取得結果格クラス

        public class ResWebSummaryList {
        	public List<String> resWordList;
        
        	public List<String> getResWordList() {
        		return resWordList;
        	}
        
        	public void setResWordList(List<String> resWordList) {
        		this.resWordList = resWordList;
        	}
        }


　
Web日本語重要文章抽出 ClalisWebExtractJpSentenceRelevanceList
-----
■概要
対象のURLのサイトから日本語を文章ごとに抽出し、  
重要な文章の順番にソートして返します。  
サンプルでは、「http://www.yahoo.co.jp/」のサイトから日本語文章を抽出し、  
結果をコンソール出力する処理となっています。  

■サンプル
mainメソッド

        	public static void main(String[] args) {
        		try{
        			final List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>();
        			nameValuePair.add(new BasicNameValuePair("url", 
        					"http://www.yahoo.co.jp/"));
        
        			String jsonText = inputStreemToString(
        					post("http://liplis.mine.nu/Clalis/v30/Post/Json/clalisWebExtractJpSentenceRelevanceList.aspx", 
        					nameValuePair).getEntity().getContent());
        
        			ResWebSummaryList result =  new Gson().fromJson(jsonText, 
        				ResWebSummaryList.class);
        
        			for(String msg : result.resWordList)
        			{
        				System.out.println(msg);
        			}
        		} catch (IOException e) {
        			return ;
        		}
        	}


ResWebSummaryList API取得結果格クラス

      public class ResWebSummaryList {
      	public List<String> resWordList;
      
      	public List<String> getResWordList() {
      		return resWordList;
      	}
      
      	public void setResWordList(List<String> resWordList) {
      		this.resWordList = resWordList;
      	}
      }

