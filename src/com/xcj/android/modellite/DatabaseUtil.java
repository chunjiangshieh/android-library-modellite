package com.xcj.android.modellite;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * 数据库操作工具类
 * @author chunjiang.shieh
 *
 */
public class DatabaseUtil {
	
	/**
	 * 检测数据库是否存在
	 * @param context
	 * @return
	 */
	public static  boolean checkDatabaseExist(Context context){
		SQLiteDatabase sqLiteDatabase = null;
		try{
			String fullPath = ModelSQLiteDatabaseConfig.getInstance(context).getDatabaseFullPath();
			sqLiteDatabase = SQLiteDatabase.openDatabase(fullPath, null, SQLiteDatabase.OPEN_READONLY);
		}catch (Exception e) {
		}
		if(sqLiteDatabase != null){
			sqLiteDatabase.close();
		}
		return sqLiteDatabase != null ? true : false;
	}
	
	/**
	 * 删除数据库DB文件
	 * @param context
	 * @return
	 */
	public static boolean deleteDatabase(Context context){
		/**
		 * 删除整个数据库DB文件
		 */
		String fullPath = ModelSQLiteDatabaseConfig.getInstance(context).getDatabaseFullPath();
		File file = new File(fullPath);
		if(file != null && file.exists()){
			File[] files = file.listFiles();
			for(int i=0;files!=null &&i<files.length;i++){
				File subFile = files[i];
				if(subFile != null && subFile.exists()){
					subFile.delete();
				}
			}
			boolean result = file.delete();
			return result;
		}
		return false;
	}

	/**
	 * 从输入流中copy database 到应用的数据库目录下
	 * @param context
	 * @param is  
	 * 	Inputstream 可以从一下几个地方读取
	 * 1.asset目录下  context.getAssets().open(dbName);
	 * 2.网络请求下来的输入流
	 * 
	 */
	public static boolean copyDatabase(Context context, InputStream is){
		boolean result = false;
		String outFileName = ModelSQLiteDatabaseConfig.getInstance(context).getDatabaseFullPath();
		try {
			OutputStream os = new FileOutputStream(outFileName);
			byte[] buff = new byte[1024];
			int len;
			while( (len = is.read(buff)) > 0){
				os.write(buff,0,len);
			}
			os.flush();
			os.close();
			is.close();
			result = true;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}
}
