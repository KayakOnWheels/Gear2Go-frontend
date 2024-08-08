package com.gear2go_frontend.customInterface;

import com.gear2go_frontend.domain.User;

import java.util.List;

@FunctionalInterface
public interface TokenCallback<T> {
    void onTokenRetrieved(List<T> items);
}
