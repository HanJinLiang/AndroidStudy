package com.hanjinliang.androidstudy.data;

import com.blankj.utilcode.util.ToastUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public abstract class BaseCallBackWithMessage<T> implements Callback<Result<T>> {

    @Override
    public void onResponse(Call<Result<T>> call, Response<Result<T>> response) {
        if(response.code()==401){
            onError(response, null);
            return;
        }
        if (response.isSuccessful() && response.body() != null ) {
            onSuccess(response.body().getCode(),response.body().getMessage(),response.body().getResult());
            if(isNeedToast()&&!"200".equals(response.body().getCode())){
                ToastUtils.showShort(response.body().getMessage());
            }
        } else {
            onError(response, null);
        }
    }

    public boolean isNeedToast(){
        return true;
    }

    @Override
    public void onFailure(Call<Result<T>> call, Throwable t) {
        onError(null, t);
    }

    protected abstract void onSuccess(String code,String message,T data);

    protected void onError(Response<Result<T>> response, Throwable t) {
        if(isNeedToast()&&(response==null||response.code()!=401)){
            ToastUtils.showShort("网络异常");
        }
    }
}
