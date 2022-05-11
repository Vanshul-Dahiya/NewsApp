package com.example.newsapp

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

// adapter ko data chaiye , constructor k through pass krege
class NewsListAdapter( private val listner: NewsItemClicked ) : RecyclerView.Adapter<NewsListAdapter.Newsviewholder>() {

    //    ab item m news ka data ayga toh vo usi type ka array bnega
    val items: ArrayList<Newsdata> = ArrayList()

    inner class Newsviewholder(itemview: View) : RecyclerView.ViewHolder(itemview) {
//        holds all view items
        val titileView: TextView = itemview.findViewById(R.id.title)
        val author : TextView = itemview.findViewById(R.id.author)
        val image : ImageView = itemview.findViewById(R.id.NewsImage)
    }

    //    ye tb call hoga viewholder create ho rha hoga
//     jitne no of views , utni bar call hoga
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Newsviewholder {

//    takes xml code of item view from parent context and inflate  it to view format

        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_view, parent, false)
        val viewHolder = Newsviewholder(view)
        view.setOnClickListener {
//          we'll get adapter's position , items k array pr position nikal  aaygi
//          ab item main activity k ps gya hoga , vha b context dena pdega
            listner.onItemClicked(items[viewHolder.adapterPosition])
        }

        return viewHolder
    }

    //    holder k ander data ko bind krega ye
    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: Newsviewholder, position: Int) {
//    position is given of current item and then bind it
        val currentItem = items[position]
        holder.titileView.text = currentItem.title

//      setting author to anonymous if there is no author i.e null
        if (currentItem.author == "null"){
            holder.author.text = "Anonymous"
        }else{
            holder.author.text = currentItem.author
        }

        Glide.with(holder.itemView.context).load(currentItem.ImageUrl).into(holder.image)
    }

    //  called only once , tells no of item
    override fun getItemCount(): Int {
        return items.size
    }

//    adapter ko directly ni de skte data , make a function
//    so that adapter updates the item
@SuppressLint("NotifyDataSetChanged")
fun  updatenews(updatedNews : ArrayList<Newsdata>){
//    sbse phle toh empty kro items ko , fr update kro

        items.clear()
        items.addAll(updatedNews)
//    ab tk ye array list of news h , abi update na ho
//     inhne btana pdega ki ab update krlo

//    this funciton makes those 3 overrided function call again -> update news
    notifyDataSetChanged()

//    first getItemCount then create view holder and at last bind

    }
    interface NewsItemClicked{
        fun onItemClicked(item : Newsdata)
    }
}



