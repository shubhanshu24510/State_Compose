package com.shubhans.stateincompose.ViewModel

import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel
import com.shubhans.stateincompose.WellnessTask
import com.shubhans.stateincompose.getWellnessTasks

class WellnessViewModel:ViewModel() {
    private  val _tasks  = getWellnessTasks().toMutableStateList()
    val tasks :List<WellnessTask>
        get() = _tasks

    fun remove(item:WellnessTask){
        _tasks.remove(item) }

    fun changeTaskChecked(item: WellnessTask,checked:Boolean){
        tasks.find { it.id ==item.id }?.let { task ->
            task.checked =checked
        }

    }

}