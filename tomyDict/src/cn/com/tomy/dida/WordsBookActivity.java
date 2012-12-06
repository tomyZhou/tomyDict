package cn.com.tomy.dida;

import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import cn.com.tomy.dida.R;
import cn.com.tomy.domain.WordService;

public class WordsBookActivity extends Activity {

	private ListView lv_word;
	private WordService service;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.wordsbook);
		lv_word = (ListView) findViewById(R.id.listview_words);
		service = new WordService(this);
		
		List worldList = service.listWords();
		if(worldList !=null && worldList.size() == 0){
			lv_word.setAdapter(new SimpleAdapter(this,worldList,R.id.ll_save_word,new String[]{"word"},new int[]{R.id.tv_save_word}));
		}
	}
	
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub

		if (keyCode == KeyEvent.KEYCODE_BACK) {

			AlertDialog.Builder builder = new AlertDialog.Builder(
					WordsBookActivity.this);
			builder.setIcon(R.drawable.bee);
			builder.setTitle("你确定退出吗？");
			builder.setPositiveButton("确定",
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog,
								int whichButton) {
							WordsBookActivity.this.finish();
							android.os.Process.killProcess(android.os.Process
									.myPid());
							android.os.Process.killProcess(android.os.Process
									.myTid());
							android.os.Process.killProcess(android.os.Process
									.myUid());
						}
					});
			builder.setNegativeButton("返回",
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog,
								int whichButton) {
							dialog.cancel();
						}
					});
			builder.show();
			return true;
		}

		return super.onKeyDown(keyCode, event);
	}

}
