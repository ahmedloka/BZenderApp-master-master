package apps.pixel.bzender.paging;

import android.arch.lifecycle.MutableLiveData;
import android.arch.paging.DataSource;
import android.arch.paging.PageKeyedDataSource;

import apps.pixel.bzender.Models.singleChat.ChatList;

public class ItemDataSourceFactory extends DataSource.Factory {
    //creating the mutable live data
    private MutableLiveData<PageKeyedDataSource<Integer, ChatList>> itemLiveDataSource = new MutableLiveData<>();

    ItemDataSource itemDataSource ;

    @Override
    public DataSource<Integer, ChatList> create() {
        //getting our data source object
        itemDataSource = new ItemDataSource();
       // itemDataSource.invalidate();

        //posting the datasource to get the values
        itemLiveDataSource.postValue(itemDataSource);

        //returning the datasource
        return itemDataSource;
    }


    //getter for itemlivedatasourcex
    public MutableLiveData<PageKeyedDataSource<Integer, ChatList>> getItemLiveDataSource() {
        return itemLiveDataSource;
    }


    public void invalidateDataSource() {
        itemDataSource.invalidate();
    }
}