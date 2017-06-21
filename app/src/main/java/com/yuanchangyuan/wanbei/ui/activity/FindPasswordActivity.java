package com.yuanchangyuan.wanbei.ui.activity;

import android.annotation.TargetApi;
import android.graphics.Color;
import android.os.Build;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.readystatesoftware.systembartint.SystemBarTintManager;
import com.yuanchangyuan.wanbei.R;
import com.yuanchangyuan.wanbei.base.BaseActivity;
import com.yuanchangyuan.wanbei.base.Constants;
import com.yuanchangyuan.wanbei.base.EventBusCenter;
import com.yuanchangyuan.wanbei.utils.NetUtil;
import com.yuanchangyuan.wanbei.utils.TelephoneUtils;
import com.yuanchangyuan.wanbei.utils.UIUtil;
import com.yuanchangyuan.wanbei.view.CleanableEditText;
import com.yuanchangyuan.wanbei.view.TitleBar;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnTextChanged;

/**
 * 找回密码
 */
public class FindPasswordActivity extends BaseActivity {

    @BindView(R.id.title_view)
    TitleBar titleView;
    CountDownTimer timer;
    @BindView(R.id.user_name)
    CleanableEditText etPhone;
    @BindView(R.id.et_check_code)
    EditText etCode;
    @BindView(R.id.tv_check_code)
    TextView tvGetCode;

    @BindView(R.id.tv_next)
    Button tvNext;

    boolean isCodeSended = false;

    @Override
    public int getContentViewLayoutId() {
        return R.layout.mine_findpassword_activity_layout;
    }

