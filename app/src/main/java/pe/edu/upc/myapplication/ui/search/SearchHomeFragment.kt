package pe.edu.upc.myapplication.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import pe.edu.upc.myapplication.databinding.FragmentSearchHomeBinding

class SearchHomeFragment : Fragment() {

    private var _binding: FragmentSearchHomeBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSearchHomeBinding.inflate(inflater,container,false)

        binding.searchButton.setOnClickListener {
            val action = SearchHomeFragmentDirections.actionSearchHomeFragment2ToSearchActivity()
            NavHostFragment.findNavController(this).navigate(action)
        }

        return binding.root
    }

}