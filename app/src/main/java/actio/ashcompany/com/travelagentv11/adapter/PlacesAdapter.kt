package actio.ashcompany.com.travelagentv11.adapter

import actio.ashcompany.com.travelagentv11.R
import actio.ashcompany.com.travelagentv11.model.PlacesPOJO
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.TextView
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.view.LayoutInflater
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions




/**
 * Created by ashwin-4529 on 31/12/17.
 */


class PlacesAdapter(private var arrayList: ArrayList<PlacesPOJO>, var context: Context) : RecyclerView.Adapter<PlacesAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent?.context)
                .inflate(R.layout.single_place_item, parent, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        Glide.with(context)
                .load(arrayList[position].img)
                .apply(RequestOptions()
                        .centerCrop())
                .into(holder?.imageView)
        holder?.imageView?.setOnClickListener({
            val i = Intent(Intent.ACTION_VIEW, Uri.parse(arrayList[position].info))
            context.startActivity(i)
        })
        holder?.textView?.text = arrayList[position].name
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view){
        val imageView = view.findViewById<View>(R.id.item_image_view) as ImageView
        val textView = view.findViewById<View>(R.id.item_text) as TextView
    }
}