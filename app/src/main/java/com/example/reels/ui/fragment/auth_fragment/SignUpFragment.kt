package com.example.reels.ui.fragment.auth_fragment

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.reels.R
import com.example.reels.databinding.FragmentSignUpBinding
import com.example.reels.model.RegisterUser
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.livinglifetechway.quickpermissions_kotlin.runWithPermissions
import java.io.ByteArrayOutputStream

class SignUpFragment : Fragment() {

    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase
    private lateinit var storage: FirebaseStorage
    private lateinit var binding: FragmentSignUpBinding
    private var uri: Uri? = null
    private lateinit var imageUri: String


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_sign_up, container, false)
        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()
        storage = FirebaseStorage.getInstance()
        addListerner()
        return binding.root
    }

    private fun addListerner() {
        binding.apply {
            btnSubmit.setOnClickListener {
                if (validation()) {
                    progressBar.visibility = View.VISIBLE
                    registerUser()
                }
            }
            ivProfile.setOnClickListener {
                runWithPermissions(
                    Manifest.permission.CAMERA,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.READ_EXTERNAL_STORAGE
                ) {
                    selectImage()
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == 1) {
                val photo = data?.extras?.get("data") as Bitmap
                uri = getImageUri(requireContext(), data?.extras?.get("data") as Bitmap)
                binding.ivProfile.setImageBitmap(photo)
            } else if (requestCode == 2) {
                val SelectedImage = data?.data as Uri
                uri = data.data as Uri
                binding.ivProfile.setImageURI(SelectedImage)
            }
        }

    }

    private fun selectImage() {
        val options = arrayOf<CharSequence>("Take Photo", "Choose from Gallery", "Cancel")
        val builder: AlertDialog.Builder = AlertDialog.Builder(context)
        builder.setTitle("Add Photo!")
        builder.setItems(options, object : DialogInterface.OnClickListener {
            override fun onClick(dialog: DialogInterface, item: Int) {
                if (options[item] == "Take Photo") {
                    val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                    startActivityForResult(intent, 1)
                } else if (options[item] == "Choose from Gallery") {
                    val intent =
                        Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                    startActivityForResult(intent, 2)
                } else if (options[item] == "Cancel") {
                    dialog.dismiss()
                }
            }
        })
        builder.show()
    }

    private fun getImageUri(inContext: Context, inImage: Bitmap): Uri? {
        val bytes = ByteArrayOutputStream()
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
        val path = MediaStore.Images.Media.insertImage(
            inContext.contentResolver,
            inImage,
            "Title",
            null
        )
        return Uri.parse(path)
    }


    private fun validation(): Boolean {

        if (binding.etEmail.text.toString().isEmpty()) {
            return false
        } else if (binding.etPassword.text.toString().isEmpty()) {
            return false
        }
        return true
    }

    private fun registerUser() {
        auth.createUserWithEmailAndPassword(
            binding.etEmail.text.toString(),
            binding.etPassword.text.toString()
        ).addOnCompleteListener(requireActivity(),
            OnCompleteListener { task ->
                if (task.isSuccessful) {
                    val reference: DatabaseReference =
                        database.reference.child("user").child(auth.uid!!)
                    val storeReference: StorageReference =
                        storage.reference.child("upload").child(auth.uid!!)

                    if (uri != null) {
                        storeReference.putFile(uri!!).addOnCompleteListener(OnCompleteListener {
                            if (it.isSuccessful) {
                                storeReference.downloadUrl.addOnSuccessListener {
                                    imageUri = it.toString()
                                    val user = RegisterUser(
                                        auth.uid!!,
                                        binding.etEmail.text.toString(),
                                        imageUri
                                    )
                                    reference.setValue(user).addOnCompleteListener {
                                        if (it.isSuccessful) {
                                            binding.progressBar.visibility = View.GONE
                                            findNavController().navigate(R.id.loginFragment)
                                            Toast.makeText(
                                                requireContext(),
                                                "SuccessFully Register",
                                                Toast.LENGTH_SHORT
                                            )
                                                .show()
                                        } else {
                                            Toast.makeText(
                                                requireContext(),
                                                "Register Fail!!!",
                                                Toast.LENGTH_SHORT
                                            )
                                                .show()
                                        }
                                    }
                                }
                            }
                        })
                    } else {
                        imageUri =
                            "https://firebasestorage.googleapis.com/v0/b/reels-65fe6.appspot.com/o/profile_user.png?alt=media&token=981d3fc7-1a5c-4b47-a3e8-2b22d4a67aa1"
                        val user = RegisterUser(
                            auth.uid!!,
                            binding.etEmail.text.toString(),
                            imageUri
                        )
                        reference.setValue(user).addOnCompleteListener {
                            if (it.isSuccessful) {
                                binding.progressBar.visibility = View.GONE
                                findNavController().navigate(R.id.loginFragment)
                                Toast.makeText(
                                    requireContext(),
                                    "SuccessFully Register",
                                    Toast.LENGTH_SHORT
                                )
                                    .show()
                            } else {
                                Toast.makeText(
                                    requireContext(),
                                    "Register Fail!!!",
                                    Toast.LENGTH_SHORT
                                )
                                    .show()
                            }
                        }
                    }
                } else {
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(requireContext(), task.exception.toString(), Toast.LENGTH_SHORT)
                        .show()
                }
            })
    }

}