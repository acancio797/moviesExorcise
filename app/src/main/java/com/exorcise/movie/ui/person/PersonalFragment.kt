package com.exorcise.movie.ui.person

import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.hilt.navigation.compose.hiltViewModel


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

        PersonalFragmentHome(
            name = username,
            onClickChangeName = { viewModel.setUsername("Alejandro") })
    }

    @Preview
    @Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
    @Composable
    fun PersonalFragmentHome(
        name: String = "person",
        onClickChangeName: () -> Unit = {},
        modifier: Modifier = Modifier
    ) {

        MaterialTheme {
            Scaffold(
                modifier = modifier,
            ){ paddingValues ->
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    AvatarIcon()
                    Text(text = "Username: $name")

                    Button(onClick = onClickChangeName) {
                        Text("Cambiar Nombre")
                    }
                }

            }

        }
    }

    @Composable
    fun AvatarIcon() {
        val image = painterResource(id = android.R.drawable.star_on)

        Image(
            painter = image,
            contentDescription = "Avatar",
            modifier = Modifier
                .size(100.dp)
                .clip(CircleShape)
        )
    }
}

