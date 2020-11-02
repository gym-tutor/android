package com.gymandroid.ui.exercise

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.storage.FileDownloadTask
import com.google.firebase.storage.FirebaseStorage
import com.gymandroid.R
import com.gymandroid.ui.exercise.dummy.Recommendations
import com.gymandroid.ui.exercise.dummy.YogaPoseRepository
import com.gymandroid.utils.GlideApp
import kotlinx.android.synthetic.main.excercise_list.*
import kotlinx.android.synthetic.main.excercise_list_content.view.*
import kotlinx.android.synthetic.main.fragment_exercise.*
import java.io.File
import java.io.InputStream


class ExerciseSelectionFragment : Fragment() {
    private val TAG = "ExerciseSelectionFragment"
    private lateinit var viewmodel: ExerciseViewModel
    private var twoPane: Boolean = false
    private var yogaPoseRepository =  YogaPoseRepository()
    private var userRecom = Recommendations()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val yogaIS: InputStream = requireActivity()
                                    .assets
                                    .open("pose_info.json")

        yogaPoseRepository.loadJson(yogaIS)
    }



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_exercise, container, false)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (excercise_detail_container != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            twoPane = true
        }
        recom.visibility = View.INVISIBLE
        recom.text = userRecom.toString()
        excercise_list.adapter = SimpleItemRecyclerViewAdapter(
                requireActivity(), this, yogaPoseRepository.getAllYogaPoseInfoList(), twoPane)
    }

    class SimpleItemRecyclerViewAdapter(
        private val parentActivity: FragmentActivity,
        private val fragment: ExerciseSelectionFragment,
        private val values: List<YogaPoseRepository.YogaPoseInfo>,
        private val twoPane: Boolean
    ) : RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder>()
    {

        inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            //            val idView: TextView = view.id_text
            val contentView: TextView = view.content
            val imgView: ImageView = view.findViewById(R.id.image)
        }

        private val storage = FirebaseStorage.getInstance().getReference()

        private val onClickListener = View.OnClickListener { v ->
            val poseInfo = v.tag as YogaPoseRepository.YogaPoseInfo
            if (twoPane) {
                val fragment = ExcerciseDetailFragment().apply {
                    arguments = Bundle().apply {
                        putString(ExcerciseDetailFragment.ARG_ITEM_ID, poseInfo.name)
                    }
                }
                parentActivity.supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.excercise_detail_container, fragment)
                    .commit()
            } else {

                val progressbar = v.findViewById<ProgressBar>(R.id.download_progressbar)
                progressbar.isVisible = true
                val warning = v.findViewById<ImageView>(R.id.warning_icon)
                storage.child(poseInfo.name).listAll()
                    .addOnSuccessListener{
                        val items = it.items
                        val prefixes = it.prefixes
                        var counts = 0
                        items.forEachIndexed{idx, item->

                            val localFile = File(parentActivity.filesDir, item.path)
                            val parentDir = File(parentActivity.filesDir,poseInfo.name)

                            if (!(parentDir.exists() && parentDir.isDirectory)){
                                parentDir.mkdir()
                            }
                            if (! localFile.exists()){
                                localFile.createNewFile()
                                item.getFile(localFile).addOnSuccessListener {

                                    counts +=1
                                    progressbar.progress = counts/items.size * 100
                                    Log.w("onSuccess",counts.toString() + " downloaded")
                                    if (counts == items.size){
                                        goToExerciseActivity(v.context,poseInfo)
                                    }
                                    // Local temp file has been created

                                }.addOnFailureListener {
                                    // Handle any errors
                                    warning.isVisible = true
                                    progressbar.isVisible = false
                                    Log.w("onFailure", it.toString())
                                }
                            }
                            else{
                                counts +=1
                                if (counts == items.size){
                                    goToExerciseActivity(v.context,poseInfo)
                                }
                            }

                        }

                        prefixes.forEach {prefix->
                            Log.w("storage.child", "prefix " + prefix.path)
                        }
                    }.addOnFailureListener{
                        warning.isVisible = true
                        progressbar.isVisible = false
                        Log.e("storage.child",it.toString())
                    }



//                val localFile: File = File(item.yogaStepVideoNames)
//                riversRef.getFile(localFile)
//                    .addOnSuccessListener(OnSuccessListener<FileDownloadTask.TaskSnapshot?> {
//                        // Successfully downloaded data to local file
//                        // ...
//                    }).addOnFailureListener(OnFailureListener {
//                        // Handle failed download
//                        // ...
//                    })

//
            }
        }

        private fun goToExerciseActivity(context: Context,poseInfo:YogaPoseRepository.YogaPoseInfo){

            val intent = Intent(context, ExcerciseActivity::class.java).apply {
                putExtra(ExcerciseDetailFragment.ARG_ITEM_ID, poseInfo.name)
                putExtra("name", poseInfo.name)
                putExtra("content", poseInfo.content)
                putExtra("detail", poseInfo.detail)
                putExtra("descVideoFileName", poseInfo.descVideoFileName)
                putExtra("caution",poseInfo.caution)
                putExtra("descImgFileName", poseInfo.descImgFileName)
                putExtra("yogaStepVideoNames",
                    poseInfo.yogaStepVideoNames)
            }
            context.startActivity(intent)
        }





        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.excercise_list_content, parent, false)
            return ViewHolder(view)
        }

        override fun getItemCount() = values.size

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val item = values[position]
            Log.w("onBindviewHolder",position.toString() + item.descImgFileName)
//            holder.idView.text = item.name
            holder.contentView.text = item.content

//            val imageInputStream = parentActivity.assets
//                .open(item.name +'/' + item.descImgFileName)


            val gsReference = storage.child(item.descImgFileName)
            GlideApp.with(fragment)
                .load(gsReference)
                .fitCenter()
                .into(holder.imgView);

            with(holder.itemView) {
                tag = item
                setOnClickListener(onClickListener)
            }
        }


    }
}

