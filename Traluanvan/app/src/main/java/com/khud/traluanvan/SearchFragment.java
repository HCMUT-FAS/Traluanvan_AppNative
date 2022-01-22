package com.khud.traluanvan;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;


import com.khud.traluanvan.databinding.FragmentHomeBinding;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;


public class SearchFragment extends Fragment  implements SwipeRefreshLayout.OnRefreshListener, LuanvanAdapter.OnLoadMoreListener {
    //Set databasse
    Traluanvandb data;
    //Set Layout
    Context mcontext;
    LuanvanAdapter luanvanAdapter;
    RecyclerView rcvluanvantracuu;
    FragmentHomeBinding Search_Binding;
    SwipeRefreshLayout swipeRefreshLayout;
    SearchView searchView;
    //Set number item in 1 page
    public final int NUM_ITEMS_PAGE = 10;
    //Set page
    int Page;
    //List data to set from database
    List<LuanvanModel> Mainscreen;
    //List data to save User's state
    List<LuanvanModel> Data_exist=new ArrayList<LuanvanModel>();
    //Set key SaveState
    private static final String Search_Key = "Search";
    //Save last Query Input
    String lastSearchQuery = "";




    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            lastSearchQuery = savedInstanceState.getString(Search_Key);
        }
    }


    @Override
    public void onViewStateRestored(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        if (savedInstanceState != null) {
            lastSearchQuery = savedInstanceState.getString(Search_Key);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //
        Search_Binding = FragmentHomeBinding.inflate(inflater, container, false);
        View rootview = Search_Binding.getRoot();
        mcontext = getActivity();
        rcvluanvantracuu = Search_Binding.rcvLuanvantracuu;
        searchView = Search_Binding.searchView;
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mcontext);
        rcvluanvantracuu.setLayoutManager(linearLayoutManager);
        swipeRefreshLayout = Search_Binding.swipeRefresh;
        data = new Traluanvandb(mcontext);
        luanvanAdapter= new LuanvanAdapter(mcontext,this::onLoadMore);
        rcvluanvantracuu.setAdapter(luanvanAdapter);
        Page = 0;
        ConnectServer connectServer=new ConnectServer();
        try {
            connectServer.Get_LuanvanFromServer(mcontext,data);
        }
        catch (Exception e){
            connectServer.Copy_Database_local(mcontext,data);
        }
        return rootview;
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //Start
        data.openDataBase();
        //Check last Query Inpput
        if (lastSearchQuery != null && !lastSearchQuery.matches("")) {
            Mainscreen = data.getData(lastSearchQuery);
            if(Data_exist!=null&&Data_exist.size()>=NUM_ITEMS_PAGE) {
                luanvanAdapter.setData(Data_exist);
                Page=((Data_exist.size()/NUM_ITEMS_PAGE)-1);
            }
            else {
                luanvanAdapter.setData(LoadList(Page, Mainscreen));
            }
            rcvluanvantracuu.setAdapter(luanvanAdapter);
        } else {
            Mainscreen=data.getAllData();
            if(Data_exist!=null&&Data_exist.size()>=NUM_ITEMS_PAGE) {
                luanvanAdapter.setData(Data_exist);
                Page=((Data_exist.size()/NUM_ITEMS_PAGE)-1);
            }
            else {
                luanvanAdapter.setData(LoadList(Page,Mainscreen));
            }
//            luanvanAdapter.setData(Mainscreen);
                rcvluanvantracuu.setAdapter(luanvanAdapter);

        }
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                try {
                    if (CheckSearchRequest(query) == true) {
                        Page=0;
                        Mainscreen = data.getData(query);
                        if(Mainscreen.size()!=0) {
                            luanvanAdapter.setData(LoadList(Page, Mainscreen));
                            rcvluanvantracuu.setAdapter(luanvanAdapter);
                            //Check Searchview lastquery
//                        Toast.makeText(mcontext,lastSearchQuery,Toast.LENGTH_SHORT).show();
                            lastSearchQuery = query;
                        }
                        else {
                            Toast.makeText(mcontext,"Không tìm được kết quả khớp,hãy thử tìm có dấu",Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(mcontext, "Vui lòng nhập lại", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    Toast.makeText(mcontext, e.toString(), Toast.LENGTH_SHORT).show();
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }

        });
        swipeRefreshLayout.setOnRefreshListener(this);
        rcvluanvantracuu.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrolled(@NonNull @NotNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                LinearLayoutManager llManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                if (dy > 0 && llManager.findLastCompletelyVisibleItemPosition() == (luanvanAdapter.getItemCount()-1)) {
                    luanvanAdapter.showLoading();
//                luanvanAdapter.addItemMore(LoadList(0,Mainscreen),1);
//                Toast.makeText(mcontext,"CO"+Page,Toast.LENGTH_SHORT).show();

                }
            }
        });
    }
    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putString(Search_Key, lastSearchQuery);
        super.onSaveInstanceState(outState);
    }

    private List<LuanvanModel> LoadList(int number, List<LuanvanModel> data) {
        List<LuanvanModel> sort = new ArrayList<>();
        int start = number * NUM_ITEMS_PAGE;
        if(start<=Mainscreen.size()) {
            for (int i = start; i < (start) + NUM_ITEMS_PAGE; i++) {
                if (i < data.size()) {
                    sort.add(data.get(i));
                } else {
                    break;
                }
            }
        }
//        else {
//            Toast.makeText(mcontext,"Đã hết luận văn",Toast.LENGTH_SHORT).show();
//        }
        return sort;
    }

    private boolean CheckSearchRequest(String Req) {
        String ReqArray1[] = Req.split(" ");
        String ReqArray2[] = Req.split("%");
        String ReqArray3[] = Req.split("%20");
        if ((ReqArray1.length == 0) || (ReqArray2.length == 0) || (ReqArray3.length == 0) || (Req.matches(""))) {

            return false;
        } else {
            return true;
        }
    }

    /**
     * Called when a swipe gesture triggers a refresh.
     */
    @Override
    public void onRefresh() {
        Mainscreen=data.getAllData();
        Page=0;
        luanvanAdapter.setData(LoadList(Page,Mainscreen));
        rcvluanvantracuu.setAdapter(luanvanAdapter);
        searchView.setQuery("", false);
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onLoadMore() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // Stop animation (This will be after 3 seconds)
                Page++;
                luanvanAdapter.dismissLoading();
                luanvanAdapter.additem(Page, LoadList(Page, Mainscreen));
                Data_exist=luanvanAdapter.getList();

            }
        }, 2000); // Delay in millis

    }
}

