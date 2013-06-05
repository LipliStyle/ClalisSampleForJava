//=======================================================================
//  ClassName : MsgMecabResultFull
//  概要      : 分解された単語と品詞を保持するデータクラスです。
//
//  Clalis
//  Copyright(c) 2011-2013 LipliStyle. All Rights Reserved.
//=======================================================================
package clalis.sample;

public class MsgMecabResultFull {
	public int idx;					//インデックス
	public String name;				//分解された文字です。
	public String pos;				//分解された文字の品詞です。
	public String pos1;				//分解された文字の品詞細分類1です。
	public String pos2;				//分解された文字の品詞細分類2です。
	public String pos3;				//分解された文字の品詞細分類3です。
	public String infetted1;		//分解された文字の活用形です。
	public String infetted2;		//分解された文字の活用形です。
	public String prototype;		//分解された文字の原形です。
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
