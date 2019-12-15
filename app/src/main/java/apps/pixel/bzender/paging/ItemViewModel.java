package apps.pixel.bzender.paging;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.paging.LivePagedListBuilder;
import android.arch.paging.PageKeyedDataSource;
import android.arch.paging.PagedList;
import android.content.Context;
import android.content.SharedPreferences;

import apps.pixel.bzender.Models.singleChat.ChatList;
import apps.pixel.bzender.Utills.MyApp;


public class ItemViewModel extends ViewModel {

    //creating livedata for PagedList  and PagedKeyedDataSource
    public LiveData<PagedList<ChatList>> itemPagedList;
    LiveData<PageKeyedDataSource<Integer, ChatList>> liveDataSource;
    private SharedPreferences sharedPreferences;
    private ItemDataSourceFactory itemDataSourceFactory;

    //constructor
    public ItemViewModel() {
        sharedPreferences = MyApp.getAppContext().getSharedPreferences("MySharedPreference", Context.MODE_PRIVATE);


        //getting our data source factory
        itemDataSourceFactory = new ItemDataSourceFactory();

        //getting the live data source from data source factory
        liveDataSource = itemDataSourceFactory.getItemLiveDataSource();

        //Getting PagedList config
        PagedList.Config pagedListConfig =
                (new PagedList.Config.Builder())
                        .setEnablePlaceholders(false)
                        .setPageSize(Integer.MAX_VALUE).build();

        //Building the paged list
        itemPagedList = (new LivePagedListBuilder(itemDataSourceFactory, pagedListConfig))
                .build();
    }

    public void invalidateDataSource() {
        itemDataSourceFactory.invalidateDataSource();
    }
}
