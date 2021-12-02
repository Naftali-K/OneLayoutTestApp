package com.nk.onelayouttestapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.ActionMode;
import androidx.core.view.MenuItemCompat;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.InputType;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.textfield.TextInputLayout;

public class MainActivity extends AppCompatActivity implements AccountSettingsDialog.ExampleDialogListener {

    Spinner publicSpinner;
    EditText passEt;
    TextView userNameFromDialogEditText, notificationBadge;

    private TextInputLayout mailInputText, passwordInputText;
    Button userSettingsBtn, regLoginBtn, openBottomDialogSheetBtn, contextMenuBtn, actionMenuBtn;

    BottomSheetDialog bottomSheetDialog;

    ActionMode actionMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().hide();

        //------------------------------------------------------------------------------------------
        //TextInputLayout mail
        mailInputText = findViewById(R.id.mail_input_text);
        passwordInputText = findViewById(R.id.password_text_input);
        regLoginBtn = findViewById(R.id.reg_login_btn);
        regLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validateEmail() | !validationPassword()){
                    Toast.makeText(getBaseContext(), "Register/Login", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getBaseContext(), "NOT Register/Login", Toast.LENGTH_SHORT).show();
                }
            }
        });
        //End TextInputLayout mail
        //------------------------------------------------------------------------------------------


        EditText textViewNormal = findViewById(R.id.text_view_normal);
        textViewNormal.getDrawableState();


        //------------------------------------------------------------------------------------------
        // Hide and show password custom made
        passEt = findViewById(R.id.pass_et);
        ImageView visible = findViewById(R.id.visible);
        TextView typesTextViews = findViewById(R.id.types_text_views);
        visible.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int rightNow = passEt.getInputType(); //129 - standard xml textPassword
                int variationPass = InputType.TYPE_TEXT_VARIATION_PASSWORD; //128
                int variationVisiblePass = InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD; // 144
                int classText = InputType.TYPE_CLASS_TEXT; // 1
                String message = "Input type: " + rightNow + "\nVariation Pass: " + variationPass + "\nVariation Visible Pass: " + variationVisiblePass + "\nClass text: " + classText;
                typesTextViews.setText(message);