    @Override
    public void initViewsAndEvents() {
        initTitle();
        etCode.setFilters(new InputFilter[]{new InputFilter.LengthFilter(4)});


        timer = new CountDownTimer(60 * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                tvGetCode.setText("重新发送" + "(" + millisUntilFinished / 1000 + "s)");
                tvGetCode.setEnabled(false);
            }

            @Override
            public void onFinish() {
                tvGetCode.setText("重新发送");
                tvGetCode.setEnabled(true);
            }
        };
    }

    private void initTitle() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setTranslucentStatus(true);
        }
        SystemBarTintManager tintManager = new SystemBarTintManager(this);
        tintManager.setStatusBarTintEnabled(true);
        tintManager.setStatusBarTintResource(R.color.color_ff6900);


        titleView.setTitle("忘记密码");
        titleView.setTitleColor(Color.WHITE);
        titleView.setBackgroundColor(getResources().getColor(R.color.color_ff6900));
        titleView.setImmersive(true);
    }
    @TargetApi(19)
    private void setTranslucentStatus(boolean on) {
        Window win = getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }
    @Override
    public void loadData() {

    }

    @Override
    public boolean isRegistEventBus() {
        return true;
    }

    @Override
    public void onMsgEvent(EventBusCenter eventBusCenter) {
        if (null != eventBusCenter) {
            if (Constants.Tag.CHANGE_PASSWORD_SUCCESS == eventBusCenter.getEvenCode()) {
                finish();
            }
        }
    }

    @Override
    protected View isNeedLec() {
        return null;
    }

    @OnClick(R.id.tv_check_code)
    public void onGetCodeClick(View view) {

        String phoneNumber = etPhone.getText().toString();
        if (TextUtils.isEmpty(phoneNumber)) {
            UIUtil.showToast("请输入手机号");
            return;
        }
        if (!TelephoneUtils.isMobile(phoneNumber)) {
            UIUtil.showToast("手机号格式错误");
            return;
        }

        if (!NetUtil.isNetworkConnected(this)) {

            UIUtil.showToast("网络连接失败，请检查您的网络");
            return;
        }
        checkRegist(phoneNumber);


    }

    private void checkRegist(final String phoneNumber) {

//        RestAdapterManager.getApi().CheckRegiste(phoneNumber, "1").enqueue(new JyCallBack<String>() {
//            @Override
//            public void onSuccess(Call<String> call, Response<String> response) {
//                if (!TextUtils.isEmpty(response.body())) {
//                    if ("true".equals(response.body())) {
//
//                        getCode(phoneNumber);
//                    } else {
//                        UIUtil.showToast("手机号未注册，快去注册吧！");
//                    }
//                } else {
//                    UIUtil.showToast(response.body());
//                }
//            }
//
//            @Override
//            public void onError(Call<String> call, Throwable t) {
//
//            }
//
//            @Override
//            public void onError(Call<String> call, Response<String> response) {
//                try {
//                    ErrorMessageUtils.taostErrorMessage(BaseContext.getInstance(), response.errorBody().string());
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        });
    }

    private void getCode(String phone) {
        timer.start();
        //更换获取验证码的接口

//        RestAdapterManager.getApi().GetCodeMessage(RestAdapterManager.getHeaderMap(),phone).enqueue(new JyCallBack<String>() {
//            @Override
//            public void onSuccess(Call<String> call, Response<String> response) {
//                LogUtils.e(response.body());
//                isCodeSended = true;
//                UIUtil.showToast("验证码已发送");
//            }
//
//            @Override
//            public void onError(Call<String> call, Throwable t) {
//
//            }
//
//            @Override
//            public void onError(Call<String> call, Response<String> response) {
//                try {
//                    ErrorMessageUtils.taostErrorMessage(BaseContext.getInstance(), response.errorBody().string());
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        });
    }


    @OnClick(R.id.tv_next)
    public void onNextClick(View view) {
        if (!NetUtil.isNetworkConnected(this)) {
            UIUtil.showToast("网络连接失败，请检查您的网络");
            return;
        }
        final String tel = etPhone.getText().toString();
        final String code = etCode.getText().toString();


//        RestAdapterManager.getApi().CheckMessageCode(RestAdapterManager.getHeaderMap(),tel, code).enqueue(new JyCallBack<ValidCodeBean>() {
//            @Override
//            public void onSuccess(Call<ValidCodeBean> call, Response<ValidCodeBean> response) {
//                try {
//                    if (response != null && response.body() != null && !TextUtils.isEmpty(response.body().uuid)) {
//
//                        LogUtils.e(response.body().uuid);
//                        Intent findPsIntent = new Intent(FindPasswordStepOneActivity.this, FindPasswordStepTwoActivity.class);
//                        timer.cancel();
//                        findPsIntent.putExtra("code", code);
//                        findPsIntent.putExtra("uuid", response.body().uuid);
//                        findPsIntent.putExtra("tel", tel);
//                        startActivity(findPsIntent);
//                    } else {
//                        UIUtil.showToast("验证验证码错误~");
//                    }
//
//                } catch (Exception e) {
//
//                }
//            }
//
//            @Override
//            public void onError(Call<ValidCodeBean> call, Throwable t) {
//
//            }
//
//            @Override
//            public void onError(Call<ValidCodeBean> call, Response<ValidCodeBean> response) {
//                UIUtil.showToast("验证码不正确");
//            }
//        });

    }


    @OnTextChanged(value = R.id.user_name, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    public void afterNameTextChanged(Editable s) {
        if (s.length() == 0) {
            tvNext.setEnabled(false);
        } else {
            if (isCodeSended && !TextUtils.isEmpty(etCode.getText().toString())) {
                tvNext.setEnabled(true);
            } else {
                tvNext.setEnabled(false);
            }

        }
    }

    @OnTextChanged(value = R.id.et_check_code, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    public void afterCodeTextChanged(Editable s) {
        if (s.length() == 0) {
            tvNext.setEnabled(false);
        } else {
            if (isCodeSended && !TextUtils.isEmpty(etPhone.getText().toString())) {
                tvNext.setEnabled(true);
            } else {
                tvNext.setEnabled(false);
            }

        }
    }

    @Override
    protected void onDestroy() {
        timer.cancel();
        super.onDestroy();
    }

}
