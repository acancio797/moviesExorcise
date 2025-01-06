package com.exorcise.movie.ui.person

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import androidx.compose.foundation.layout.Column
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel

class PersonalFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        return ComposeView(requireContext()).apply {
            setContent {
                PersonalFragmentUI(viewModel = hiltViewModel())
            }
        }
    }


    @Composable
    fun PersonalFragmentUI(viewModel: PersonalViewModel) {

        val username by viewModel.username.observeAsState("")

        MaterialTheme {
            Surface {
                Column {
                    Text(text = "Pantalla Personal")
                    Text(text = "Username: $username")

                    Button(onClick = { viewModel.setUsername("Jane Doe") }) {
                        Text("Cambiar Nombre")
                    }
                }
            }
        }
    }
}

