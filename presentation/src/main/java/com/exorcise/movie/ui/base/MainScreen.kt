import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.fragment.app.FragmentActivity
import androidx.hilt.navigation.compose.hiltViewModel
import com.exorcise.movie.ui.base.BottomNavBar
import com.exorcise.movie.ui.base.MainViewModel

import com.exorcise.movie.ui.components.BottomNavItem
import com.exorcise.movie.ui.components.FragmentContainer


@Composable
fun MainScreen(
    mainViewModel: MainViewModel = hiltViewModel(),
) {
    val currentTab by mainViewModel.selectedTab.observeAsState(BottomNavItem.Personal)
    val activity = LocalContext.current as FragmentActivity
    val fragmentManager = activity.supportFragmentManager

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            BottomNavBar(currentRoute = currentTab.route, onItemSelected = { selectedItem ->
                mainViewModel.selectTab(selectedItem)
            })
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            content = {
                FragmentContainer(
                    fragmentManager = fragmentManager,
                    currentItem = currentTab,
                    modifier = Modifier.fillMaxSize()
                )
            }
        )
    }
}


