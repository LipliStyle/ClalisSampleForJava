//=======================================================================
//  ClassName : ResMecabResultFull
//  概要      : 分解された単語データをリストで保持するクラスです。
//
//  Clalis
//  Copyright(c) 2011-2013 LipliStyle. All Rights Reserved.
//=======================================================================
package clalis.sample;

public class ResMecabResultFull {
	public MsgMecabResultFull[] resWordList;

	public MsgMecabResultFull[] getResWordList() {
		return resWordList;
	}

	public void setResWordList(MsgMecabResultFull[] resWordList) {
		this.resWordList = resWordList;
	}
}
