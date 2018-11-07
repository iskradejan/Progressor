package com.progressor.progressor.service

class ExerciseCreator {
    fun createWeightExercise(sets: Int, reps: Int, weight: Int): LinkedHashMap<HashMap<Int, Int>, Int> {
        val newExercise = LinkedHashMap<HashMap<Int, Int>, Int>()
        val newExerciseSetsReps = HashMap<Int, Int>()
        newExerciseSetsReps.put(sets, reps)
        newExercise.put(newExerciseSetsReps, weight)

        return newExercise
    }

    fun createBodyExercise(sets: Int, reps: Int): LinkedHashMap<Int, Int> {
        val newExercise = LinkedHashMap<Int, Int>()
        newExercise.put(sets, reps)
        return newExercise
    }
}