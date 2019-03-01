package com.pby.examplegethwalletapp.main;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.pby.examplegethwalletapp.R;
import com.pby.examplegethwalletapp.main.adapter.WalletsRecyclerAdapter;

import org.web3j.protocol.Web3j;
import org.web3j.protocol.Web3jFactory;
import org.web3j.protocol.core.methods.response.EthAccounts;
import org.web3j.protocol.http.HttpService;

import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {

    final private String URL = "";

    private Button mButtonNewAccount;
    private RecyclerView mRecyclerViewWalletList;
    private RecyclerView.Adapter mAdapterWalletList;
    private RecyclerView.LayoutManager mLayoutManagerWalletList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mButtonNewAccount = (Button) findViewById(R.id.btn_new_account_main);
        mRecyclerViewWalletList = (RecyclerView) findViewById(R.id.recycler_wallet_list_main);

        mButtonNewAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        new AsyncTask(){

            @Override
            protected Object doInBackground(Object[] objects) {

                String[] accounts = new String[0];

                try {
                    Web3j web3 = Web3jFactory.build(new HttpService(URL));
                    EthAccounts ethAccounts = web3.ethAccounts().sendAsync().get();
                    accounts = ethAccounts.getAccounts().toArray(new String[0]);

                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                final String[] accountArray = accounts;

                mAdapterWalletList = new WalletsRecyclerAdapter(accountArray);
                mLayoutManagerWalletList = new LinearLayoutManager(getApplicationContext());
                mRecyclerViewWalletList.setLayoutManager(mLayoutManagerWalletList);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        mRecyclerViewWalletList.setAdapter(mAdapterWalletList);
                        mAdapterWalletList.notifyDataSetChanged();
                    }
                });

                return null;
            }
        }.execute();
    }
}
