package com.example.passwordlayout;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.preference.PreferenceManager;

public class PasswordFragment extends Fragment {

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

    private boolean isAutomaticCopy;

    private ImageButton mImageButtonSettings;

    public static PasswordFragment newInstance() {
        return new PasswordFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_password, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData();
        setDataFromPreferences();
        initListeners();
    }

    private void initData() {

        mSystemConvert = new SystemConvert(
                getResources().getString(R.string.dict_rus),
                getResources().getString(R.string.dict_eng_spec)
        );

        mSystemPassword = new SystemPassword(
                getResources().getString(R.string.dict_rus),
                getResources().getString(R.string.dict_num),
                getResources().getString(R.string.dict_spec)
        );

    }

    private void initViews(View view) {
        mTextViewResult = view.findViewById(R.id.textView_result);
        mTextViewSymbols = view.findViewById(R.id.textView_symbols);
        mTextViewSafety = view.findViewById(R.id.textView_textSafetyPassword);

        mTextEditSource = view.findViewById(R.id.textEdit_source);
        mTextEditPasswordLength = view.findViewById(R.id.editText_passwordLength);

        mImageViewLock = view.findViewById(R.id.ImageView_lock);

        mButtonGeneratePassword = view.findViewById(R.id.button_generatePassword);
        mButtonCopy = view.findViewById(R.id.imageButton_copy);

        mCheckBoxUppercase = view.findViewById(R.id.checkBox_isUppercase);
        mCheckBoxNumber = view.findViewById(R.id.checkBox_isNumber);
        mCheckBoxSpecial = view.findViewById(R.id.checkBox_isSpecial);

        mImageButtonSettings = view.findViewById(R.id.imageButton_settings);
    }

    private void setDataFromPreferences(){

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(requireContext());
        Resources resources = requireContext().getResources();

        isAutomaticCopy = preferences.getBoolean(
                getString(R.string.pref_copy_key),
                resources.getBoolean(R.bool.pref_copy_default));

        mCheckBoxUppercase.setChecked(preferences.getBoolean(
                getString(R.string.pref_uppercase_key),
                resources.getBoolean(R.bool.pref_uppercase_default)));

        mCheckBoxNumber.setChecked(preferences.getBoolean(
                getString(R.string.pref_number_key),
                resources.getBoolean(R.bool.pref_number_default)));

        mCheckBoxSpecial.setChecked(preferences.getBoolean(
                getString(R.string.pref_special_key),
                resources.getBoolean(R.bool.pref_special_default)));

        mTextEditPasswordLength.setText(String.valueOf(preferences.getInt(
                getString(R.string.pref_length_key),
                resources.getInteger(R.integer.pref_length_default))));
    }

    private void initListeners() {
        mButtonCopy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mTextViewResult.getText().length() != 0) {
                    copyToBuffer(mTextViewResult.getText());
                    Toast.makeText(requireContext(), R.string.MainActivity_mButtonCopy_toast_text_message_copied, Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(requireContext(), R.string.MainActivity_mButtonCopy_toast_text_message_error, Toast.LENGTH_SHORT).show();
                }
            }
        });

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
                if (isAutomaticCopy){
                    copyToBuffer(mTextViewResult.getText());
                }
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

        mImageButtonSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentActivity activity = getActivity();
                if (activity instanceof SettingFragmentHolder){
                    ((SettingFragmentHolder)activity).showSetting();
                }
            }
        });

    }

    private void copyToBuffer(CharSequence text){
        ClipboardManager manager = (ClipboardManager) requireActivity().getSystemService(Context.CLIPBOARD_SERVICE);
        manager.setPrimaryClip(ClipData.newPlainText(
                requireActivity().getString(R.string.MainActivity_clipboard_manager_title), text));
    }


    public interface SettingFragmentHolder{
        void showSetting();
    }


}
