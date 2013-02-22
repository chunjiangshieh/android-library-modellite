package com.xcj.android.modellite;

import android.content.Context;

/**
 * 单例模式下数据库的配置信息
 * @author chunjiang.shieh
 *
 */
public class ModelSQLiteDatabaseConfig {
	
	/**
	 * 数据库名称
	 */
	private static final String DATABASE_NAME = "test.db";
	/**
	 * 数据库版本
	 */
	private static final int DATABASE_VERSION = 1;
	
	private static ModelSQLiteDatabaseConfig mInstance;
	
	private static Context mContext;
	
	private ModelSQLiteDatabaseConfig(){
		
	}
	
	public static ModelSQLiteDatabaseConfig getInstance(Context context){
		if(mInstance == null){
			mInstance = new ModelSQLiteDatabaseConfig();
			mContext = context;
		}
		return mInstance;
	} 
	
	/**
	 * 获取数据库名字
	 * @return
	 */
	public String getDatabaseName(){
		return DATABASE_NAME;
	}

	/**
	 * 获取数据库版本
	 * @return
	 */
	public int getDatabaseVersion(){
		return DATABASE_VERSION;
	}
	
	
	/**
	 * 获取数据库的路径（Copy db文件 时会用到）
	 * @return
	 */
	public String getDatabasePath(){
		StringBuffer databasePath = new StringBuffer();
		databasePath.append("/data/data/");
		databasePath.append(mContext.getPackageName());
		databasePath.append("/databases/");
		return databasePath.toString();
	}
	
	/**
	 * 获取数据库的完整路径
	 * @return
	 */
	public String getDatabaseFullPath(){
		return getDatabasePath() + getDatabaseName();
	}
	
}
