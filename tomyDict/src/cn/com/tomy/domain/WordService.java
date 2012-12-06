package cn.com.tomy.domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import cn.com.tomy.dida.DidaActivity;

/**
 * 收藏单词
 * 
 */
public class WordService {
	private DBOpenHandler dbOpenHandler;

	public WordService(Context context) {
		this.dbOpenHandler = new DBOpenHandler(context);
	}

	public void insertWords(Dict dict) {
		SQLiteDatabase db = dbOpenHandler.getWritableDatabase();
		// ContentValues content=new ContentValues();
		// content.put(DBOpenHandler.NAME,dict.getKey());
		// content.put(DBOpenHandler.AUDIO,dict.getPron());
		// content.put(DBOpenHandler.PS, dict.getPs());
		// content.put(DBOpenHandler.DEF, dict.getAcceptation());
		// content.put(DBOpenHandler.XML, DidaActivity.sb.toString());
		//
		// return db.insert(DBOpenHandler.TB_WORD, null, content);
		db.execSQL(
				"insert into tb_word (name,audio,ps,def,xml) values (?,?,?,?,?)",
				new Object[] { dict.getKey(), dict.getPron(), dict.getPs(),
						dict.getAcceptation(), DidaActivity.sb.toString() });
		db.close();
	}

	/**
	 * 获得存储的单词
	 * @return
	 */
	public List<Map> listWords() {
		SQLiteDatabase db = dbOpenHandler.getReadableDatabase();
		List<Map> list = new ArrayList<Map>();
		Cursor cursor = db.query(DBOpenHandler.TB_WORD, new String[] {
				DBOpenHandler.NAME, DBOpenHandler.DEF }, null, null, null,
				null, null);
		while (cursor.moveToNext()) {
			Map map = new HashMap<String,String>();
			map.put("word", cursor.getColumnName(0)+"  "+cursor.getColumnName(1));
			list.add(map);
		}
		return list;
	}

	public Cursor queryWord() {
		SQLiteDatabase db = dbOpenHandler.getReadableDatabase();
		return db.query(DBOpenHandler.TB_WORD,
				new String[] { DBOpenHandler.NAME }, null, null, null, null,
				null);
	}
}
