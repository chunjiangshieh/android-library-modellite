package com.xcj.android.modellite;

import java.util.ArrayList;
import java.util.List;


import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * 数据库操作的基类
 * 一个数据库表对应它的一个实现类
 * 基类提供简单的查询操作
 * @author chunjiang.shieh
 *
 */
public abstract class ModelSQLiteOperateBase {
	
	/**SQLiteDatabase的操作类**/
	private SQLiteDatabase mSQLiteDatabase;

	private Context mContext;
	
	/**
	 * 继承该类的实现类都需要重写该构造方法，获得上下文
	 * @param context
	 */
	public ModelSQLiteOperateBase(Context context){
		this.mContext = context;
	}
	
	/**
	 * 获取SQLiteDatabase的操作类
	 * @return
	 */
	public SQLiteDatabase getDatabase(){
		if(mSQLiteDatabase == null){
			mSQLiteDatabase = ModelSQLiteOpenHelper.getInstance(mContext).getWritableDatabase();
		}
		return mSQLiteDatabase;
	}
	
	/**
	 * 根据SQL语句返回对象的结果集
	 * @param sqlText
	 * @return
	 */
	protected List<Object> getList(String sqlText){
		Cursor cursor = execQuerySQL(sqlText);
		return cursorToList(cursor);
	}
	
	/**
	 * 游标转列表
	 * @param cursor
	 * @return
	 */
	private List<Object> cursorToList(Cursor cursor) {
		List<Object> list	= new ArrayList<Object>();
		while(cursor.moveToNext()){
			Object obj = findModel(cursor);
			list.add(obj);
		}
		cursor.close();
		return list;
	}
	
//	/**
//	 * 根据SQL语句插入
//	 * @param sqlText
//	 */
//	protected void add(String sqlText){
//		execUpdateSQL(sqlText);
//	}
	
//	/**
//	 * 根据SQL语句更新
//	 * @param sqlText
//	 */
//	protected void update(String sqlText){
//		execUpdateSQL(sqlText);
//	}
	
	/**
	 * 根据游标（Cursor）返回单个对象
	 * 抽象方法 实现类重写该方法
	 * @param cursor
	 * @return
	 */
	protected abstract Object findModel(Cursor cursor);
	
	/**
	 * 执行查询语句
	 * @param sqlText
	 * @return
	 */
	protected Cursor execQuerySQL(String sqlText){
		return getDatabase().rawQuery(sqlText, null);
	}
	
//	/**
//	 * 执行更新语句(包括Insert 和 Update)
//	 * @param sqlText
//	 */
//	protected void execUpdateSQL(String sqlText){
//		getDatabase().execSQL(sqlText);
//	}
	
	
	
	
	/**
	 * 开启事务
	 */
	public void beginTransaction(){
		getDatabase().beginTransaction();
	}
	/**
	 * 设置事务成功
	 */
	public void setTransactionSuccess(){
		getDatabase().setTransactionSuccessful();
	}
	/**
	 * 关闭事务
	 */
	public void endTransaction(){
		getDatabase().endTransaction();
	}
}
