package pl.softwarelytest.View

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.koin.android.ext.android.inject
import pl.softwarelytest.R
import pl.softwarelytest.ViewModels.MainViewModel


class MainFragment : Fragment() {
    private val mainViewModel: MainViewModel by inject()
    var catsListAdapter: CatsAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val catRecycleView = view.findViewById<RecyclerView>(R.id.catsRecycleView)

        catsListAdapter = CatsAdapter()
        catRecycleView.apply {
            adapter = catsListAdapter
            layoutManager = LinearLayoutManager(context)
        }

        mainViewModel.getCatModelList(requireContext()).observe(viewLifecycleOwner) {
            catsListAdapter!!.setList(it)
        }
    }

}