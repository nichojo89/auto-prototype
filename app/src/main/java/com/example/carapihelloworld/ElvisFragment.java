package com.example.carapihelloworld;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;

public class ElvisFragment extends Fragment {

    private WebView mWebView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_elvis, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState){
        mWebView = getView().findViewById(R.id.webView);

        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        mWebView.loadUrl("https://web.powerva.microsoft.com/environments/571a5215-bf8d-e3b5-9aab-3ba3f7a09c14/bots/new_bot_d285b931a9a64f739dc6f1f13d1d4beb/webchat");
    }
}