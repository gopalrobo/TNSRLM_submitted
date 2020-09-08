package cst.smart.tnsrlm;

import android.content.Context;
import android.content.DialogInterface;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.app.AlertDialog;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;


public class CustomFontEditText extends android.support.v7.widget.AppCompatEditText {

    public static final String ANDROID_SCHEMA = "http://schemas.android.com/apk/res/android";
    private String result;
    String tittle = "";

    private String TAG_DATE_PICKER_DIALOG = "3000";

    public CustomFontEditText(Context context, AttributeSet attrs) {
        super(context, attrs);

        applyCustomFont(context, attrs);
    }

    public CustomFontEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        applyCustomFont(context, attrs);
    }

    private void applyCustomFont(final Context context, AttributeSet attrs) {
        final TypedArray attributeArray = context.obtainStyledAttributes(
                attrs,
                R.styleable.CustomFontTextView);

        String fontName = attributeArray.getString(R.styleable.CustomFontTextView_typeface);

        final String category = attributeArray.getString(R.styleable.CustomFontTextView_category);
        final String choices = attributeArray.getString(R.styleable.CustomFontTextView_choices);
        tittle = attributeArray.getString(R.styleable.CustomFontTextView_tittle);
        if (getHint() != null && getHint().length() > 0) {
            tittle = getHint().toString();
        }

        if (category != null && (category.equals(getResources().getString(R.string.selectable)) || category.equals(getResources().getString(R.string.multiSselectable)))) {
            setOnFocusChangeListener(new OnFocusChangeListener() {
                @Override
                public void onFocusChange(View view, boolean b) {
                    if (b) {
                        if (view instanceof TextView) {
                            final TextView textView = (TextView) view;
                            if (category != null && category.equals(getResources().getString(R.string.selectable))) {
                                InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                                AlertDialog.Builder builderSingle = new AlertDialog.Builder(context);
                                //  builderSingle.setTitle(getResources().getString(R.string.select) + " " + tittle + ":-");


                                TextView myMsg = new TextView(context);
                                myMsg.setText(getResources().getString(R.string.select) + "\n" + tittle + ":-");
                                myMsg.setGravity(Gravity.CENTER_HORIZONTAL);
                                myMsg.setTextSize(13);
                                myMsg.setTextColor(Color.BLACK);
                                //set custom title
                                builderSingle.setCustomTitle(myMsg);


                                String[] choicesStrings = choices.split(",");
                                final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1);
                                for (int i = 0; i < choicesStrings.length; i++) {
                                    arrayAdapter.add(choicesStrings[i]);
                                }
                                builderSingle.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                        String strName = arrayAdapter.getItem(which);
                                        if (strName.equalsIgnoreCase("Others")) {
                                            othersModule(tittle, context, textView);
                                        } else {
                                            setText(strName);
                                        }
                                    }
                                });
                                builderSingle.show();
                            } else if (category != null && category.equals(getResources().getString(R.string.multiSselectable))) {
                                multiSelectionModule(context, getResources().getString(R.string.select) + "\n" + tittle + ":-", choices.split(","));
                            }
                        }
                    }
                }
            });


            setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (view instanceof TextView) {
                        final TextView textView = (TextView) view;
                        if (category != null && category.equals(getResources().getString(R.string.selectable))) {
                            InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                            AlertDialog.Builder builderSingle = new AlertDialog.Builder(context);
                            TextView myMsg = new TextView(context);
                            myMsg.setText(getResources().getString(R.string.select) + "\n" + tittle + ":-");
                            myMsg.setGravity(Gravity.CENTER_HORIZONTAL);
                            myMsg.setTextSize(13);
                            myMsg.setTextColor(Color.BLACK);
                            //set custom title
                            builderSingle.setCustomTitle(myMsg);
                            String[] choicesStrings = choices.split(",");
                            final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1);
                            for (int i = 0; i < choicesStrings.length; i++) {
                                arrayAdapter.add(choicesStrings[i]);
                            }
                            builderSingle.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                    String strName = arrayAdapter.getItem(which);
                                    if (strName.equalsIgnoreCase("Others")) {
                                        othersModule(tittle, context, textView);
                                    } else {
                                        setText(strName);
                                    }
                                }
                            });
                            builderSingle.show();
                        } else if (category != null && category.equals(getResources().getString(R.string.multiSselectable))) {
                            multiSelectionModule(context, getResources().getString(R.string.select) + "\n" + tittle + ":-", choices.split(","));
                        }
                    }
                }
            });
        }
        if (fontName != null) {
            // Typeface customFont = selectTypeface(context, fontName);
            //  setTypeface(customFont);
        }


        attributeArray.recycle();
    }

    private Typeface selectTypeface(Context context, String fontName) {
        return Typeface.createFromAsset(context.getAssets(), "fonts/" + fontName + ".ttf");
    }

    public String othersModule(String tittle, final Context context, final TextView textView) {
        result = "";
        LayoutInflater li = LayoutInflater.from(context);
        View dialogView = li.inflate(R.layout.dialog_activity, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                context);
        alertDialogBuilder.setTitle(tittle);
        alertDialogBuilder.setView(dialogView);
        final EditText userInput = (EditText) dialogView
                .findViewById(R.id.et_input);
        userInput.setHint(getResources().getString(R.string.enter) + " " + tittle);
        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("ok",
                        new DialogInterface.OnClickListener() {


                            public void onClick(DialogInterface dialog,

                                                int id) {
                                if (userInput.getText().toString().length() > 0) {
                                    result = userInput.getText().toString();
                                    textView.setText(result);
                                }
                            }
                        })
                .setNegativeButton("cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int id) {
                                if (userInput.getText().toString().length() > 0) {
                                    dialog.cancel();
                                }
                            }
                        });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
        return result;
    }


    private void multiSelectionModule(final Context context, String title, final String[] items) {
        // final CharSequence[] items = items;
        // arraylist to keep the selected items
        final ArrayList seletedItems = new ArrayList();
        boolean[] checkedItems = new boolean[items.length];

        if (getText().toString() != null && getText().toString().length() > 0) {
            String[] strings = getText().toString().split(",");
            for (int j = 0; j < strings.length; j++) {
                for (int i = 0; i < items.length; i++) {
                    if (items[i].equals(strings[j])) {
                        checkedItems[i] = true;
                        seletedItems.add(items[i]);
                        break;
                    }
                }
            }
        }


        TextView myMsg = new TextView(context);
        myMsg.setText(title);
        myMsg.setGravity(Gravity.CENTER_HORIZONTAL);
        myMsg.setTextSize(13);
        myMsg.setTextColor(Color.BLACK);


        AlertDialog dialog = new AlertDialog.Builder(context)
                .setCustomTitle(myMsg)
                .setMultiChoiceItems(items, checkedItems, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int indexSelected, boolean isChecked) {
                        if (isChecked) {
                            // If the user checked the item, add it to the selected items
                            seletedItems.add(items[indexSelected]);
                        } else if (seletedItems.contains(items[indexSelected])) {
                            // Else, if the item is already in the array, remove it
                            seletedItems.remove(items[Integer.valueOf(indexSelected)]);
                        }
                    }
                }).setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        //  Your code when user clicked on OK
                        //  You can write the code  to save the selected item here
                        StringBuffer stringBuffer = new StringBuffer();

                        for (int i = 0; i < seletedItems.size(); i++) {
                            stringBuffer.append(seletedItems.get(i));
                            if (i != seletedItems.size() - 1) {
                                stringBuffer.append(",");
                            }
                            setText(stringBuffer.toString());
                        }
                    }
                }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        //  Your code when user clicked on Cancel
                    }
                }).create();
        dialog.show();
    }


}