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

        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.layout_swipe_main);
        mRecyclerViewWalletList = (RecyclerView) findViewById(R.id.recycler_wallet_list_main);
        mEtFromTx = (EditText) findViewById(R.id.et_from_tx_main);
        mEtToTx = (EditText) findViewById(R.id.et_to_tx_main);
        mEtEtherTx = (EditText) findViewById(R.id.et_ether_tx_main);
        mEtPasswordTx = (EditText) findViewById(R.id.et_password_tx_main);
        mBtnSendTx = (Button) findViewById(R.id.btn_send_tx_main);

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new AsyncTask(){

                    @Override
                    protected Object doInBackground(Object[] objects) {

                        List<UserWallet> userWallets = new ArrayList<>();

                        try {
                            Web3j web3 = Web3jFactory.build(new HttpService(URL));
                            EthAccounts ethAccounts = web3.ethAccounts().sendAsync().get();
                            List<String> accounts = ethAccounts.getAccounts();

                            for(String account : accounts){
                                EthGetBalance ethGetBalance = web3.ethGetBalance(account, DefaultBlockParameterName.LATEST).sendAsync().get();
                                BigDecimal ether = Convert.fromWei(ethGetBalance.getBalance().toString(), Convert.Unit.ETHER);
                                userWallets.add(new UserWallet(account, ether));
                            }

                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } finally {
                            final List<UserWallet> userWalletList = userWallets;

                            mAdapterWalletList = new WalletsRecyclerAdapter(userWalletList);
                            mLayoutManagerWalletList = new LinearLayoutManager(getApplicationContext());

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {

                                    mRecyclerViewWalletList.setLayoutManager(mLayoutManagerWalletList);
                                    mRecyclerViewWalletList.setAdapter(mAdapterWalletList);
                                    mAdapterWalletList.notifyDataSetChanged();

                                    mSwipeRefreshLayout.setRefreshing(false);
                                }
                            });
                        }

                        return null;
                    }
                }.execute();
            }
        });

                return null;
            }
        }.execute();
    }
}
