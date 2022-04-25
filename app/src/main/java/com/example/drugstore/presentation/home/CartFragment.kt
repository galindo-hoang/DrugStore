package com.example.drugstore.presentation.home

//@AndroidEntryPoint
//class CartFragment : Fragment() {
//    private lateinit var binding: FragmentCartBinding
//    @Inject
//    lateinit var cartVM:CartVM
//    private var sum = 0
//
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View {
//        binding = FragmentCartBinding.inflate(inflater,container,false)
//
//        binding.tb.setNavigationOnClickListener {
//            parentFragmentManager.popBackStack()
//        }
//
//        val cartAdapter = CartAdapter()
//        cartVM.getCartProducts().observe(viewLifecycleOwner){
//            cartAdapter.setList(it)
//            sum = 0
//            it.forEach { i ->
//                sum += i.Quantity*i.Price
//            }
//            binding.tvRawPrice.text = DecimalFormat("##,###").format(sum)
//            binding.tvSumPrice.text = DecimalFormat("##,###").format(sum)
//        }
//
//        cartAdapter.onMinusClick = {cartProduct ->
//            cartVM.decreaseQuantityProduct(cartProduct.Quantity,cartProduct.ProID)
//        }
//
//        cartAdapter.onAddClick = {cartProduct ->
//            cartVM.increaseQuantityProduct(cartProduct.Quantity,cartProduct.ProID)
//        }
//
//        binding.rcViewCart.adapter = cartAdapter
//        binding.rcViewCart.layoutManager = LinearLayoutManager(context)
//        // Inflate the layout for this fragment
//
//
//        binding.btnContinue.setOnClickListener {
//            if(sum == 0) {
//                Toast.makeText(context,"Please chose product to continue to order",Toast.LENGTH_SHORT).show()
//            }else startActivity(Intent(context,OrderActivity::class.java))
//        }
//        return binding.root
//    }
//}