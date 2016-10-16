package com.maninder.skylarktest;

/**
 * Created by Maninder on 12/10/16.
 */

public interface BaseView<T extends BasePresenter> {

    void setPresenter(T presenter);
}
