package com.gems.one_plan.common

import androidx.lifecycle.ViewModel
import com.gems.one_plan.database.AppDatabase
import com.gems.one_plan.database.Plans
import kotlinx.coroutines.flow.Flow

class MyViewModel(db: AppDatabase) : ViewModel() {
    private val planDao = db.planDao()
    val plans: Flow<List<Plans>> = planDao.getAll()

    fun updatePlan(plan: Plans) {
        planDao.update(plan)
    }

    fun deletePlan(plan: Plans) {
        planDao.delete(plan)
    }

    fun addPlan(plan: Plans) {
        planDao.insert(plan)
    }

    fun getPlanById(id: Int): Flow<Plans> {
        return planDao.getPlanById(id)
    }
}
