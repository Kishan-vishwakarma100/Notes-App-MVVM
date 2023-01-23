package com.example.notesappmvvm.ui.Fragments

import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.notesappmvvm.Model.Notes
import com.example.notesappmvvm.R
import com.example.notesappmvvm.ViewModel.NotesViewModel
import com.example.notesappmvvm.databinding.FragmentHomeBinding
import com.example.notesappmvvm.ui.Adapter.NotesAdapter

@Suppress("DEPRECATION")
class HomeFragment : Fragment() {
    lateinit var binding:FragmentHomeBinding
    val viewModel : NotesViewModel by viewModels()
    var oldMyNotes = arrayListOf<Notes>()
    lateinit var adapter : NotesAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding = FragmentHomeBinding.inflate(layoutInflater,container,false)
        setHasOptionsMenu(true)

        val staggeredGridLayoutManager =
            StaggeredGridLayoutManager(2,LinearLayoutManager.VERTICAL)
        binding.rcvAllNotes.layoutManager = staggeredGridLayoutManager

        //get all notes
        viewModel.getNotes().observe(viewLifecycleOwner,{notesList ->
            oldMyNotes = notesList as ArrayList<Notes>
            adapter = NotesAdapter(requireContext(),notesList)
            binding.rcvAllNotes.adapter = adapter
        })

        //filter high
        binding.filterHigh.setOnClickListener {
            viewModel.getHighNotes().observe(viewLifecycleOwner, { notesList ->
                oldMyNotes = notesList as ArrayList<Notes>
                adapter = NotesAdapter(requireContext(),notesList)
                binding.rcvAllNotes.adapter = adapter
            })
        }

        //all notes
        binding.allNotes.setOnClickListener {
            viewModel.getNotes().observe(viewLifecycleOwner,{notesList ->
                oldMyNotes = notesList as ArrayList<Notes>
                adapter = NotesAdapter(requireContext(),notesList)
                binding.rcvAllNotes.adapter = adapter
            })
        }

        //filter low
        binding.filterLow.setOnClickListener {
            viewModel.getLowNotes().observe(viewLifecycleOwner,{notesList ->
                oldMyNotes = notesList as ArrayList<Notes>
                adapter = NotesAdapter(requireContext(),notesList)
                binding.rcvAllNotes.adapter = adapter
            })
        }

        //filter medium
        binding.filterMedium.setOnClickListener {
            viewModel.getMediumNotes().observe(viewLifecycleOwner,{notesList ->
                oldMyNotes = notesList as ArrayList<Notes>
                adapter = NotesAdapter(requireContext(),notesList)
                binding.rcvAllNotes.adapter = adapter
            })
        }

        binding.btnAddNotes.setOnClickListener{
            Navigation.findNavController(it).navigate(R.id.action_homeFragment_to_createNotesFragment)
        }

        return binding.root
    }

    @Deprecated("Deprecated in Java")
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.search_menu,menu)

        val item= menu.findItem(R.id.app_bar_search)
        val searchView = item.actionView as SearchView
        searchView.queryHint = "Search Notes Here.."
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(p0: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                NotesFiltering(p0)
                return true
            }

        })

        return super.onCreateOptionsMenu(menu, inflater)
    }

    private fun NotesFiltering(p0:String?) {
        val newFilterList = arrayListOf<Notes>()
        for(i in oldMyNotes){
            if(i.title.contains(p0!!) || i.subTitle.contains(p0)){
                newFilterList.add(i)
            }
        }
        adapter.filtering(newFilterList)
    }

}