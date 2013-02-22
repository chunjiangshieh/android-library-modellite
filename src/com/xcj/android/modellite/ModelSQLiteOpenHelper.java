package com.xcj.android.modellite;

import java.io.IOException;
import java.io.InputStream;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
/**
 * SQLite的工具类
 * @author chunjiang.shieh
 *
 */
public class ModelSQLiteOpenHelper extends SQLiteOpenHelper {

	private static ModelSQLiteDatabaseConfig mDatabaseConfig;
	private static ModelSQLiteOpenHelper mInstance;
	
	private ModelSQLiteOpenHelper(Context context) {
		super(context, 
				mDatabaseConfig.getDatabaseName(), 
				null, 
				mDatabaseConfig.getDatabaseVersion());
//		createDatabase(context); //或者自己写建表语句，二者选一
	}
	
	
	public static ModelSQLiteOpenHelper getInstance(Context context){
		if(mInstance == null){
			mDatabaseConfig = ModelSQLiteDatabaseConfig.getInstance(context);
			mInstance = new ModelSQLiteOpenHelper(context);
		}
		return mInstance;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub

	}

	
	
	/**
	 * 创建整个DB文件
	 */
	public void createDatabase(Context context){
		boolean dbExist = DatabaseUtil.checkDatabaseExist(context);
		if(dbExist){
			// do nothing - database already exist
			//TODO  升级之用
			//获取用户原有的数据，如收藏 用户行为表的数据，然后删掉自己的数据库，
			//把asset数据库文件移动这里，在onUpdrage()方法里，把数据存放到新表里
		}else{
			// By calling this method and empty database will be created into
			// the default system path
			// of your application so we are gonna be able to overwrite that
			// database with our database.
			//已经创建了空的数据库，表已经建立
			this.getReadableDatabase();
			InputStream is;
			try {
				is = context.getAssets().open(mDatabaseConfig.getDatabaseName());
				DatabaseUtil.copyDatabase(context,is);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
