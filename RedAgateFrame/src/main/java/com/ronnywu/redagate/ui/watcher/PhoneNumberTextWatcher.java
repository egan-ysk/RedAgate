package com.ronnywu.redagate.ui.watcher;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

/**
 * 自动格式化手机号码,自动添加分隔符
 */
public class PhoneNumberTextWatcher implements TextWatcher {

    /**
     * 排除多次调用的可能.
     */
    private boolean isRun = false;

    /**
     * 输入框
     */
    private EditText editText;

    /**
     * 构造器
     *
     * @param editText 文本框
     */
    public PhoneNumberTextWatcher(EditText editText) {
        this.editText = editText;
    }


    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        if (isRun) {
            isRun = false;
            return;
        }
        isRun = true;
        String finalString = "";
        int index = 0;
        String telString = charSequence.toString().replace(" ", "");
        if ((index + 3) < telString.length()) {
            finalString += (telString.substring(index, index + 3) + " ");
            index += 3;
        }
        while ((index + 4) < telString.length()) {
            finalString += (telString.substring(index, index + 4) + " ");
            index += 4;
        }
        finalString += telString.substring(index, telString.length());
        editText.setText(finalString);
        // 此语句不可少，否则输入的光标会出现在最左边，不会随输入的值往右移动
        editText.setSelection(finalString.length());
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }
}