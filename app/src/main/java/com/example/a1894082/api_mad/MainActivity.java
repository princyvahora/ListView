package com.example.a1894082.api_mad;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {

    ListView lsp;
    MyListAdapter adapt;
    ArrayList<Products> pro;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lsp = findViewById(R.id.lst_products);

        String ls = getResources().getString(R.string.link);

        pro =  new ArrayList<>();

        try {
            String mysts =  new Asycdata().execute(ls).get();

            System.out.println("This is from MainActivity:"+mysts);

            JSONObject mainobj =  new JSONObject(mysts);

            JSONArray proarray = mainobj.getJSONArray("products");

            for (int i=0;i<proarray.length();i++ )
            {
                JSONObject childobj = proarray.getJSONObject(i);

                String pname = childobj.getString("title");
                String pimg = childobj.getString("image");
                String desc = childobj.getString("description");
                String brand = childobj.getString("brand");
                long price = childobj.getLong("price");

                pro.add(new Products(pimg,pname,desc,brand,price));

                //System.out.println("Bag names : "+childobj.getString("title"));
            }

            System.out.println("ArrayList size: "+pro.size());


            adapt =  new MyListAdapter(MainActivity.this,pro);

            lsp.setAdapter(adapt);

            lsp.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Toast.makeText(MainActivity.this,pro.get(position).getPname(),Toast.LENGTH_LONG).show();

                    Bundle bundle =  new Bundle();
                    bundle.putParcelable("data",pro.get(position));


                    Intent i = new Intent(MainActivity.this,ProductsDesc.class);
                    i.putExtra("data",pro.get(position));
                    startActivity(i);
                }
            });



        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
