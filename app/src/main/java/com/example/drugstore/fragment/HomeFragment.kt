package com.example.drugstore.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.drugstore.R
import com.example.drugstore.adapter.CategoryAdapter
import com.example.drugstore.adapter.NewsAdapter
import com.example.drugstore.adapter.ProductAdapter
import com.example.drugstore.databinding.FragmentHomeBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment

        binding = FragmentHomeBinding.inflate(inflater,container,false)
        val a = listOf("a","a","a","a","a","a")
        binding.rvTopTrending.adapter = ProductAdapter(a)
        binding.rvTopTrending.layoutManager = LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false)


        binding.rvSupplement.adapter = ProductAdapter(a)
        binding.rvSupplement.layoutManager = LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false)


        binding.rvCategory.adapter = CategoryAdapter(a)
        binding.rvCategory.layoutManager = GridLayoutManager(context,2,GridLayoutManager.HORIZONTAL,false)



        binding.rvNews.adapter = NewsAdapter(a)
        binding.rvNews.layoutManager = LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false)

        binding.clHeaderCategory.setOnClickListener {
            setUpChangeFragment()
        }

        return binding.root
    }

    private fun setUpChangeFragment() {
        Log.e("---","aaa")
        val fragmentTransaction = childFragmentManager.beginTransaction()
        Log.e("---","bbb")
        fragmentTransaction.replace(R.id.fragmentBottomNav,CategoryFragment())
        fragmentTransaction.commit()
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment HomeFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HomeFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}