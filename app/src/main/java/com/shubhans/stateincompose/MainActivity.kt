package com.shubhans.stateincompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.shubhans.stateincompose.ViewModel.WellnessViewModel
import com.shubhans.stateincompose.ui.theme.StateInComposeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            StateInComposeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    WaterScreen(modifier = Modifier)
                }
            }
        }
    }
}
@Composable
fun WaterScreen(modifier: Modifier = Modifier,
                wellnessViewModel: WellnessViewModel = viewModel()) {
    Column(modifier = Modifier) {
        WaterCounter(modifier)

        WellnessTasksList(list =wellnessViewModel.tasks,
            onCheckedTask ={ task, checked ->
                wellnessViewModel.changeTaskChecked(task,checked) },
                    onCloseTask = {task -> wellnessViewModel.remove(task)})
    }
}

@Composable
fun WaterCounter(modifier: Modifier) {
    Column(modifier = Modifier.padding(16.dp)) {
        var count by remember { mutableStateOf(0) }
        if (count > 0) {
            Text(text = "you have count $count Drink",
                modifier = Modifier.padding(10.dp))
        }
            Button(onClick = { count++ },
                enabled = count < 13) {
                Text(text = "Add One") }
        }
    }


@Composable
fun WellnessTask(taskName: String, onClose: () -> Unit){
    var checkedState by rememberSaveable { mutableStateOf(false) }
    WellnessTaskItem(
        taskName = taskName,
        checked = checkedState,
        onCheckedCheked ={newValue -> checkedState =newValue} ,
        onClose = onClose,
        modifier =Modifier
    )
}

@Composable
fun WellnessTaskItem(
    taskName:String,
    checked:Boolean,
    onCheckedCheked:(Boolean) ->Unit,
    onClose:() ->Unit,
    modifier: Modifier) {
    Row(horizontalArrangement = Arrangement.Center) {
        Text(text = taskName,
            modifier
                .weight(1f)
                .padding(start = 10.dp))
        Checkbox(checked = checked, onCheckedChange =onCheckedCheked )

        IconButton(onClick = onClose) {
            Icon(imageVector = Icons.Default.Close, contentDescription = null)
        }
    }
}
class WellnessTask(
    val id:Int,
    val label:String,
    initialChecked:Boolean =false){
    var checked by mutableStateOf(initialChecked)
}
 fun getWellnessTasks() = List(50){i -> WellnessTask(i,"# Task $i")}

@Composable
fun WellnessTasksList(
    list: List<WellnessTask>,
    onCloseTask: (WellnessTask) -> Unit,
    modifier: Modifier = Modifier,
    onCheckedTask: (WellnessTask,Boolean) ->Unit
){
    LazyColumn(modifier = Modifier.padding(vertical = 100.dp)){

        items(
            items =list,
            key = {task -> task.id}){task ->
          WellnessTask(taskName = task.label, onClose = {onCloseTask(task)})
        }
    }
    
}






