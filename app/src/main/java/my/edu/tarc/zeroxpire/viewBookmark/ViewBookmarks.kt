package my.edu.tarc.zeroxpire.viewBookmark

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.navigation.fragment.findNavController
import my.edu.tarc.zeroxpire.R

class ViewBookmarks : Fragment() {
    private var imageButtonBookmarksBack: ImageButton? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_view_bookmarks, container, false)

        imageButtonBookmarksBack = view.findViewById(R.id.imageButtonBookmarksBack)

        imageButtonBookmarksBack!!.setOnClickListener{
            findNavController().popBackStack()
        }
        return view
    }
}