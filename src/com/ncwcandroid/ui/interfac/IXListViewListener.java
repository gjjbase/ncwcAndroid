package com.ncwcandroid.ui.interfac;
/**
 * 上拉加载和下拉刷新
 * @author Administrator
 *
 */
public interface IXListViewListener {
	/**下拉刷新*/
	public void onRefresh();
	/**下拉加载*/
	public void onLoadMore();
	/**处理分页事务完成后的操作*/
	public void onLoad();
}
