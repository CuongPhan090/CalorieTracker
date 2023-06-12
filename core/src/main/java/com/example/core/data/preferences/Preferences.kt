package com.example.core.data.preferences

import android.content.SharedPreferences
import com.example.core.domain.model.ActivityLevel
import com.example.core.domain.model.Gender
import com.example.core.domain.model.GoalType
import com.example.core.domain.model.UserInfo
import com.example.core.domain.preferences.Preferences
import javax.inject.Inject

class DefaultPreferences @Inject constructor(
    private val sharedPreferences: SharedPreferences
) : Preferences {
    override fun saveGender(gender: Gender) {
        sharedPreferences.edit().putString(
            Preferences.KEY_GENDER,
            gender.name
        ).apply()
    }

    override fun saveAge(age: Int) {
        sharedPreferences.edit().putInt(
            Preferences.KEY_AGE,
            age
        ).apply()
    }

    override fun saveWeight(weight: Float) {
        sharedPreferences.edit().putFloat(
            Preferences.KEY_WEIGHT,
            weight
        ).apply()
    }

    override fun saveHeight(height: Int) {
        sharedPreferences.edit().putInt(
            Preferences.KEY_HEIGHT,
            height
        ).apply()
    }

    override fun saveActivityLevel(activityLevel: ActivityLevel) {
        sharedPreferences.edit().putString(
            Preferences.KEY_ACTIVITY_LEVEL,
            activityLevel.name
        ).apply()
    }

    override fun saveGoalType(goalType: GoalType) {
        sharedPreferences.edit().putString(
            Preferences.KEY_GOAL_TYPE,
            goalType.name
        ).apply()
    }

    override fun saveCarbRatio(ratio: Float) {
        sharedPreferences.edit().putFloat(
            Preferences.KEY_CARB_RATIO,
            ratio
        ).apply()
    }

    override fun saveProteinRatio(ratio: Float) {
        sharedPreferences.edit().putFloat(
            Preferences.KEY_PROTEIN_RATIO,
            ratio
        ).apply()
    }


    override fun saveFatRatio(ratio: Float) {
        sharedPreferences.edit().putFloat(
            Preferences.KEY_FAT_RATIO,
            ratio
        ).apply()
    }

    override fun loadUserInfo(): UserInfo {
        sharedPreferences.run {
            return UserInfo(
                gender = Gender.fromString(getString(Preferences.KEY_GENDER,  null) ?: "male"),
                age = getInt(Preferences.KEY_AGE, -1),
                weight = getFloat(Preferences.KEY_WEIGHT, -1f),
                height = getInt(Preferences.KEY_HEIGHT, -1),
                activityLevel = ActivityLevel.fromString(getString(Preferences.KEY_ACTIVITY_LEVEL, "") ?: "medium"),
                goalType = GoalType.fromString(getString(Preferences.KEY_GOAL_TYPE, "") ?: "keep_weight"),
                carbRatio = getFloat(Preferences.KEY_CARB_RATIO, -1f),
                proteinRatio = getFloat(Preferences.KEY_PROTEIN_RATIO, -1f),
                fatRatio = getFloat(Preferences.KEY_FAT_RATIO, -1f),
            )
        }
    }

    override fun saveShouldShowOnboarding(shouldShow: Boolean) {
        sharedPreferences.edit().putBoolean(
            Preferences.KEY_SHOULD_SHOW_ONBOARDING,
            shouldShow
        ).apply()
    }

    override fun loadShouldShowOnboarding(): Boolean {
        return  sharedPreferences.getBoolean(
            Preferences.KEY_SHOULD_SHOW_ONBOARDING,
            true
        )
    }
}
