//=======================================================================
//  ClassName : MsgMecabResult
//  概要      : 分解された単語と品詞を保持するデータクラスです。
//
//  Clalis
//  Copyright(c) 2011-2013 LipliStyle. All Rights Reserved.
//=======================================================================
package clalis.sample;

public class MsgMecabResult {
	public int idx;				//インデックス
	public String name;			//分解された文字です。
	public String pos;			//分解された文字の品詞です。
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
