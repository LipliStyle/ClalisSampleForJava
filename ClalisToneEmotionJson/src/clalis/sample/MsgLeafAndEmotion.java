//=======================================================================
//  ClassName : MsgLeafAndEmotion
//  概要      : 分解された単語とメタ感情値を保持するデータクラスです。
//
//  Clalis
//  Copyright(c) 2011-2013 LipliStyle. All Rights Reserved.
//=======================================================================
package clalis.sample;

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
