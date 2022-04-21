package com.example.drugstore.presentation.home

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.example.drugstore.R
import com.example.drugstore.data.models.Article
import com.example.drugstore.databinding.FragmentNewsBinding
import com.example.drugstore.utils.Constants
class NewsFragment : Fragment() {
    private lateinit var binding: FragmentNewsBinding
    private var article:Article? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if(arguments?.containsKey(Constants.OBJECT_ARTICLE) == true){
            article = arguments?.getParcelable(Constants.OBJECT_ARTICLE)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentNewsBinding.inflate(inflater,container,false)

        Glide.with(binding.root)
            .load(article!!.urlToImage)
            .placeholder(R.drawable.ic_launcher_foreground)
            .into(binding.ivNews)

        binding.tvAuthor.text = article!!.author
        binding.tvTitle.text = article!!.title
        binding.tvContent.text = article!!.content.split("[")[0]

        return binding.root
    }
}