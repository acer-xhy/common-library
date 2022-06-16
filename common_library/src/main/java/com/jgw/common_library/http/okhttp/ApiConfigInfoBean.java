package com.jgw.common_library.http.okhttp;

import android.text.TextUtils;

import java.util.Objects;

public class ApiConfigInfoBean<T> {
    public ApiConfigInfoBean(Class<T> clazz, String baseUrl) {
        this.clazz = clazz;
        this.baseUrl = baseUrl;
    }

    public Class<T> clazz;
    public String baseUrl;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ApiConfigInfoBean<?> that = (ApiConfigInfoBean<?>) o;
        return Objects.equals(clazz, that.clazz) && TextUtils.equals(baseUrl, that.baseUrl);
    }

    @Override
    public int hashCode() {
        return Objects.hash(clazz, baseUrl);
    }
}
