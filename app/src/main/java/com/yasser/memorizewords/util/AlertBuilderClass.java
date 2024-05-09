package com.yasser.memorizewords.util;

import android.app.AlertDialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.yasser.memorizewords.R;
import com.yasser.memorizewords.data.model.Category;
import com.yasser.memorizewords.ui.viewmodels.CateViewModel;
import com.yasser.memorizewords.ui.viewmodels.ViewModel;

public class AlertBuilderClass {


    public static AlertDialog AskDeleteCategory(Category category, CateViewModel cateViewModel, Context context, int i)
    {
        AlertDialog myQuittingDialogBox = new AlertDialog.Builder(context)
                // set message, title, and icon
                .setTitle("Delete")
                .setMessage("Do you want to delete the selected category?")


                .setPositiveButton("Delete", (dialog, whichButton) -> {
                    //your deleting code



                    switch (cateViewModel.countCategory("General"))
                    {
                        case 0:
                            if (i !=0)
                            {category.setId(category.getId());
                                category.setCategory("General");
                                cateViewModel.update(category);
                            }
                            else {cateViewModel.deleteCategory(category);}


                            break;
                        case 1:
                            Log.d("CategoryToDelete", category.getCategory());
                            cateViewModel.updateWordsByCategory
                                    (category.getCategory(),
                                            "General");
                            cateViewModel.deleteCategory(category);
                            break;

                    }



                    dialog.dismiss();
                })
                .setNegativeButton("cancel", (dialog, which) -> dialog.dismiss())
                .create();

        return myQuittingDialogBox;
    }

    public static AlertDialog AskDeleteAllWords(ViewModel wordViewModel, Context context, String categoryName)
    {
        AlertDialog myQuittingDialogBox = new AlertDialog.Builder(context)
                // set message, title, and icon
                .setTitle("Delete")
                .setMessage("Do you want to delete all words in the category ("+categoryName+ ")?")


                .setPositiveButton("Delete", (dialog, whichButton) -> {
                    //your deleting code

                    wordViewModel.deleteAllWords(categoryName);

                    dialog.dismiss();
                })
                .setNegativeButton("cancel", (dialog, which) -> dialog.dismiss())
                .create();

        return myQuittingDialogBox;
    }

    public static void categoryManipulation(Category categoryToHandle,
                                            CateViewModel cateViewModel,
                                            Context context)
    {
        LayoutInflater layoutInflaterAndroid = LayoutInflater.from(context);
        View mView = layoutInflaterAndroid.inflate(R.layout.input_dialog, null);
        AlertDialog.Builder alertDialogBuilderUserInput = new AlertDialog.Builder(context);
        alertDialogBuilderUserInput.setView(mView);

        final EditText userInputDialogEditText =mView.findViewById(R.id.userInputDialog);
        if (categoryToHandle !=null)
        {
            userInputDialogEditText.setText(categoryToHandle.getCategory());


        }

        alertDialogBuilderUserInput
                .setCancelable(false).setTitle("Insert Category:")
                .setPositiveButton("Save", (dialogBox, id) -> {

                    //m_Text = userInputDialogEditText.getText().toString().trim();

                    if (userInputDialogEditText.getText().toString().trim().isEmpty()) {
                        Toast.makeText(context, "No new category has been saved",
                                Toast.LENGTH_SHORT).show();
                    }

                    else if(cateViewModel.countCategory
                            (userInputDialogEditText.getText().toString().trim())==0)
                        {
                        Category category = new Category(userInputDialogEditText.getText().
                                toString().trim());

                        if (categoryToHandle.getCategory().isEmpty())
                        {


                            cateViewModel.insertCat(category);
                        }
                        else
                        {
                            category.setId(categoryToHandle.getId());

                            cateViewModel.update(category);
                                    /*cateViewModel.updateWordsByCategory(
                                            categoryToHandle.getCategory(),
                                            category.getCategory());*/




                        }
                    } else{
                        Toast.makeText(context, "This category already exists!",
                                Toast.LENGTH_SHORT).show();
                    }
                }).setNegativeButton("Cancel",
                (dialogBox, id) -> dialogBox.cancel());

        AlertDialog alertDialogAndroid = alertDialogBuilderUserInput.create();
        alertDialogAndroid.show();
    }
}
