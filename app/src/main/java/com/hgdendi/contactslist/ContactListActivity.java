/**
 * Copyright 2017 ChenHao Dendi
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.hgdendi.contactslist;

import android.Manifest;
import android.app.ActionBar;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.hgdendi.contactslist.common.FloatingBarItemDecoration;
import com.hgdendi.contactslist.common.IndexBar;
import com.hgdendi.contactslist.common.Utils;

import java.util.ArrayList;
import java.util.LinkedHashMap;

public class ContactListActivity extends AppCompatActivity {
    private final int PERMISSION_REQUEST_CODE_READ_CONTACTS = 71;

    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;
    private LinkedHashMap<Integer, String> mHeaderList;
    private ArrayList<ShareContactsBean> mContactList;
    private ContactsListAdapter mAdapter;
    private PopupWindow mOperationInfoDialog;
    private View mLetterHintView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_list);
        fetchData();
        initView();
    }

    private void initView() {
        mRecyclerView = (RecyclerView) findViewById(R.id.share_add_contact_recyclerview);
        mRecyclerView.setLayoutManager(mLayoutManager = new LinearLayoutManager(this));
        mRecyclerView.addItemDecoration(
                new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        mRecyclerView.addItemDecoration(
                new FloatingBarItemDecoration(this, mHeaderList));
        initAdapter();
        mRecyclerView.setAdapter(mAdapter);
        IndexBar indexBar = (IndexBar) findViewById(R.id.share_add_contact_sidebar);
        indexBar.setNavigators(new ArrayList<>(mHeaderList.values()));
        indexBar.setOnTouchingLetterChangedListener(new IndexBar.OnTouchingLetterChangeListener() {
            @Override
            public void onTouchingLetterChanged(String s) {
                showLetterHintDialog(s);
                for (Integer position : mHeaderList.keySet()) {
                    if (mHeaderList.get(position).equals(s)) {
                        mLayoutManager.scrollToPositionWithOffset(position, 0);
                        return;
                    }
                }
            }

            @Override
            public void onTouchingStart(String s) {
                showLetterHintDialog(s);
            }

            @Override
            public void onTouchingEnd(String s) {
                hideLetterHintDialog();
            }
        });
    }

    /**
     * realated to {@Link IndexBar#OnTouchingLetterChangeListener}
     * show dialog in the center of this window
     * @param s
     */
    private void showLetterHintDialog(String s) {
        if (mOperationInfoDialog == null) {
            mLetterHintView = getLayoutInflater().inflate(R.layout.dialog_letter_hint, null);
            mOperationInfoDialog = new PopupWindow(mLetterHintView, ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT, false);
            mOperationInfoDialog.setOutsideTouchable(true);
        }
        ((TextView) mLetterHintView.findViewById(R.id.dialog_letter_hint_textview)).setText(s);
        getWindow().getDecorView().post(new Runnable() {
            @Override
            public void run() {
                mOperationInfoDialog.showAtLocation(getWindow().getDecorView().findViewById(android.R.id.content), Gravity.CENTER, 0, 0);
            }
        });
    }

    private void hideLetterHintDialog() {
        mOperationInfoDialog.dismiss();
    }

    private void initAdapter() {
        mAdapter = new ContactsListAdapter(LayoutInflater.from(this),
                mContactList);
        mAdapter.setOnContactsBeanClickListener(new ContactsListAdapter.OnContactsBeanClickListener() {
            @Override
            public void onContactsBeanClicked(ShareContactsBean bean) {
                Snackbar.make(mRecyclerView, "Clicked " + bean.getName(), Snackbar.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * fetch the data to display
     * need permission of READ_CONTACTS
     */
    private void fetchData() {
        mHeaderList = new LinkedHashMap<>();
        fetchContactList();
    }

    protected void fetchContactList() {
        if (Utils.checkHasPermission(this, Manifest.permission.READ_CONTACTS, PERMISSION_REQUEST_CODE_READ_CONTACTS)) {
            mContactList = ContactsManager.getPhoneContacts(this);
        } else {
            mContactList = new ArrayList<>(0);
        }
        preOperation();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == PERMISSION_REQUEST_CODE_READ_CONTACTS) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                fetchData();
                mAdapter.notifyDataSetChanged();
            } else {
                Snackbar.make(mRecyclerView, "Do not have enough permission", Snackbar.LENGTH_SHORT).show();
            }
        }
    }

    /**
     * calculate the TAG and add to {@Link #mHeaderlist}
     */
    private void preOperation() {
        mHeaderList.clear();
        if (mContactList.size() == 0) {
            return;
        }
        addHeaderToList(0, mContactList.get(0).getInitial());
        for (int i = 2; i < mContactList.size(); i++) {
            if (!mContactList.get(i - 1).getInitial().equalsIgnoreCase(mContactList.get(i).getInitial())) {
                addHeaderToList(i, mContactList.get(i).getInitial());
            }
        }
    }

    private void addHeaderToList(int index, String header) {
        mHeaderList.put(index, header);
    }

}
