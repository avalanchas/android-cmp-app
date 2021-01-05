package com.sourcepoint.example_app;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.app.AppCompatActivity;
import com.sourcepoint.example_app.core.DataProvider;
import com.sourcepoint.gdpr_cmplibrary.GDPRConsentLib;
import com.sourcepoint.gdpr_cmplibrary.GDPRConsentLibImpl;
import com.sourcepoint.gdpr_cmplibrary.v6.ccpa.CCPAConsentLibClient;
import com.sourcepoint.gdpr_cmplibrary.v6.client.CCPAClient;
import com.sourcepoint.gdpr_cmplibrary.v6.client.GDPRClient;
import com.sourcepoint.gdpr_cmplibrary.v6.gdpr.GDPRConsentLibClient;
import com.sourcepoint.gdpr_cmplibrary.v6.BuilderV6;
import kotlin.Lazy;

import static org.koin.java.KoinJavaComponent.inject;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "**MainActivity";

    final static int accountId = 22;
    final static int propertyId = 7639;
    final static String propertyName = "tcfv2.mobile.webview";
    final static String pmId = "122058";

    private ViewGroup mainViewGroup;

    private final Lazy<DataProvider> dataProvider = inject(DataProvider.class);

    private void showView(View view) {
        if (view.getParent() == null) {
            view.setLayoutParams(new ViewGroup.LayoutParams(0, 0));
            view.getLayoutParams().height = ViewGroup.LayoutParams.MATCH_PARENT;
            view.getLayoutParams().width = ViewGroup.LayoutParams.MATCH_PARENT;
            view.bringToFront();
            view.requestLayout();
            mainViewGroup.addView(view);
        }
    }

    private void removeView(View view) {
        if (view.getParent() != null) mainViewGroup.removeView(view);
    }

    private GDPRConsentLib buildGDPRConsentLib() {
        return GDPRConsentLibImpl.newBuilder(accountId, propertyName, propertyId, pmId, this)
                .setOnConsentUIReady(this::showView)
                .setOnAction(actionType -> Log.i(TAG, "ActionType: " + actionType.toString()))
                .setOnConsentUIFinished(this::removeView)
                .setOnConsentReady(consent -> {
                    // at this point it's safe to initialise vendors
                    for (String line : consent.toString().split("\n"))
                        Log.i(TAG, line);
                })
                .setOnError(error -> Log.e(TAG, "Something went wrong"))
                .setAuthId(dataProvider.getValue().getAuthId())
                .build();
    }

    @Override
    protected void onResume() {
        super.onResume();
        buildGDPRConsentLib().loadMessage();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mainViewGroup = findViewById(android.R.id.content);
        findViewById(R.id.review_consents).setOnClickListener(_v -> buildGDPRConsentLib().loadPrivacyManager());
        findViewById(R.id.auth_id_activity).setOnClickListener(_v -> startActivity(new Intent(this, MainActivityAuthId.class)));

        /**
         * First option
         */
        GDPRConsentLibClient first = new BuilderV6()
                .setAccountId(1)
                .setContex(this)
                .setProperty("")
                .setPropertyId(1)
                .setPmId("")
                .build(GDPRConsentLibClient.class);

        CCPAConsentLibClient second = new BuilderV6()
                .setAccountId(1)
                .setContex(this)
                .setProperty("")
                .setPropertyId(1)
                .setPmId("")
                .build(CCPAConsentLibClient.class);

        first.setClient(new GDPRClient() {});
        second.setClient(new CCPAClient() {});

        System.out.println("======" + first);
        System.out.println("======" + second);
    }
}