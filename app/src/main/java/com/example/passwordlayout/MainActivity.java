package com.example.passwordlayout;


import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

    private SystemConvert mSystemConvert;
    private SystemPassword mSystemPassword;

    private TextView mTextViewResult;
    private TextView mTextViewSymbols;
    private TextView mTextViewSafety;

    private EditText mTextEditSource;
    private EditText mTextEditPasswordLength;

    private View mButtonCopy;
    private ImageView mImageViewLock;

    private Button mButtonGeneratePassword;

    private CheckBox mCheckBoxUppercase;
    private CheckBox mCheckBoxNumber;
    private CheckBox mCheckBoxSpecial;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSystemConvert = new SystemConvert(
                getResources().getString(R.string.dict_rus),
                getResources().getString(R.string.dict_eng_spec)
        );

        mSystemPassword = new SystemPassword(
                getResources().getString(R.string.dict_rus),
                getResources().getString(R.string.dict_num),
                getResources().getString(R.string.dict_spec)
        );

        mTextViewResult = findViewById(R.id.textView_result);
        mTextViewSymbols = findViewById(R.id.textView_symbols);
        mTextViewSafety = findViewById(R.id.textView_textSafetyPassword);

        mTextEditSource = findViewById(R.id.textEdit_source);
        mTextEditPasswordLength = findViewById(R.id.editText_passwordLength);

        mImageViewLock = findViewById(R.id.ImageView_lock);

        mButtonGeneratePassword = findViewById(R.id.button_generatePassword);
        mButtonCopy = findViewById(R.id.imageButton_copy);

        mCheckBoxUppercase = findViewById(R.id.checkBox_isUppercase);
        mCheckBoxNumber = findViewById(R.id.checkBox_isNumber);
        mCheckBoxSpecial = findViewById(R.id.checkBox_isSpecial);


        mButtonCopy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mTextViewResult.getText().length() != 0)
                {
                    ClipboardManager manager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                    manager.setPrimaryClip(ClipData.newPlainText(
                            MainActivity.this.getString(R.string.MainActivity_clipboard_manager_title),
                            mTextViewResult.getText()
                    ));
                    Toast.makeText(MainActivity.this, R.string.MainActivity_mButtonCopy_toast_text_message_copied, Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(MainActivity.this, R.string.MainActivity_mButtonCopy_toast_text_message_error, Toast.LENGTH_SHORT).show();
                }

            }
           }
        );

        mTextEditSource.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                mTextViewResult.setText(mSystemConvert.convert(s));
                mTextViewSymbols.setText(getResources().getQuantityString(R.plurals.plurals_symbols, mTextViewResult.getText().length(), mTextViewResult.getText().length()));
                mImageViewLock.setImageLevel(mSystemPassword.getValueSafety(mTextViewResult.getText()) * 1000);
                mTextViewSafety.setText(getResources().getStringArray(R.array.textSafetyPassword)[mSystemPassword.getValueSafety(s)]);

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mButtonGeneratePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mSystemPassword.setUppercase(mCheckBoxUppercase.isChecked());
                mSystemPassword.setNumbers(mCheckBoxNumber.isChecked());
                mSystemPassword.setSpecial(mCheckBoxSpecial.isChecked());

                mTextEditSource.setText(mSystemPassword.getGeneratePassword(Byte.valueOf(mTextEditPasswordLength.getText().toString())));

            }
        });


    }
}