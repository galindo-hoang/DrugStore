package com.example.drugstore.presentation.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.drugstore.R
import com.example.drugstore.data.models.Article
import com.example.drugstore.databinding.FragmentNewsTopicBinding
import com.example.drugstore.presentation.adapter.ButtonTopicAdapter
import com.example.drugstore.presentation.adapter.NewsAdapter
import com.example.drugstore.utils.Constants
import com.example.drugstore.utils.Status

class NewsTopicFragment : Fragment() {
    private val newsVM: NewsVM by activityViewModels()
    private lateinit var binding: FragmentNewsTopicBinding
    private val topicButtonAdapter = ButtonTopicAdapter()
    private val newsAdapter = NewsAdapter()
    private var topic:String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentNewsTopicBinding.inflate(inflater,container,false)

        setupNewsAdapter()
        setupNews()
        setupButtonAdapter()

        return binding.root
    }

    private fun setupNews() {
        newsVM.getNotesByTopicFromApi(topic).observe(viewLifecycleOwner){ it ->
            it.let { resource ->
                when(resource.status){
                    Status.SUCCESS -> {
                        resource.data.let { itData ->
                            itData?.let { it1 -> newsAdapter.setList(it1.articles) }
                        }
                    }
                    Status.ERROR -> {
                        Toast.makeText(context,"Cant fetch data",Toast.LENGTH_SHORT).show()
                    }
                    Status.LOADING -> {
                        Toast.makeText(context,"Loading data",Toast.LENGTH_SHORT).show()
                    }
                }

            }
        }
    }

    private fun setupNewsAdapter(){
        binding.rvNews.adapter = newsAdapter
        newsAdapter.onItemClick = {article -> setUpTransactionFragment(article)}
        binding.rvNews.layoutManager = LinearLayoutManager(context)
    }

    private fun setUpTransactionFragment(article: Article) {
        val fragmentTransaction = parentFragmentManager.beginTransaction()
        val bundle = Bundle()
        bundle.putParcelable(Constants.OBJECT_ARTICLE,article)
        val fragment = NewsFragment()
        fragment.arguments = bundle
        fragmentTransaction.replace(R.id.fragmentBottomNav,fragment)
        fragmentTransaction.addToBackStack("News")
        fragmentTransaction.commit()
    }

    private fun setupButtonAdapter() {
        binding.rvTopics.adapter = topicButtonAdapter
        binding.rvTopics.layoutManager = LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false)
        topicButtonAdapter.onItemClick = { topicNews ->
            topic = topicNews.topic
            setupNews()
        }
        newsVM.getListTopics().observe(viewLifecycleOwner){
            topicButtonAdapter.setList(it)
        }
    }
}