//=======================================================================
//  ClassName : ResMecabResult
//  概要      : 分解された単語データをリストで保持するクラスです。
//
//  Clalis
//  Copyright(c) 2011-2013 LipliStyle. All Rights Reserved.
//=======================================================================
package clalis.sample;

public class ResMecabResult {
	public MsgMecabResult[] resWordList;

	public MsgMecabResult[] getResWordList() {
		return resWordList;
	}

	public void setResWordList(MsgMecabResult[] resWordList) {
		this.resWordList = resWordList;
	}

}