//                Toast.makeText(getBaseContext(), "change visible", Toast.LENGTH_LONG).show();
                if(passEt.getInputType() == 129){
                    Toast.makeText(getBaseContext(), "show pass", Toast.LENGTH_SHORT).show();
                    passEt.setInputType(1);
                }else{
                    Toast.makeText(getBaseContext(), "hide pass", Toast.LENGTH_SHORT).show();
                    passEt.setInputType(129);
                }

            }
        });
        // End hide and show password custom made
        //------------------------------------------------------------------------------------------

        //test spinner
        publicSpinner = findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> arraySpinnerAdapter = ArrayAdapter.createFromResource(this, R.array.public_spinner, R.layout.spinner_text_view);
        publicSpinner.setAdapter(arraySpinnerAdapter);

        userNameFromDialogEditText = findViewById(R.id.user_name_from_dialog_edittext);

        // Dialog Fragment
        userSettingsBtn = findViewById(R.id.user_settings_btn);
        userSettingsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AccountSettingsDialog accountSettingsDialog = new AccountSettingsDialog();
                accountSettingsDialog.show(getSupportFragmentManager(), "");
            }
        });


        //------------------------------------------------------------------------------------------
        // Bottom Sheet Dialog

        openBottomDialogSheetBtn = findViewById(R.id.open_bottom_dialog_sheet_btn);

        bottomSheetDialog = new BottomSheetDialog(this, R.style.CustomBottomSheetDialogTheme);
        createBottomSheetDialog();

        openBottomDialogSheetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetDialog.show();
            }
        });

        // End bottom Sheet Dialog
        //------------------------------------------------------------------------------------------



        //------------------------------------------------------------------------------------------
        // Context Menu

        contextMenuBtn = findViewById(R.id.context_menu_btn);
        registerForContextMenu(contextMenuBtn);

        // End Context Menu
        //------------------------------------------------------------------------------------------



        //------------------------------------------------------------------------------------------
        // Action menu

        actionMenuBtn = findViewById(R.id.action_menu_btn);
        actionMenuBtn.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                if (actionMode != null){
                    return false;
                }

                actionMode = startSupportActionMode(myActionModeCallback);
                //color change in themes.xml - light and night

                return true;
            }
        });

        // End action menu
        //------------------------------------------------------------------------------------------
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        getMenuInflater().inflate(R.menu.context_menu, menu);

    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.option_1:
                Toast.makeText(getBaseContext(), "Option 1", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.option_2:
                Toast.makeText(getBaseContext(), "Option 2", Toast.LENGTH_SHORT).show();
                return true;
            default:
                Toast.makeText(getBaseContext(), "Something Wrong!", Toast.LENGTH_SHORT).show();
                return super.onContextItemSelected(item);
        }
    }


    //----------------------------------------------------------------------------------------------
    // Action Menu
    private ActionMode.Callback myActionModeCallback = new ActionMode.Callback() {
        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            mode.getMenuInflater().inflate(R.menu.action_menu, menu);
//            mode.setTitle("Choose you option");

            //not working
//            final MenuItem menuItem = menu.findItem(R.id.notification_btn);
//            View actionView = MenuItemCompat.getActionView(menuItem);
//            notificationBadge = actionView.findViewById(R.id.notification_badge);
//            notificationBadge.setText("10");
//
//            setupBadge();

            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {

            switch (item.getItemId()){
                case R.id.delete_btn:
                    Toast.makeText(getBaseContext(), "Delete item", Toast.LENGTH_SHORT).show();
//                    mode.setTitle("Delete");
                    mode.finish();
                    return true;
                case R.id.share_btn:
                    Toast.makeText(getBaseContext(), "Share item", Toast.LENGTH_SHORT).show();
                    mode.finish();
                    return true;
//                case R.id.close_btn:
//                    Toast.makeText(getBaseContext(), "Close menu", Toast.LENGTH_SHORT).show();
//                    mode.finish();
//                    return true;
                default:
                    return false;
            }
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {
            actionMode = null;
        }
    };

    private void setupBadge(){
        if (notificationBadge != null){
            notificationBadge.setVisibility(View.VISIBLE);
        }
    }
    // End action Menu
    //----------------------------------------------------------------------------------------------



    private void createBottomSheetDialog(){
        View view = getLayoutInflater().inflate(R.layout.bottom_dialog_sheet, null, false);
//            ((View) view.getParent()).setBackgroundColor(Color.TRANSPARENT);

        Button sendBtn = view.findViewById(R.id.send_btn);
        EditText inputEditText = view.findViewById(R.id.input_edit_text);

        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetDialog.dismiss();
                Toast.makeText(getBaseContext(), inputEditText.getText().toString(), Toast.LENGTH_SHORT).show();
            }
        });

//        bottomSheetDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT)); //Not working
        bottomSheetDialog.setContentView(view);
    }


    //----------------------------------------------------------------------------------------------
    //TextInputLayout validation
    private boolean validateEmail(){
        String emailInput = mailInputText.getEditText().getText().toString().trim();

        if(emailInput.isEmpty()){
            mailInputText.setError("Failed, can't be empty");
            return false;
        }else if(emailInput.length() > 20) {
            mailInputText.setError("Failed, too long");
            return false;
        }else{
            mailInputText.setError(null);
//            mailInputText.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validationPassword(){
        String passwordInput = passwordInputText.getEditText().getText().toString().trim();

        if(passwordInput.isEmpty()){
            passwordInputText.setError("Failed, can't be empty");
            return false;
        }else if(passwordInput.length() < 8) {
            passwordInputText.setError("Failed, too short password");
            return false;
        }else{
            passwordInputText.setError(null);
//            mailInputText.setErrorEnabled(false);
            return true;
        }
    }

    //End TextInputLayout validation
    //----------------------------------------------------------------------------------------------

    @Override
    public void applyText(String userName) {
        userNameFromDialogEditText.setText(userName);
    }
}