package com.yasser.memorizewords.ui.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.yasser.memorizewords.R;
import com.yasser.memorizewords.data.model.Category;
import com.yasser.memorizewords.ui.adapters.CatAdapter;
import com.yasser.memorizewords.ui.viewmodels.CateViewModel;
import com.yasser.memorizewords.ui.viewmodels.ViewModel;
import com.yasser.memorizewords.util.AlertBuilderClass;

public class CategoryListingActivity extends AppCompatActivity {


    private long backPressedTime;
    private String categoryToInsert;


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {


            case R.id.about:

             Intent intent = new Intent(this, About.class);
                startActivity(intent);
                overridePendingTransition( R.anim.slide_in_up, R.anim.slide_out_up );
                return true;



            default:
                return super.onOptionsItemSelected(item);

        }
    }

        @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_category, menu);
        return super.onCreateOptionsMenu(menu);
    }

    private CateViewModel cateViewModel;
    RecyclerView recyclerView;
    ViewModel wordViewModel;
     CatAdapter adapter;
     //Category category;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        recyclerView = findViewById(R.id.recyclerviewCat);

        adapter = new CatAdapter(new CatAdapter.CategoryDiff());
        cateViewModel = ViewModelProviders.of(this)
                .get(CateViewModel.class);
        wordViewModel = ViewModelProviders.
                of(this).get(ViewModel.class);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2
                , StaggeredGridLayoutManager.VERTICAL));
        cateViewModel.getAllCategories().
                observe(this, categories ->
        {
            adapter.submitList(categories);
            adapter.setOnItemClickListener(new CatAdapter.mSetOnClickListener() {
                @Override
                public void onClick(Category category) {


                    Intent intent = new Intent(CategoryListingActivity.this,
                            WordListingActivity.class);
                    intent.putExtra("category", category.getCategory());
                    startActivity(intent);

                }

                @Override
                public void onLongClick(Category category) {


                    if (!category.getCategory().isEmpty())
                    {
                        categoryToInsert = category.getCategory();
                    }


                }
            });
        });


        ExtendedFloatingActionButton fab = findViewById(R.id.fabCat);

        fab.setOnClickListener(view -> {
            AlertBuilderClass.
                    categoryManipulation(new Category(""),
                            cateViewModel,
                    this);

        });
    }



    @Override
    public boolean onContextItemSelected(MenuItem item) {

        adapter.getCategory(item.getGroupId());
        switch (item.getItemId()) {
            case 0:
                AlertBuilderClass.categoryManipulation(
                        adapter.getCategory(item.getGroupId()),
                        cateViewModel,this);
                return true;

            case 1:
                Category category = adapter.getCategory(item.getGroupId());
                int i = wordViewModel.countWordByCat(category.getCategory());

                    AlertDialog diaBox = AlertBuilderClass.
                            AskDeleteCategory(
                                    adapter.getCategory
                                            (item.getGroupId()),
                                    cateViewModel,this,i);
                diaBox.show();

                return true;
            default:
                return super.onContextItemSelected(item);

        }

    }

    @Override
    public void onBackPressed() {
        if (backPressedTime + 2000 > System.currentTimeMillis())
        {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.putExtra("EXIT", true);
            startActivity(intent);
        }
        else
        {
            Toast.makeText(this, "Press back again to finish", Toast.LENGTH_SHORT).show();
        }
        backPressedTime = System.currentTimeMillis();
    }
/* @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
         super.onOptionsItemSelected(item);
        switch (item.getItemId()) {
            case 1:


                categoryManipulation(category);
                break;

            case 2:


                break;
        }
        return true;
    }


    private AlertDialog AskOption(Category category)
    {
        AlertDialog myQuittingDialogBox = new AlertDialog.Builder(this)
                // set message, title, and icon
                .setTitle("Delete")
                .setMessage("Do you want to delete the selected category?")


                .setPositiveButton("Delete", (dialog, whichButton) -> {
                    //your deleting code

                    switch (cateViewModel.countCategory("General"))
                    {
                        case 0:
                            category.setId(category.getId());
                            category.setCategory("General");
                            cateViewModel.update(category);
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
    }*/
}