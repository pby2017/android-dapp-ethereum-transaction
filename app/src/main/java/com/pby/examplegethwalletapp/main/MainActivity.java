package com.pby.examplegethwalletapp.main;

import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.pby.examplegethwalletapp.R;
import com.pby.examplegethwalletapp.main.adapter.WalletsRecyclerAdapter;
import com.pby.examplegethwalletapp.main.model.UserWallet;

import org.web3j.exceptions.MessageDecodingException;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.Web3jFactory;
import org.web3j.protocol.admin.Admin;
import org.web3j.protocol.admin.AdminFactory;
import org.web3j.protocol.admin.methods.response.PersonalUnlockAccount;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.request.Transaction;
import org.web3j.protocol.core.methods.response.EthAccounts;
import org.web3j.protocol.core.methods.response.EthGetBalance;
import org.web3j.protocol.core.methods.response.EthSendTransaction;
import org.web3j.protocol.http.HttpService;
import org.web3j.utils.Convert;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {

    private String URL;

    private SwipeRefreshLayout mSwipeRefreshLayout;

    private RecyclerView mRecyclerViewWalletList;
    private RecyclerView.Adapter mAdapterWalletList;
    private RecyclerView.LayoutManager mLayoutManagerWalletList;

    private EditText mEtFromTx;
    private EditText mEtToTx;
    private EditText mEtEtherTx;
    private EditText mEtPasswordTx;
    private Button mBtnSendTx;

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
